package com.nehms.game.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nehms.game.entites.Room;
import com.nehms.game.repository.RoomRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoomService {

	private RoomRepository roomRepository;

	public void servCreateRoom(Room room) {
		roomRepository.save(room);
	}

	public Room findRoomByKey(String key) {

		List<Room> rooms = new ArrayList<>();
		
		rooms.addAll(this.roomRepository.findByRoomKey(key));
		
		if(!rooms.isEmpty()) {
			return rooms.get(0);
		}
		
		return null;
		
	}

}
