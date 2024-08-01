package com.nehms.game.controllers;

import com.nehms.game.entities.Pattern;
import com.nehms.game.entities.ProcessMessage;

public class MessageProcessor {

	public ProcessMessage processingMessage(String message) {

		ProcessMessage processMessage = new ProcessMessage();

		String[] reponse;

		if (message.contains("-")) {
			reponse = message.trim().split("-");
			if (reponse.length == 2) {
				try {
					processMessage.setNumber(Integer.parseInt(reponse[0]));

					processMessage.setPattern(Pattern.valueOf(reponse[1].toUpperCase()));
					return processMessage;
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
}
