package org.traffic.normalizer;

public interface Normalizer {
    /**
     * Checks if record is valid.
     */
    Boolean isValid(String record);

    /**
     * Normalize records.
     */
    String normalize(String record);

}