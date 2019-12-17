package com.geller.charts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Person.
 */
//@Document(collection = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    @NotNull
    @Field("internal_id")
    private String internalId;

    @NotNull
    @Field("firstName")
    private String firstName;

    @NotNull
    @Field("lastName")
    private String lastName;

    @NotNull
    
    @Field("description")
    private String description;

   
    
    @NotNull
    @Field("personType")
    private Integer personType;
    
    @Field("attributes")
    protected Set<PersonalAttribute> attributes = new HashSet<>();
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalId() {
        return internalId;
    }

    public Person internalId(String internalId) {
        this.internalId = internalId;
        return this;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPersonType() {
        return personType;
    }

    public Person personType(Integer personType) {
        this.personType = personType;
        return this;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }
    
    public String getDescription() {
        return description;
    }

    public Person description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Set<PersonalAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<PersonalAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public Person addAttributes(PersonalAttribute attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public Person removeAttributes(PersonalAttribute attribute) {
        this.attributes.remove(attribute);
        return this;
    }
    
    public Person attributes(Set<PersonalAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }



	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", internalId='" + getInternalId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", personType=" + getPersonType() +
            "}";
    }
}
