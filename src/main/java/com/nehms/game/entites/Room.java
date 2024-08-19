package com.nehms.game.entites;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Room {

	private GameSession gameSession;


	private String roomKey;

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

	public Room() {
		String uniqueString = UUID.randomUUID().toString() + System.currentTimeMillis();

		try {
			this.setRoomKey(generateSHAKey(uniqueString));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		this.setGameSession(new GameSession());
	}

}
