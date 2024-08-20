package com.nehms.game.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@Component
@Slf4j
public class RoomHandler implements WebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String room = Objects.requireNonNull(session.getUri()).getPath().replace("/test/", "");
        log.info("Connected - {}", room);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Message - {} - {}", session.getId(), message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Transport Error - {} - {}", session.getId(), exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)  {
        log.info("Connection closed - {} - {}", session.getId(), closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
