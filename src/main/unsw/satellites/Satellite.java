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

    // Deletes files if storage limits reached
    public void manageStorage() {
        int presentBytes = 0;
        int presentFiles = 0;

        // There's no file limit, make presentFiles a
        // really large negative number to not trigger
        // For Shrinking Satellite
        if (fileLimit == -1) {
            presentFiles = -99999999;
        }

        if (byteLimit == -1) {
            presentBytes = -999999999;
        }

        for (File f : files) {
            presentBytes = presentBytes + f.getCurrentSize();
            presentFiles = presentFiles + 1;
        }

        while (presentBytes > byteLimit || presentFiles > fileLimit) {
            // If recently added file would be too large
            // anyway, delete it
            if (files.get(files.size() - 1).getFileSize() > byteLimit) {
                files.remove(-1);
            // Delete the first file in file storage until
            // back in memory range
            } else {
                files.remove(0);
            }
        }
    }

    @Override
    public String toString() {
        String output = ("I am " + objectId + " at position " + position
                            + " degrees, " + height + " high and "
                            + "am a " + type + " satellite travelling at " + speed + " speed");
        return output;
    }
}
