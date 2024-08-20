package com.nehms.game.valueobjets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageProcessed {
	
	private String number;
	private Pattern pattern;
	private Pattern patternPlay;
	
}
