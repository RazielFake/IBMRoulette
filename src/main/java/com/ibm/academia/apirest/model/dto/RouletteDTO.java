package com.ibm.academia.apirest.model.dto;


import com.ibm.academia.apirest.enums.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouletteDTO {

	private Integer id;
	
	private boolean isOpen;
	
	private Integer numberWiner;
	
	private Color colorWiner;
	
}
