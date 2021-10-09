package unsw.satellites;

import java.util.ArrayList;

import unsw.other.NetworkObject;
import unsw.other.File;
import unsw.utils.Angle;

public class Satellite extends NetworkObject {
    public double speed;
    public int byteLimit;
    public int fileLimit;
    public int sendRate;
    public int receiveRate;

    /**
     * Default constructor for Satellite
     */
    public Satellite() {
        super();
        speed = -1;
        byteLimit = -1;
        fileLimit = -1;
        sendRate = -1;
        receiveRate = -1;
    }

    public double getSpeed() {
        return speed;
    }

    public int getByteLimit() {
        return byteLimit;
    }

    public int getFileLimit() {
        return fileLimit;
    }

    public int getSendRate() {
        return sendRate;
    }

    public int getReceiveRate() {
        return receiveRate;
    }

    // Allows satellite to change position, follows
    // standard orbital formula used by most satellites
    // except for relay (w = v / r)
    public void travel() {
        // Since this is the change in rads / min
        // We simply just add to current position
        // as that corresponds to the minute interval
        Angle angvelocity = new Angle();
        angvelocity = angvelocity.fromRadians(speed/height);
        position = position.add(angvelocity);
    }

    @Override
    public String toString() {
        String output = ("I am " + objectId + " at position " + position
                            + " degrees, " + height + " high and "
                            + "am a " + type + " satellite travelling at " + speed + " speed");
        return output;
    }
}
