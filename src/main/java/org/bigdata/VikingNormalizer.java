package org.bigdata;

import java.util.Objects;

public class VikingNormalizer implements Normalizer{

    /**
     * Class Attributes
     */
    private final String station;

    /**
     * Class Constructor.
     */
    VikingNormalizer(String station) {
        this.station = station;
    }

    private String vehicleCategory(String c) {
        if (Objects.equals(c, "PL/Bus")) {
            return "PL";
        } else if (Objects.equals(c, "Vélo") || Objects.equals(c, "2RM")) {
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

        if (tokens.length != 7){
            return false;
        }

        if(!tokens[4].contains("=") || tokens[4].length() != 5) {
            return false;
        }

        if(tokens[3].length() != 3 && tokens[3].length() != 4) {
            return false;
        }

        if(tokens[2].length() != 3 && tokens[2].length() != 4) {
            return false;
        }

        return true;
    }

    /**
     * Normalizes records captured by a Viking sensor.
     */
    public String normalize(String record) {
        String[] tokens = record.split(",");
        String category = vehicleCategory(tokens[6]);
        String day = (tokens[1].length() == 1) ? "0" + tokens[1] : tokens[1];
        String hour = (tokens[2].length() == 3) ? "0" + tokens[2].charAt(0) : tokens[2].substring(0, 2);
        String minute = (tokens[2].length() == 3) ? tokens[2].substring(1) : tokens[2].substring(2);
        String second = (tokens[3].length() == 3) ? "0" + tokens[3].charAt(0) : tokens[3].substring(0, 2);
        String date = day + "/10/2022" + " " + hour + ":" + minute + ":" + second;
        String direction;
        if (Objects.equals(station, "P4")) {
            direction = (Objects.equals(tokens[0], "2")) ? "vers sortie" : "vers entree";
        } else if (Objects.equals(station, "P5")) {
            direction = (Objects.equals(tokens[0].split("")[0],"E")) ? "vers entree" : "vers sortie";
        } else {
            direction = (Objects.equals(tokens[0], "1")) ? "vers avenue schweitzer" : "vers p16";
        }
        String speed = tokens[4].substring(2);
        return station + "," + date + "," + category + "," + direction + "," + speed;
    }
}