package com.imac;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    conf.setInt("mapreduce.job.reduces", 1);
		Job job = Job.getInstance(conf, "WordCount");
//		DistributedCache.addCacheFile(new URI("/user/ubuntu/viewpoint/part-m-00000"),job.getConfiguration());
		job.addCacheFile(new URI("/user/ubuntu/viewpoint/part-m-00000"));
//		DistributedCache.addCacheFile(new URI("hdfs://hadoop:9000/user/hadoop/viewpoint/part-m-00001"),job.getConfiguration());
//		DistributedCache.addCacheFile(new URI("hdfs://hadoop:9000/user/hadoop/viewpoint/part-m-00002"),job.getConfiguration());
//		DistributedCache.addCacheFile(new URI("hdfs://hadoop:9000/user/hadoop/viewpoint/part-m-00003"),job.getConfiguration());
//		DistributedCache.addCacheFile(new URI("hdfs://hadoop:9000/user/scene.txt"),job.getConfiguration());
//		job.addCacheFile(new URI("hdfs://hdfs://10.21.20.51:9000/user/scene:9000/user/scene"));
		job.setJarByClass(WordCount.class);
		job.setMapperClass(mapper.class);
		job.setReducerClass(reducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
}