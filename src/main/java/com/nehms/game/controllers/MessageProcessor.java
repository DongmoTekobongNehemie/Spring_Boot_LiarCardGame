package com.nehms.game.controllers;

import com.nehms.game.controllers.interfaces.Processable;
import com.nehms.game.entites.Pattern;
import com.nehms.game.entites.ProcessMessage;

public class MessageProcessor implements Processable{

	@Override
	public ProcessMessage processingMessage(String message) {

		ProcessMessage processMessage = new ProcessMessage();

		String[] reponse;

		if (message.contains("-")) {
			reponse = message.trim().split("-");
			if (reponse.length == 3) {
				try {
					processMessage.setNumber((reponse[0]).trim());

					processMessage.setPattern(Pattern.valueOf(reponse[1].toUpperCase().trim()));
					
					processMessage.setPatternPlay(Pattern.valueOf(reponse[2].toUpperCase().trim()));
					return processMessage;
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
}
