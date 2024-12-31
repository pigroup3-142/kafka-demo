package demo;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import decoder.Decoder;
import kafka.ITaskProducer;
import kafka.producer.SimpleProducer;

public class KafkaSendData {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException, ParseException, InterruptedException {
		ITaskProducer producer = new SimpleProducer();
		producer.open();
		
		SparkConf conf = new SparkConf().setAppName("read parquet file");
		SparkContext sc = new SparkContext(conf);
		try (SparkSession spark = new SparkSession(sc)) {
			Dataset<Row> data = spark.read().parquet("/home/trannguyenhan/dataset/data/2021_06_09");
			
			// data get by spark can disturbed, we can sort it
			List<Row> listData = data.filter(data.col("cpa_value").contains("@@@100118")).orderBy("time_create").collectAsList();
					
			for(Row r : listData) {
				long guid = r.getLong(8);
				long timeCreate = r.getLong(0);
				String url = 
						Decoder.decode(r.getString(5));
						//r.getString(5);
						
				JSONObject json = new JSONObject();
				json.put("guid", guid);
				json.put("time", timeCreate);
				json.put("url", url);
				
				producer.send("tiki-2", json.toJSONString());
				Thread.sleep(10000);
				
				System.out.println(json.toJSONString());
			}
		}
		
		producer.close();
	}
}
