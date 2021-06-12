package com.charts.service.diagramhandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charts.domain.DataHeader;
import com.charts.domain.DataLine;
import com.charts.domain.FileData;
import com.charts.domain.SeriesItem;
import com.charts.domain.charts.ChartRequest;
import com.charts.domain.charts.ChartResponse;
import com.charts.domain.charts.DiagramChart;
import com.charts.domain.charts.PieChart;
import com.charts.domain.charts.PieDataset;
import com.charts.domain.treemenu.TreeNode;
import com.charts.service.util.ColorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * Series - 
 * 	Axis X =  SERIE: TreeNode.data [SeriesItem]
 * 	Axis Y = CATEGORY:  TreeNode.data [DataHeader]	
 * 	chartType: PIE (1)
 * 			   BAR (2)
 * 		 
 * @author user
 *
 */
@Component(value="seriesDiagramHandler")
@Scope("prototype")
public class SeriesDiagramHandler extends DiagramHandler {

	private final Logger log = LoggerFactory.getLogger(SeriesDiagramHandler.class);
	
	
	protected List<Integer> getSelectedAxisXItemsIndex(List<TreeNode> selectXnodes) throws Exception{
		List<Integer> selectedAxisX  = new ArrayList<>();
		selectXnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {

			if(x.getData() == null) {
				return;
			}
			
			SeriesItem item = null;
			try {
				item = obj.readValue(x.getData(), SeriesItem.class);
			} catch (IOException e) {
				log.error("SeriesItem", e);
				return;
			} 
    		selectedAxisX.add(item.getValue().intValue());
    	});
		return selectedAxisX;
    	
	}
	protected List<Integer> getSelectedAxisYItemsIndex(List<TreeNode> selectYnodes){
		List<Integer> selectedAxisY  = new ArrayList<>();
		selectYnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			if(x.getData() == null) {
				return;
			}
			DataHeader item = null; 
			try {
				item = obj.readValue(x.getData(), DataHeader.class);
			} catch (IOException e) {
				log.error("DataHeader", e);
				return;
			} 
    		selectedAxisY.add(item.getIndex());
    	});
		return selectedAxisY;
	}
	
	
	private List<SeriesItem> getSelectSeriesItems(List<TreeNode> selectXnodes){
		List<SeriesItem> selectedAxisX  = new ArrayList<>();
		selectXnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			if(x.getData() == null) {
				return;
			}
			
			SeriesItem item = null;
			try {
				item = obj.readValue(x.getData(), SeriesItem.class);
			} catch (IOException e) {
				log.error("SeriesItem", e);
				return;
			} 
			selectedAxisX.add(item);
    	});
		return selectedAxisX;

	}

	private List<DataHeader> getSelectedDataHeader(List<TreeNode> selectYnodes){
		List<DataHeader> selectedAxisY  = new ArrayList<>();
		selectYnodes.stream().filter(x-> x.isLeaf()).forEach(x -> {
			
			if(x.getData() == null) {
				return;
			}
			DataHeader item = null; //(DataHeader)x.getData();
			try {
				item = obj.readValue(x.getData(), DataHeader.class);
			} catch (IOException e) {
				log.error("DataHeader", e);
				return;
			} 
			
    		selectedAxisY.add(item);
    	});
		return selectedAxisY;

	}

	protected void setPieData(ChartRequest chartRequest, FileData fileData, ChartResponse chartResponse){
    	PieChart pieChart = new PieChart();
    	chartResponse.setChartTypeStr("pie");
    	
    	List<SeriesItem> selectedAxisX  = getSelectSeriesItems(chartRequest.getSelectXnodes());
    	List<DataHeader> selectedAxisY  = getSelectedDataHeader(chartRequest.getSelectYnodes());
    	
    	List<Double> valueList = new ArrayList<>();
    	
    	//over on each selected category and add its values
    	fileData.getDataLines().stream().filter(data-> !data.isDisabled()).forEach(x -> {
    		selectedAxisY.stream().forEach(y -> 
    				valueList.add(getDouble(x.getData().get(y.getIndex())))
				);
    	});
    	
    	//Calculate the sum of value list
    	Double sum = valueList.stream().reduce(Double.valueOf(0), (a, b) -> a + b);
    	PieDataset dataset = new PieDataset();
    	
    	selectedAxisX.forEach(series -> {
    		Double seriesSum =  valueList.stream().filter( x -> x.equals(series.getValue())).reduce(Double.valueOf(0), (a, b) -> a + b);
    		dataset.getData().add(calculatePercentage(seriesSum, sum));
    		dataset.getBackgroundColor().add(ColorUtil.allocateColor(dataset.getBackgroundColor().size()));
    		dataset.getHoverBackgroundColor().add(ColorUtil.allocateColor(dataset.getHoverBackgroundColor().size()));
    		pieChart.getLabels().add(getSeriesLabel(series));   		
    	});
    	
    	pieChart.getDatasets().add(dataset);
    	chartResponse.setOptions(pieChart.getOptions());
    	chartResponse.setData(pieChart);
 }
	
	
	 
	 
	 protected List<Double> getDataSetData(List<Integer> selectedAxisXList, Integer selectedAxisYindex, FileData fileData, Map<Integer, Double> valuesMap) {
			List<Double> valueList = new ArrayList<>();
			
			for(DataLine dataLine: fileData.getValidDataLines()){
					
				Integer value = getDouble(dataLine.getData().get(selectedAxisYindex)).intValue();
				if(valuesMap.get(value)== null) {
					log.warn("No mapping for value= " + value +"[line num=" + dataLine.getLineNum() + " cell=" + selectedAxisYindex + "]");
					continue;
				}
				valuesMap.put(value, valuesMap.get(value)+1) ; 
			}
			
			for(Integer selectedAxisXKey : selectedAxisXList ){
				valueList.add(valuesMap.get(selectedAxisXKey));
				
			}
			
			return valueList;
		}
	 
	 public static String getSeriesLabel(SeriesItem seriesItem) {
			return StringUtils.isEmpty(seriesItem.getTitle())? 
								String.valueOf(seriesItem.getValue()) :
									seriesItem.getTitle() + " (" + String.valueOf(seriesItem.getValue()) + ")";
		}
	 
	 protected String getDiagramDataSetLabel(FileData fileData, Integer selectedAxisY, boolean isChartByGroup) {
			return fileData.getHeaders().get(selectedAxisY).getTitle();
	 }
	 
	 
	 protected Boolean supportDistrubution() {return false;} 
	 @Override
	protected void buildDistributionDiagram(DiagramChart diagramChart) {
		
		return;
	}
	@Override
	protected List<Integer> getSelectedItems(TreeNode treeNode) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Double getCountForSeries(List<Integer> selectedAxisXList, SeriesItem seriesItem) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
