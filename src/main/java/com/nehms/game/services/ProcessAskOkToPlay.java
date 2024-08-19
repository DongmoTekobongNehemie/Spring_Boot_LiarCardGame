package com.nehms.game.services;

import java.io.IOException;
import java.util.List;

import com.nehms.game.controllers.AskToPlay;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Room;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessAskOkToPlay implements Ochestrater {

	Ochestrater askOkToPlay;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.askOkToPlay = ochestrater;
	}

	@Override
	public void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException {

		int indice = 0;
		String[] message = socketGestionner.getCurrentMessage().split("-");
		System.out.println("a ctte etape " + socketGestionner.getCurrentMessage());
		System.out.println(message.length);
////
		if (message.length == 2) {
			System.out.println("ok c'est bon");
			indice = findTheIndexOfRoom(message[0], socketGestionner.getRoomsOftheSocket());

		} else {
			return;
		}
//		
		if (socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().getGameStep()
				.equals(GameStep.ACCEPT_TO_PLAY)) {

			AskToPlay askToPlay = new AskToPlay();
			socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().setCurrentMessage(message[1].trim());
			askToPlay.askIfIsOkToPlay(socketGestionner.getRoomsOftheSocket().get(indice).getGameSession());
			return;
		}
	}

//
	public int findTheIndexOfRoom(String keyroom, List<Room> rooms) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getRoomKey().equals(keyroom)) {
				return i;
			}
		}
		return -1;
	}

}
