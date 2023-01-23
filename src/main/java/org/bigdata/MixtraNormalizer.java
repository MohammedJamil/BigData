package org.bigdata;

import java.security.InvalidParameterException;
import java.util.Objects;

public class MixtraNormalizer implements Normalizer{

    /**
     * Class Attributes
     */
    private final String station;
    private final String direction;

    /**
     * Class Constructor.
     */
    MixtraNormalizer(String station, String fileName) {
        this.station = station;

        if (Objects.equals(fileName, "P9_Vers_Fac_1.csv") || Objects.equals(fileName, "P9_Vers_Fac_2.csv")){
            this.direction = "vers entree";
        }
        else if (Objects.equals(fileName, "P9_Vers_Talence_1.csv") || Objects.equals(fileName, "P9_Vers_Talence_2.csv")){
            this.direction = "vers talence";
        }
        else if (Objects.equals(fileName, "Mixtra_Sortie_Fac_1.csv") || Objects.equals(fileName, "Mixtra_Sortie_Fac_2.csv") || Objects.equals(fileName, "Mixtra_Sortie_Fac_3.csv")){
            this.direction = "vers sortie";
        }
        else if (Objects.equals(fileName, "P19_Entree.csv")){
            this.direction = "vers entree";
        }
        else if (Objects.equals(fileName, "P19_Sortie.csv")){
            this.direction = "vers sortie";
        }
        else if (Objects.equals(fileName, "P23_Vers_BEC_1.csv") || Objects.equals(fileName, "P23_Vers_BEC_2.csv")){
            this.direction = "vers bec";
        }
        else if (Objects.equals(fileName, "P23_Vers_COSEC.csv")){
            this.direction = "vers cosec";
        }
        else if (Objects.equals(fileName, "P24_Vers_Fac.csv")){
            this.direction = "vers entree";
        }
        else if (Objects.equals(fileName, "P24_Vers_Rocade.csv") || Objects.equals(fileName, "P26_Vers_Rocade_1.csv") || Objects.equals(fileName, "P26_Vers_Rocade_2.csv")){
            this.direction = "vers rocade";
        }
        else if (Objects.equals(fileName, "P26_Vers_Fac_1.csv") || Objects.equals(fileName, "P26_Vers_Fac_2.csv") || Objects.equals(fileName, "P26_Vers_Fac_3.csv")){
            this.direction = "vers entree";
        } else {
            throw new InvalidParameterException("File name invalid : " + fileName);
        }
    }

    /**
     *
     */
    private String vehicleCategory(String c) {
        if (Objects.equals(c, "Bus") || Objects.equals(c, "PL") || Objects.equals(c, "Bus articulée")) {
            return "PL";
        } else if (Objects.equals(c, "Vélo") || Objects.equals(c, "2RM") || Objects.equals(c, "Vélo électrique") || Objects.equals(c, "Trottinette") ||Objects.equals(c, "Moto")) {
            return "2R";
        } else {
            return c;
        }
    }

    /**
     * Checks if record is valid.
     */
    public Boolean isValid(String record){
        String[] tokens = record.split(",");

        if (tokens.length != 8){
            return false;
        }

        if (!Objects.equals(tokens[5], "VL") &&
                !Objects.equals(tokens[5], "PL") &&
                !Objects.equals(tokens[5], "Trottinette") &&
                !Objects.equals(tokens[5], "2RM") &&
                !Objects.equals(tokens[5], "Vélo électrique") &&
                !Objects.equals(tokens[5], "Vélo") &&
                !Objects.equals(tokens[5], "Moto") &&
                !Objects.equals(tokens[5], "Bus") &&
                !Objects.equals(tokens[5], "Bus articulée")) {
            return false;
        }

        return true;
    }

    /**
     * Normalizes records captured by a Mixtra sensor.
     */
    public String normalize(String record) {
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[5]);
        String day = ((tokens[0].split("/")[0].length() == 1) ? "0" : "") + tokens[0].split("/")[0];
        String month = ((tokens[0].split("/")[1].length() == 1) ? "0" : "") + tokens[0].split("/")[1];
        String year = "20" + tokens[0].split("/")[2];
        String hour = ((tokens[1].split(":")[0].length() == 1) ? "0" : "") + tokens[1].split(":")[0];
        String minute = ((tokens[1].split(":")[1].length() == 1) ? "0" : "") + tokens[1].split(":")[1];
        String second = ((tokens[2].length() == 1) ? "0" : "") + tokens[2];
        String date = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
        String speed = tokens[4];
        return station + "," + date + "," + category + "," + direction + "," + speed;
    }
}