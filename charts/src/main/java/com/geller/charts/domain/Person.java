package com.geller.charts.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class Person {
	
	@Id
    private String id;

    @Field("internalId")
    private String internalId;
    
   
    @NotNull
    @Field("firstName")
    private String firstName;

    
    @NotNull
    @Field("lastName")
    private String lastName;

    @NotNull
    @Field("personType")
    private Integer personType;
    
    
    @Field("attributes")
    private Set<PersonalAttribute> attributes = new HashSet<>();

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getInternalId() {
		return internalId;
	}


	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Integer getPersonType() {
		return personType;
	}


	public void setPersonType(Integer personType) {
		this.personType = personType;
	}



    
    
    
    
    
}
