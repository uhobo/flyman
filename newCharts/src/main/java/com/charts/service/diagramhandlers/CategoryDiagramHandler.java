package com.charts.service.diagramhandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charts.domain.DataHeader;
import com.charts.domain.DataLine;
import com.charts.domain.FileData;
import com.charts.domain.SeriesItem;
import com.charts.domain.charts.DiagramChart;
import com.charts.domain.treemenu.GroupPackage;
import com.charts.domain.treemenu.TreeNode;



/**
 *  X Axis = CATEGORY TreeNode.data [DataHeader]
 *  Y Axis = TOPIC    TreeNode.data [DataLine]
 * @author user
 *
 */
@Component(value="categoryDiagramHandler") 
@Scope("prototype")
public class CategoryDiagramHandler extends DiagramHandler{
	private List<GroupPackage> selectedGroups = new ArrayList<>();
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
			
			if(this.chartRequest.isChartByGroup()) {
				try {
					GroupPackage groupPackage = obj.readValue(x.getData(), GroupPackage.class);
					selectedAxisY.addAll(groupPackage.getGroupMemebers());
					selectedGroups.add(groupPackage);
				} catch (IOException e) {
					log.error("GroupPackage", e);
				} 
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
	protected String getDiagramDataSetLabel(FileData fileData, Integer selectedAxisY, boolean isChartByGroup) {
		if(isChartByGroup) {
			return fileData.getDataLines().get(selectedAxisY).getData().get(fileData.getGroupColIndex()).toString();
		}
		
		return fileData.getDataLines().get(selectedAxisY).getData().get(fileData.getTopicColIndex()).toString();
	}

	@Override
	protected List<Double> getDataSetData(List<Integer> selectedAxisXList, Integer selectedAxisYindex,
			FileData fileData, Map<Integer, Double> valuesMap) {
		if(this.chartRequest.isChartByGroup()) {
			return getDataSetDataByGroup(selectedAxisXList, selectedAxisYindex, fileData);	
		}
		
		List<Double> valueList = new ArrayList<>();
		DataLine dataLine = fileData.getDataLines().get(selectedAxisYindex);
		
		for(Integer selectedAxisX: selectedAxisXList){
			Double value = getDouble(dataLine.getData().get(selectedAxisX));
			valueList.add(value);
		}
		
		return valueList;
	}

	private List<Double> getDataSetDataByGroup(List<Integer> selectedAxisXList, Integer selectedAxisYindex,
			FileData fileData) {
		
		List<Double> valueList = new ArrayList<>();
		DataLine dataLine = null;
		GroupPackage group = selectedGroups.get(selectedAxisYindex);
		
			
		for(Integer selectedAxisX: selectedAxisXList){
			Double value = Double.valueOf(0);
			for(Integer child:group.getGroupMemebers()) {
				dataLine = fileData.getDataLines().get(child);
				value +=getDouble(dataLine.getData().get(selectedAxisX));	
			}
			valueList.add(value);
		}
		return valueList;
	}
	
	
	/**
	 * X -CATEGORY
	 * Y - series 
	 * how many shows of each series values per selected CATEGORY
	 */
	//@Override
	//protected void buildDistributionDiagram(DiagramChart diagramChart) {
	//	
	//}
	
	//X - category
	//Y - topic 
	protected Double getCountForSeries(List<Integer> selectedAxisXList, SeriesItem seriesItem) {
		long count = 0;
		
		for(Integer selectedAxisY : this.selectedAxisYIds){
			count += selectedAxisXList.stream().filter(x -> getDouble(fileData.getDataLines().get(selectedAxisY).getData().get(x)).equals(seriesItem.getValue())).count();
			//count += fileData.getDataLines().get(selectedAxisY).getData().stream().filter(x -> getDouble(x).equals(seriesItem.getValue())).count(); 
		}
		return Double.valueOf(count);
		
	}

	@Override
	protected List<Integer> getSelectedItems(TreeNode treeNode) {
		List<Integer> selectedAxisX  = new ArrayList<>();
		if(treeNode.getData() == null) {
			return selectedAxisX;
		}
		
		DataHeader item = null;//(DataHeader)x.getData();
		try {
			item = obj.readValue(treeNode.getData(), DataHeader.class);
		} catch (IOException e) {
			log.error("DataHeader", e);
			return selectedAxisX;
		}
		selectedAxisX.add(item.getIndex());
		return selectedAxisX;
	}
	
	

}
