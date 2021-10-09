package unsw.other;

public class File {
    private String filename;
    private String data;
    private int fileSize;
    private int currentSize;
    private boolean status;
    private String prevOwner;

    /**
     * Default constructor for file
     */
    public File() {
        filename = "default";
        data = "default";
        fileSize = -1;
        currentSize = 0;
        status = false;
        prevOwner = "default";
    }
    
    /**
     * Constructor for File
     * @param filename - name of file
     * @param data - content of the file
     * @param filesize - amount of bytes the full file will occupy
     * @param currentsize - amount of bytes the file currently occupies
     * @param status - whether the file is finished uploading
     * prevOwner - Hardcoded to be original (changed when sent)
     */
    public File(String fn, String d, int fs, int cs, Boolean s) {
        filename = fn;
        data = d;
        fileSize = fs;
        currentSize = cs;
        status = s;
        prevOwner = "Original";
    }

    public String getFilename() {
        return filename;
    }

    public String getData() {
        return data;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public boolean getStatus() {
        return status;
    }

    public String getPrevOwner() {
        return prevOwner;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPrevOwner(String prevOwner) {
        this.prevOwner = prevOwner;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
