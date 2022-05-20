package com.ibm.academia.apirest.model.services;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.academia.apirest.model.entities.Player;

public interface IPlayerService extends IGenericService<Player>{
	
	
	public Optional<Player> findPlayerByName(String name);
	
	public ResponseEntity<?> createPlayer(@Valid @RequestBody Player player, BindingResult result);
	
}
