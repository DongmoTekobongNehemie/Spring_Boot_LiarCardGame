package com.nehms.game.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSessionNullException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final String message;

	public GameSessionNullException() {
		this.message = "L'objet GameSession est nul";
	}
	
}
