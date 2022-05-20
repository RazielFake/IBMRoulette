package com.ibm.academia.apirest.model.dto;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

	private Integer id;
	
	@NotNull(message = "This field can not be null")
	@NotEmpty(message = "This field can not be empty")
	@Size(min = 5, max = 80)
	private String name;
	
}
