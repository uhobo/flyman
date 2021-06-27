package ServiceGenrator.beans;

import java.util.ArrayList;
import java.util.List;

public class ObjectData {
	String name;
	List<MemeberData> memebers = new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MemeberData> getMemebers() {
		return memebers;
	}

	public void setMemebers(List<MemeberData> memebers) {
		this.memebers = memebers;
	}
	
	
	
}
