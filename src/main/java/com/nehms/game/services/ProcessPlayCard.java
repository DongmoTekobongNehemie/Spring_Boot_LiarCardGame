package com.nehms.game.services;

import java.io.IOException;

import com.nehms.game.controllers.PlayCard;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessPlayCard implements Ochestrater {

	Ochestrater playCardOchestrater;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.playCardOchestrater = ochestrater;
	}

	@Override
	public void processTheCurrentState(GameSession gameSession) throws GameSessionNullException, IOException {

		if(gameSession==null) {
			throw new GameSessionNullException();
		}
		
		if (gameSession.getGameStep().equals(GameStep.PLAY_CARD)) {
			
			PlayCard playCard = new PlayCard();
			playCard.playCard(gameSession);
			return;
		}
	}

}
