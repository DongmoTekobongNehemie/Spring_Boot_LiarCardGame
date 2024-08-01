package com.nehms.game.services;

import java.io.IOException;

import com.nehms.game.controllers.AskToPlay;
import com.nehms.game.controllers.Ochestrater;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessAskOkToPlay implements Ochestrater {

	Ochestrater askOkToPlay;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.askOkToPlay = ochestrater;
	}

	@Override
	public void processTheCurrentState(GameSession gameSession) throws GameSessionNullException, IOException{

		if(gameSession==null) {
			throw new GameSessionNullException();
		}
		
		if (gameSession.getGameStep().equals(GameStep.ACCEPT_TO_PLAY)) {

			AskToPlay askToPlay = new AskToPlay();
			askToPlay.askIfIsOkToPlay(gameSession);
			return;
		}
	}

}
