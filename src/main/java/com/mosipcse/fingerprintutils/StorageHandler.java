package com.mosipcse.fingerprintutils;

import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StorageHandler {
    public static void serializeFingerprints(ArrayList<IdentityRecord> fingerprintData) {
        // TODO: change this method to serialize a IdentityRecordDAO array object
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
    public static ArrayList<IdentityRecord> loadFingerprints() {
        ArrayList<IdentityRecordDAO> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fingerprintData");
             ObjectInputStream ois = new ObjectInputStream(fis);) {

            list = (ArrayList<IdentityRecordDAO>) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        ArrayList<IdentityRecord> idRecordList = new ArrayList<>() ;
        for (IdentityRecordDAO idRecDAO:list
             ) {idRecordList.add(convertFromDAO(idRecDAO));
        }
        return idRecordList ;
    }
    public static IdentityRecordDAO convertToDAO(IdentityRecord idRecord){
        String id = idRecord.getId() ;
        ArrayList<FingerprintTemplate> fingerprints = idRecord.getFingerprints() ;
        ArrayList<byte[]> fingerprintsByteArr = new ArrayList<>() ;
        fingerprints.forEach((temp)-> fingerprintsByteArr.add(temp.toByteArray()));
        return new IdentityRecordDAO(id, fingerprintsByteArr) ;
    }
    public static IdentityRecord convertFromDAO(IdentityRecordDAO idRecordDAO) {
        String id = idRecordDAO.getId();
        ArrayList<byte[]> fingerprints = idRecordDAO.getFingerprints() ;
        ArrayList<FingerprintTemplate> fingerTemplates = new ArrayList<>();
        fingerprints.forEach((temp)-> fingerTemplates.add(new FingerprintTemplate(temp)));
        return new IdentityRecord(id, fingerTemplates) ;
    }
}
