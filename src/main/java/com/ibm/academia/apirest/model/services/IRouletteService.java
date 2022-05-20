package com.ibm.academia.apirest.model.services;

import java.util.List;

import com.ibm.academia.apirest.model.entities.Roulette;

public interface IRouletteService extends IGenericService<Roulette>{
	
	public Integer createRoulette();
	
	public List<Roulette> findRoulettesByIsOpen(Boolean isOpen);
	
	public void openRoulette(Integer rouletteId);

	public void closeRoulette(Integer rouletteId);
	
	public void isOpen(Integer rouletteId);
	
	public void updateRoulette(Roulette foundRoulette);
	
	
}
