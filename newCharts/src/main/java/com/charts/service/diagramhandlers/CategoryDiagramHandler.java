package com.charts.service.diagramhandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.charts.domain.DataHeader;
import com.charts.domain.DataLine;
import com.charts.domain.FileData;
import com.charts.domain.treemenu.TreeNode;


/**
 *  X Axis = CATEGORY TreeNode.data [DataHeader]
 *  Y Axis = TOPIC    TreeNode.data [DataLine]
 * @author user
 *
 */
@Component(value="categoryDiagramHandler") 
public class CategoryDiagramHandler extends DiagramHandler{

	private final Logger log = LoggerFactory.getLogger(CategoryDiagramHandler.class);
	@Override
	protected List<Integer> getSelectedAxisXItemsIndex(List<TreeNode> selectXnodes) {
		List<Integer> selectedAxisX  = new ArrayList<>();
		selectXnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			
			if(x.getData() == null) {
				return;
			}
			
			DataHeader item = null;//(DataHeader)x.getData();
			try {
				item = obj.readValue(x.getData(), DataHeader.class);
			} catch (IOException e) {
				log.error("DataHeader", e);
				return;
			}
    		selectedAxisX.add(item.getIndex());
    	});
		return selectedAxisX;
	}

	@Override
	protected List<Integer> getSelectedAxisYItemsIndex(List<TreeNode> selectYnodes) {
		List<Integer> selectedAxisY  = new ArrayList<>();
		selectYnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			
			if(x.getData() == null) {
				return;
			}
			
			DataLine item = null;
			try {
				item = obj.readValue(x.getData(), DataLine.class);
			} catch (IOException e) {
				log.error("DataLine", e);
				return;
			} 
    		selectedAxisY.add(item.getLineNum());
    	});
		return selectedAxisY;
	}

	@Override
	protected String getDiagramDataSetLabel(FileData fileData, Integer selectedAxisY) {
		return fileData.getValidDataLines().get(selectedAxisY).getData().get(fileData.getTopicColIndex()).toString();
	}

	@Override
	protected List<Double> getDataSetData(List<Integer> selectedAxisXList, Integer selectedAxisYindex,
			FileData fileData, Map<Integer, Double> valuesMap) {
		List<Double> valueList = new ArrayList<>();
		DataLine dataLine = fileData.getDataLines().get(selectedAxisYindex);
		
		for(Integer selectedAxisX: selectedAxisXList){
			Double value = getDouble(dataLine.getData().get(selectedAxisX));
			valueList.add(value);
		}
		
		return valueList;
	}

}
