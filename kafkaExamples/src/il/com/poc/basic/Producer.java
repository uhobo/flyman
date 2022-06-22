package il.com.poc.basic;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import il.com.poc.messages.EventMessage;

public class Producer implements Runnable {
	private KafkaProducer kafkaProducer;
	
	
	public Producer(String bootstrapServers) {
		Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "il.com.poc.messages.JsonSerializer");
        kafkaProducer = new KafkaProducer(properties);
	}
	 
	protected void finalize () {
		 kafkaProducer.close();
	}
	
	public void send( String topicName, EventMessage event, Integer count){
       
        try{
            for(int i = 0; i < count; i++){
                System.out.println(i);
                kafkaProducer.send(new ProducerRecord(topicName, Integer.toString(i), event ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
