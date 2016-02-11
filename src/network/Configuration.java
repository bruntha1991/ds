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
    private static List<Neighbor> backUpNeighbors = new ArrayList<Neighbor>();

    public static boolean setConfiguration(String[] configurationDetails) {
        try {

            if (configurationDetails.length != 5)
                return false;

            //update configurations details
            Configuration.myIpAddress = configurationDetails[0];
            Configuration.myPortNumber = Integer.parseInt(configurationDetails[1]);
            Configuration.myUserName = configurationDetails[2];
            Configuration.serverIpAddress = configurationDetails[3];
            Configuration.serverPortNumber = Integer.parseInt(configurationDetails[4]);

            //updateFiles
            myFiles = Files.getRandomFiles();
        } catch (Exception e) {
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

    public static void setNeighbor(String ip, int port) {
        synchronized (neighbors) {
            Neighbor temp = new Neighbor(ip, port);
            if (!neighbors.contains(temp)) {
                neighbors.add(temp);

            }
        }

    }

    public static List<Neighbor> getNeighbors() {
        synchronized (neighbors) {
            return neighbors;
        }
    }

    public static void removeNeighbor(String ip, int port) {
//        synchronized (neighbors) {
//            Iterator<Neighbor> neighborsIterator = neighbors.iterator();
//            while (neighborsIterator.hasNext()) {
//                Neighbor temp = neighborsIterator.next();
//                if (temp.toString().equals(ip + ":" + port)) {
//                    neighborsIterator.remove();
//                }
//            }
//        }

        Neighbor temp = new Neighbor(ip, port);
        if(neighbors.contains(temp)){
            neighbors.remove(temp);

        }
        if(backUpNeighbors.contains(temp)){
            backUpNeighbors.remove(temp);

        }
    }

    public static ArrayList<String> getMyFiles() {
        synchronized (myFiles) {
            return myFiles;
        }
    }

    public static void addFile(String file) {
        synchronized (myFiles) {
            if (!myFiles.contains(file)) {
                myFiles.add(file);
            }
        }
    }

    public static void setBackUpNeighbor(String ip, int port){
        Neighbor temp = new Neighbor(ip, port);
        if(!backUpNeighbors.contains(temp)){

            backUpNeighbors.add(temp);

        }

    }
    public static List<Neighbor> getBackUpNeighbors(){

        return backUpNeighbors;
    }
    public static void removeBackUpNeighbor(String ip, int port){


        Neighbor temp = new Neighbor(ip, port);
        if(backUpNeighbors.contains(temp)){
            backUpNeighbors.remove(temp);

        }


    }

}
