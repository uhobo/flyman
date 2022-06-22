package il.com.poc.basic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;

import il.com.poc.messages.EventMessage;



public class Consumer implements Runnable {
	private KafkaConsumer<?, ?> kafkaConsumer;
	private Properties properties = new Properties();
	private List<String> topics = new ArrayList<String>();
	
	
	
	public Consumer(String bootstrapServers, String groupName, List<String> topics) {
        properties.put("bootstrap.servers", bootstrapServers); 
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "il.com.poc.messages.JsonDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        this.topics.addAll(topics);
	}
	
	protected void finalize () {
		kafkaConsumer.close();
	}
	
	
	
	@SuppressWarnings("rawtypes")
    public void run() {
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, Thread.currentThread().getName());
		kafkaConsumer = new KafkaConsumer(properties);
		kafkaConsumer.subscribe(topics);
		
		System.out.println(getMyInfo() + ": Starting consume..."  );

        try{
            while (true){
				try {
	            	ConsumerRecords records = kafkaConsumer.poll(Duration.ofSeconds(10) );
					 Iterator itr = records.iterator(); 
					while(itr.hasNext()) {
						ConsumerRecord record = (ConsumerRecord)itr.next();
						EventMessage message = (EventMessage) record.value();
						System.out.println(String.format("%s Topic - %s, Partition - %d", getMyInfo(), record.topic(), record.partition()));
						System.out.println(message);
						
					}
				}catch (SerializationException  e){
		        	
		        	String s = e.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
		            String topics = s.split("-")[0];
		            int offset = Integer.valueOf(s.split("offset ")[1]);
		            int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);

		            TopicPartition topicPartition = new TopicPartition(topics, partition);
		            //log.info("Skipping " + topic + "-" + partition + " offset " + offset);
		            kafkaConsumer.seek(topicPartition, offset + 1);
		        }
				
            }
        }catch (Exception e) {
        	
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }

	private String getMyInfo() {
		StringBuilder builder = new StringBuilder();
		return builder.append("[").append(Thread.currentThread().getName()).append("]").append(" groupMetadata:").append(kafkaConsumer.groupMetadata().toString()).toString();
	}
}