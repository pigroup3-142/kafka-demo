package kafka;


public interface ITaskProducer extends ITask{
	/**
	 * data is json string
	 * if want modify type of data, you can extends ITask and edit KafkaProperties class
	 */
	void send(String topic, String data);
}
