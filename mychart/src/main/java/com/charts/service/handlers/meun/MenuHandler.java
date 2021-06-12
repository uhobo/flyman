package com.charts.service.handlers.meun;

import java.util.ArrayList;
import java.util.List;

import com.charts.domain.FileData;
import com.charts.domain.treemenu.TreeNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class MenuHandler {
	protected final ObjectMapper obj = new ObjectMapper();
	
	public List<TreeNode> createTreeMenu(FileData fileData) throws JsonProcessingException {
		List<TreeNode>  resultList = new ArrayList<>();
		resultList.add(createTreeNode(fileData));
		return resultList;
		
	}
	public List<TreeNode> createGroupTreeMenu(FileData fileData) throws JsonProcessingException{
		List<TreeNode>  resultList = new ArrayList<>();
		resultList.add(createGroupTreeNode(fileData));
		return resultList;
	}
	
	protected abstract TreeNode createTreeNode(FileData fileData) throws JsonProcessingException;
	
	protected abstract TreeNode createGroupTreeNode(FileData fileData) throws JsonProcessingException;
	
	protected abstract String getParentLabel();
}
