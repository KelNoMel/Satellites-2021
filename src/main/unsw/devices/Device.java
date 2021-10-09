package unsw.devices;

import unsw.other.NetworkObject;
import unsw.other.File;

public class Device extends NetworkObject {

    /**
     * Default Constructor
     */
    public Device() {
        super();
    }

    @Override
    public String toString() {
        String output = ("I am " + objectId + " at position " + position
                            + " degrees, " + height + " high and "
                            + "am a " + type + " device");
        return output;
    }
}
