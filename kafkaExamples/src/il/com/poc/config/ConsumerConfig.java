package il.com.poc.config;

import java.util.List;

public class ConsumerConfig {
	private String appName;
	private String consumerName; 
	private String groupName;
	private String cronScheduling;
	private List<String> topicNames;
	private boolean isEnabled;
	
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCronScheduling() {
		return cronScheduling;
	}
	public void setCronScheduling(String cronScheduling) {
		this.cronScheduling = cronScheduling;
	}
	public List<String> getTopicNames() {
		return topicNames;
	}
	public void setTopicNames(List<String> topicNames) {
		this.topicNames = topicNames;
	}
	
	
}
