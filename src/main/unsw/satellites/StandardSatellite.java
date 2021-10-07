package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class StandardSatellite extends Satellite{
    final int byteLimit = 80;
    final int fileLimit = 3;
    final int sendRate = 1;
    final int receiveRate = 1;
    
    public StandardSatellite(String objId, Angle pos, double hght) {
        objectId = objId;
        position = pos;
        height = hght;
        speed = 2500;
        type = "StandardSatellite";
        files = new ArrayList<File>();
        maxRange = 150000;
        exclusion = "DesktopDevice";
    }
}