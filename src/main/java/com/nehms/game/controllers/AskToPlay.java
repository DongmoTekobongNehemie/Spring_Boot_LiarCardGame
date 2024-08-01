package com.nehms.game.controllers;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;

public class AskToPlay {

	public void askIfIsOkToPlay(GameSession gameSession) throws IOException {

		BrosdCast brosdCast = new BrosdCast();

		if (gameSession.getCurrentMessage().equals("")) {
			return;
		}

		if (!gameSession.getGameStep().equals(GameStep.ACCEPT_TO_PLAY)) {
			return;
		}

		if (gameSession.getCurrentMessage().equalsIgnoreCase("oui")) {
			if (!gameSession.getSocketOfOk().contains(gameSession.getCurrentSession())) {
				gameSession.getSocketOfOk().add(gameSession.getCurrentSession());
				gameSession.setCurrentMessage(""); // Effacer le message après traitement
			} else {
				gameSession.getCurrentSession().sendMessage(new TextMessage("Vous avez déjà répondu"));
				return;
			}
		} else {
			if (!gameSession.getSocketOfOk().contains(gameSession.getCurrentSession())) {
				gameSession.getCurrentSession().sendMessage(new TextMessage("Pour commencer dites <<oui>>"));
			}
			return;
		}

		if (gameSession.getSocketOfOk().size() == gameSession.getMaxPlayer()) {
			gameSession.setGameStep(GameStep.PLAY_CARD);
			GameConfigurer configurer = new GameConfigurer();
			configurer.liarGameConfiguration(gameSession);

			brosdCast.broadcastMessage("Bonne change a tous Joueur_1 a toi de jouer", gameSession.getSocketSessions());
//			return; j
		}
	}
}
