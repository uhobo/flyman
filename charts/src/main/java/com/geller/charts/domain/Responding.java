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
 * A Responding.
 */
@Document(collection = "responding")
public class Responding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @DBRef
    @Field("attributes")
    private Set<PersonalAttribute> attributes = new HashSet<>();
    
    @DBRef
    @Field("surveys")
    private Set<Survey> surveys = new HashSet<>();

    //private Set<SurveymetaData> surveysData = new HashSet<>();
    
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Responding description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PersonalAttribute> getAttributes() {
        return attributes;
    }

    public Responding attributes(Set<PersonalAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Responding addAttributes(PersonalAttribute attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public Responding removeAttributes(PersonalAttribute attribute) {
        this.attributes.remove(attribute);
        return this;
    }

    public void setAttributes(Set<PersonalAttribute> attributes) {
        this.attributes = attributes;
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
        Responding responding = (Responding) o;
        if (responding.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responding.getId());
    }

  


	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Responding{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
