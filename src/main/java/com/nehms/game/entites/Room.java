package com.nehms.game.entites;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ROOM")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
	
	@Column(unique = true, nullable = false)
	private String roomKey;

	@PrePersist
	public void generateRoomKey() {
		String uniqueString = UUID.randomUUID().toString() + System.currentTimeMillis();
		try {
			this.roomKey = generateSHAKey(uniqueString);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	
	public static String generateSHAKey(String input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString().substring(0, 10);
	}

}
