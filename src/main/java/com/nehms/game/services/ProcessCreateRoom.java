package com.nehms.game.services;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import com.nehms.game.controllers.Jsonation;
import com.nehms.game.controllers.interfaces.Ochestrater;
import com.nehms.game.entites.Message;
import com.nehms.game.entites.Room;
import com.nehms.game.entites.SocketGestionner;
import com.nehms.game.exceptions.GameSessionNullException;

public class ProcessCreateRoom implements Ochestrater {

	 Ochestrater ochestraterCreateRoom;
	
	@Override
	public void nextOchestrater(Ochestrater ochestrater) {
		this.ochestraterCreateRoom = ochestrater;
	}

	@Override
	public void processTheCurrentState(SocketGestionner socketGestionner) throws GameSessionNullException, IOException {
		
		if(socketGestionner.getCurrentMessage()==null) {
			return;
		}
		
		
		if(socketGestionner.getCurrentMessage().equals("createRoom")) {
			Jsonation jsonation = new Jsonation();
		Message message = new Message();
		Room room = new Room();	
		message.setBody(room.getRoomKey());
		message.setType("roomkey");

		System.out.println(room); 
		
		if(!socketGestionner.getRoomsOftheSocket().contains(room)) {
			socketGestionner.getRoomsOftheSocket().add(room);
			socketGestionner.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));
			message.setType("roomCreated");
			message.setBody("Bienvenue Veillez rejoindre une partie ou creer un salon de jeu");
			socketGestionner.getCurrentSession().sendMessage(new TextMessage(jsonation.convertToJson(message)));
		}
		
		}
		
		
	}

}
