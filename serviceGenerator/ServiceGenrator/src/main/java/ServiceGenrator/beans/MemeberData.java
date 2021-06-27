package ServiceGenrator.beans;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MemeberData {
	String typeStr;
	String name;
	boolean mandatory;
	String defaultVal;
	String exmpleVal;
	
	final static Map<String, Class<?>> javaObjectMap = new HashMap<String, Class<?>>();
	static {
		javaObjectMap.put("STRING", String.class);
		javaObjectMap.put("BOOLEAN", boolean.class);
		javaObjectMap.put("BOOL", boolean.class);
		javaObjectMap.put("INTEGER", Integer.class);
		javaObjectMap.put("LONG", Long.class);
		javaObjectMap.put("BIGDECIMAL", BigDecimal.class);
		javaObjectMap.put("DATE", String.class);
	}
	
	public static Object createJavaObject(String type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> clazz = javaObjectMap.get(type.toUpperCase());
		return clazz != null ? clazz.getDeclaredConstructor().newInstance() : null;
	}
	
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getExmpleVal() {
		return exmpleVal;
	}
	public void setExmpleVal(String exmpleVal) {
		this.exmpleVal = exmpleVal;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	
	
}
