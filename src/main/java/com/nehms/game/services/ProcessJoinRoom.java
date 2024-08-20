package com.nehms.game.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.nehms.game.controllers.Jsonation;
import com.nehms.game.controllers.PlayerCreator;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.Message;
import com.nehms.game.entites.Room;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessJoinRoom implements Ochestrater {

	Ochestrater ochestraterJoinRoom;

	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.ochestraterJoinRoom = ochestrater;
	}

	@Override

	public void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException {

		String checkRomkey = socketGestionner.getCurrentMessage();
		PlayerCreator playerCreator = new PlayerCreator();
		Message message = new Message();
		Jsonation jsonation = new Jsonation();
		if (checkRomkey != null) {

			for (Room room : socketGestionner.getRoomsOftheSocket()) {
				if (room.getRoomKey().equals(checkRomkey)) {
					
					if(room.getGameSession().getSocketSessions().size()==room.getGameSession().getMaxPlayer()) {
						message.setBody("la sesseion de la socket est full !! ");
						message.setType("full");
						socketGestionner.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));
						return;
					}
					
					room.getGameSession().setCurrentSession(socketGestionner.getCurrentSession());
					room.getGameSession().setCurrentMessage(socketGestionner.getCurrentMessage());
					playerCreator.createPlayers(room.getGameSession());
					
					socketGestionner.setCurrentRoomIndex(findTheIndexOfRoom(checkRomkey, socketGestionner.getRoomsOftheSocket()));
					
					message.setType("gameok");
					message.setBody("");
					room.getGameSession().getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));
					System.out.println(message);
				}
			}
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
