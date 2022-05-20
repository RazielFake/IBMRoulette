package com.ibm.academia.apirest.model.dto;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.ibm.academia.apirest.enums.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO {

	private Integer id;
	
	@NotNull(message = "This field can not be null")
	@Min(value = 100, message = "The minimum bet is $100")
	@Max(value = 10000, message = "The maximum bet is $10000")
	@Positive(message = "This field must be above 0.")
	private Double amount;
	
	@NotNull(message = "This field can not be null")
	@Min(value = 0, message = "Roulette range is between 0 and 36")
	@Max(value = 36, message = "Roulette range is between 0 and 36")
	private Integer rouletteNumber;
	
	@Enumerated(EnumType.STRING)
	private Color rouletteColor;
	
	//@NotNull(message = "This field can not be null")
	//@Min(value = 0, message = "This field must be 0 or above")
	private Integer playerId;
	
	//@NotNull(message = "This field can not be null")
	//@Min(value = 0, message = "This field must be 0 or above")
	private Integer rouletteId;
	
	private Boolean isWinner;
	
	private Double reward;
	
}
