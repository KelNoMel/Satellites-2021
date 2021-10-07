package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class ShrinkingSatellite extends Satellite{
    final int byteLimit = 150;
    final int sendRate = 10;
    final int receiveRate = 15;
    
    public ShrinkingSatellite(String objId, Angle pos, double hght) {
        objectId = objId;
        position = pos;
        height = hght;
        speed = 1000;
        type = "ShrinkingSatellite";
        files = new ArrayList<File>();
        maxRange = 200000;
        exclusion = "None";
    }

   // public void compress(String Filename) {

    //}
}
