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

import com.ibm.academia.apirest.Mapper.PlayerMapper;
import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.model.dto.PlayerDTO;
import com.ibm.academia.apirest.model.entities.Player;
import com.ibm.academia.apirest.model.services.IPlayerService;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private IPlayerService playerService;
	
	private final static Logger logger = LoggerFactory.getLogger(RouletteController.class);

	
	/**
	 * Endpoint to create a new Player
	 * @param player Name of the player
	 * @param result Json with the information of the player
	 * @return The information of the new player
	 * @author BRPI 17/05/22
	 */
	@PostMapping
	public ResponseEntity<?> createPlayer(@Valid @RequestBody Player player, BindingResult result){
		return this.playerService.createPlayer(player, result);
	}
	
	/**
	 * Endpoint to list the existing players
	 * @return List of the existing players
	 * @author BRPI 17/05/22
	 */
	@GetMapping("/players/list")
	public ResponseEntity<?> findAll(){
		List<Player> players = null;
		try {
			players = this.playerService.findAll();
		}catch(BadRequestException e) {
			logger.info("Message: " + e.getMessage() + ". Cause: " +e.getCause());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<PlayerDTO> listPlayers = players
				.stream()
				.map(PlayerMapper::mapPlayer)
				.collect(Collectors.toList());
		return new ResponseEntity<List<PlayerDTO>>(listPlayers, HttpStatus.OK);
	}
	
}
