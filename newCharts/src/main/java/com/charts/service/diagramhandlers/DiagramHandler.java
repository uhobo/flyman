package com.charts.service.diagramhandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.charts.domain.FileData;
import com.charts.domain.charts.ChartRequest;
import com.charts.domain.charts.ChartResponse;
import com.charts.domain.charts.DiagramChart;
import com.charts.domain.charts.DiagramDataset;
import com.charts.domain.treemenu.TreeNode;
import com.charts.service.util.ColorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class DiagramHandler {

	protected final ObjectMapper obj = new ObjectMapper();
	
	public void createPackageResult(Optional<FileData> fileData, ChartRequest chartRequest,
			ChartResponse chartResponse) throws Exception {
		this.setDiagramData(chartRequest, fileData.get(), chartResponse);
	}
	
	protected abstract List<Integer> getSelectedAxisXItemsIndex(List<TreeNode> selectXnodes) throws Exception;
	protected abstract List<Integer> getSelectedAxisYItemsIndex(List<TreeNode> selectYnodes);
	protected abstract String getDiagramDataSetLabel(FileData fileData, Integer selectedAxisY);
	protected abstract List<Double> getDataSetData(List<Integer> selectedAxisXList, Integer selectedAxisYindex, FileData fileData, Map<Integer, Double> valuesMap);
	
	protected void setDiagramData(ChartRequest chartRequest,  FileData fileData, ChartResponse chartResponse) throws Exception{
	 	
    	DiagramChart diagramChart = new DiagramChart();
    	chartResponse.setChartTypeStr("bar");
    	List<Integer> selectedAxisXIds  = getSelectedAxisXItemsIndex(chartRequest.getSelectXnodes());
    	List<Integer> selectedAxisYIds  = getSelectedAxisYItemsIndex(chartRequest.getSelectYnodes());
    	
    	List<String> labelsList = chartRequest.getSelectXnodes().stream().filter(x -> x.isLeaf()).map(TreeNode::getLabel).collect(Collectors.toList());
    	diagramChart.setLabels(labelsList);
    	
    	Map<Integer, Double> valuesMap = initializeDataMap(selectedAxisXIds);
    	
    	List<Double> allValues = new ArrayList<>();
    	String avergeColor = null;
    	String medianColor = null;
    	
 
    	if(chartRequest.isIncludeAverage()) {
    		avergeColor = ColorUtil.allocateColor(diagramChart.getDatasets().size());
    	}
    	
    	if(chartRequest.isIncludeMedian()) {
    		medianColor = ColorUtil.allocateColor(diagramChart.getDatasets().size()+1);
    	}
    	
    	Integer itrIndex = 0;
    	for(Integer selectedAxisY : selectedAxisYIds){
    		DiagramDataset diagramDataset = new DiagramDataset();
    		diagramDataset.setLabel(getDiagramDataSetLabel(fileData, selectedAxisY));
    		String color = ColorUtil.allocateColor(itrIndex++);
    		diagramDataset.setBackgroundColor(color);
    		diagramDataset.setBorderColor(color);
    		diagramChart.getDatasets().add(diagramDataset);
    		diagramDataset.setData(getDataSetData(selectedAxisXIds, selectedAxisY, fileData, valuesMap));
    		if(chartRequest.isIncludeAverage() || chartRequest.isIncludeMedian()) {
    			allValues.addAll(diagramDataset.getData());	
    		}
    		valuesMap.entrySet().forEach(x -> x.setValue(Double.valueOf(0)));
    	}
    	
	    	if(chartRequest.isIncludeAverage() && !diagramChart.getDatasets().isEmpty()) {
    		Integer diagramSize = diagramChart.getDatasets().size();
    		Integer dataSize = diagramChart.getDatasets().get(0).getData().size();
    		if(diagramSize > dataSize) {
    			dataSize = diagramSize;
    		}
    		Double average  = allValues.stream().mapToDouble(val -> val).average().orElse(0.0);
	    	DiagramDataset diagramDataset = new DiagramDataset();
	    	diagramDataset.setLabel("Average (" + average + ")");
	    	List<Double> averageList = new ArrayList<>();
	    	for(int statInx = 0; statInx < dataSize; statInx++) {
	    		averageList.add(average);
	    	}
	    	//diagramDataset.setOrder(order++);
	    	diagramDataset.setData(averageList);
	    	diagramDataset.setBorderColor(avergeColor);
	    	diagramDataset.setBackgroundColor(avergeColor);
	    	diagramDataset.setStatistics(true);
	    	diagramDataset.setType("line");
	    	diagramDataset.setSpanGaps(true);
	    	diagramDataset.setPointStyle("line");
	    	diagramDataset.setFill(false);
	    	diagramChart.getDatasets().add(diagramDataset);
	    	
	    		
    	}
    	if(chartRequest.isIncludeMedian() && !diagramChart.getDatasets().isEmpty()) {
    		Integer diagramSize = diagramChart.getDatasets().size();
    		Integer dataSize = diagramChart.getDatasets().get(0).getData().size();
    		if(diagramSize > dataSize) {
    			dataSize = diagramSize;
    		}
    		Double median = calculateMedian(allValues);
    		DiagramDataset diagramDataset = new DiagramDataset();
	    	diagramDataset.setLabel("Median (" + median + ")");
	    	List<Double> medianList = new ArrayList<>();
	    	for(int statInx = 0; statInx < dataSize; statInx++) {
	    		medianList.add(median);
	    	}
	    	diagramDataset.setData(medianList);
	    	diagramDataset.setBackgroundColor(medianColor);
	    	diagramDataset.setBorderColor(medianColor);
	    	diagramDataset.setStatistics(true);
	    	diagramDataset.setType("line");
	    	diagramDataset.setSpanGaps(true);
	    	diagramDataset.setPointStyle("line");
	    	diagramDataset.setFill(false);
	    	//diagramDataset.setOrder(order++);
	    	diagramChart.getDatasets().add(diagramDataset);
		   		
    	}
    	chartResponse.setOptions(diagramChart.getOptions());
    	chartResponse.setData(diagramChart);
    }
 
	
	
	
	protected Double getDouble(Object value) {
		if(value == null || StringUtils.isEmpty(value.toString())) {
			return Double.valueOf(0);
		}
		
		return Double.valueOf(value.toString());  	
	 }
	
	protected double calculatePercentage(double obtained, double total) {
	        return obtained * 100 / total;
	 }
	
	protected Map<Integer, Double> initializeDataMap(List<Integer> selectedAxisX) {
		
		Map<Integer, Double> dataValueMap = new HashMap<>();	
		for(Integer item: selectedAxisX){
			dataValueMap.put(item, Double.valueOf(0));
		}
		return dataValueMap;
	 }
	
	protected double calculateMedian(List<Double> valueList ) {
    	List<Double> tempList = new ArrayList<>();
    	tempList.addAll(valueList);
    	Collections.sort(tempList);
    	int middle = tempList.size()/2;
        
    	if (tempList.size()%2 == 1) {
            return tempList.get(middle);
        } else {
            return (tempList.get(middle-1) + tempList.get(middle)) / 2.0;
        }
	 }
}
