package com.nehms.game.play;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.GameSession;
import com.nehms.game.entites.GameStep;
import com.nehms.game.exceptions.GameSessionNullException;
import com.nehms.game.services.ProcessAskOkToPlay;
import com.nehms.game.services.ProcessContestation;
import com.nehms.game.services.ProcessCreatePlayers;
import com.nehms.game.services.ProcessPlayCard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Setter
@Getter
public class Game {

	private GameSession gameSession = new GameSession();

	public void play(WebSocketSession session, TextMessage textMessage) throws GameSessionNullException, IOException {

		Ochestrater ochestraterCreatePlayer = new ProcessCreatePlayers();
		Ochestrater ochestraterAskToPlay = new ProcessAskOkToPlay();
		Ochestrater ochestraterPlayCard = new ProcessPlayCard();
		Ochestrater ochestraterContestation = new ProcessContestation();

		if (textMessage != null && !gameSession.getGameStep().equals(GameStep.CREATE_PLAYER)) {
			gameSession.setCurrentMessage(textMessage.getPayload());
		}

		gameSession.setCurrentSession(session);
		
			ochestraterCreatePlayer.processTheCurrentState(gameSession);
			ochestraterAskToPlay.processTheCurrentState(gameSession);
			ochestraterPlayCard.processTheCurrentState(gameSession);
			ochestraterContestation.processTheCurrentState(gameSession);

	}
	

}
