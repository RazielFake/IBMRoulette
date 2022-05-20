package com.ibm.academia.apirest.model.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.model.entities.Roulette;
import com.ibm.academia.apirest.model.repositories.RouletteRepository;

@Service
public class RouletteServiceImpl extends GenericServiceImpl<Roulette, RouletteRepository> implements IRouletteService{

	@Autowired
	public RouletteServiceImpl(RouletteRepository repository) {
		super(repository);
	}

	@Autowired
	private IGameService gameService;
	
	@Override
	@Transactional
	public Integer createRoulette() {
		Roulette roulette = new Roulette();
		this.repository.save(roulette);
		return roulette.getId();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Roulette> findRoulettesByIsOpen(Boolean isOpen) {
		return (List<Roulette>) this.repository.findRoulettesByIsOpen(isOpen);
	}

	
	@Transactional
	public void updateRoulette(Roulette foundRoulette) {
		this.repository.save(foundRoulette);
	}

	@Override
	public void openRoulette(Integer rouletteId) {
		if(!this.repository.findById(rouletteId).isPresent())
			throw new NotFoundException(String.format(
					"Roulette with ID: %d does not exist.", rouletteId));
		
		Roulette foundRoulette = this.findById(rouletteId).get();
		
		if(foundRoulette.isOpen())
			throw new BadRequestException(String.format(
					"Roulette with ID: %d is already open.", rouletteId));
		
		foundRoulette.setOpen(true);
		updateRoulette(foundRoulette);
	}


	@Override
	public void isOpen(Integer RouletteId) {
		if(this.findById(RouletteId).get().isOpen())
			throw new BadRequestException(String.format(
					"Roulette with ID: %d is open an can not be deleted", RouletteId));

	}

	@Override
	public void closeRoulette(Integer rouletteId) {
		if(!this.repository.findById(rouletteId).isPresent())
			throw new NotFoundException(String.format(
					"Roulette with ID: %d does not exist.", rouletteId));
		
		Roulette foundRoulette = this.findById(rouletteId).get();
		
		if(foundRoulette.isOpen() == false)
			throw new BadRequestException(String.format(
					"Roulette with ID: %d has been closed already.", rouletteId));
		
		foundRoulette.setOpen(false);
		updateRoulette(foundRoulette);
		
		this.gameService.playRoulette(rouletteId);
	}


}






