package com.mosipcse.fingerprintutils;

import java.io.Serializable;
import java.util.ArrayList;

public class IdentityRecordDAO implements Serializable {
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
