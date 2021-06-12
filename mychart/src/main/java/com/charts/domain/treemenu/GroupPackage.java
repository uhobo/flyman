package com.charts.domain.treemenu;

import java.util.ArrayList;
import java.util.List;

public class GroupPackage {
	private String groupName;
	private List<Integer> groupMemebers = new ArrayList<>();

	public List<Integer> getGroupMemebers() {
		return groupMemebers;
	}

	public void setGroupMemebers(List<Integer> groupMemebers) {
		this.groupMemebers = groupMemebers;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	} 
	
	
}
