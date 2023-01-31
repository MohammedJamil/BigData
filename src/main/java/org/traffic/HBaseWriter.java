package org.traffic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;

import java.io.IOException;
import java.security.InvalidParameterException;

public class HBaseWriter {

    public static final String[] ANALYSIS = {
            "io_per_day",
            "io_per_hour",
            "io_per_category",
            "io_per_station",
            "speed_per_category",
            "category_per_direction",
    };


    public static final String[] TABLES = {
            "InOutPerDay",
            "InOutPerHour",
            "InOutPerCategory",
            "InOutPerStation",
            "SpeedPerCategory",
            "CategoryPerDirection",
    };

    public static final String NAMESPACE = "mjamil:";

    /**
     * Class Constructor
     */
    HBaseWriter() {}

    public static class IOReducer extends TableReducer<Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text val : values) {
                String[] tokens = val.toString().split(",");
                int nbIn = Integer.parseInt(tokens[0]);
                int nbOut = Integer.parseInt(tokens[1]);
                Put put = new Put(key.getBytes());
                put.addColumn(Bytes.toBytes("IN_Number"), Bytes.toBytes("IN_Number"), Bytes.toBytes(String.valueOf(nbIn)));
                put.addColumn(Bytes.toBytes("OUT_Number"), Bytes.toBytes("OUT_Number"), Bytes.toBytes(String.valueOf(nbOut)));
                context.write(new Text(key.toString()), put);
            }
        }
    }

    public static class SpeedReducer extends TableReducer<Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text val : values) {
                String speed = val.toString();
                Put put = new Put(key.getBytes());
                put.addColumn(Bytes.toBytes("Speed"), Bytes.toBytes("Speed"), Bytes.toBytes(speed));
                context.write(new Text(key.toString()), put);
            }
        }
    }

    public static class CategoryReducer extends TableReducer<Text, Text, Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text val : values) {
                String[] tokens = val.toString().split(",");
                int nbVL = Integer.parseInt(tokens[0]);
                int nbPL = Integer.parseInt(tokens[1]);
                int nb2R = Integer.parseInt(tokens[2]);
                Put put = new Put(key.getBytes());
                put.addColumn(Bytes.toBytes("VL_Number"), Bytes.toBytes("VL_Number"), Bytes.toBytes(String.valueOf(nbVL)));
                put.addColumn(Bytes.toBytes("PL_Number"), Bytes.toBytes("PL_Number"), Bytes.toBytes(String.valueOf(nbPL)));
                put.addColumn(Bytes.toBytes("2R_Number"), Bytes.toBytes("2R_Number"), Bytes.toBytes(String.valueOf(nb2R)));
                context.write(new Text(key.toString()), put);
            }
        }
    }

    public static void createTable(Connection connect, String tableName) {
        try {
            final Admin admin = connect.getAdmin();
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(NAMESPACE + tableName));

            switch (tableName) {
                case "InOutPerDay":
                    ColumnFamilyDescriptor columnDayDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("Day".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnDayDescriptor);
                    ColumnFamilyDescriptor columnInDescriptor1 = ColumnFamilyDescriptorBuilder.newBuilder("IN_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnInDescriptor1);
                    ColumnFamilyDescriptor columnOutDescriptor1 = ColumnFamilyDescriptorBuilder.newBuilder("OUT_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnOutDescriptor1);
                    break;
                case "InOutPerHour":
                    ColumnFamilyDescriptor columnHourDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("Hour".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnHourDescriptor);
                    ColumnFamilyDescriptor columnInDescriptor2 = ColumnFamilyDescriptorBuilder.newBuilder("IN_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnInDescriptor2);
                    ColumnFamilyDescriptor columnOutDescriptor2 = ColumnFamilyDescriptorBuilder.newBuilder("OUT_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnOutDescriptor2);
                    break;
                case "InOutPerCategory":
                    ColumnFamilyDescriptor columnCategoryDescriptor1 = ColumnFamilyDescriptorBuilder.newBuilder("Category".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnCategoryDescriptor1);
                    ColumnFamilyDescriptor columnInDescriptor3 = ColumnFamilyDescriptorBuilder.newBuilder("IN_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnInDescriptor3);
                    ColumnFamilyDescriptor columnOutDescriptor3 = ColumnFamilyDescriptorBuilder.newBuilder("OUT_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnOutDescriptor3);
                    break;
                case "InOutPerStation":
                    ColumnFamilyDescriptor columnStationDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("Station".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnStationDescriptor);
                    ColumnFamilyDescriptor columnInDescriptor4 = ColumnFamilyDescriptorBuilder.newBuilder("IN_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnInDescriptor4);
                    ColumnFamilyDescriptor columnOutDescriptor4 = ColumnFamilyDescriptorBuilder.newBuilder("OUT_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnOutDescriptor4);
                    break;
                case "SpeedPerCategory":
                    ColumnFamilyDescriptor columnCategoryDescriptor2 = ColumnFamilyDescriptorBuilder.newBuilder("Category".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnCategoryDescriptor2);
                    ColumnFamilyDescriptor columnSpeedDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("Speed".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnSpeedDescriptor);
                    break;
                case "CategoryPerDirection":
                    ColumnFamilyDescriptor columnDirectionDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("Direction".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnDirectionDescriptor);
                    ColumnFamilyDescriptor columnVLDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("VL_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnVLDescriptor);
                    ColumnFamilyDescriptor columnPLDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("PL_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(columnPLDescriptor);
                    ColumnFamilyDescriptor column2RDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("2R_Number".getBytes()).build();
                    tableDescriptorBuilder.setColumnFamily(column2RDescriptor);
                    break;
                default:
                    throw new InvalidParameterException("Invalid table name : " + NAMESPACE + tableName);
            }
            TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
            if (admin.tableExists(TableName.valueOf(NAMESPACE + tableName))) {
                admin.disableTable(TableName.valueOf(NAMESPACE + tableName));
                admin.deleteTable(TableName.valueOf(NAMESPACE + tableName));
            }
            admin.createTable(tableDescriptor);
            admin.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main (String[] args) throws Exception {
        if (args.length < 1) {
            throw new IOException("Not enough arguments !");
        }
        String analysisPath = args[0];

        for(int i = 0; i < ANALYSIS.length; i++) {
            String analysisDirectory = ANALYSIS[i];
            String tableName = TABLES[i];
            String InputPath = analysisPath + "/" + analysisDirectory + "/part-r-00000";
            Configuration conf = HBaseConfiguration.create();
            Job job = Job.getInstance(conf, "HBaseWriter " + tableName);
            job.setJarByClass(HBaseWriter.class);
            Connection connection = ConnectionFactory.createConnection(conf);
            createTable(connection, tableName);
            FileInputFormat.addInputPath(job, new Path(InputPath));
            job.setInputFormatClass(SequenceFileInputFormat.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            switch (analysisDirectory) {
                case "io_per_day":
                case "io_per_hour":
                case "io_per_category":
                case "io_per_station":
                    TableMapReduceUtil.initTableReducerJob(NAMESPACE + tableName, IOReducer.class, job);
                    break;
                case "speed_per_category":
                    TableMapReduceUtil.initTableReducerJob(NAMESPACE + tableName, SpeedReducer.class, job);
                    break;
                case "category_per_direction":
                    TableMapReduceUtil.initTableReducerJob(NAMESPACE + tableName, CategoryReducer .class, job);
                    break;
                default:
                    throw new InvalidParameterException("Invalid table name : " + tableName);
            }

            job.waitForCompletion(true);
        }
    }
}
