package com.nehms.game.play;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;
import com.nehms.game.services.ProcessAskOkToPlay;
import com.nehms.game.services.ProcessContestation;
import com.nehms.game.services.ProcessCreateRoom;
import com.nehms.game.services.ProcessJoinRoom;
import com.nehms.game.services.ProcessPlayCard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Setter
@Getter
public class Game {

	private SocketGestionner socketGestionner = new SocketGestionner();

	public void play(WebSocketSession session, TextMessage textMessage) throws GameSessionNullException, IOException {

		Ochestrater ochestraterAskToPlay = new ProcessAskOkToPlay();
		Ochestrater ochestraterPlayCard = new ProcessPlayCard();
		Ochestrater ochestraterContestation = new ProcessContestation();
		Ochestrater ochestraterCreateRoom = new ProcessCreateRoom();
		Ochestrater ochestraterJoinRoom = new ProcessJoinRoom();

		if (textMessage != null) {
			socketGestionner.setCurrentMessage(textMessage.getPayload());
		}

		socketGestionner.setCurrentSession(session);

		ochestraterCreateRoom.processTheCurrentState(socketGestionner);
		ochestraterJoinRoom.processTheCurrentState(socketGestionner);
		ochestraterAskToPlay.processTheCurrentState(socketGestionner);	
		ochestraterPlayCard.processTheCurrentState(socketGestionner);
		ochestraterContestation.processTheCurrentState(socketGestionner);
//			ochestraterAskToPlay.processTheCurrentState(socketGestionner);
//			ochestraterContestation.processTheCurrentState(socketGestionner);

	}

}
