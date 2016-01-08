package com.imac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();
	private Text item_id = new Text();
//	private ArrayList<Integer> arrayListValue = new ArrayList<Integer>();
	private HashMap<String, Integer>masHashMap = new HashMap<String, Integer>();
	int index = 1;
	
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}
		if(!key.toString().trim().equals("")){
			masHashMap.put(key.toString(),sum);
		}
//		item_id.set(key.toString()+","+sum);
//		context.write(item_id, null);
	}
	
	
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		try{
			Map<String, Integer> hasMap = sortByValue(masHashMap);
			Iterator<String> iterator = hasMap.keySet().iterator();
			for (int i = 0; i<10; i++) {
				String key = iterator.next();
				result.set(hasMap.get(key));
				item_id.set(key+","+hasMap.get(key));
				context.write(item_id, null);
			}
		}catch(Exception e){
			
		}
		
	}
	
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		LinkedList<java.util.Map.Entry<K, V>> list = new LinkedList<>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
}
