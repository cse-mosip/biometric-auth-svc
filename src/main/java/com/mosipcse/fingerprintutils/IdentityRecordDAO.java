package com.mosipcse.fingerprintutils;

import java.util.ArrayList;

public class IdentityRecordDAO {
    private final String id ;
    private final ArrayList<byte[]> fingerprints ;

    public IdentityRecordDAO(String id, ArrayList<byte[]> fingerprints) {
        this.id = id;
        this.fingerprints = fingerprints;
    }

    public String getId() {
        return id;
    }

    public ArrayList<byte[]> getFingerprints() {
        return fingerprints;
    }
}
