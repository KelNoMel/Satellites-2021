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
                HandHeldDevice newDevice = new HandHeldDevice(deviceId, position);
                devices.put(newDevice.objectId, newDevice);
                break;
            case "LaptopDevice":
                LaptopDevice newDevice = new LaptopDevice(deviceId, position);
                devices.put(newDevice.objectId, newDevice);
                break;
            case "DesktopDevice":
                DesktopDevice newDevice = new DesktopDevice(deviceId, position);
                devices.put(newDevice.objectId, newDevice);
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
                StandardSatellite newSatellite = new StandardSatellite(satelliteId, position, height);
                satellites.put(newSatellite.objectId, newSatellite);
                break;
            case "ShrinkingSatellite":
                ShrinkingSatellite newSatellite = new ShrinkingSatellite(satelliteId, position, height);
                satellites.put(newSatellite.objectId, newSatellite);
                break;
            case "RelaySatellite":
                RelaySatellite newSatellite = new RelaySatellite(satelliteId, position, height);
                satellites.put(newSatellite.objectId, newSatellite);
                break;
        }
    }

    public void removeSatellite(String satelliteId) {
        // TODO: Task 1d)
        satellites.remove(satelliteId);
    }

    public List<String> listDeviceIds() {
        // TODO: Task 1e)
        Set<Integer> setOfKeys = devices.keySet();
 
        // Iterating through the Hashtable
        // object using for-Each loop
        for (String key : setOfKeys) {
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
