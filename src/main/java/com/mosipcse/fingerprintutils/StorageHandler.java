package com.mosipcse.fingerprintutils;

import java.io.*;
import java.util.ArrayList;

public class StorageHandler {
    public static void serializeFingerprints(ArrayList<IdentityRecord> fingerprintData) {
        try (FileOutputStream fos = new FileOutputStream("fingerprintData");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            oos.writeObject(fingerprintData);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found " + e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO Exception " + e);
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<IdentityRecord> loadFingerprints(String filename) {
        ArrayList<IdentityRecord> list = null;

        try (FileInputStream fis = new FileInputStream("listData");
             ObjectInputStream ois = new ObjectInputStream(fis);) {

            list = (ArrayList<IdentityRecord>) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return list ;
    }
}
