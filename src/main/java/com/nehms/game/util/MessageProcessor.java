package com.nehms.game.util;

import com.nehms.game.valueobjets.Pattern;
import com.nehms.game.valueobjets.MessageProcessed;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements Processor {

    @Override
    public MessageProcessed processingMessage(String message) {

        MessageProcessed messageProcessed = new MessageProcessed();

        String[] response;

        if (message.contains("-")) {
            response = message.trim().split("-");
            if (response.length == 3) {
                try {
                    messageProcessed.setNumber((response[0]).trim());

                    messageProcessed.setPattern(Pattern.valueOf(response[1].toUpperCase().trim()));

                    messageProcessed.setPatternPlay(Pattern.valueOf(response[2].toUpperCase().trim()));
                    return messageProcessed;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
}
