package com.nehms.game.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessMessage {
	
	private String number;
	private Pattern pattern;
	private Pattern patternPlay;
	
}
