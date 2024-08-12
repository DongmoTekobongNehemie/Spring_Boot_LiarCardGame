package com.nehms.game.controllers.interfaces;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.nehms.game.entities.Card;

public interface Brosdcastable {
	
	public void broadcastMessage(String message, List<WebSocketSession> sessions);
	public void broadCastCards(List<Card> cards, WebSocketSession session);
	
}
