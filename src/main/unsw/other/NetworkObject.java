package unsw.other;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import unsw.utils.Angle;
import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;

public class NetworkObject {
    public String objectId;
    public Angle position;
    public double height;
    public String type;
    public ArrayList<File> files;
    public double maxRange;
    public ArrayList<String> compatible;

    /**
     * Default constructor for NetworkObject
     */
    public NetworkObject() {
        objectId = "Default";
        position = Angle.fromDegrees(10);
        height = 0;
        type = "Default";
        files = new ArrayList<File>();
        maxRange = 0;
        compatible = new ArrayList<String>();
    }

    public String getObjectId() {
        return objectId;
    }
    
    public Angle getPosition() {
        return position;
    }

    public double getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public ArrayList<String> getCompatible() {
        return compatible;
    }

    public void setPosition(Angle position) {
        this.position = position;
    }

    /**
     * Getter for various class variables, including:
     * id (String)
     * position (Angle)
     * height (int)
     * type (String)
     * files (Map<String, FileInfoResponse>)
    */
    public EntityInfoResponse getInfo() {

        // Convert files list into correct type
        Map<String, FileInfoResponse> firMap = new HashMap<String, FileInfoResponse>();
        for (File x : files) {
            FileInfoResponse fir = new FileInfoResponse(x.getFilename(), x.getData().substring(0, x.getCurrentSize()), x.getFileSize(), x.getStatus());
            firMap.put(x.getFilename(), fir);
        }
        
        EntityInfoResponse eir = new EntityInfoResponse(objectId, position, height, type, firMap);
        return eir;
    }

    public boolean isCompatible(String type) {
        for (String entry : compatible) {
            if (type == entry) {
                return true;
            }
        }
        return false;
    }
    
    // Adds a file to NetworkObject files field
    public void addFile(String filename, String content, int current, boolean status, String prevOwner) {
        int len = content.length();
        File newFile = new File(filename, content, len, current, status);
        newFile.setPrevOwner(prevOwner);
        this.files.add(newFile);
    }
}
