package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nehms.game.controllers.interfaces.Brosdcastable;
import com.nehms.game.entites.Card;

public final class Broadcast implements  Brosdcastable {
	
	@Override
	public void broadcastMessage(String message, List<WebSocketSession> sessions) {
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void broadCastCards(List<Card> cards, WebSocketSession session) {
		for (Card card : cards) {
			try {
				session.sendMessage(new TextMessage("Vos cartes "+card.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
