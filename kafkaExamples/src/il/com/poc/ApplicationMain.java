package il.com.poc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;

import il.com.poc.basic.AdminKafka;
import il.com.poc.basic.Consumer;
import il.com.poc.basic.Producer;
import il.com.poc.messages.EventMessage;

/**
 * https://github.com/smartloli/EFAK 
 * monitor kafka
 * @author user
 *
 */
public class ApplicationMain {
	static final String bootstrapServers = "localhost:9092";
	static AdminKafka adminKafka =  new AdminKafka(bootstrapServers);
	
	static ExecutorService executor = Executors.newWorkStealingPool();
	//ScheduledExecutorService
	static AdminClient  adminClient;
	static Scanner scanner = new Scanner(System.in); 
	
	static Map<String, Producer> producerMap = new HashMap<>();
	
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		adminClient = AdminClient.create(properties);
		
		
		int choice = 0;
		do {
			System.out.println("Hello to Kafka examples!!!");
			System.out.println("1. List of topics");
			System.out.println("2. create a Topic");
			System.out.println("3. Run consumer");
			System.out.println("4. send a message");
			System.out.println("5. exit");
			
			choice = scanner.nextInt();
			try {
				switch(choice) {
					case 1: listTopics(); break;
					case 2: createTopic();break;
					case 3: createConsumer();break;
					case 4: sendEvent();break;
					default: continue;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}while(choice != 5);
		
		scanner.close();	
			
		
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
	}
	
	
	
	private static Object listTopics()  {
		
		Map<String, Map<String, List<String>>> topicDescriptionMap= new HashMap<String, Map<String,List<String>>>();
		
		try {
			//Here you get all the consumer groups
			List<String> groupIds = adminClient.listConsumerGroups().all().get().
			                       stream().map(s -> s.groupId()).collect(Collectors.toList()); 
	
			//Here you get all the descriptions for the groups
			Map<String, ConsumerGroupDescription> groups = adminClient.
			                                               describeConsumerGroups(groupIds).all().get();
			for (final String groupId : groupIds) {
			    ConsumerGroupDescription descr = groups.get(groupId);
			    descr.members().stream().forEach(memberDescription -> memberDescription.assignment().topicPartitions().stream().forEach(topic -> {
			    	Map<String, List<String>> entry = topicDescriptionMap.get(topic.topic());	
			    
			    	if(entry == null) {
			    		entry = new HashMap<String, List<String>>();
			    		topicDescriptionMap.put(topic.topic(), entry);
			    	}
			    	List<String> consumerList = entry.get(groupId);
			    	if(consumerList == null) {
			    		consumerList = new ArrayList<String>();
			    	}
			    	consumerList.add(memberDescription.clientId());
			    	entry.put(groupId, consumerList);
			    	
			    }));
			} 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		topicDescriptionMap.keySet().forEach( topic -> {
			builder.append("Topic: ").append(topic).append("\n");
			Map<String, List<String>> groupMap = topicDescriptionMap.get(topic);
			groupMap.keySet().forEach(groupName -> {
				builder.append("\tGroupName: ").append(groupName).append("\n");
				List<String> consumerList = groupMap.get(groupName);
				consumerList.forEach(clientId -> {
					builder.append("\t\tClientId: ").append(clientId).append("\n");
				});
				
			});
			
		});
		
		return null;
	}

	public static void sendEvent() throws InterruptedException, ExecutionException {
		scanner = new Scanner(System.in);
		System.out.println("Type Topic Name[test1]:");
		
		
		String topicName = scanner.nextLine();
		if(topicName == null || topicName.isEmpty()) {
			topicName = "test1";
		}
		
		
		if(!adminKafka.isTopicExist(topicName)) {
			System.out.println("Topic is not exist");
			return;
		}
		
		Producer producer = producerMap.get(topicName);
		if(producer == null) {
			producer = new Producer(bootstrapServers);
			producerMap.put(topicName, producer);
		}
		
		System.out.println("Type text data:");
		String message = scanner.nextLine();
		
		EventMessage eventMessage = new EventMessage("infoMessage");
		eventMessage.getEventDataMap().put("text", message);
		producer.send(topicName, eventMessage, 1);
		//scanner.close();	
	}
	
	public static void createConsumer() throws InterruptedException, ExecutionException {
		scanner = new Scanner(System.in);
		
		System.out.println("Type Topic Name[test1]:");
		String topicName = scanner.nextLine();
		if(topicName == null || topicName.isEmpty()) {
			topicName = "test1";
		}
		
		ListConsumerGroupsResult groupList = adminClient.listConsumerGroups();
		KafkaFuture<Collection<ConsumerGroupListing>> listTemp = groupList.all();
		
		System.out.println("List of Groups:");
		for (ConsumerGroupListing rr : listTemp.get()) {
			System.out.println(rr.groupId());
			//DescribeConsumerGroupsResult result = adminClient.describeConsumerGroups(Arrays.asList(rr.groupId()));
			//result.describedGroups().
		}
		
		System.out.println("Type group id:");
		String groupId = scanner.nextLine();
		executor.execute(new Consumer(bootstrapServers, groupId, Arrays.asList(topicName)));
		//scanner.close();	
	}
	
	
	public static void createTopic() {
		scanner = new Scanner(System.in); 
		System.out.println("Type Topic Name:");
		String topicName = scanner.nextLine();
		System.out.println("Type Topic number of Partitions [1]:");
		int numPartitions = scanner.nextInt();
		if(numPartitions < 1) {
			numPartitions = 1;
		}
		adminKafka.createTopics(topicName, numPartitions);
		//scanner.close();	
	}

}
