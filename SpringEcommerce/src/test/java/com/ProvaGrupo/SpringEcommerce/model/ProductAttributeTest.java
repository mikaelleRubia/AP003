package com.ProvaGrupo.SpringEcommerce.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductAttributeTest {

    @Test
    public void testDefaultConstructor() {
        ProductAttribute productAttribute = new ProductAttribute();
        assertNull(productAttribute.getAttributeName());
        assertNull(productAttribute.getAttributeValue());
    }

    @Test
    public void testParameterizedConstructor() {
        ProductAttribute productAttribute = new ProductAttribute("Color", "Red");
        assertEquals("Color", productAttribute.getAttributeName());
        assertEquals("Red", productAttribute.getAttributeValue());
    }

    @Test
    public void testSetters() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setAttributeName("Size");
        productAttribute.setAttributeValue("Large");
        assertEquals("Size", productAttribute.getAttributeName());
        assertEquals("Large", productAttribute.getAttributeValue());
    }

    @Test
    public void testGetters() {
        ProductAttribute productAttribute = new ProductAttribute("Material", "Cotton");
        assertEquals("Material", productAttribute.getAttributeName());
        assertEquals("Cotton", productAttribute.getAttributeValue());
    }
}