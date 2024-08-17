package com.nehms.game.controllers.interfaces;

import java.io.IOException;

import com.nehms.game.entites.GameSession;

public interface PlayerCreatable {
	
	public void createPlayers(GameSession gameSession) throws IOException;
}
