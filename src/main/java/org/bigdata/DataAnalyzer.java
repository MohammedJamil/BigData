package org.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.bigdata.analyser.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;

public class DataAnalyzer {

    public static Analyzer initializeAnalyzer(String analysisId) {
        if (Objects.equals(analysisId, "io_per_day")) {
            return new IOPerDayAnalyzer();
        } else if (Objects.equals(analysisId, "io_per_hour")) {
            return new IOPerHourAnalyzer();
        } else if (Objects.equals(analysisId, "io_per_category")) {
            return new IOperCategoryAnalyzer();
        } else if (Objects.equals(analysisId, "io_per_station")) {
            return new IOperStationAnalyzer();
        } else if (Objects.equals(analysisId, "speed_per_category")) {
            return new SpeedPerCatAnalyzer();
        } else if (Objects.equals(analysisId, "category_per_direction")) {
            return new CatPerDirectionAnalyzer();
        } else {
            throw new InvalidParameterException("Invalid analysis id : " + analysisId);
        }
    }

    public static Text produce(String analysisId, Iterable<Text> values) {
        if (Objects.equals(analysisId, "io_per_day")) {
            return IOPerDayAnalyzer.produce(values);
        } else if (Objects.equals(analysisId, "io_per_hour")) {
            return IOPerHourAnalyzer.produce(values);
        } else if (Objects.equals(analysisId, "io_per_category")) {
            return IOperCategoryAnalyzer.produce(values);
        } else if (Objects.equals(analysisId, "io_per_station")) {
            return IOperStationAnalyzer.produce(values);
        } else if (Objects.equals(analysisId, "speed_per_category")) {
            return SpeedPerCatAnalyzer.produce(values);
        } else if (Objects.equals(analysisId, "category_per_direction")) {
            return CatPerDirectionAnalyzer.produce(values);
        } else {
            throw new InvalidParameterException("Invalid analysis id : " + analysisId);
        }
    }

    public static class AnalysisMapper extends Mapper<Object, Text, Text, Text> {
        private Analyzer analyzer;

        @Override
        protected void setup(Context context) {
            Configuration conf = context.getConfiguration();
            String analysisId = conf.get("analysis_id");
            analyzer = DataAnalyzer.initializeAnalyzer(analysisId);
        }

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            analyzer.analyse(line);
            if (analyzer.isToConsider()) {
                context.write(new Text(analyzer.getKey()), new Text(analyzer.getValue()));
            }
        }
    }

    public static class AnalysisReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Configuration conf = context.getConfiguration();
            String analysisId = conf.get("analysis_id");
            Text value = DataAnalyzer.produce(analysisId, values);
            context.write(key, value);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IOException("Not enough arguments !");
        }
        String sequenceFilePath = args[0];
        String outputDirectory = args[1];
        String[] AnalysisArray = {
                "io_per_day",
                "io_per_hour",
                "io_per_category",
                "io_per_station",
                "speed_per_category",
                "category_per_direction"
        };

        for(String analysisId : AnalysisArray) {
            Configuration conf = new Configuration();
            conf.set("analysis_id", analysisId);
            Job job = Job.getInstance(conf, "analysis" + analysisId);
            job.setNumReduceTasks(1);
            job.setJarByClass(DataAnalyzer.class);
            job.setMapperClass(AnalysisMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setReducerClass(AnalysisReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);
            job.setInputFormatClass(SequenceFileInputFormat.class);
            FileInputFormat.addInputPath(job, new Path(sequenceFilePath));
            FileOutputFormat.setOutputPath(job, new Path(outputDirectory + "/" + analysisId));
            job.waitForCompletion(true);
        }
    }
}
