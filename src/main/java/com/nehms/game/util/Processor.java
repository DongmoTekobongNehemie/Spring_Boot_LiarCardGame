package com.nehms.game.util;

import com.nehms.game.valueobjets.MessageProcessed;

public interface Processor {
	
	MessageProcessed processingMessage(String message);
}
