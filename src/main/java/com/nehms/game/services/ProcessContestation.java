package com.nehms.game.services;

import java.io.IOException;

import com.nehms.game.controllers.Contestation;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessContestation implements Ochestrater {

	Ochestrater ochestraterEndOfGame;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.ochestraterEndOfGame = ochestrater;
	}

	@Override
	public void processTheCurrentState(GameSession gameSession) throws GameSessionNullException, IOException {

		if (gameSession == null) {
			throw new GameSessionNullException();
		}

		if (gameSession.getGameStep().equals(GameStep.CONTESTAITON)) {

			Contestation contestation = new Contestation();
			try {
				contestation.contestation(gameSession);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			return; d
		}
	}

}
