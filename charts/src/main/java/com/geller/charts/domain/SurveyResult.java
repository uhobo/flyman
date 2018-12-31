package com.geller.charts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SurveyResult.
 */
@Document(collection = "survey_result")
public class SurveyResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("submit_date")
    private LocalDate submitDate;

    //@DBRef
    //@Field("survey")
    //@JsonIgnoreProperties("")
    @Transient
    private Survey survey;
    
    @Field("surveyId")
    @JsonIgnoreProperties("")
    private String surveyId;
   
    @Field("askedResult")
    @JsonIgnoreProperties("")
    private AskedResult askedResult;

    //@DBRef
    //@Field("reponding")
    //@JsonIgnoreProperties("")
    @Transient
    private Responding responding;
    
    @Field("respondingId")
    @JsonIgnoreProperties("")
    private String respondingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public SurveyResult submitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
        return this;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }

    public Survey getSurvey() {
        return survey;
    }

    public SurveyResult survey(Survey survey) {
        this.survey = survey;
        return this;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    
	
	

	public Responding getResponding() {
		return responding;
	}

	public AskedResult getAskedResult() {
		return askedResult;
	}

	public void setAskedResult(AskedResult askedResult) {
		this.askedResult = askedResult;
	}

	public void setResponding(Responding responding) {
		this.responding = responding;
	}
	
	

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getRespondingId() {
		return respondingId;
	}

	public void setRespondingId(String respondingId) {
		this.respondingId = respondingId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SurveyResult surveyResult = (SurveyResult) o;
        if (surveyResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), surveyResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SurveyResult{" +
            "id=" + getId() +
            ", submitDate='" + getSubmitDate() + "'" +
            "}";
    }
}
