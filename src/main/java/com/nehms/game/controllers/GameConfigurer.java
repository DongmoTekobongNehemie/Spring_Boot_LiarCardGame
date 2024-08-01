package com.nehms.game.controllers;

import com.nehms.game.controllers.interfaces.Configurable;
import com.nehms.game.entities.GameSession;

public class GameConfigurer {
	
	
	public void liarGameConfiguration(GameSession gameSession) {
		
		  Configurable configureTheGame = new ConfigurationsMethods();
	      configureTheGame.createCards(gameSession.getCards());
	      configureTheGame.mixCards(gameSession.getCards());
	      configureTheGame.distribute(gameSession.getCards(), gameSession.getPlayers());
	}
}
