package com.mosipcse.fingerprintutils;

import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<IdentityRecord> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fingerprintDatachang");
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
    private static IdentityRecordDAO convertToDAO(IdentityRecord idRecord){
        String id = idRecord.getId() ;
        ArrayList<FingerprintTemplate> fingerprints = idRecord.getFingerprints() ;
        ArrayList<byte[]> fingerprintsByteArr = new ArrayList<>() ;
        fingerprints.forEach((temp)-> fingerprintsByteArr.add(temp.toByteArray()));
        return new IdentityRecordDAO(id, fingerprintsByteArr) ;
    }
}
