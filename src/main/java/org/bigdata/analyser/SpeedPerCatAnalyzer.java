package org.bigdata.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class SpeedPerCatAnalyzer extends Analyzer{
    /**
     * Class constructor.
     */
    public SpeedPerCatAnalyzer (String record) {
        analyse(record);
    }

    /**
     * Analyze to initialize analyzer.
     */
    private void analyse(String record) {
        String[] tokens = record.split(",");
        String category = tokens[3];
        String speed = tokens[5];

        super.analyzable = true;
        super.key = category;
        super.value = speed;

    }

    public static Text produce(Iterable<Text> values) {
        int nbValues = 0;
        double speedMean = 0;

        for (Text value : values) {
            String strSpeed = value.toString();
            if (!Objects.equals(strSpeed, "")) {
                int speed = Integer.parseInt(strSpeed);
                speedMean += speed;
                nbValues++;
            }
        }

        speedMean = speedMean/nbValues;

        return new Text(String.valueOf(speedMean));
    }
}
