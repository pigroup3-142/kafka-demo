package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import kafka.ITaskProducer;
import kafka.properties.KafkaProperties;

public class SimpleProducer implements ITaskProducer {
	public Producer<String, String> producer;
	
	/**
	 * Open Producer 
	 */
	@Override
	public void open() {
		producer = new KafkaProducer<String, String>(KafkaProperties.getInstance());
	}
	
	/**
	 * Send Product to topic of kafka
	 * @param product
	 */
	@Override
	public void send(String topic, String data) {
		if(data == null) {
			throw new NullPointerException("Producer no initialization");
		}
		
		producer.send(new ProducerRecord<String, String>(topic, "key", data));
		System.out.println("successfully send " + data.toString());
	}
	
	/**
	 * Close Producer 
	 */
	@Override
	public void close() {
		producer.close();
	}


}
