package com.ProvaGrupo.SpringEcommerce.controller.form;

import java.math.BigDecimal;
import java.util.List;

import com.ProvaGrupo.SpringEcommerce.model.*;

public record ProductForm(String name, String description, BigDecimal price, String sku, String imageUrl, Category category, List<ProductAttribute> productAttributeList, Integer quantity, String manufacturer, boolean featured, List<ProductRating> productRating) {

}
