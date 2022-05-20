package com.ibm.academia.apirest.Mapper;

import com.ibm.academia.apirest.model.dto.RouletteDTO;
import com.ibm.academia.apirest.model.entities.Roulette;

public class RouletteMapper {

	
	public static RouletteDTO mapRouletteStatus(Roulette roulette) {
		RouletteDTO rouletteDTO = new RouletteDTO();
		rouletteDTO.setId(roulette.getId());
		rouletteDTO.setOpen(roulette.isOpen());
		rouletteDTO.setNumberWiner(roulette.getNumberWiner());
		rouletteDTO.setColorWiner(roulette.getColorWiner());
		return rouletteDTO;
	}
	
}
