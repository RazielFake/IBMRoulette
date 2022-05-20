package com.ibm.academia.apirest.Mapper;

import com.ibm.academia.apirest.model.dto.BetDTO;
import com.ibm.academia.apirest.model.entities.Bet;

public class BetMapper {

	public static BetDTO mapBet(Bet bet) {
		BetDTO betDTO = new BetDTO();
		betDTO.setId(bet.getId());
		betDTO.setAmount(bet.getAmount());
		betDTO.setRouletteNumber(bet.getRouletteNumber());
		betDTO.setRouletteColor(bet.getRouletteColor());
		betDTO.setIsWinner(bet.getIsWinner());
		betDTO.setReward(bet.getReward());
		betDTO.setPlayerId(bet.getPlayer().getId());
		betDTO.setRouletteId(bet.getRoulette().getId());
		return betDTO;
	}
	
}
