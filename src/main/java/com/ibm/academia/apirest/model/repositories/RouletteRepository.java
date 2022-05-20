package com.ibm.academia.apirest.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.model.entities.Roulette;

@Repository
public interface RouletteRepository extends PagingAndSortingRepository<Roulette, Integer>{

	public Iterable<Roulette> findRoulettesByIsOpen(Boolean isOpen);
	
	public Iterable<Roulette> findRoulettesByBetsId(Integer betId);
	
}
