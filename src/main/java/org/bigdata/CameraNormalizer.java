package org.bigdata;

import java.util.Objects;

public class CameraNormalizer implements Normalizer{

    /**
     * Class Attributes
     */
    private final String station;

    /**
     * Class Constructor.
     */
    CameraNormalizer(String station) {
        this.station = station;
    }

    /**
     * Check if category of vehicle is valid
     */
    private Boolean isValidCategory(String category) {
        return (Objects.equals(category, "UT") ||
                Objects.equals(category, "VL") ||
                Objects.equals(category, "VELO") ||
                Objects.equals(category, "MOTO") ||
                Objects.equals(category, "PL_1") ||
                Objects.equals(category, "PL_2") ||
                Objects.equals(category, "BUS") ||
                Objects.equals(category, "EDPM"));
    }

    /**
     * Returns category.
     */
     private String vehicleCategory(String c) {
        if (Objects.equals(c, "PL_1") || Objects.equals(c, "PL_2")) {
            return "PL";
        } else if (Objects.equals(c, "VELO") || Objects.equals(c, "MOTO")) {
            return "2RM";
        } else {
            return c;
        }
    }

    /**
     * Checks if a record is valid.
     */
    public Boolean isValid(String record) {
        String[] tokens = record.split(",");
        if (!Objects.equals(station, "P20") && tokens.length != 5 && tokens.length != 4){
            return false;
        }
        if (Objects.equals(station, "P20") && tokens.length != 6 && tokens.length != 5) {
            return false;
        }
        if (!isValidCategory(tokens[1])) {
            return false;
        }

        if (tokens.length == 5 && Objects.equals(tokens[3], "") && Objects.equals(tokens[4], "")){
            return false;
        }
        if (tokens.length == 5 && !Objects.equals(tokens[3], "") && !Objects.equals(tokens[4], "")) {
            return false;
        }
        if (tokens.length == 4 && Objects.equals(tokens[3], "")) {
            return false;
        }
        //check if date is valid
        return true;
    }

    /**
     * Normalize records captured by P1
     */
    public String normalizeP1(String record) {
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = Objects.equals(tokens[3], "") ? "vers Cours de la liberation" : "vers CROUS";
        return "P1" + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalizes records captured by a Camera.
     */
    public String normalize(String record) {
        if (Objects.equals(station,"P1")) {
            return normalizeP1(record);
        }
        return null;
    }
}