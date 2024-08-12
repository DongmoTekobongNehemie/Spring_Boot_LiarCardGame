package com.nehms.game.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

	private String namePlayer;
	private String type; //alert, info
	private String body;
	private List<Card> cards;
	private Card currentCard;
	private Pattern currentPattern;
	
}
