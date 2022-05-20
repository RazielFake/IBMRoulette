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

import com.ibm.academia.apirest.model.entities.Player;
import com.ibm.academia.apirest.model.repositories.PlayerRepository;

@Service
public class PlayerServiceImpl extends GenericServiceImpl<Player, PlayerRepository> implements IPlayerService{

	@Autowired
	public PlayerServiceImpl(PlayerRepository repository) {
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Player> findPlayerByName(String name) {
		return this.repository.findPlayerByName(name);
	}

	@Override
	@Transactional
	public ResponseEntity<?> createPlayer(@Valid Player player, BindingResult result) {
		Map<String, Object> validations = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> listErrors = result.getFieldErrors()
					.stream()
					.map(errors -> "Field: " + errors.getField() + " - " + errors.getDefaultMessage())
					.collect(Collectors.toList());
			validations.put("Errors List", listErrors);
			return new ResponseEntity<Map<String, Object>>(validations, HttpStatus.BAD_REQUEST);
		}
		Player savedPlayer = this.repository.save(player);
		return new ResponseEntity<Player>(savedPlayer, HttpStatus.CREATED);
	}
	
	

}
