package com.nehms.game.controllers;

import com.nehms.game.entities.GameSession;

public class GameConfigurer {
	
	
	public void liarGameConfiguration(GameSession gameSession) {
		
		  ConfigurationsMethods configureTheGame = new ConfigurationsMethods();
	      configureTheGame.createCards(gameSession.getCards());
	      configureTheGame.mixCards(gameSession.getCards());
	      configureTheGame.distribute(gameSession.getCards(), gameSession.getPlayers());
	}
}
