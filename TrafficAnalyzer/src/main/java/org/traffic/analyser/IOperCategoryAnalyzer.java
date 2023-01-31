package org.traffic.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class IOperCategoryAnalyzer extends Analyzer {
    /**
     * Class constructor.
     */
    public IOperCategoryAnalyzer() {}

    /**
     * Analyze to initialize analyzer.
     */
    public void analyse(String record) {
        String[] tokens = record.split(",");
        String category = tokens[2];
        String io = tokens[5];

        if(Objects.equals(io, "out") || Objects.equals(io, "in")) {
            super.analyzable = true;
            super.key = category;
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
