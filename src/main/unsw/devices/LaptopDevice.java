package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class LaptopDevice extends Device {

    /**
     * Constructor for LaptopDevice
     * @param objId - Assigned id
     * @param pos - Assigned position
     * 
     * All other fields are hardcoded
     */
    public LaptopDevice(String objId, Angle pos) {
        super();
        objectId = objId;
        position = pos;
        height = 69911;
        type = "LaptopDevice";
        files = new ArrayList<File>();
        maxRange = 100000;
        compatible.add("StandardSatellite");
        compatible.add("ShrinkingSatellite");
        compatible.add("ElephantSatellite");
    }
}