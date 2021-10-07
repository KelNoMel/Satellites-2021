package unsw.other;

import java.util.List;
import java.util.Hashtable;

import unsw.utils.Angle;
//import unsw.response.models.EntityInfoResponse;

public class NetworkObject {
    public String objectId;
    public Angle position;
    public double height;
    public String type;
    public List<File> files;
    public double maxRange;
    public String exclusion;

    /**
     * Sends file to the device, also making sure that all the
     * conditions for transfer are met (valid object type, in-range,
     * line of sight etc.)
     * @param device
     * @param file
     * TODO: Math stuff
     */
    //public void sendFile(String device, String file) {
    //    excludeList = this.exclusions;

        //if (device in exclusions) {
        //    dont;
        //}
    //}

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

    public String getExclusion() {
        return exclusion;
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
    //public void getInfo() {

    //     Convert files list into correct type
        //List<Files> files = this.files;
        
    //    Map<String, FileInfoResponse> firList = new HashMap<>();
    //    return EntityInfoResponse(this.objectId, this.position, this.height, this.type, firList);
    //}
}
