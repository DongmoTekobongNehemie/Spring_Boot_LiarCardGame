package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.nehms.game.entities.Card;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.entities.Pattern;
import com.nehms.game.entities.Player;

public class PlayCard {

	public void playCard(GameSession gameSession) throws IOException {

		BrosdCast brosdCast = new BrosdCast();

		Winner winner = new Winner();
		winner.checkWinner(gameSession);

		if (gameSession.getGameStep().equals(GameStep.PLAY_CARD)) {

			if (getVeritablePlayer(gameSession)) {

				MessageProcessor messageProcessing = new MessageProcessor();
				if (messageProcessing.processingMessage(gameSession.getCurrentMessage()) == null) {
					gameSession.getCurrentSession().sendMessage(new TextMessage("mauvais format du message"));
					return;
				} else {

					int number = messageProcessing.processingMessage(gameSession.getCurrentMessage()).getNumber();
					Pattern pattern = messageProcessing.processingMessage(gameSession.getCurrentMessage()).getPattern();

					
					gameSession.setCurrentPattern(pattern);

					playOneCard(number, gameSession.getPlayers().get(getIndexOfCurrentPlayer(gameSession)),
							gameSession.getGameTable(), gameSession.getCurrentcard());

					gameSession.getCurrentSession().sendMessage(new TextMessage("vous venez de jouer la carte "
							+ gameSession.getCurrentcard().toString() + " et vous avez dit " + pattern));

					gameSession.setCurrentPlayer(gameSession.getPlayers().get(getIndexOfCurrentPlayer(gameSession)));
					
					gameSession.setIndexplayers(getIndexOfCurrentPlayer(gameSession));	
					
					gameSession.setCurrentindexOfsessionCard(
							(gameSession.getCurrentindexOfsessionCard() + 1) % gameSession.getMaxPlayer());

					gameSession.setNbreTour((gameSession.getNbreTour() + 1));
					
					if (gameSession.getNbreTour() >=4) {
						gameSession.setGameStep(GameStep.CONTESTAITON);
						brosdCast.broadcastMessage("voulez-vous contester le pattern ?",
								gameSession.getSocketSessions());
					}

					brosdCast.broadcastMessage(
							"c'est actuelement le tour du " + gameSession.getPlayers()
									.get(gameSession.getCurrentindexOfsessionCard()).getNamePlayer(),
							gameSession.getSocketSessions());
					gameSession.setCurrentMessage("");
					return;
				}

			} else {

				if ("".equals(gameSession.getCurrentMessage())) {
					return;
				}
				gameSession.getCurrentSession().sendMessage(new TextMessage("ce n'est pas a votre tour de jouer !"));
				return;
			}
		}
	}

	
	public boolean getVeritablePlayer(GameSession gameSession) {

		return getIndexOfCurrentPlayer(gameSession) == gameSession.getCurrentindexOfsessionCard() ;
		
	}

	public int getIndexOfCurrentPlayer(GameSession gameSession) {

		for (int j = 0; j < gameSession.getSocketSessions().size(); j++) {
			if (gameSession.getSocketSessions().get(j).equals(gameSession.getCurrentSession())) {
				return j;
			}
		}
		return 0;
	}

	public void playOneCard(int number, Player joueur, List<Card> cardsPlayed, Card currentCard) {
		
		currentCard.setPattern(joueur.getHand().get(number).getPattern());
		
		currentCard.setNumber(joueur.getHand().get(number).getNumber());
		cardsPlayed.add(joueur.getHand().get(number));
		joueur.getHand().remove(number);
	}

}
