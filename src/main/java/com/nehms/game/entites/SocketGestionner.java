package com.nehms.game.entites;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketGestionner {
	
	private List<Room> roomsOftheSocket;
	private int currentRoomIndex;
	private WebSocketSession currentSession;
	private String currentMessage;

	public SocketGestionner() {
		this.setRoomsOftheSocket(new ArrayList<>());
	}
	
}
