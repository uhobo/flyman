package com.charts.service.diagramhandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charts.domain.DataHeader;
import com.charts.domain.DataLine;
import com.charts.domain.FileData;
import com.charts.domain.SeriesItem;
import com.charts.domain.charts.DiagramDataset;
import com.charts.domain.treemenu.GroupPackage;
import com.charts.domain.treemenu.TreeNode;

/**
 *  X Axis = TOPIC		TreeNode.data [DataLine]
 *  Y Axis = CATEGORY 	TreeNode.data [DataHeader]
 * @author user
 *
 */
@Component(value="topicDiagramHandler")
@Scope("prototype")
public class TopicDiagramHandler extends DiagramHandler{
	

	private final Logger log = LoggerFactory.getLogger(TopicDiagramHandler.class);
	
	private List<GroupPackage> selectedGroups = new ArrayList<>();
	
	
	@Override
	protected String getTitle() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.chartRequest.getSelectYnodes().size() > 1?  "Categories: " : "Category :");
		builder.append(getSelectedCategoriesTitle(this.selectedAxisYIds)).append("\n");
		builder.append(getPopulationTitle(this.selectedAxisXIds));
		return builder.toString();
	}
	
	@Override
	protected List<Integer> getSelectedAxisXItemsIndex(List<TreeNode> selectXnodes) {
		List<Integer> selectedAxisX  = new ArrayList<>();
		
		selectXnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			
			if(x.getData() == null) {
				return;
			}
			
			if(this.chartRequest.isChartByGroup()) {
				try {
					GroupPackage groupPackage = obj.readValue(x.getData(), GroupPackage.class);
					selectedAxisX.add(selectedGroups.size());
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
			}
    		selectedAxisX.add(item.getLineNum());
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
			
			DataHeader item = null; //(DataHeader)x.getData();
			try {
				item = obj.readValue(x.getData(), DataHeader.class);
			} catch (IOException e) {
				log.error("DataHeader", e);
			}
    		selectedAxisY.add(item.getIndex());
    	});
		return selectedAxisY;
	}

	@Override
	protected String getDiagramDataSetLabel(FileData fileData, Integer selectedAxisY, boolean isChartByGroup) {
		return fileData.getHeaders().get(selectedAxisY).getTitle();
	}

	@Override
	protected List<Double> getDataSetData(List<Integer> selectedAxisXList, Integer selectedAxisYindex,
			FileData fileData, Map<Integer, Double> valuesMap) {
		
		
		if(this.chartRequest.isChartByGroup()) {
			return getDataSetDataByGroup(selectedAxisYindex, valuesMap);
		}
		List<Double> valueList = new ArrayList<>();
		for(Integer selectedAxisX: selectedAxisXList){
			DataLine dataLine = fileData.getDataLines().get(selectedAxisX);
			if(dataLine.isDisabled() || selectedAxisYindex >= dataLine.getData().size()) {
				log.error("The dataLine {} the size less the selectedIndex {}", dataLine.getLineNum(), selectedAxisYindex );
				continue;
			}
			Double value = getDouble(dataLine.getData().get(selectedAxisYindex));
			valueList.add(value);
		}
		return valueList;
	}

	private List<Double> getDataSetDataByGroup(Integer selectedAxisYindex, Map<Integer, Double> valuesMap){
		
		List<Double> valueList = new ArrayList<>();
		
		for(GroupPackage group : selectedGroups) {
			Double value = Double.valueOf(0);
			
			for(Integer child:group.getGroupMemebers()) {
				
				DataLine dataLine = fileData.getDataLines().get(child);
				if(dataLine.isDisabled() || selectedAxisYindex >= dataLine.getData().size()) {
					log.error("The dataLine {} the size less the selectedIndex {}", dataLine.getLineNum(), selectedAxisYindex );
					continue;
				}
				value += getDouble(dataLine.getData().get(selectedAxisYindex));
			}
			valueList.add(value);
		}
		
		
		return valueList;
	}

	
	/**
	 * X -TOPIC
	 * Y - series
	 *  how many shows of each series values per selected Topic
	 */
//	@Override
//	protected void buildDistributionDiagram(DiagramChart diagramChart) {
//		
//		List<TreeNode> selectedNodeList = chartRequest.getSelectXnodes().stream().filter(x -> x.isLeaf()).collect(Collectors.toList());
//		//log.debug("labelsList: " + obj.writeValueAsString(labelsList));
//		//Set<String> labelList = new HashSet<String>();
//		
//		Integer itrIndex = 0;
//		for(SeriesItem seriesItem : fileData.getSeriesList()) {
//			DiagramDataset diagramDataset = new DiagramDataset();
//			diagramDataset.setLabel(ChartAppUtil.getSeriesLabel(seriesItem));
//			String color = ColorUtil.allocateColor(itrIndex++);
//    		diagramDataset.setBackgroundColor(color);
//    		diagramDataset.setBorderColor(color);
//    		
//    		for(TreeNode treeNode: selectedNodeList) {
//    			List<Integer> selectedAxisXList =  getSelectedItems(treeNode);
//        		diagramDataset.getData().add(getCountForSeries(selectedAxisXList, seriesItem));
//    		}
//    	
//	    	diagramChart.getDatasets().add(diagramDataset);
//			
//		}
//		
//		
//		//Add DiagramDataset total for each series 
//		
//	}

	//X Axis = TOPIC		TreeNode.data [DataLine]
	 //Y Axis = CATEGORY 	TreeNode.data [DataHeader]
	protected Double getCountForSeries(List<Integer> selectedAxisXList,  SeriesItem seriesItem) {
		long count = 0;
	
		for(Integer selectedY : this.selectedAxisYIds) {
				count+=selectedAxisXList.stream().filter(x -> getDouble(fileData.getDataLines().get(x).getData().get(selectedY)).equals(seriesItem.getValue())).count();	
		}
		//selectedAxisYIds contain the group index
		
		
		return Double.valueOf(count);
		
	}

	private Boolean isCounted(DataLine dataLine, List<Integer> selectedAxisXList, Integer selectedAxisY,
			SeriesItem seriesItem) {
		if(dataLine.isDisabled()) {
			return false;
		}
		if(!selectedAxisXList.contains(dataLine.getLineNum())) {
			return false;
		}
		Double value = getDouble(dataLine.getData().get(selectedAxisY));
		
		if(value.equals(seriesItem.getValue())) {
			return true;
		}
		
		return false;
	}

	protected List<Integer> getSelectedItems(TreeNode treeNode) {
		
		List<Integer> selectedItems = new ArrayList<Integer>();
		
		if(treeNode.getData() == null) {
			return selectedItems;
		}
		
		if(this.chartRequest.isChartByGroup()) {
			try {
				GroupPackage groupPackage = obj.readValue(treeNode.getData(), GroupPackage.class);
				return groupPackage.getGroupMemebers();
			} catch (IOException e) {
				log.error("GroupPackage", e);
			} 
			return selectedItems;
		}
		
		DataLine item = null;
		try {
			item = obj.readValue(treeNode.getData(), DataLine.class);
			selectedItems.add(item.getLineNum());
		} catch (IOException e) {
			log.error("DataLine", e);
		}
		
		return  selectedItems;
	}
	
	
}
