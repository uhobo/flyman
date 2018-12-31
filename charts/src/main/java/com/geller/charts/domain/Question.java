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
 * A Question.
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("questionId")
    private Integer questionId;
    
    @NotNull
    @Field("order")
    private Integer order;

    @NotNull
    @Field("description")
    private String description;

    

    public String getDescription() {
        return description;
    }

    public Question description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        if (question.getOrder() == null || getOrder() == null) {
            return false;
        }
        return Objects.equals(getOrder(), question.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDescription());
    }

    @Override
    public String toString() {
        return "Question{" +
            "order=" + getOrder() +
            ", description='" + getDescription() + "'" +
            "}";
    }

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
    
    
}
