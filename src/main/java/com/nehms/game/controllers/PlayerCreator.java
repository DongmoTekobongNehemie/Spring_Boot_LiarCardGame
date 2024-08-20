package com.nehms.game.controllers;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.nehms.game.controllers.interfaces.Brosdcastable;
import com.nehms.game.entites.GameSession;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Message;
import com.nehms.game.entites.Player;

@Component
public class PlayerCreator {

	public void createPlayers(GameSession gameSession) throws IOException {

		Brosdcastable brosdCast = new Broadcast();

		Message objectResponse = new Message();

		Jsonation jsonation = new Jsonation();

		if (gameSession.getGameStep().equals(GameStep.CREATE_PLAYER)) {

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

				System.out.println("Nous avons un nouveau joueur la liste des connection est a : "
						+ gameSession.getSocketSessions().size());

			}

			if (gameSession.getPlayers().size() == gameSession.getMaxPlayer()) {

				objectResponse.setBody("Are you ready to play ?");

				objectResponse.setType("are you ready ?");

				brosdCast.broadcastMessage(jsonation.convertToJson(objectResponse), gameSession.getSocketSessions());
				gameSession.setGameStep(GameStep.ACCEPT_TO_PLAY);
				
				System.out.println("nous somme a la phase de "+gameSession.getGameStep());
				return;
			}
		}

		System.out.println("dans la liste des socket "+gameSession.getSocketSessions().size()+" dans la liste de players "+gameSession.getPlayers().size());;
		
	}

}
