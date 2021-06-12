package com.charts.service.handlers.meun;

import org.springframework.stereotype.Component;

import com.charts.domain.FileData;
import com.charts.domain.SeriesItem;
import com.charts.domain.charts.SubjectType;
import com.charts.domain.treemenu.TreeNode;
import com.charts.service.util.ChartAppUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component(value="seriesMenuHandler")
public class SeriesMenuHandler extends MenuHandler {
	
	
	protected TreeNode createTreeNode(FileData fileData) throws JsonProcessingException {
		TreeNode parenttreeNode = new TreeNode();
		parenttreeNode.setLabel(SubjectType.SERIE.getCaption());
		parenttreeNode.setExpanded(true);
		for(SeriesItem serie : fileData.getSeriesList()) {
			TreeNode leafNode = new TreeNode();
			leafNode.setLabel(ChartAppUtil.getSeriesLabel(serie));
			leafNode.setData(obj.writeValueAsString(serie));
			leafNode.setLeaf(true);
			parenttreeNode.getChildren().add(leafNode);
		}
		
		return parenttreeNode;
	}

	@Override
	protected String getParentLabel() {
		return SubjectType.SERIE.getCaption();
	}

	@Override
	protected TreeNode createGroupTreeNode(FileData fileData) throws JsonProcessingException {
		return createTreeNode(fileData);
	}
}
