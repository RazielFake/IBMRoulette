package com.ibm.academia.apirest.Mapper;

import com.ibm.academia.apirest.model.dto.PlayerDTO;
import com.ibm.academia.apirest.model.entities.Player;

public class PlayerMapper {

	public static PlayerDTO mapPlayer(Player player) {
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setId(player.getId());
		playerDTO.setName(player.getName());
		return playerDTO;
	}

	
}
