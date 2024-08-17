package com.nehms.game.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nehms.game.entites.Room;
import com.nehms.game.services.RoomService;

import lombok.AllArgsConstructor;

@RequestMapping("liarGameRoom")
@RestController
@AllArgsConstructor
public class RoomController {

	private RoomService roomService;

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public Room createRoom(@RequestBody Room room) {
		roomService.servCreateRoom(room);
		return room;
	}

	@GetMapping(path = "{key}", produces = APPLICATION_JSON_VALUE)
	public boolean findRoom(@PathVariable String key) {
		
		if(this.roomService.findRoomByKey(key)!=null) {
			return true;
		}
		return false;
	}

}
