package unsw.blackout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import unsw.devices.DesktopDevice;
import unsw.devices.Device;
import unsw.devices.LaptopDevice;
import unsw.devices.HandHeldDevice;
import unsw.response.models.EntityInfoResponse;
import unsw.satellites.RelaySatellite;
import unsw.satellites.Satellite;
import unsw.satellites.ShrinkingSatellite;
import unsw.satellites.StandardSatellite;
import unsw.utils.Angle;

public class BlackoutController {
    private Hashtable<String, Device> devices;
    private Hashtable<String, Satellite> satellites;
    
    // Hashtables to store devices/satellites
    // The Id strings are already assumed unique
    // so we can use them as the keys
    public BlackoutController() {
        Hashtable<String, Device> devices = new Hashtable<String, Device>();
        Hashtable<String, Satellite> statellites = new Hashtable<String, Satellite>();
    }
    
    
    public void createDevice(String deviceId, String type, Angle position) {
        // TODO: Task 1a)
        switch(type) {
            case "HandheldDevice":
                HandHeldDevice newHandheld = new HandHeldDevice(deviceId, position);
                devices.put(newHandheld.objectId, newHandheld);
                break;
            case "LaptopDevice":
                LaptopDevice newLaptop = new LaptopDevice(deviceId, position);
                devices.put(newLaptop.objectId, newLaptop);
                break;
            case "DesktopDevice":
                DesktopDevice newDesktop = new DesktopDevice(deviceId, position);
                devices.put(newDesktop.objectId, newDesktop);
                break;
        }
    }

    public void removeDevice(String deviceId) {
        // TODO: Task 1b)
        devices.remove(deviceId);

    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        // TODO: Task 1c)
        switch (type) {
            case "StandardSatellite":
                StandardSatellite newStandard = new StandardSatellite(satelliteId, position, height);
                satellites.put(newStandard.objectId, newStandard);
                break;
            case "ShrinkingSatellite":
                ShrinkingSatellite newShrinking = new ShrinkingSatellite(satelliteId, position, height);
                satellites.put(newShrinking.objectId, newShrinking);
                break;
            case "RelaySatellite":
                RelaySatellite newRelay = new RelaySatellite(satelliteId, position, height);
                satellites.put(newRelay.objectId, newRelay);
                break;
    }

    public void removeSatellite(String satelliteId) {
        // TODO: Task 1d)
        satellites.remove(satelliteId);
    }

    public List<String> listDeviceIds() {
        // TODO: Task 1e)
        List<String> deviceList = new ArrayList<String>();
        String key;
        for (String key : devices) {
            // Print and display the Rank and Name
            deviceList.add(key);
        }
        return deviceList;
    }

    public List<String> listSatelliteIds() {
        // TODO: Task 1f)
        List<String> satelliteList = new ArrayList<String>();
        for (String id : satellites.keys()) {
            satelliteList.add(id);
        }
        return satelliteList;
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        // TODO: Task 1g)
    }

    public EntityInfoResponse getInfo(String id) {
        // TODO: Task 1h)
        return null;
    }

    public void simulate() {
        // TODO: Task 2a)
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
        // TODO Task 2b)
        return new ArrayList<>();
    }

    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        // TODO Task 2c)
    }
}
