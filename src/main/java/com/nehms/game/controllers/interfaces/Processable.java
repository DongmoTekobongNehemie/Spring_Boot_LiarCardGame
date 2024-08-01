package com.nehms.game.controllers.interfaces;

import com.nehms.game.entities.ProcessMessage;

public interface Processable {
	
	public ProcessMessage processingMessage(String message);
}
