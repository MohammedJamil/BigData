package org.traffic;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Objects;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.traffic.normalizer.*;

enum StationType {CAMERA, MIXTRA, VIKINGS, TAGMASTER, UNKNOWN}

public class DataNormalizer {

    public static Normalizer initializeNormalizer(String station, String fileName) {
        switch (stationType(station)) {
            case CAMERA :
                return new CameraNormalizer(station);
            case MIXTRA:
                return new MixtraNormalizer(station, fileName);
            case VIKINGS:
                return new VikingNormalizer(station);
            case TAGMASTER:
                return new TagMasterNormalizer(station);
            default:
                throw new InvalidParameterException("Invalid Station : " + station);
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
        // String[] allStations = {"P1", "P3", "P4", "P5", "P9", "P10", "P12", "P13", "P15", "P17", "P19", "P20", "P23", "P24", "P26"};

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

    public static class NormalizationMapper extends Mapper<Object, Text, NullWritable, Text> {
        private String fileName;
        private Normalizer normalizer;

        @Override
        protected void setup(Context context){
            InputSplit split = context.getInputSplit();
            if (split instanceof FileSplit) {
                Path filePath = ((FileSplit) split).getPath();
                fileName = filePath.getName();
            }
            Configuration conf = context.getConfiguration();
            String station = conf.get(fileName);
            normalizer  = DataNormalizer.initializeNormalizer(station, fileName);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (normalizer.isValid(line)) {
                context.write(NullWritable.get(), new Text(normalizer.normalize(line)));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IOException("Not enough arguments !");
        }

        String directoryPath = args[0];
        Configuration conf = new Configuration();

        try {
            InputStream in = DataNormalizer.class.getClassLoader().getResourceAsStream("paths.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineTokens = line.split(",");
                String station = lineTokens[0];
                conf.set(lineTokens[1], station);
            }

            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Job job = Job.getInstance(conf, "Good Job");
        job.setNumReduceTasks(1);
        job.setJarByClass(DataNormalizer.class);
        job.setMapperClass(NormalizationMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(Reducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setInputFormatClass(TextInputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        try {
            InputStream in = DataNormalizer.class.getClassLoader().getResourceAsStream("paths.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineTokens = line.split(",");
                String filePath = directoryPath + lineTokens[0] + "/" + lineTokens[1];
                FileInputFormat.addInputPath(job, new Path(filePath));
            }

            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
