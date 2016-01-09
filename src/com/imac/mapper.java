package com.imac;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;

public class mapper extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	private ArrayList<String> arrayListValue = new ArrayList<String>();
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] token = value.toString().split(",");
		for (String keyWord : token) {
			for (String scene : arrayListValue) {
				if (keyWord.contains(scene)) {
					word.set(scene);
					context.write(word, one);
				}
			}
		}
	}

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// URI[] localCacheFiles = context.getCacheFiles();
		Path[] cacheFile = context.getLocalCacheFiles();
//		for(Path mPath : cacheFile){
//			partition++;
//			String data = readFile(mPath.toString());
//			
//			String[] array_scene = data.split(",");
//			for (int i=1;i<array_scene.length ; i++) {
//				if(i>=3 && i<array_scene.length-2 && i%2==1){
//					arrayListValue.add(array_scene[i].replaceAll("\\s+", ""));
//				}
				
		 readFile(cacheFile[0].toString());
	}
	
	private void readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line=br.readLine()) != null) {
			arrayListValue.add(line.split(",")[1].trim().replaceAll("\\s+", ""));
		}
	}
}
