package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class DesktopDevice extends Device {

    /**
     * Constructor DesktopDevice
     * @param objId - Assigned id
     * @param pos - Assigned position
     * 
     * All other fields are hardcoded
     */
    public DesktopDevice(String objId, Angle pos) {
        super();
        objectId = objId;
        position = pos;
        height = 69911;
        type = "DesktopDevice";
        files = new ArrayList<File>();
        maxRange = 200000;
        compatible.add("ShrinkingSatellite");
        compatible.add("ElephantSatellite");
    }
}