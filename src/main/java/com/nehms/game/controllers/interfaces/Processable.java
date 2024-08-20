package com.nehms.game.controllers.interfaces;

import com.nehms.game.entites.ProcessMessage;

public interface Processable {
	
	public ProcessMessage processingMessage(String message);
}
