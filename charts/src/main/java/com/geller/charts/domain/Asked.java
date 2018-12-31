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
 * A Asked.
 */
@Document(collection = "asked")
public class Asked implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("internalId")
    private String internalId;
    
   
    @NotNull
    @Field("description")
    private String description;

    @DBRef
    @Field("attributes")
    private Set<Attribute> attributes = new HashSet<>();

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

    public Asked description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Asked attributes(Set<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Asked addAttributes(Attribute attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public Asked removeAttributes(Attribute attribute) {
        this.attributes.remove(attribute);
        return this;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
    
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asked asked = (Asked) o;
        if (asked.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asked.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asked{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
