package il.com.poc.messages;

import java.util.HashMap;
import java.util.Map;

public class EventMessage {
	private String eventName;
	private Map<String, Object> eventDataMap;
	public EventMessage() {
		
	}
	public EventMessage(String eventName) {
		this.eventName = eventName;
		this.eventDataMap = new HashMap<String, Object>();
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Map<String, Object> getEventDataMap() {
		return eventDataMap;
	}

	public void setEventDataMap(Map<String, Object> eventDataMap) {
		this.eventDataMap = eventDataMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[" + Thread.currentThread().getName() + "] EventMessage [eventName=").append(eventName).append(", eventDataMap=");
		eventDataMap.forEach((key, value) -> builder.append(key + ":" + value));
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
