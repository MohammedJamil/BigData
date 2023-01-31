package org.traffic.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class SpeedPerCatAnalyzer extends Analyzer{
    /**
     * Class constructor.
     */
    public SpeedPerCatAnalyzer() {}

    /**
     * Analyze to initialize analyzer.
     */
    public void analyse(String record) {
        String[] tokens = record.split(",");
        String category = tokens[2];
        String speed = tokens[4];

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
