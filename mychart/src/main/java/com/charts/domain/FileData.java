package com.charts.domain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.charts.domain.treemenu.GroupPackage;
import com.charts.service.util.ChartAppUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A FileData.
 */
@Document(collection = "file_data")
public class FileData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("file_name")
    private String fileName;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("timestamp")
    private LocalDate timestamp;
    
    private Integer topicColIndex;
    
    private Integer groupColIndex;
    
    @Field("headers")
    private List<DataHeader> headers;
   
    @Field("dataLines")
    private List<DataLine>  dataLines;
    
    @Field("series")
    private List<SeriesItem> seriesList;
    
    @Field("infoData")
    private List<InfoData> infoDataList;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public FileData fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public FileData title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public FileData timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
	public Integer getTopicColIndex() {
		return topicColIndex;
	}

	public void setTopicColIndex(Integer topicColIndex) {
		this.topicColIndex = topicColIndex;
	}
	
	
	
	

	public List<InfoData> getInfoDataList() {
		return infoDataList;
	}

	public void setInfoDataList(List<InfoData> infoDataList) {
		this.infoDataList = infoDataList;
	}

	@JsonIgnore
	public Map<String, Integer> getPopulationData(){
		Map<String, Integer> groupData = new HashMap<>();
		
		this.dataLines.stream().forEach(x -> {
			Integer total = groupData.get("total");
			if(total == null) {
				total = 0;
			}
			groupData.put("total", total++);
			if(this.getGroupColIndex() != null) {
				String groupName = x.getData().get(this.getGroupColIndex()).toString();
				total = groupData.get(groupName);
				if(total == null) {
					total = 0;
				}
				groupData.put(groupName, total++);	
			}
			
		});
		return groupData;
	}

	public void setImportData(ImportExcelData importData) {
		
		this.title = importData.getSheetName();
		
		this.seriesList = new ArrayList<>();
		for(Double seriesVal : importData.getSeriesList()) {
			SeriesItem cItemp = new SeriesItem();
			cItemp.setValue(seriesVal);
			this.seriesList.add(cItemp);
		}
		
		this.headers = new ArrayList<>();
		//TODO if the header is 0 than mark as disabled
		for(int index =0 ; index < importData.getData().get(0).size(); index++) {
			DataHeader dataHeader = new DataHeader(importData.getData().get(0).get(index).toString(), index);
			if(StringUtils.isEmpty(dataHeader.getTitle())) {
				dataHeader.setShow(false);
				dataHeader.setDisabled(true);
			}
			this.headers.add(dataHeader);
		}
		
		//The dataLine can be enabled if at least have number of valid data size as number of valid headers 
		Long numValidHeaders = this.headers.stream().filter(x -> x.isShow()).count();
		
		
		this.dataLines = new ArrayList<>();
		importData.getData().subList(1, importData.getData().size()).forEach(item -> {
			Integer numOfOkCell = 0;
			DataLine dataLine = new DataLine();
			dataLine.setData(item);
			this.dataLines.add(dataLine);
			Integer currentIndex = this.dataLines.size();
			dataLine.setLineNum(currentIndex-1);
			for(Integer dataItemInx=0; dataItemInx < item.size(); dataItemInx++) {
				// -1 because the sublist(1,importData.getData().size()) command 
				if(importData.getErrorSuspect().get(currentIndex-1 + "_" + dataItemInx)  == null) {
					numOfOkCell++;
				}
			}
		
			if(numOfOkCell >= numValidHeaders) {
				dataLine.setDisabled(false);	
			}else {
				dataLine.setDisabled(true);
			}
			
			
		});
	}
	

	public List<DataHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<DataHeader> headers) {
		this.headers = headers;
	}

	public List<DataLine> getDataLines() {
		return dataLines;
	}

	public void setDataLines(List<DataLine> dataLines) {
		this.dataLines = dataLines;
	}
	
	public List<DataLine> getValidDataLines(){
		return dataLines.stream().filter(x -> !x.isDisabled()).collect(Collectors.toList());
	}
	public List<SeriesItem> getSeriesList() {
		return seriesList;
	}

	public void setSeriesList(List<SeriesItem> seriesList) {
		this.seriesList = seriesList;
	}

	public Integer getGroupColIndex() {
		return groupColIndex;
	}

	public void setGroupColIndex(Integer groupColIndex) {
		this.groupColIndex = groupColIndex;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileData)) {
            return false;
        }
        return id != null && id.equals(((FileData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
    	ObjectMapper map = new ObjectMapper();
		try {
			map.setSerializationInclusion(Include.NON_NULL);
			return map.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    
    public void setGeneralInfo() {
    	if(dataLines == null || dataLines.isEmpty()) {
    		return;
    	}
    	Map<String, InfoData> tempInfoData = new HashMap<>();
    	InfoData infoData = new InfoData("NumberOfTopics");
    	infoData.setCount(this.dataLines.size());
    	tempInfoData.put(infoData.getTitle(), infoData);
    	
    	infoData = new InfoData("NumberOfSubjects");
    	infoData.setCount(this.headers.size());
    	tempInfoData.put(infoData.getTitle(), infoData);
    	
    	infoData = new InfoData("NumberOfGroups");
    	infoData.setCount(0);
    	if(this.groupColIndex != null) {
    		Map<String, GroupPackage> groupMap = new HashMap<String, GroupPackage>();
    		this.dataLines.stream().filter(f -> !f.isDisabled()).forEach(line -> {
    			GroupPackage groupPackage = groupMap.get(line.getData().get(this.groupColIndex));
    			if(groupPackage == null) {
    				groupPackage = new GroupPackage();
    				groupPackage.setGroupName(line.getData().get(this.groupColIndex) != null ? line.getData().get(this.groupColIndex).toString():line.getData().get(this.topicColIndex).toString() );
    			}
    			groupPackage.getGroupMemebers().add(line.getLineNum());
    			groupMap.put(groupPackage.getGroupName(), groupPackage);
    		});
    		
    		infoData.setCount(groupMap.size());
    	}
    	infoData.setCount(this.headers.size());
    	tempInfoData.put(infoData.getTitle(), infoData);
    	
    }
    
    
    
    @JsonIgnore
    public void setInfo() {
    	this.infoDataList = new ArrayList<>();
    	Map<String, InfoData> tempInfoData = new HashMap<>();
    	
    	if(dataLines == null || dataLines.isEmpty()) {
    		return;
    	}
    	InfoData numberOfRows = new InfoData("NumberOfRows");
    	numberOfRows.setCount(this.dataLines.size());
    	tempInfoData.put("NumberOfRows", numberOfRows);
    	
    	
    	
    	StringBuilder disabledRows = new StringBuilder();
    	
    	this.dataLines.stream().forEach(x-> {
    		if(x.isDisabled()) {
    			InfoData numOfDisabledRow  = tempInfoData.get("numOfDisabledRow");
    			if(numOfDisabledRow == null) {
    				numOfDisabledRow = new InfoData("numOfDisabledRow");
    			}
    			disabledRows.append(x.getLineNum()).append(",");
    			numOfDisabledRow.setCount(numOfDisabledRow.getCount()+1);
    			tempInfoData.put(numOfDisabledRow.getTitle(), numOfDisabledRow);
        		return;
    		}
    		
    		IntStream.range(0, headers.size()).forEach(index-> {
    			if(index != this.topicColIndex && index != this.groupColIndex && x.getData().get(index) == null) {
    				InfoData numOfErrorCell  = tempInfoData.get("numOfErrorCell");
    				if(numOfErrorCell == null) {
    					numOfErrorCell = new InfoData("numOfErrorCell");
    				}
    				numOfErrorCell.setCount(numOfErrorCell.getCount()+1);
    				numOfErrorCell.setContent(numOfErrorCell.getContent() + String.format("[Line: {} Col: {}]", x.getLineNum(), index));
    				
    			}
    			
    		});	
        	
    		
    		if(this.groupColIndex != null ) {
    			InfoData currentGroupSize = tempInfoData.get(x.getData().get(this.groupColIndex));
    			if(currentGroupSize  == null) {
    				currentGroupSize  = new InfoData(x.getData().get(this.groupColIndex).toString());
    				currentGroupSize.setContent("Number of topics in the group");
    			}
    			currentGroupSize.setCount(currentGroupSize.getCount()+1);
    			tempInfoData.put(x.getData().get(this.groupColIndex).toString(),currentGroupSize );
    		}
    		
    		
    	});
    	
   	
    	if(this.groupColIndex != null) {
    		this.seriesList.stream().forEach(seriesItem -> {
    			this.setGroupSeriesCount(seriesItem, tempInfoData);
    		});
    	}
        
        
    	this.infoDataList = new ArrayList<>(tempInfoData.values());
    	
    	
    }

	private void setGroupSeriesCount(SeriesItem seriesItem, Map<String, InfoData> data) {
		this.dataLines.stream().filter(x -> !x.isDisabled()).forEach(dataLine -> {
			InfoData infoData = data.get(dataLine.getData().get(this.groupColIndex).toString() + " " + seriesItem.getValue());
			if(infoData == null) {
				infoData = new InfoData(dataLine.getData().get(this.groupColIndex).toString() + " " + seriesItem.getValue());
			}
			Long count = IntStream.range(0, dataLine.getData().size()).filter(index -> {
				if(index != this.topicColIndex && index != this.groupColIndex && ChartAppUtil.getDouble(dataLine.getData().get(index)).equals(seriesItem.getValue())) {
					return true;
				}
				return false;
			}).count();
			infoData.setCount(infoData.getCount() + count.intValue());
			data.put(infoData.getTitle(),infoData);
			
		});
	}
}
