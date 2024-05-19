package com.ProvaGrupo.SpringEcommerce.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class to represent a product attribute object with the necessary fields. 
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAttribute implements Serializable {
   
    /**
     * The name of the attribute.
     */
    private String attributeName;
   
    /**
     * The value of the attribute.
     */
    private String attributeValue;
}
