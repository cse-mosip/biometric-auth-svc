package com.mosipcse.fingerprintutils;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Component
public class FingerPrintHandler {
    private ArrayList<IdentityRecord> fingerprintDb;

    public FingerPrintHandler() {}

    @PostConstruct
    public void init() {
        this.fingerprintDb = StorageHandler.loadFingerprints();
    }
    public String findMatchingPrint(String filepath) {
        System.out.println("Matching in progress");
        FingerprintMatcher matcher = getFingerPrintMatcher(filepath);
        IdentityRecord successfulMatch = matchFingerprint(matcher) ;
        if (successfulMatch == null) {
            System.out.println("No matches found") ;
            return null ;
        }
        System.out.println("Match found") ;
        return successfulMatch.getId();
    }
    public String enterNewRecord(IdentityRecord idRecord) {
        System.out.println("Matching in progress");
        FingerprintMatcher matcher = new FingerprintMatcher(idRecord.getFingerprints().get(0)) ;
        IdentityRecord successfulMatch = matchFingerprint(matcher) ;
        if (successfulMatch == null) {
            System.out.println("No matches found") ;
            /*
            * TODO:
            *  save to fingerprint arraylist
            * TODO: serialize at shutdown or save to db
            *
            * */
            fingerprintDb.add(idRecord);
            return "Fingerprint Added" ;
        }else {
            System.out.println("Match found") ;
            return "Fingerprint already in database" ;
        }

    }
    public static FingerprintTemplate getFingerPrintTemplate(String filepath) {
        FingerprintImage image;
        try {
            image = new FingerprintImage(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FingerprintTemplate(image);
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

    private FingerprintMatcher getFingerPrintMatcher(String filepath) {
        FingerprintTemplate template = getFingerPrintTemplate(filepath) ;
        return new FingerprintMatcher(template);
    }
    /*
     * TODO add preDestruct method here to store the
     * array list.
     */
    @PreDestroy
    public void saveFingerPrints() {
        ArrayList<IdentityRecordDAO> idListDAO = new ArrayList<>() ;
        for (IdentityRecord idRec: this.fingerprintDb
             ) {
            idListDAO.add(StorageHandler.convertToDAO(idRec));
        }
        System.out.println("serializing fingerprints");
        StorageHandler.serializeFingerprints(idListDAO);
    }
}
