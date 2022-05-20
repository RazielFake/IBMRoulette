package com.ibm.academia.apirest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.Mapper.BetMapper;
import com.ibm.academia.apirest.Mapper.RouletteMapper;
import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.model.dto.BetDTO;
import com.ibm.academia.apirest.model.dto.RouletteDTO;
import com.ibm.academia.apirest.model.entities.Bet;
import com.ibm.academia.apirest.model.entities.Roulette;
import com.ibm.academia.apirest.model.services.IBetService;
import com.ibm.academia.apirest.model.services.IRouletteService;

@RestController
@RequestMapping("/roulette")
public class RouletteController {

	@Autowired
	private IRouletteService rouletteService;
	
	@Autowired 
	private IBetService betService;
	
	private final static Logger logger = LoggerFactory.getLogger(RouletteController.class);
	
	/**
	 * Endpoint to create a new roulette
	 * @return Returns the returns roulette's id
	 * @author BRPI 17/05/22
	 */
	@PostMapping
	public ResponseEntity<?> createRoulette(){
		Integer idRoulette = this.rouletteService.createRoulette();
		return new ResponseEntity<String>(String.format(
				"Roulette with ID: %d created.", idRoulette), HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint to list all existing roulettes
	 * @return List with all roulettes in the database
	 * @BadRequestException In case there weren't roulettes on the database
	 * @author BRPI 17/05/22
	 */
	@GetMapping("/roulette/list")
	public ResponseEntity<?> findAll(){
		List<Roulette> roulettes = null;
		try {
			roulettes = this.rouletteService.findAll();
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>("There aren't roulettes registered", HttpStatus.BAD_REQUEST);
		}
		List<RouletteDTO> listRoulettes = roulettes
				.stream()
				.map(RouletteMapper::mapRouletteStatus)
				.collect(Collectors.toList());
		
		return new ResponseEntity<List<RouletteDTO>>(listRoulettes, HttpStatus.OK);
	}
	
	
	/**
	 * Endpoint to open a roulette 
	 * @param rouletteId Receives the id of the roulette to open
	 * @NotFoundException In case the roulette doesn't exist
	 * @BadRequestException In case the roulette is already open
	 * @return Verification that the roulette was open
	 * @author BRPI 14/05/22
	 */
	@PostMapping("/open-roulette/rouletteId/{rouletteId}")
	public ResponseEntity<?> openRoulette(@PathVariable Integer rouletteId) {
		try {
			this.rouletteService.openRoulette(rouletteId);
		}catch(NotFoundException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(String.format(
				"Roulette with ID: %d open", rouletteId), HttpStatus.OK);
	}
	
	/**
	 * Endpoint to find a roulette by Id
	 * @param rouletteId The id of the roulette we want to find
	 * @return Object Roulette with httpStatus code
	 * @author BRPI 19/05/22
	 */
	@GetMapping("/rouletteId/{rouletteId}")
	public ResponseEntity<?> findRouletteById(@PathVariable Integer rouletteId){
		Optional<Roulette> oRoulette;
		try {
			oRoulette = this.rouletteService.findById(rouletteId);
		}catch(NotFoundException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Roulette>(oRoulette.get(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint to delete a roulette
	 * @param rouletteId The id of the roulette to delete
	 * @return Confirmation the roulette was deleted 
	 * @BadRequestException in case the roulette is open and therefor can not be deleted
	 * @NotFoundException in case the roulette doesn't exist
	 * @author BRPI 19/05/22
	 */
	@DeleteMapping("/delete/rouletteId/{rouletteId}")
	public ResponseEntity<?> deleteRouletteById(@PathVariable Integer rouletteId) {
		try {
			this.rouletteService.findById(rouletteId);
			this.rouletteService.isOpen(rouletteId);
			this.rouletteService.deleteById(rouletteId);
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(NotFoundException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(String.format(
				"Roulette with ID: %d deleted", rouletteId), HttpStatus.OK);
	}
	
	/**
	 * Endpoint to close a roulette and start the game
	 * @param rouletteId The id of the roulette to close
	 * @NotFoundException In case the roulette does not exist
	 * @BadRequestException In case the roulette were closed or the list of bets were empty
	 * @return List of the bets on the roulette
	 */
	@PostMapping("/close/rouletteId/{rouletteId}")
	public ResponseEntity<?> closeRoulette(@PathVariable Integer rouletteId){
		try {
			this.rouletteService.closeRoulette(rouletteId);
		}catch(NotFoundException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<Bet> bets;
		try {
			bets = this.betService.findBetsByRouletteId(rouletteId);
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
