package ServiceGenrator.beans;

import java.util.HashMap;
import java.util.Map;

public class ServiceData {
	String serviceName;
	Map<String, ObjectData> objectDataMap = new HashMap<String, ObjectData>();
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public Map<String, ObjectData> getObjectDataMap() {
		return objectDataMap;
	}
	public void setObjectDataMap(Map<String, ObjectData> objectDataMap) {
		this.objectDataMap = objectDataMap;
	}
	
	
}
