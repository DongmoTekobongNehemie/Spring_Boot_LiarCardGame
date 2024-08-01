package com.nehms.game.controllers.interfaces;

import java.io.IOException;

import com.nehms.game.entities.GameSession;
import com.nehms.game.exceptions.GameSessionNullException;

public interface Ochestrater {

	void nextOchestrater(Ochestrater ochestrater);
	void processTheCurrentState(GameSession gameSession) throws GameSessionNullException, IOException;
}
