package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class DesktopDevice extends Device {
    
    public DesktopDevice(String objId, Angle pos) {
        objectId = objId;
        position = pos;
        type = "DesktopDevice";
        files = new ArrayList<File>();
        maxRange = 200000;
        exclusion = "StandardSatellite";
    }
}