package unsw.devices;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class HandHeldDevice extends Device {
    
    public HandHeldDevice(String objId, Angle pos) {
        objectId = objId;
        position = pos;
        type = "HandHeldDevice";
        files = new ArrayList<File>();
        maxRange = 50000;
        exclusion = "ElephantSatellite";
    }
}
