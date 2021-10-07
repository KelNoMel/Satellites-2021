package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class RelaySatellite extends Satellite{
    
    public RelaySatellite(String objId, Angle pos, double hght) {
        objectId = objId;
        position = pos;
        height = hght;
        speed = 1500;
        type = "RelaySatellite";
        files = new ArrayList<File>();
        maxRange = 300000;
        exclusion = "None";
    }
}
