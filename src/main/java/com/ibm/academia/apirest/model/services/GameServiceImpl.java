package com.ibm.academia.apirest.model.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ibm.academia.apirest.enums.Color;
import com.ibm.academia.apirest.model.entities.Roulette;

@Service
public class GameServiceImpl implements IGameService{

	@Autowired
	@Lazy
	private IRouletteService rouletteService;
	
	@Autowired
	@Lazy
	private IBetService betService;
	
	@Override
	public void playRoulette(Integer idRoulette) {
		Integer numberWinner = new Random().nextInt(37);
		Roulette roulette = this.rouletteService.findById(idRoulette).get();
		roulette.setNumberWiner(numberWinner);
		
		roulette.setColorWiner(findColor(numberWinner));
		this.rouletteService.updateRoulette(roulette);
		this.setRewards(roulette.getId(), numberWinner);
	}

	
	public void setRewards(Integer idRoulette, Integer numberWinner) {
		this.betService.findBetsByRouletteId(idRoulette).forEach(
				bet -> {
					if((bet.getRouletteNumber() != null) && (bet.getRouletteNumber() == numberWinner)) {
						bet.setIsWinner(true);
						bet.setReward(bet.getAmount()*36);
						this.betService.save(bet);
					}else if((bet.getRouletteColor() != null) && (this.findColor(numberWinner) == bet.getRouletteColor())) {
						bet.setIsWinner(true);
						bet.setReward(bet.getAmount()*2);
						this.betService.save(bet);
					}
				});
	}
	
	public Color findColor(Integer winner) {
		return switch(winner){
		case 1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36 -> Color.RED;
		case 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35-> Color.BLACK;
		default -> null;
		};
	}
	
	

}
