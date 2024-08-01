package com.nehms.game.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Component
public class WebSocketConfiguration implements WebSocketConfigurer {
	
	private final Communication communication;

	public WebSocketConfiguration(Communication communication) {
		this.communication = communication;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(communication , "/game").setAllowedOrigins("*");
	}

}