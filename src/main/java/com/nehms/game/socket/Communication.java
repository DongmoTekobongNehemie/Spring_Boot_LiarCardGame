package com.nehms.game.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.nehms.game.play.Game;

@Component
public class Communication extends TextWebSocketHandler {
	
	private final Game game;
	
	public Communication(Game play) {
		this.game = play;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		game.play(session, null);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		game.play(session, message);
	}

}