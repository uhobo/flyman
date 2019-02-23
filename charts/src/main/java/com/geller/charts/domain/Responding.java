package com.geller.charts.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Responding.
 */
@Document(collection = "responding")
public class Responding extends Person {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  
  //  @DBRef
   // @Field("surveys")
    @Transient
    private Set<Survey> surveys = new HashSet<>();

    @Field("surveyIds")
    @JsonIgnoreProperties("")
    private Set<String> surveyIds = new HashSet<>();
    
    //private Set<SurveymetaData> surveysData = new HashSet<>();
    
    
   

    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove



	public Set<String> getSurveyIds() {
		return surveyIds;
	}

	public void setSurveyIds(Set<String> surveyIds) {
		this.surveyIds = surveyIds;
	}

	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}
	
    @Override
    public String toString() {
        return "Responding{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
