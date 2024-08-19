package com.nehms.game.services;

import java.io.IOException;
import java.util.List;

import com.nehms.game.controllers.PlayCard;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Room;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessPlayCard implements Ochestrater {

	Ochestrater playCardOchestrater;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.playCardOchestrater = ochestrater;
	}

	@Override
	public void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException {

		int indice = 0;
		String[] message = socketGestionner.getCurrentMessage().split("-");
		System.out.println("a ctte etape " + socketGestionner.getCurrentMessage());
		System.out.println(message.length);
////
		if (message.length == 4) {
			System.out.println("ok c'est bon");

			indice = findTheIndexOfRoom(message[0], socketGestionner.getRoomsOftheSocket());

			// gestion des erreus d'incice important

		} else {
			return;
		}
//		
		if (socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().getGameStep()
				.equals(GameStep.PLAY_CARD)) {

			PlayCard playCard = new PlayCard();
			socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().setCurrentSession(socketGestionner.getCurrentSession());

			socketGestionner.getRoomsOftheSocket().get(indice).getGameSession()
					.setCurrentMessage(message[1] + "-" + message[2] + "-" + message[3]);
			playCard.playCard(socketGestionner.getRoomsOftheSocket().get(indice).getGameSession());
			return;
		}
	}

	public int findTheIndexOfRoom(String keyroom, List<Room> rooms) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getRoomKey().equals(keyroom)) {
				return i;
			}
		}
		return -1;
	}
}
