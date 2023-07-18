package com.mosipcse.fingerprintutils;

import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.Serializable;
import java.util.ArrayList;

public class IdentityRecord implements Serializable {
    private final String id ;
    private final ArrayList<FingerprintTemplate> fingerprints ;

    IdentityRecord(String id, ArrayList<FingerprintTemplate> fingerprints) {
        this.fingerprints = fingerprints ;
        this.id = id ;
    }

    public ArrayList<FingerprintTemplate> getFingerprints() {
        return fingerprints;
    }

    public String getId() {
        return id;
    }
}
