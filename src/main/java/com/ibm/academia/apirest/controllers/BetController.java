package com.ibm.academia.apirest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.Mapper.BetMapper;
import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.model.dto.BetDTO;
import com.ibm.academia.apirest.model.entities.Bet;
import com.ibm.academia.apirest.model.services.IBetService;

@RestController
@RequestMapping("/bet")
public class BetController {

	@Autowired
	private IBetService betService;
	
	private final static Logger logger = LoggerFactory.getLogger(RouletteController.class);

	/**
	 * Endpoint to create a new bet
	 * @param bet Recieve the information of the bet
	 * @param result 
	 * @return Confirmation that the bet was created
	 * @author BRPI 18/05/22
	 */
	@PostMapping
	public ResponseEntity<?> createBet(@Valid @RequestBody Bet bet, BindingResult result ){
		return this.betService.createBet(bet, result);
	}
	
	
	/**
	 * Endpoint to list existing bets
	 * @return List with existing bets
	 * @author BRPI 18/05/22
	 */
	@GetMapping("/bets/list")
	public ResponseEntity<?> findAll(){
		List<Bet> bets = null;
		try {
		bets = this.betService.findAll();
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<BetDTO> listBets = bets
				.stream()
				.map(BetMapper::mapBet)
				.collect(Collectors.toList());
		return new ResponseEntity<List<BetDTO>>(listBets, HttpStatus.OK);
	}
	
	
	
}
