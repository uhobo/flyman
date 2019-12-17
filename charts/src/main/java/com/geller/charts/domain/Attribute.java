package com.geller.charts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Attribute.
 */
@Document(collection = "attribute")
public class Attribute<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    @Indexed(unique=true)
    private String name;

   	private String description;
   	 
    @NotNull
   	private InputTypeEnum inputType;
   	  
    @NotNull
   	private String className;

    
    //TODO replace to Object type -> the type should be taken from attributeType
    @Field("valuesList")
    private List<T> valuesList; 

    private boolean mandatory;
    
    
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

    public Attribute<T> name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        Attribute<?> attribute = (Attribute<?>) o;
        if (attribute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attribute.getId());
    }

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "Attribute [id=" + id + ", name=" + name + ", description=" + description + ", inputType=" + inputType
				+ ", className=" + className + ", valuesList=" + valuesList + ", mandatory=" + mandatory + "]";
	}

    

	




	
}
