package org.bigdata.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class IOPerDayAnalyzer extends Analyzer{
    /**
     * Class constructor.
     */
    public IOPerDayAnalyzer (String record) {
        analyse(record);
    }

    /**
     * Analyze to initialize analyzer.
     */
    private void analyse(String record) {
        String[] tokens = record.split(",");
        String date = tokens[2].split(" ")[0];
        String io = tokens[6];

        if(Objects.equals(io, "out") || Objects.equals(io, "in")) {
            super.analyzable = true;
            super.key = date;
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
