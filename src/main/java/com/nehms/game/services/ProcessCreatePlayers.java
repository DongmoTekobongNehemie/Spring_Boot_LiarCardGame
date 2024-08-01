package com.nehms.game.services;

import java.io.IOException;

import com.nehms.game.controllers.PlayerCreator;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entities.GameSession;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessCreatePlayers implements Ochestrater {

	 Ochestrater nextOchestrater;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.nextOchestrater = ochestrater;
	}

	@Override
	public void processTheCurrentState(GameSession gameSession) throws GameSessionNullException, IOException {
		
		if(gameSession==null) {
			throw new GameSessionNullException();
		}
		
		PlayerCreator playerCreator = new PlayerCreator();
		playerCreator.createPlayers(gameSession);
	
	
	}

}
