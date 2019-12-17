package CallJsonP;

public class Dictionary {
	private String fieldHeb;
	private String filedEng;
	private FieldTypes fieldType;
	public String getFieldHeb() {
		return fieldHeb;
	}
	
	
	
	public Dictionary(String fieldHeb, String filedEng, FieldTypes fieldType) {
		super();
		this.fieldHeb = fieldHeb;
		this.filedEng = filedEng;
		this.fieldType = fieldType;
	}



	public void setFieldHeb(String fieldHeb) {
		this.fieldHeb = fieldHeb;
	}
	public String getFiledEng() {
		return filedEng;
	}
	public void setFiledEng(String filedEng) {
		this.filedEng = filedEng;
	}
	public FieldTypes getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldTypes fieldType) {
		this.fieldType = fieldType;
	}



	@Override
	public String toString() {
		return "dictionary.put(\"" + fieldHeb  + "\", new Dictionary(" + fieldHeb + "," + filedEng + "," + fieldType + ");";
	}
	
	
	
	
	
}
