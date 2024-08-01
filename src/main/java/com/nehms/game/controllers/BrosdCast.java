package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public final class BrosdCast {
	
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
}
