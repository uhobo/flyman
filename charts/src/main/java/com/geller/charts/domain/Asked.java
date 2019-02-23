package com.geller.charts.domain;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;



/**
 * A Asked.
 */
@Document(collection = "asked")
public class Asked extends Person {

    private static final long serialVersionUID = 1L;

       

    @Override
    public String toString() {
        return "Asked{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
