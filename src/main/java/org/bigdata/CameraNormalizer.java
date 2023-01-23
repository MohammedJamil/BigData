package org.bigdata;

import java.util.Objects;

public class CameraNormalizer implements Normalizer {

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
        if (Objects.equals(c, "PL_1") || Objects.equals(c, "PL_2") || Objects.equals(c, "UT") || Objects.equals(c, "BUS")) {
            return "PL";
        } else if (Objects.equals(c, "MOTO") || Objects.equals(c, "VELO") || Objects.equals(c, "EDPM")) {
            return "2R";
        } else {
            return c;
        }
    }

    private boolean isInvalidDirectionTokenP20(String token) {
         return !Objects.equals(token, "E") &&
                 !Objects.equals(token, "S1") &&
                 !Objects.equals(token, "S2") &&
                 !Objects.equals(token, "S3");
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

        if (!Objects.equals(station, "P20") && tokens.length == 5 && Objects.equals(tokens[3], "") && Objects.equals(tokens[4], "")){
            return false;
        }
        if (!Objects.equals(station, "P20") && tokens.length == 5 && !Objects.equals(tokens[3], "") && !Objects.equals(tokens[4], "")) {
            return false;
        }
        if (!Objects.equals(station, "P20") && tokens.length == 4 && Objects.equals(tokens[3], "")) {
            return false;
        }

        //P20 condition added
        if (Objects.equals(station, "P20") && isInvalidDirectionTokenP20(tokens[3]) && isInvalidDirectionTokenP20(tokens[4])){
            return false;
        }

        return true;
    }

    /**
     * Normalize records captured by P1
     */
    public String normalizeP1(String record) {
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = Objects.equals(tokens[3], "") ? "vers cours de la liberation" : "vers crous";
        return station + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalize records captured by P10
     */
    public String normalizeP10(String record){
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = Objects.equals(tokens[3], "") ? "vers sortie fac" : "vers entree fac";
        return station + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalize records captured by P12
     */
    public String normalizeP12(String record){
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = Objects.equals(tokens[3], "") ? "vers sortie fac" : "vers entree fac";
        return station + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalize records captured by P13
     */
    public String normalizeP13(String record){
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = Objects.equals(tokens[3], "") ? "vers carrefour à feux av Roul" : "vers bibliotheque";
        return station + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalize records captured by P20
     */
    public String normalizeP20(String record){
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[1]);
        String date = tokens[2].split("\\.")[0];
        String direction = "";
        if (Objects.equals(tokens[4],"E")){
            direction="entree du site";
        }
        else if(Objects.equals(tokens[4],"S1")){
            direction="vers parking cafeteria";
        }
        else if (Objects.equals(tokens[4],"S2")){
            direction="vers avenue leon duguit";
        }
        else if(Objects.equals(tokens[4],"S3")){
            direction="vers parking des professeurs";
        }
        return station + "," + date + "," + category + "," + direction + "," + "";
    }

    /**
     * Normalizes records captured by a Camera.
     */
    public String normalize(String record) {
        if (Objects.equals(station,"P1")) {
            return normalizeP1(record);
        }
        else if (Objects.equals(station,"P10")) {
            return normalizeP10(record);
        }
        else if (Objects.equals(station,"P12")) {
            return normalizeP12(record);
        }
        else if (Objects.equals(station,"P13")) {
            return normalizeP13(record);
        }
        else {
            return normalizeP20(record);
        }
    }
}