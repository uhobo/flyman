package com.geller.charts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Answer.
 */
@Document(collection = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private Integer questionOrder;

  
    @Field("result")
    @JsonIgnoreProperties("")
    private AnswerScale result;

    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(Integer questionOrder) {
		this.questionOrder = questionOrder;
	}

	public AnswerScale getResult() {
        return result;
    }

    public Answer result(AnswerScale answerScale) {
        this.result = answerScale;
        return this;
    }

    public void setResult(AnswerScale answerScale) {
        this.result = answerScale;
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
        Answer answer = (Answer) o;
        if (answer.getQuestionOrder() == null || getQuestionOrder() == null) {
            return false;
        }
        return Objects.equals(getQuestionOrder(), answer.getQuestionOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            "}";
    }
}
