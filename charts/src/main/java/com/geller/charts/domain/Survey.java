package com.geller.charts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Survey.
 */
@Document(collection = "survey")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("answerScales")
    private Set<AnswerScale> answerScales = new HashSet<>();
    
    @Field("questions")
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Survey title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Survey description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Survey questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Survey addQuestions(Question question) {
        this.questions.add(question);
        return this;
    }

    public Survey removeQuestions(Question question) {
        this.questions.remove(question);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Survey survey = (Survey) o;
        if (survey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), survey.getId());
    }

    public Set<AnswerScale> getAnswerScales() {
		return answerScales;
	}

	public void setAnswerScales(Set<AnswerScale> answerScales) {
		this.answerScales = answerScales;
	}
	
	 public Survey removeAnswerScales(AnswerScale answerScale) {
	        this.answerScales.remove(answerScale);
	        return this;
	 }


	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Survey{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
