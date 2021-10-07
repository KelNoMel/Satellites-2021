package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class LaptopDevice extends Device {
    
    public LaptopDevice(String objId, Angle pos) {
        objectId = objId;
        position = pos;
        type = "LaptopDevice";
        files = new ArrayList<File>();
        maxRange = 100000;
        exclusion = "None";
    }
}