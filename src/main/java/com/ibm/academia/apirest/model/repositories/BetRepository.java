package com.ibm.academia.apirest.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.model.entities.Bet;

@Repository
public interface BetRepository extends PagingAndSortingRepository<Bet, Integer>{
	
	@Query("select b from Bet b join fetch b.roulette r where r.id = ?1")
	public Iterable<Bet> findBetsByRouletteId(Integer id);
	
}
