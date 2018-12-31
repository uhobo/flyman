package com.geller.charts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Attribute.
 */
@Document(collection = "attribute")
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @DBRef
    @Field("atributeType")
    private AttributeType attributeType;
    
    @NotNull
    @Field("isClosedValueList")
    private Boolean isClosedValueList;
    
    @DBRef
    @Field("valuesList")
    private List<AttributeValue> valuesList; 

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Attribute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute attributeType(AttributeType attributeType) {
    	this.attributeType = attributeType;
    	return this;
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
        Attribute attribute = (Attribute) o;
        if (attribute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attribute.getId());
    }

    public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    

	

	public Boolean getIsClosedValueList() {
		return isClosedValueList;
	}

	public void setIsClosedValueList(Boolean isClosedValueList) {
		this.isClosedValueList = isClosedValueList;
	}

	public List<AttributeValue> getValuesList() {
		return valuesList;
	}

	public void setValuesList(List<AttributeValue> valuesList) {
		this.valuesList = valuesList;
	}

	@Override
    public String toString() {
        return "Attribute{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", attributeType='" + getAttributeType() + "'" +
            "}";
    }
}
