package unsw.devices;

import unsw.other.NetworkObject;
import unsw.other.File;

public class Device extends NetworkObject {

    private static double height = 69911;

    public void addFile(String filename, String content) {
        int len = content.length();
        File newFile = new File(filename, content, len, len, "finished");
        this.files.add(newFile);
    }
}