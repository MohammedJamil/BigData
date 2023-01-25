package org.bigdata.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class CatPerDirectionAnalyzer extends Analyzer{
    /**
     * Class constructor.
     */
    public CatPerDirectionAnalyzer(String record) {
        analyse(record);
    }

    /**
     * Analyze to initialize analyzer.
     */
    private void analyse(String record) {
        String[] tokens = record.split(",");
        String category = tokens[3];
        String direction = tokens[4];

        super.analyzable = true;
        super.key = direction;
        super.value = category;
    }

    public static Text produce(Iterable<Text> values) {
        int nbVL = 0;
        int nbPL = 0;
        int nb2R = 0;

        for (Text value : values) {
            String category = value.toString();
            if (Objects.equals(category, "VL")) {
                nbVL++;
            } else if (Objects.equals(category, "PL")) {
                nbPL++;
            } else {
                nb2R++;
            }
        }

        return new Text(nbVL + "," + nbPL + "," + nb2R);
    }
}
