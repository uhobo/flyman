package il.com.poc.basic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

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
	private Integer myId;
	private String groupName;
	
	
	public Consumer(Integer myId, String bootstrapServers, String groupName, List<String> topics) {
		this.myId = myId;
		this.groupName = groupName;
        properties.put("bootstrap.servers", bootstrapServers); 
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "il.com.poc.messages.JsonDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        this.topics.addAll(topics);
	}
	
	protected void finalize () {
		kafkaConsumer.close();
	}
	
	
	
	@SuppressWarnings("rawtypes")
    public void run() {
		Thread.currentThread().setName("Consumer <" + this.myId + "> group <" + this.groupName + ">");
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
						String command = (String)message.getEventDataMap().get("text");
						String[] commandArr = command.split(",");
						if("seek".equals(commandArr[0])) {
							doSeek(commandArr);
						}
						
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

	private void doSeek(String[] commandArr) {
		System.out.println("["+ Thread.currentThread().getName()+ "] seek command on Topic=" +  commandArr[1] + ", Position=" + commandArr[2]);
		Set<TopicPartition> partitionsList = kafkaConsumer.assignment();
		Optional<TopicPartition> myPartition = partitionsList.stream().filter(x -> x.topic().equals(commandArr[1])).findFirst();
		TopicPartition topicPartition = new TopicPartition(myPartition.get().topic(), myPartition.get().partition());
		long myPosition = kafkaConsumer.position(topicPartition);
		System.out.println("["+ Thread.currentThread().getName()+ "] my Partition Number= " + myPartition.get().partition() + " at Position=" + myPosition);
		long newPosition = Long.valueOf(commandArr[2]);
		if(newPosition > myPosition) {
			System.out.println("["+ Thread.currentThread().getName()+ "] Error invalid  input new Position=");
			return;
		}
		
		kafkaConsumer.seek(topicPartition, newPosition);
	}

	private String getMyInfo() {
		StringBuilder builder = new StringBuilder();
		return builder.append("[").append(Thread.currentThread().getName()).append("]").append(" groupMetadata:").append(kafkaConsumer.groupMetadata().toString()).toString();
	}
}