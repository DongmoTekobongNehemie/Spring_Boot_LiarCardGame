package com.nehms.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nehms.game.entites.Room;
import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
	
	public List<Room> findByRoomKey(String roomKey);
	
}
