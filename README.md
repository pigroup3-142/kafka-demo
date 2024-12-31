Build project with mvn : ` mvn clean package` <br />
Run with Spark-submit : `spark-submit --class demo.KafkaSendData --master local[2] target/Kafka-V1-jar-with-dependencies.jar`

Project get data from parquet file with Spark and send to Kafka producer
