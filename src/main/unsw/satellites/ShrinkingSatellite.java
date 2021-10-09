package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class ShrinkingSatellite extends Satellite{
    
    /**
     * Constructor for RelaySatellite
     * @param objId
     * @param pos
     * @param hght
     * All other fields are hardcoded
     */
    public ShrinkingSatellite(String objId, Angle pos, double hght) {
        super();
        objectId = objId;
        position = pos;
        height = hght;
        speed = 1000;
        type = "ShrinkingSatellite";
        files = new ArrayList<File>();
        maxRange = 200000;
        byteLimit = 150;
        sendRate = 10;
        receiveRate = 15;
        compatible.add("HandheldDevice");
        compatible.add("LaptopDevice");
        compatible.add("DesktopDevice");
        compatible.add("CloudStorageDevice");
        compatible.add("StandardSatellite");
        compatible.add("ShrinkingSatellite");
    }
}
