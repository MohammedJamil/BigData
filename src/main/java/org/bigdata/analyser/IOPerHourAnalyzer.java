package org.bigdata.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class IOPerHourAnalyzer extends Analyzer{
    /**
     * Class constructor.
     */
    public IOPerHourAnalyzer() {}

    /**
     * Analyze to initialize analyzer.
     */
    public void analyse(String record) {
        String[] tokens = record.split(",");
        String hhmmss = tokens[1].split(" ")[1];
        String hour = hhmmss.split(":")[0];
        String io = tokens[5];

        if(Objects.equals(io, "out") || Objects.equals(io, "in")) {
            super.analyzable = true;
            super.key = hour;
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
