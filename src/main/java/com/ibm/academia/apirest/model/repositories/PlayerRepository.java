package com.ibm.academia.apirest.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.model.entities.Player;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Integer>{

	public Optional<Player> findPlayerByName(String name);
	
}
