package com.charts.service.handlers.meun;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.charts.domain.DataLine;
import com.charts.domain.FileData;
import com.charts.domain.charts.SubjectType;
import com.charts.domain.treemenu.GroupPackage;
import com.charts.domain.treemenu.TreeNode;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component(value="topicMenuHandler")
public class TopicMenuHandler extends MenuHandler{

	
	
	
	/**
	 * Set the data for side menu by group
	 * @param fileData
	 * @return
	 * @throws JsonProcessingException 
	 */
	@Override
	protected TreeNode createTreeNode(FileData fileData) throws JsonProcessingException {
		
		//<i class="fas fa-chalkboard-teacher"></i>
		//<i class="fas fa-child"></i>
		//<i class="fas fa-user"></i>
		Map<String, TreeNode> mapResult = new HashMap<>();
		Integer groupColIndex =fileData.getGroupColIndex();
		
		
		for(DataLine dataLine : fileData.getDataLines()) {
			
			if(dataLine.isDisabled()) {
				continue;
			}
			TreeNode leafNode = new TreeNode();
			leafNode.setLabel(dataLine.getData().get(fileData.getTopicColIndex()).toString());
			leafNode.setData(obj.writeValueAsString(dataLine));
			leafNode.setIcon("fa fa-user");
			leafNode.setLeaf(true);
			
			Object groupData = null;
			if(groupColIndex  !=null) {
				groupData = dataLine.getData().get(groupColIndex);
			}
			//No group
			//handle case the child don't has teacher
			if(groupData == null) {
				mapResult.put(leafNode.getLabel(), leafNode);
				continue;
			}
			
			TreeNode teacher = mapResult.get(groupData.toString());
			if(teacher == null) {
				teacher = new TreeNode();
				teacher.setLabel(groupData.toString());
				teacher.setExpanded(true);
				teacher.setExpandedIcon("fa fa-chalkboard-teacher");
				teacher.setCollapsedIcon("fa fa-chalkboard-teacher");
			}
			
			
			teacher.getChildren().add(leafNode);
			mapResult.put(teacher.getLabel(), teacher);
		}
		TreeNode parenttreeNode = new TreeNode();
		parenttreeNode.setLabel(SubjectType.TOPIC.getCaption());
		parenttreeNode.setExpanded(true);
		for(TreeNode treeNode: mapResult.values()) {
			if(!treeNode.getChildren().isEmpty()) {
				parenttreeNode.getChildren().add(0,treeNode);
			}else {
				parenttreeNode.getChildren().add(treeNode);
			}
		}
		return parenttreeNode;
	}
	
	protected TreeNode createGroupTreeNode(FileData fileData) throws JsonProcessingException {
		Map<String, GroupPackage> mapResult = new HashMap<>();
		
		Integer groupColIndex =fileData.getGroupColIndex();
		TreeNode parenttreeNode = new TreeNode();
		parenttreeNode.setLabel(SubjectType.TOPIC.getCaption());
		parenttreeNode.setExpanded(true);
		
		if(groupColIndex == null) {
			return createTreeNode(fileData);
		}
		
		for(DataLine dataLine : fileData.getDataLines()) {
			if(dataLine.isDisabled()) {
				continue;
			}
			Object groupData = dataLine.getData().get(groupColIndex);
			if(groupData == null) {
				continue;
			}
			GroupPackage groupPackage = mapResult.get(groupData.toString());
			if(groupPackage == null) {
				groupPackage = new GroupPackage();
				groupPackage.setGroupName(groupData.toString());
			}
			groupPackage.getGroupMemebers().add(dataLine.getLineNum());
			mapResult.put(groupData.toString(), groupPackage);
		}
		
		for(Entry<String, GroupPackage> entry : mapResult.entrySet()) {
			TreeNode teacher = new TreeNode();
			teacher.setLabel(entry.getKey());
			teacher.setData(obj.writeValueAsString(entry.getValue()));
			teacher.setLeaf(true);
			teacher.setIcon("fa fa-chalkboard-teacher");
			parenttreeNode.getChildren().add(teacher);
		}
		return parenttreeNode;
	}

	@Override
	protected String getParentLabel() {
		return SubjectType.TOPIC.getCaption();
	}
}
