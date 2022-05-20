package com.ibm.academia.apirest.model.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.model.entities.Bet;
import com.ibm.academia.apirest.model.entities.Player;
import com.ibm.academia.apirest.model.entities.Roulette;
import com.ibm.academia.apirest.model.repositories.BetRepository;

@Service
public class BetServiceImpl extends GenericServiceImpl<Bet, BetRepository> implements IBetService{

	@Autowired
	private IRouletteService rouletteService;
	
	@Autowired
	private IPlayerService playerService;
	
	@Autowired
	public BetServiceImpl(BetRepository repository) {
		super(repository);
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<Bet> findBetsByRouletteId(Integer id) {
		List<Bet> bets = (List<Bet>)this.repository.findBetsByRouletteId(id);
		if(bets.isEmpty())
			throw new BadRequestException(String.format(
					"Roulette with ID: %d has not any bet.", id));
		return (List<Bet>)this.repository.findBetsByRouletteId(id);
	}

	@Override
	@Transactional
	public ResponseEntity<?> createBet(@Valid @RequestBody Bet bet, BindingResult result ) {
		Map<String, Object> validations = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> listErrors = result.getFieldErrors()
					.stream()
					.map(errors -> "Field: " + errors.getField() + " - " + errors.getDefaultMessage())
					.collect(Collectors.toList());
			validations.put("Errors List", listErrors);
			return new ResponseEntity<Map<String, Object>>(validations, HttpStatus.BAD_REQUEST);
		}
		
		if(!verifyFields(bet))
			return new ResponseEntity<String>("Especify a number or a color for the bet.", HttpStatus.BAD_REQUEST);
		
		try {
			asociateRoulettePlayer(bet.getRouletteId(), bet.getPlayerId());
		}catch(NotFoundException e) {
			throw e;
		}
		
		Roulette roulette = this.rouletteService.findById(bet.getRouletteId()).get();
		
		if(!roulette.isOpen())
			return new ResponseEntity<String>(String.format(
					"The rulette with ID: %d is closed.", roulette.getId()), HttpStatus.BAD_REQUEST);
		
		bet.setRoulette(roulette);
		bet.setPlayer(this.playerService.findById(bet.getPlayerId()).get());
	
		Bet savedBet = this.repository.save(bet);
		return new ResponseEntity<Bet>(savedBet, HttpStatus.CREATED);
	}
	
	
	public void asociateRoulettePlayer(Integer rouletteId, Integer playerId) {
		Optional<Roulette> oRoulette = this.rouletteService.findById(rouletteId);
		if(!oRoulette.isPresent())
			throw new NotFoundException(String.format(
					"The roulette with ID: %d doesn't exist.", rouletteId));
		
		
		Optional<Player> oPlayer = this.playerService.findById(playerId);
		if(!oPlayer.isPresent())
			throw new NotFoundException(String.format(
					"The player with ID: %d doesn't exist.", playerId));
		
	}
	
	
	public boolean verifyFields(Bet bet) {
		if(bet.getRouletteNumber() == null && bet.getRouletteColor() == null)
			return false;
		else return true;
	}
	
	
	
	

}
