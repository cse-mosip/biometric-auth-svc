package com.mosipcse.fingerprintutils;

import com.machinezoo.sourceafis.FingerprintTemplate;

import java.util.ArrayList;

public class IdentityRecordFactory {
    public static IdentityRecord createIdFromImages(ArrayList<String> fileNames, String id) {
        ArrayList<FingerprintTemplate> templates = new ArrayList<>() ;
        for (String filename:fileNames
             ) {
            templates.add(FingerPrintHandler.getFingerPrintTemplate(filename));
        }
        return new IdentityRecord(id, templates) ;
    }
}
