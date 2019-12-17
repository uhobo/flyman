package CallJsonP;

import java.util.HashMap;
import java.util.Map;

public enum FieldTypes {
	NUMBER("numeric"),
	STRING("text"),
	TIMESTAMP("timestamp");
	
	private String type;
	private final static Map<String, FieldTypes> lookup= new HashMap<String, FieldTypes>();
	static {
		for(FieldTypes field: FieldTypes.values()) {
			lookup.put(field.getType(), field);
		}
	}
	private FieldTypes(String typeName) {
		this.type = typeName;
	}
	public String getType() {
		return type;
	}
	
	public static FieldTypes getByString(String typeName) {
		return lookup.get(typeName);
	}
}
