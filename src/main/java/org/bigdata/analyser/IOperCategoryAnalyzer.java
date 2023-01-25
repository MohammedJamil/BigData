package org.bigdata.analyser;

import org.apache.hadoop.io.Text;

import java.util.Objects;

public class IOperCategoryAnalyzer extends Analyzer {
    /**
     * Class constructor.
     */
    public IOperCategoryAnalyzer (String record) {
        analyse(record);
    }

    /**
     * Analyze to initialize analyzer.
     */
    private void analyse(String record) {
        String[] tokens = record.split(",");
        String category = tokens[3];
        String io = tokens[6];

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
