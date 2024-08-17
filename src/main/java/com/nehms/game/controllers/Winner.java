package com.nehms.game.controllers;

import com.nehms.game.entites.GameSession;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Message;
public class Winner {

    private final BrosdCast brosdCast = new BrosdCast();

    public void checkWinner(GameSession gameSession) {
    	
    	Message message = new Message();
    	Jsonation jsonation = new Jsonation();

        for (int i = 0; i < gameSession.getPlayers().size(); i++) {

			
			message.setType("CARD");
			
            if (gameSession.getPlayers().get(i).getHand().isEmpty()) {
                String winnerMessage = "Le gagnant du jeu est " + gameSession.getPlayers().get(i).getNamePlayer() + " 🎉🎉🎉🎉🎉";
                message.setBody(winnerMessage);
                message.setType("gagnant");
                brosdCast.broadcastMessage(jsonation.convertToJson(message), gameSession.getSocketSessions());
                gameSession.setGameStep(GameStep.END);
                
                return;
            }
        }
    }
}
