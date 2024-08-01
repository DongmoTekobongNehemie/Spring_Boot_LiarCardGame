package com.nehms.game.controllers.interfaces;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

public interface Brosdcastable {
	
	public void broadcastMessage(String message, List<WebSocketSession> sessions);

}
