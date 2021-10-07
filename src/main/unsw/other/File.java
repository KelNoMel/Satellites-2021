package unsw.other;

public class File {
    private String filename;
    private String data;
    private int fileSize;
    private int currentSize;
    private String status;

    /**
     * Constructor for File
     * @param filename - name of file
     * @param data - content of the file
     * @param filesize - amount of bytes the full file will occupy
     * @param currentsize - amount of bytes the file currently occupies
     * @param status - whether the file is finished uploading
     */
    public File(String fn, String d, int fs, int cs, String s) {
        filename = fn;
        data = d;
        fileSize = fs;
        currentSize = cs;
        status = s;
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

    public String getStatus() {
        return status;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
