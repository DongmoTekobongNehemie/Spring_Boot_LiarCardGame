package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.nehms.game.entities.Card;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.entities.Message;
import com.nehms.game.entities.Pattern;
import com.nehms.game.entities.Player;

public class PlayCard {

	public void playCard(GameSession gameSession) throws IOException {

		BrosdCast brosdCast = new BrosdCast();

		Message message = new Message();
		Jsonation jsonation = new Jsonation();

		Winner winner = new Winner();
		winner.checkWinner(gameSession);

		if (gameSession.getGameStep().equals(GameStep.PLAY_CARD)) {

			if (getVeritablePlayer(gameSession)) {

				MessageProcessor messageProcessing = new MessageProcessor();
				if (messageProcessing.processingMessage(gameSession.getCurrentMessage()) == null) {
					gameSession.getCurrentSession().sendMessage(new TextMessage("mauvais format du message"));
					return;
				} else {

					String number = messageProcessing.processingMessage(gameSession.getCurrentMessage()).getNumber();
					Pattern pattern = messageProcessing.processingMessage(gameSession.getCurrentMessage()).getPattern();
					Pattern patternplay =messageProcessing.processingMessage(gameSession.getCurrentMessage()).getPatternPlay();

							gameSession.setCurrentPattern(pattern);

					playOneCard(number, pattern, gameSession.getPlayers().get(getIndexOfCurrentPlayer(gameSession)),
							gameSession.getGameTable(), gameSession.getCurrentcard());
					
					gameSession.setCurrentPattern(patternplay);

					// brosdCast.broadCastCards(gameSession.getPlayers().get(getIndexOfCurrentPlayer(gameSession)).getHand(),
					// gameSession.getCurrentSession());

					message.setBody("Votre main ♠️");
					message.setType("CARD");
					message.setCurrentCard(new Card(gameSession.getCurrentcard().getPattern(),
							gameSession.getCurrentcard().getNumber()));
					message.setCurrentPattern(patternplay);

					for (int i = 0; i < gameSession.getSocketSessions().size(); i++) {
						message.setCards(gameSession.getPlayers().get(i).getHand());
						gameSession.getSocketSessions().get(i)
								.sendMessage(new TextMessage(jsonation.convertToJson(message)));
					}

					
					message.setBody("vous venez de jouer la carte [ "
							+ gameSession.getCurrentcard().getNumber() +", "+gameSession.getCurrentcard().getPattern()+ " ] et vous avez dit " + patternplay);
					
					message.setType("vous avez jouez");
					
					gameSession.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));

					gameSession.setCurrentPlayer(gameSession.getPlayers().get(getIndexOfCurrentPlayer(gameSession)));

					gameSession.setIndexplayers(getIndexOfCurrentPlayer(gameSession));

					gameSession.setCurrentindexOfsessionCard(
							(gameSession.getCurrentindexOfsessionCard() + 1) % gameSession.getMaxPlayer());

					gameSession.setNbreTour((gameSession.getNbreTour() + 1));

					if (gameSession.getNbreTour() >= 4) {

						gameSession.setGameStep(GameStep.CONTESTAITON);


						message.setType("voulez-vous contester ?"); 
						message.setBody("voulez-vous contester le pattern ?");
						
						brosdCast.broadcastMessage(jsonation.convertToJson(message), gameSession.getSocketSessions());
								
					}

					
					message.setType("actuellement le tour"); 
					message.setBody("c'est actuelement le tour du " + gameSession.getPlayers()
					.get(gameSession.getCurrentindexOfsessionCard()).getNamePlayer());
					
					
					brosdCast.broadcastMessage(jsonation.convertToJson(message), gameSession.getSocketSessions());
							
					gameSession.setCurrentMessage("");
					return;
				}

			} else {

				if ("".equals(gameSession.getCurrentMessage())) {
					return;
				}
				
				message.setBody("ce n'est pas a votre tour de jouer !");
				message.setType("not your turn");
				
				gameSession.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));
				return;
			}
		}
	}

	public boolean getVeritablePlayer(GameSession gameSession) {

		return getIndexOfCurrentPlayer(gameSession) == gameSession.getCurrentindexOfsessionCard();

	}

	public int getIndexOfCurrentPlayer(GameSession gameSession) {

		for (int j = 0; j < gameSession.getSocketSessions().size(); j++) {
			if (gameSession.getSocketSessions().get(j).equals(gameSession.getCurrentSession())) {
				return j;
			}
		}
		return 0;
	}

	public void playOneCard(String numberOfcard, Pattern patternOfCard, Player joueur, List<Card> cardsPlayed,
			Card currentCard) {

		int indice = findCardInCards(joueur.getHand(), new Card(patternOfCard, numberOfcard));

		currentCard.setPattern(joueur.getHand().get(indice).getPattern());

		currentCard.setNumber(joueur.getHand().get(indice).getNumber());

		cardsPlayed.add(joueur.getHand().get(indice));
		joueur.getHand().remove(indice);
	}

	public int findCardInCards(List<Card> cards, Card card) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(card)) {
				return i;
			}
		}
		return -1;
	}

}
