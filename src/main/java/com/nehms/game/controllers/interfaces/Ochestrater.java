package com.nehms.game.controllers.interfaces;

import java.io.IOException;

import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public interface Ochestrater {

	void nextOchestrater(Ochestrater ochestrater);
	void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException;
}
