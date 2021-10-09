package unsw.satellites;

import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.other.File;

public class RelaySatellite extends Satellite{
    double direction;
    
    /**
     * Constructor for RelaySatellite
     * @param objId
     * @param pos
     * @param hght
     * All other fields are hardcoded
     */
    public RelaySatellite(String objId, Angle pos, double hght) {
        super();
        objectId = objId;
        position = pos;
        height = hght;
        speed = 1500;
        type = "RelaySatellite";
        files = new ArrayList<File>();
        maxRange = 300000;
        compatible.add("Relay");
        direction = 1;
    }

    // Relays orbit weird, therefore we must override travel method
    @Override
    public void travel() {
        // Same formula for angvelocity
        Angle angvelocity = new Angle();
        angvelocity = angvelocity.fromRadians(speed/height);
        
        // However direction depends on position
        double angDegrees = position.toDegrees();
        // Switch direction if on boundary
        if (angDegrees >= 345 || angDegrees <= 140) {
            direction = 1;
        } else if (angDegrees < 345 && angDegrees >=190 ){
            direction = -1;
        }
        if (direction == 1) {
            position = position.add(angvelocity);
        } else {
            position = position.subtract(angvelocity);
        }
        
    }
}
