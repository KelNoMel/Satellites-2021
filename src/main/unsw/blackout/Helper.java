package unsw.blackout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.List;
import java.util.Enumeration;

import unsw.devices.Device;
import unsw.other.File;
import unsw.other.NetworkObject;
import unsw.satellites.Satellite;
import unsw.satellites.RelaySatellite;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

// Helper methdos for Assignment
public class Helper {
    
    // A method given a Device and a hashtable of
    // satellites that shows which ones are available
    // including those via relay connection. Device -> Satellite
    public static ArrayList<String> findAccessibleD(Device src, Hashtable<String, Satellite> satellites, Hashtable<String, Device> devices) {
        // Initialise list that we will return
        ArrayList<String> returnList = new ArrayList<String>();
        // Initialise list of visited relays
        ArrayList<String> visited = new ArrayList<String>();
        // Initialise list of all satellites
        Set<String> satelliteSet = satellites.keySet();
        ArrayList<String> satList = new ArrayList<>(satelliteSet);
        // Check every satellite to see if it can be accessed.
        
        for (String satId : satList) {
            Satellite sat = satellites.get(satId);
        
            
            // If satellite is visible and in range
            if (MathsHelper.getDistance(sat.getHeight(), sat.getPosition(), src.getPosition())
                                                        <= src.getMaxRange() &&
                MathsHelper.isVisible(sat.getHeight(), sat.getPosition(), src.getPosition())
                                                        == true) {


                
                // If a relay is visible, start recursive relay method
                if (sat.getType() == "RelaySatellite") {
                    RelaySatellite relay = new RelaySatellite(sat.objectId, sat.position, sat.height);
                    
                    returnList = findAccessibleRecRS(returnList, visited, relay, satellites, devices, src.getCompatible());
                // Otherwise check if it is compatible + not present and add if so
                } else if (src.isCompatible(sat.getType()) == true &&
                            returnList.contains(satId) == false) {
                    returnList.add(satId);
                }
            }
        }
        return returnList;
    }

    // A method given a Satellite and a hashtable of
    // devices/satellites that shows which ones are available
    // including those via relay connection. Satellite -> Device + Satellite
    public static ArrayList<String> findAccessibleS(Satellite source, Hashtable<String, Device> devices, Hashtable<String, Satellite> satellites) {
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<String> visited = new ArrayList<String>();

        // First, get all satellites able to
        // be broadcasted to
        Set<String> satelliteSet = satellites.keySet();
        List<String> satList = new ArrayList<>(satelliteSet);
        
        for (String satId : satList) {
            Satellite sat = satellites.get(satId);
            
            // If satellite is visible and in range
            if (MathsHelper.getDistance(sat.getHeight(), sat.getPosition(), source.getHeight(), source.getPosition())
                                                        <= source.getMaxRange() &&
                MathsHelper.isVisible(sat.getHeight(), sat.getPosition(), source.getHeight(), source.getPosition())
                                                        == true) {
                
                // If a relay is visible and not visited, recurse
                if (sat.getType() == "RelaySatellite" &&
                    visited.contains(satId) == false) {
                    visited.add(satId);
                    RelaySatellite relay = new RelaySatellite(sat.objectId, sat.position, sat.height);
                    returnList.addAll(findAccessibleRecRS(returnList, visited, relay, satellites, devices, source.getCompatible()));
                    ArrayList<String> remDupes = new ArrayList<String>();
                    for (String item : returnList) {
                        if (!remDupes.contains(item)) {
                            remDupes.add(item);
                        }
                    } 
                    returnList = remDupes;
                // Otherwise check if it isn't in the returnList
                } else if (returnList.contains(satId) == false && sat != source) {
                    returnList.add(satId);
                }
            }
        }
        // Using the visited list, we can get all broadcasting
        // satellites that can then transmit to devices
        ArrayList<Satellite> broadcastList = new ArrayList<Satellite>();
        broadcastList.add(source);
        for (String satId : visited) {
            Satellite sat = satellites.get(satId);
            broadcastList.add(sat);
        }


        // For all broadcasting satellites stemming from source
        // find all devices accessible
        for (Satellite src: broadcastList) {
            Set<String> deviceSet = devices.keySet();
            List<String> devList = new ArrayList<>(deviceSet);

            for (String devId : devList) {
                Device dev = devices.get(devId);
                if (src.isCompatible(dev.getType()) == true && 
                        MathsHelper.getDistance(src.getHeight(), src.getPosition(), dev.getPosition())
                                                            <= src.getMaxRange() &&
                        MathsHelper.isVisible(src.getHeight(), src.getPosition(), dev.getPosition())
                                                            == true &&
                        returnList.contains(devId) == false) {
                    
                    returnList.add(devId);
                }
            }
        }

        return returnList;
    }

    // A recursive method given a relay and a list of
    // satellites that returns a list of the sat network stemming from
    // the relay. Relay -> Satellite.
    public static ArrayList<String> findAccessibleRecRS(ArrayList<String> returnList, ArrayList<String> visited, 
                                                        RelaySatellite src, Hashtable<String, Satellite> satellites,
                                                        Hashtable<String, Device> devices, ArrayList<String> compatible) {
        Set<String> satelliteSet = satellites.keySet();
        List<String> satList = new ArrayList<>(satelliteSet);
        
        // Show devices accessible from Relay Satellite
        Set<String> deviceSetI = devices.keySet();
        List<String> devListI = new ArrayList<>(deviceSetI);

        for (String devId : devListI) {
            Device dev = devices.get(devId);
            if (MathsHelper.getDistance(src.getHeight(), src.getPosition(), dev.getPosition())
                                                    <= src.getMaxRange() &&
                    MathsHelper.isVisible(src.getHeight(), src.getPosition(), dev.getPosition())
                                                    == true &&
                    returnList.contains(devId) == false) {
                
                returnList.add(devId);
            }
        }
        
        for (String satId : satList) {
            Satellite sat = satellites.get(satId);
            
            // If satellite is visible and in range
            if (MathsHelper.getDistance(sat.getHeight(), sat.getPosition(), src.getPosition())
                                                        <= src.getMaxRange() &&
                MathsHelper.isVisible(sat.getHeight(), sat.getPosition(), src.getPosition())
                                                        == true) {

                // If a relay is visible and not visited, recurse
                if (sat.getType() == "RelaySatellite" &&
                    visited.contains(satId) == false) {
                    
                    visited.add(satId);
                    RelaySatellite relay = new RelaySatellite(sat.objectId, sat.position, sat.height);
                    returnList = findAccessibleRecRS(returnList, visited, relay, satellites, devices, compatible);
                // Otherwise check if it isn't in the returnList
                } else if (returnList.contains(satId) == false &&
                            compatible.contains(sat.getType()) == true) {
                    returnList.add(satId);
                }
            }
        }
        
        return returnList;
    }

    // Checks downloads for satellites from devices, part 1/3 for
    // managing file transfers
    public static void satDownloads(Satellite sat, List<String> conns, Hashtable<String, Device> devices, Hashtable<String, Satellite> satellites) {
                    
        // Check downloading files
        // Search for files that are incomplete, then check if they
        // should still be downloading (if they have prevowners in range)
        ArrayList<String> downloadList = new ArrayList<String>();
        ArrayList<File> deleteList = new ArrayList<File>();
        for (File file : sat.getFiles()) {
            if (file.getStatus() == false) {
                String connId = file.getPrevOwner();
                // If valid, add to download list
                if (conns.contains(connId) == true) {
                    downloadList.add(file.getFilename());
                // If type isn't elephant and invalid, flag file for deletion
                } else if (conns.contains(connId) == false && 
                            sat.getType() != "ElephantSatellite" &&
                            file.getStatus() == false) {
                    deleteList.add(file);
                }
            }
        }
        // Delete flagged files
        for (File delFile : deleteList) {
            sat.files.remove(delFile);
        }

        // Identified all files that are downloading, commit to downloads
        int numDown = downloadList.size();
        // End if no downloads
        if (numDown == 0) {
            return;
        }
        int downloadRate = (sat.getReceiveRate() / numDown); 

        for (File file : sat.getFiles()) {
            if (downloadList.contains(file.getFilename()) == true) {
                file.setCurrentSize(file.getCurrentSize() + downloadRate);

                // Check if files finished downloading
                int effectiveSize = file.getFileSize();
                if (file.getCurrentSize() >= effectiveSize) {
                    file.setStatus(true);
                    file.setCurrentSize(file.getFileSize());
                }
                // Consider shrinking completed files for shrinking satellites
                if (sat.getType() == "ShrinkingSatellite" &&
                    file.getData().contains("quantum") &&
                    file.getStatus() == true) {
                    file.setFileSize(file.getFileSize() - (file.getFileSize() / 3));
                }
            }
        }
    }
    
    // Checks uploads for satellites to other satellites/devices, 
    // part 2/3 for managing file transfers
    public static void satUploads(Satellite sat, List<String> conns, Hashtable<String, Device> devices, Hashtable<String, Satellite> satellites) {
                    
        // Check uploading files
        // Check in range objects, and see if they have unfinished files
        // which have sat as a prevOwner
        Hashtable<String, String> uploadList = new Hashtable<>();
        for (String objId : conns) {
            NetworkObject obj = new NetworkObject();
            
            // Search for and retrieve object
            obj = satellites.get(objId);
            if (obj == null) {
                obj = devices.get(objId);
            }

            // Check if satellite is uploading any files to the device
            for (File file : obj.getFiles()) {
                if (file.getStatus() == false) {
                    String connId = file.getPrevOwner();
                    // If valid, add to upload hashmap
                    if (connId == sat.getObjectId()) {
                        uploadList.put(obj.getObjectId(), file.getFilename());
                    }
                }
            }
        }

        // We've now got a hashtable of objects we're uploading to and the file
        // we're uploading, commit to uploads
        int numUp = uploadList.size();
        if (numUp == 0) {
            return;
        }
        int uploadRate = (sat.getSendRate() / numUp); 

        // Enumeration helps us with looping through HashTable
        Enumeration<String> e = uploadList.keys();
        while (e.hasMoreElements()) {
            String obj = e.nextElement();

            NetworkObject receiver = satellites.get(obj);
            if (receiver == null) {
                receiver = devices.get(obj);
            }
            
            // Find uploading file and set currentsize
            for (File file : receiver.files) {
                if (file.getFilename() == uploadList.get(obj)) {
                    file.setCurrentSize(file.getCurrentSize() + uploadRate);
    
                    // Check if files finished downloading
                    int effectiveSize = file.getFileSize();
                          
                    if (file.getCurrentSize() >= effectiveSize) {
                        file.setStatus(true);
                        file.setCurrentSize(file.getFileSize());
                    }
                }
            }
        }
        
    }

    // Check downloads for devices from satellites, only permitted to
    // delete files that haven't finished downloading and have owners
    // that are out of range. File transfer 3/3
    public static void deviceRemoveUnfinished(Device dev, List<String> conns,  Hashtable<String, Device> devices) {
        ArrayList<File> deleteList = new ArrayList<File>();
        for (File file : dev.files) {
            if (file.getStatus() == false) {
                String ownId = file.getPrevOwner();
                if (conns.contains(ownId) == false && file.getStatus() == false) {
                    deleteList.add(file);
                }
            }
        }
        // Delete flagged files
        for (File delFile : deleteList) {
            dev.files.remove(delFile);
        }
    } 

}