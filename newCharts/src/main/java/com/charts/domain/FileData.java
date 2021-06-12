package com.charts.domain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
