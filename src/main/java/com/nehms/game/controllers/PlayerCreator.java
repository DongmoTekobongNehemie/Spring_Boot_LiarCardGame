package com.nehms.game.controllers;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.entities.Player;

@Component
public class PlayerCreator {


	public void createPlayers(GameSession gameSession) throws IOException{
		
		BrosdCast brosdCast = new  BrosdCast();

		if (gameSession.getGameStep().equals(GameStep.CREATE_PLAYER)) {

			Player player = new Player("Player_" + (gameSession.getPlayers().size() + 1));

			if (!gameSession.getSocketSessions().contains(gameSession.getCurrentSession())
					&& gameSession.getPlayers().size() < gameSession.getMaxPlayer()) {

				gameSession.getPlayers().add(player);

				gameSession.getSocketSessions().add(gameSession.getCurrentSession());
				brosdCast.broadcastMessage("Bienvenue "
						+ gameSession.getPlayers().get(gameSession.getSocketSessions().size() - 1).getNamePlayer(),
						gameSession.getSocketSessions());

			}

			if (gameSession.getPlayers().size() == gameSession.getMaxPlayer()) {
				brosdCast.broadcastMessage("Are you ready to play ?", gameSession.getSocketSessions());
				gameSession.setGameStep(GameStep.ACCEPT_TO_PLAY);
				return;
			}
		}

		if (gameSession.getGameStep() != (GameStep.CREATE_PLAYER)
				&& !gameSession.getSocketSessions().contains(gameSession.getCurrentSession())) {
			gameSession.getCurrentSession().sendMessage(new TextMessage("The session game it's full !!"));
			gameSession.getCurrentSession().close();
		}
	}

}
