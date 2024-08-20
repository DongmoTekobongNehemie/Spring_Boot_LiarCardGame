package com.nehms.game.controllers;

import java.io.IOException;
import org.springframework.web.socket.TextMessage;

import com.nehms.game.entites.GameSession;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Message;

public class AskToPlay {

	public void askIfIsOkToPlay(GameSession gameSession) throws IOException {

		Broadcast broadcast = new Broadcast();
		Jsonation jsonation = new Jsonation();
		Message message = new Message();

		if (gameSession.getCurrentMessage().equals("")) {
			return;
		}

		if (!gameSession.getGameStep().equals(GameStep.ACCEPT_TO_PLAY)) {
			return;
		}

		if (gameSession.getCurrentMessage().equalsIgnoreCase("oui")) {
			gameSession.setNbreOkfOk(gameSession.getNbreOkfOk() + 1);
			System.out.println("nbre de oui"+gameSession.getNbreOkfOk());

			if (gameSession.getNbreOkfOk() == gameSession.getMaxPlayer()) {
				gameSession.setGameStep(GameStep.PLAY_CARD);
				GameConfigurer configurer = new GameConfigurer();
				configurer.liarGameConfiguration(gameSession);
				message.setBody("Votre main ♠️");
				message.setType("CARD");

				for (int i = 0; i < gameSession.getSocketSessions().size(); i++) {
					message.setCards(gameSession.getPlayers().get(i).getHand());
					gameSession.getSocketSessions().get(i)
							.sendMessage(new TextMessage(jsonation.convertToJson(message)));
				}

				message.setBody("Bonne change a tous  !! 🍀🍀");
				message.setType("bonne chance");
				message.setCards(null);

				broadcast.broadcastMessage(jsonation.convertToJson(message), gameSession.getSocketSessions());

				message.setBody("Joueur 1 a toi de jouer !! 🚩");
				message.setType("a toi");

				gameSession.getSocketSessions().getFirst()
						.sendMessage(new TextMessage(jsonation.convertToJson(message)));// first
			}

		} else {
			return;
		}

	}
}
