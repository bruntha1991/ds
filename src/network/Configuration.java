package network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruba on 1/10/2016.
 */
public class Configuration {
    private static String myIpAddress;
    private static int myPortNumber;
    private static String myUserName;
    private static String serverIpAddress;
    private static int serverPortNumber;
    private static ArrayList<String> myFiles;
    private static List<Neighbor> neighbors = new ArrayList<Neighbor>();
    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    public static boolean setConfiguration(String[] configurationDetails){
        try{

            if(configurationDetails.length != 5)
                return false;

            //update configurations details
            Configuration.myIpAddress = configurationDetails[0];
            Configuration.myPortNumber = Integer.parseInt(configurationDetails[1]);
            Configuration.myUserName = configurationDetails[2];
            Configuration.serverIpAddress = configurationDetails[3];
            Configuration.serverPortNumber = Integer.parseInt(configurationDetails[4]);

            //updateFiles
            myFiles = Files.getRandomFiles();
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public static String getMyIpAddress() {
        return myIpAddress;
    }

    public static int getMyPortNumber() {
        return myPortNumber;
    }

    public static String getMyUserName() {
        return myUserName;
    }

    public static String getServerIpAddress() {
        return serverIpAddress;
    }

    public static int getServerPortNumber() {
        return serverPortNumber;
    }

    public static void setNeighbor(String ip, int port){
        synchronized (neighbors) {
            Neighbor temp = new Neighbor(ip, port);
            if (!neighbors.contains(temp)) {
                neighbors.add(temp);

            }
        }

    }
    public static List<Neighbor> getNeighbors(){
        synchronized (neighbors) {
            return neighbors;
        }
    }
    public static void removeNeighbor(String ip, int port){
        synchronized (neighbors) {
            Neighbor temp = new Neighbor(ip, port);
            if (neighbors.contains(temp)) {
                neighbors.remove(temp);

            }
        }
    }
    public static ArrayList<String> getMyFiles() {
        synchronized (myFiles) {
            return myFiles;
        }
    }

    public static void addFile(String file){
        synchronized (myFiles) {
            if (!myFiles.contains(file)) {
                myFiles.add(file);
            }
        }
    }

}
