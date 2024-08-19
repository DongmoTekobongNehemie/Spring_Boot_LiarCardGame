package com.nehms.game.services;

import java.io.IOException;
import java.util.List;

import com.nehms.game.controllers.Contestation;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Room;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessContestation implements Ochestrater {

	Ochestrater ochestraterEndOfGame;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.ochestraterEndOfGame = ochestrater;
	}

	@Override
	public void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException {

		if (socketGestionner == null) {
			throw new GameSessionNullException();
		}

		int indice = 0;
		String[] message = socketGestionner.getCurrentMessage().split("=");
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
				.equals(GameStep.CONTESTAITON)) {

			Contestation contestation = new Contestation();
			System.out.println("le message nevoyer"+message[1]);
	
			socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().setCurrentMessage(message[1]);
			socketGestionner.getRoomsOftheSocket().get(indice).getGameSession().setCurrentSession(socketGestionner.getCurrentSession());

			
			try {
				contestation.contestation(socketGestionner.getRoomsOftheSocket().get(indice).getGameSession());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//			return; d
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
