package il.com.examples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
	 

	public static void main(String[] args) {
	        Properties properties = new Properties();
	        properties.put("bootstrap.servers", "localhost:9092");
	        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        properties.put("group.id", "test-group");
	        
	        
	        @SuppressWarnings("rawtypes")
			KafkaConsumer<?, ?> kafkaConsumer = new KafkaConsumer(properties);
	        List<String> topics = new ArrayList<String>();
	        topics.add("devglan-test");
	        kafkaConsumer.subscribe(topics);
	        try{
	            while (true){
	                
					Duration timeout = Duration.ofSeconds(1);
					ConsumerRecords<?, ?> records = kafkaConsumer.poll(timeout );
					
					for (ConsumerRecord record: records){
	                  System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
	               }
	            }
	        }catch (Exception e){
	            System.out.println(e.getMessage());
	        }finally {
	            kafkaConsumer.close();
	        }
	    }
}
