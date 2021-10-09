package unsw.blackout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import unsw.blackout.FileTransferException.VirtualFileNotFoundException;
import unsw.blackout.FileTransferException.VirtualFileAlreadyExistsException;
import unsw.devices.DesktopDevice;
import unsw.devices.Device;
import unsw.devices.LaptopDevice;
import unsw.other.File;
import unsw.other.NetworkObject;
import unsw.devices.HandHeldDevice;
import unsw.response.models.EntityInfoResponse;
import unsw.satellites.RelaySatellite;
import unsw.satellites.Satellite;
import unsw.satellites.ShrinkingSatellite;
import unsw.satellites.StandardSatellite;
import unsw.utils.Angle;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.util.Arrays;
import java.util.Map;
import unsw.response.models.FileInfoResponse;
import java.util.HashMap;



public class BlackoutController {
    private Hashtable<String, Device> devices;
    private Hashtable<String, Satellite> satellites;
    
    // Hashtables to store devices/satellites in class
    // The Id strings are already assumed unique
    // so we can use them as the keys
    public BlackoutController() {
        devices = new Hashtable<String, Device>();
        satellites = new Hashtable<String, Satellite>();
    }

    // For testing purposes
    public Hashtable<String, Satellite> getSatellites() {
        return satellites;
    }
    
    
    public void createDevice(String deviceId, String type, Angle position) {
        // Depending on value of type, use different subclass constructors
        // and add to BlackoutController Hashtable
        switch(type) {
            case "HandheldDevice":
                Device newHandheld = new HandHeldDevice(deviceId, position);
                devices.put(newHandheld.objectId, newHandheld);
                break;
            case "LaptopDevice":
                Device newLaptop = new LaptopDevice(deviceId, position);
                devices.put(newLaptop.objectId, newLaptop);
                break;
            case "DesktopDevice":
                Device newDesktop = new DesktopDevice(deviceId, position);
                devices.put(newDesktop.objectId, newDesktop);
                break;
        }
    }

    public void removeDevice(String deviceId) {
        devices.remove(deviceId);
    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        // Depending on value of type, use different subclass constructors
        // and add to BlackoutController Hashtable
        switch (type) {
            case "StandardSatellite":
                Satellite newStandard = new StandardSatellite(satelliteId, position, height);
                satellites.put(newStandard.objectId, newStandard);
                break;
            case "ShrinkingSatellite":
                Satellite newShrinking = new ShrinkingSatellite(satelliteId, position, height);
                satellites.put(newShrinking.objectId, newShrinking);
                break;
            case "RelaySatellite":
                Satellite newRelay = new RelaySatellite(satelliteId, position, height);
                satellites.put(newRelay.objectId, newRelay);
                break;
        }
    }

    public void removeSatellite(String satelliteId) {
        satellites.remove(satelliteId);
    }

    public List<String> listDeviceIds() {
        // Convert Device Hashtable to appropriate variable type and return
        Set<String> deviceSet = devices.keySet();
        List<String> deviceList = new ArrayList<>(deviceSet);
        return deviceList;
    }

    public List<String> listSatelliteIds() {
        // Convert Satellite Hashtable to appropriate variable type and return
        Set<String> satelliteSet = satellites.keySet();
        List<String> satelliteList = new ArrayList<>(satelliteSet);
        return satelliteList;
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        // Use NetworkObject superclass method to add file to files field
        Device creatorDevice = devices.get(deviceId);
        creatorDevice.addFile(filename, content, content.length(), true, "Original");
    }

    public EntityInfoResponse getInfo(String id) {
        // Find the object by checking both Hashtables and return using class method
        NetworkObject findObj = devices.get(id);
        if (findObj == null) {
            findObj = satellites.get(id);
        }
        return findObj.getInfo();
    }

    public void simulate() {
        //Order of simulation
        //1. All satellites make travel movements
        //2. File transfer take place

        // Simulate satellites
        List<String> satList = listSatelliteIds();
        for (String satId : satList) {
            Satellite sat = satellites.get(satId);
            List<String> conns = communicableEntitiesInRange(satId);
            // Satellites make orbital movements
            sat.travel();

            // Satellites check their facilitated file transfers (both up/downstream)
            // Check all files and monitor those that are incomplete
            Helper.satDownloads(sat, conns, devices, satellites);
            Helper.satUploads(sat, conns, devices, satellites);
            sat.manageStorage();
        }

        // Simulate devices
        List<String> devList = listDeviceIds();
        for (String devId : devList) {
            Device dev = devices.get(devId);
            List<String> conns = communicableEntitiesInRange(devId);

            // Devices must delete unfinished files that are not transferring themselves
            Helper.deviceRemoveUnfinished(dev, conns, devices);
        }


    }

    /**
     * Simulate for the specified number of minutes.
     * You shouldn't need to modify this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    public List<String> communicableEntitiesInRange(String id) {
        
        // Identify whether id references a device or satellite
        Device broadcastDevice = devices.get(id);
        Satellite broadcastSatellite = satellites.get(id);
        
        // Depending on class type, use an alternative helper function
        ArrayList<String> candidateList = new ArrayList<String>();
        if (broadcastDevice != null) {
            return Helper.findAccessibleD(broadcastDevice, satellites, devices);
            
        } else if (broadcastSatellite != null) {
            return Helper.findAccessibleS(broadcastSatellite, devices, satellites);
        }
        return null;
    }

    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        // Check whether in range (Not part of spec, useful in debugging though)
        List<String> available = communicableEntitiesInRange(fromId);
        
        if (available.contains(toId) == false) {
            System.out.println("Destination not available");
            return;
        }

        // Initialise NetworkObject class representing sender and receiver
        NetworkObject sendObj = devices.get(fromId);
        if (sendObj == null) {
            sendObj = satellites.get(fromId);
        }
        
        NetworkObject recObj = devices.get(toId);
        if (recObj == null) {
            recObj = satellites.get(toId);
        }
        
        // Check if file already exists in destination, throws exception
        for (File x : recObj.files) {
            if (x.getFilename() == fileName) {
                throw new VirtualFileAlreadyExistsException(fileName);
            }
        }

        // Initialise return variable
        File sendFile = new File();

        // Find and copy file to be sent
        for (File y : sendObj.files) {
            if (y.getFilename() == fileName) {
                sendFile = y;
                sendFile.setPrevOwner(sendObj.getObjectId());
            }
        }
        
        // If file isn't found, size of sendFile would be default value -1 = throw exception
        if (sendFile.getFileSize() == -1) {
            throw new VirtualFileNotFoundException(fileName);
        }
        
        // All conditions passed, add file to receiver with 0 bytes transferred
        recObj.addFile(fileName, sendFile.getData(), 0, false, sendFile.getPrevOwner());

        // Do storage management for satellites if receiver
        recObj = satellites.get(toId);
        if (recObj != null) {
            Satellite cleanSat = satellites.get(toId);
            cleanSat.manageStorage();
        }

    }

    public static void main(String[] args) {
        // Task 2 - part of testing communicableEntitiesInRange(String id);
        BlackoutController controller = new BlackoutController();

        // Creates 4 satellites, 1 device
        // 3 satellites are relays, 1 is Standard
        // Standard is really far away and must use 2 relays to connect to device
        // 1 relay tests the boundary direction
        controller.createSatellite("Satellite1", "RelaySatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(345));
        controller.createSatellite("Satellite2", "RelaySatellite", 10000 + RADIUS_OF_JUPITER, Angle.fromDegrees(140));
        controller.createSatellite("Satellite3", "RelaySatellite", 200000 + RADIUS_OF_JUPITER, Angle.fromDegrees(160));
        controller.createSatellite("Satellite4", "StandardSatellite", 200000  + RADIUS_OF_JUPITER, Angle.fromDegrees(160.1));
        controller.createSatellite("Satellite5", "StandardSatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(345));


        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(160));
        String msg = "Hi";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite4"));

        
        controller.simulate();

    }
}
