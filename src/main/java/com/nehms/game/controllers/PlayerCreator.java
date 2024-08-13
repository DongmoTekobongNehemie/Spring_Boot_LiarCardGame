package com.nehms.game.controllers;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.entities.Message;
import com.nehms.game.entities.Player;

@Component
public class PlayerCreator {

	public void createPlayers(GameSession gameSession) throws IOException {
		

		BrosdCast brosdCast = new BrosdCast();

		Message objectResponse = new Message();

		Jsonation jsonation = new Jsonation();

		if (gameSession.getGameStep().equals(GameStep.CREATE_PLAYER) ) {

			Player player = new Player("Player " + (gameSession.getPlayers().size() + 1));

			objectResponse.setNamePlayer("Player " + (gameSession.getPlayers().size() + 1));

			if (!gameSession.getSocketSessions().contains(gameSession.getCurrentSession())
					&& gameSession.getPlayers().size() < gameSession.getMaxPlayer()) {

				gameSession.getPlayers().add(player);
				
				gameSession.getSocketSessions().add(gameSession.getCurrentSession());

				objectResponse.setBody("Bienvenue "
						+ gameSession.getPlayers().get(gameSession.getSocketSessions().size() - 1).getNamePlayer());

				objectResponse.setType("creation de joueur");

				gameSession.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(objectResponse)));
				
				objectResponse.setType("");
				
				//System.out.println("Nous avons un nouveau joueur la liste des connection est a : "
					//	+ gameSession.getSocketSessions().size());

			}

			if (gameSession.getPlayers().size() == gameSession.getMaxPlayer()) {

				objectResponse.setBody("Are you ready to play ?");
				
				objectResponse.setType("are you ready ?");

				brosdCast.broadcastMessage(jsonation.convertToJson(objectResponse), gameSession.getSocketSessions());
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
