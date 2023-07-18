package com.mosipcse.fingerprintutils;


import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FingerPrintHandler {
    private ArrayList<IdentityRecord> fingerprintDb;
    public String findMatchingPrint(String filepath) {
        System.out.println("Matching in progress");
        FingerprintMatcher matcher = getFingerPrintMatcher(filepath);
        IdentityRecord successfulMatch = matchFingerprint(matcher) ;
        if (successfulMatch == null) {
            return null ;
        }
        return successfulMatch.getId();
    }

    private IdentityRecord matchFingerprint(FingerprintMatcher matcher) {
        IdentityRecord matchingId = null;
        double max = Double.NEGATIVE_INFINITY;
        for (IdentityRecord candidate : this.fingerprintDb) {
            ArrayList<FingerprintTemplate> fingerprints = candidate.getFingerprints();
            for (FingerprintTemplate template : fingerprints) {
                double similarity = matcher.match(template);
                if (similarity > max) {
                    max = similarity;
                    matchingId = candidate;
                }
            }
        }
        double threshold = 40;
        return max >= threshold ? matchingId : null;
    }

    public String enterNewRecord(String filepath) {
        return "id" ;
    }

    private FingerprintMatcher getFingerPrintMatcher(String filepath) {
        FingerprintImage image;
        try {
            image = new FingerprintImage(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FingerprintTemplate template = new FingerprintTemplate(image);
        return new FingerprintMatcher(template);
    }



}
