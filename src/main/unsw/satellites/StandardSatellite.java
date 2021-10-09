package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class StandardSatellite extends Satellite{
    
    /**
     * Constructor for RelaySatellite
     * @param objId
     * @param pos
     * @param hght
     * All other fields are hardcoded
     */
    public StandardSatellite(String objId, Angle pos, double hght) {
        super();
        objectId = objId;
        position = pos;
        height = hght;
        speed = 2500;
        type = "StandardSatellite";
        files = new ArrayList<File>();
        maxRange = 150000;
        byteLimit = 80;
        fileLimit = 3;
        sendRate = 1;
        receiveRate = 1;
        compatible.add("HandheldDevice");
        compatible.add("LaptopDevice");
        compatible.add("CloudStorageDevice");
        compatible.add("StandardSatellite");
        compatible.add("ShrinkingSatellite");
    }
}