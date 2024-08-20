package com.nehms.game.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptyCardsListException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String message;

	public EmptyCardsListException() {
		this.message = "la liste de carte est vide !!";
	}
}
