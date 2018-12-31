package com.geller.charts.domain;

import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AnswerScale.
 */
public class AnswerScale implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("score")
    private Integer score;

    public String getDescription() {
        return description;
    }

    public AnswerScale description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public AnswerScale score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
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
        AnswerScale answerScale = (AnswerScale) o;
        if (answerScale.getScore() == null || getScore() == null) {
            return false;
        }
        return Objects.equals(getScore(), answerScale.getScore());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getScore());
    }

    @Override
    public String toString() {
        return "AnswerScale{" +
            " description='" + getDescription() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
