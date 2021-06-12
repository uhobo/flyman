package com.charts.service.handlers.meun;

import org.springframework.stereotype.Component;

import com.charts.domain.DataHeader;
import com.charts.domain.FileData;
import com.charts.domain.charts.SubjectType;
import com.charts.domain.treemenu.TreeNode;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component(value="categoryMenuHandler")
public class CategoryMenuHandler extends MenuHandler{

	@Override
	protected TreeNode createTreeNode(FileData fileData) throws JsonProcessingException {
		TreeNode parenttreeNode = new TreeNode();
		parenttreeNode.setLabel(SubjectType.CATEGORY.getCaption());
		parenttreeNode.setExpanded(true);
		for(DataHeader header : fileData.getHeaders()) {
			if(!header.isShow() || header.isDisabled() || header.getIndex().equals(fileData.getTopicColIndex())  || header.getIndex().equals(fileData.getGroupColIndex())) {
				continue;
			}
			TreeNode leafNode = new TreeNode();
			leafNode.setLabel(header.getTitle());
			leafNode.setData(obj.writeValueAsString(header));
			leafNode.setLeaf(true);
			parenttreeNode.getChildren().add(leafNode);
		}
		
		return parenttreeNode;
	}

	@Override
	protected String getParentLabel() {
		return SubjectType.CATEGORY.getCaption();
	}

	@Override
	protected TreeNode createGroupTreeNode(FileData fileData) throws JsonProcessingException {
		return createTreeNode(fileData);
	}

}
