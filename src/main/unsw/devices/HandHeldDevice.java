package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class HandHeldDevice extends Device {

    /**
     * Constructor for HandHeldDevice
     * @param objId - Assigned id
     * @param pos - Assigned position
     * 
     * All other fields are hardcoded
     */
    public HandHeldDevice(String objId, Angle pos) {
        super();
        objectId = objId;
        position = pos;
        height = 69911;
        type = "HandheldDevice";
        files = new ArrayList<File>();
        maxRange = 50000;
        compatible.add("StandardSatellite");
        compatible.add("ShrinkingSatellite");
    }
}