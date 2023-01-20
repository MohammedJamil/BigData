package org.bigdata;

import java.io.IOException;
import java.util.Objects;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

enum StationType {CAMERA, MIXTRA, VIKINGS, TAGMASTER, UNKNOWN}

public class DataNormalizer {

    /**
     * Class Attributes
     */
    public static Normalizer normalizer = initializeNormalizer("P1");
    public static String directoryPath;

    /**
     * Class Constructor
     */
    public static Normalizer initializeNormalizer(String station) {
        switch (stationType(station)) {
            case CAMERA :
                return new CameraNormalizer(station);
            case MIXTRA:
                return new MixtraNormalizer();
            case VIKINGS:
                return new VikingNormalizer();
            case TAGMASTER:
                return new TagMasterNormalizer();
            default:
                return null;
        }
    }

    /**
     * Tells if stations is in arrStation.
     */
    private static Boolean Exists(String station, String[] arrStation) {
        for (String s : arrStation) {
            if (Objects.equals(station, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns Station Type.
     */
    private static StationType stationType(String station) {
        String[] cameraStations = {"P1", "P10", "P12", "P13", "P20"};
        String[] mixtraStations = {"P3", "P9", "P19", "P23", "P24", "P26"};
        String[] vikingStations = {"P4", "P5", "P17"};
        String[] tagMasterStations = {"P2"};
        String[] allStations = {
                "P1", "P3", "P4", "P5", "P9", "P10", "P12", "P13", "P15", "P17", "P19", "P20", "P23", "P24", "P26"
        };

        if (Exists(station, cameraStations)) {
            return StationType.CAMERA;
        }
        if (Exists(station, mixtraStations)) {
            return StationType.MIXTRA;
        }
        if (Exists(station, vikingStations)) {
            return StationType.VIKINGS;
        }
        if (Exists(station, tagMasterStations)) {
            return StationType.TAGMASTER;
        }
        return StationType.UNKNOWN;
    }

    public static class myMapper extends Mapper<Object, Text, NullWritable, Text> {
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (DataNormalizer.normalizer.isValid(line)) {
                context.write(NullWritable.get(), new Text(DataNormalizer.normalizer.normalize(line)));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IOException("Not enough arguments !");
        }
        DataNormalizer.directoryPath = args[0];

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Good Job");
        job.setNumReduceTasks(1);
        job.setJarByClass(DataNormalizer.class);
        job.setMapperClass(myMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(Reducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0] + "P1" + "/" + "P1.csv"));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
