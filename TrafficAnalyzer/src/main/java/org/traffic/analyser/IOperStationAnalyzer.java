package org.traffic.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class IOperStationAnalyzer extends Analyzer {
    /**
     * Class constructor.
     */
    public IOperStationAnalyzer() {}

    /**
     * Analyze to initialize analyzer.
     */
    public void analyse(String record) {
        String[] tokens = record.split(",");
        String station = tokens[0];
        String io = tokens[5];

        if(Objects.equals(io, "out") || Objects.equals(io, "in")) {
            super.analyzable = true;
            super.key = station;
            super.value = io;
        } else {
            super.analyzable = false;
        }
    }

    public static Text produce(Iterable<Text> values) {
        int numberIn = 0;
        int numberOut = 0;

        for (Text value : values) {
            String io = value.toString();
            if (Objects.equals(io, "in")) {
                numberIn++;
            } else {
                numberOut++;
            }
        }

        return new Text(numberIn + "," + numberOut);
    }
}
