package com.ibm.academia.apirest.model.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.academia.apirest.model.entities.Bet;

public interface IBetService extends IGenericService<Bet>{
	
	public List<Bet> findBetsByRouletteId(Integer id);
	
	public ResponseEntity<?> createBet(@Valid @RequestBody Bet bet, BindingResult result );
	
}
