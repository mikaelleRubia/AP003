package com.ProvaGrupo.SpringEcommerce.controller.dto;

import java.util.List;

import com.ProvaGrupo.SpringEcommerce.model.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
	private Long id;
    private String name;
    
    private List<String> possibleFacets;
    
    
	public CategoryDto (Category entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
