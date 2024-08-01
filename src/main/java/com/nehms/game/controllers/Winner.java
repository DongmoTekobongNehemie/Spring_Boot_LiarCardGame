package com.nehms.game.controllers;

import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
public class Winner {

    private final BrosdCast brosdCast = new BrosdCast();

    public void checkWinner(GameSession gameSession) {

        for (int i = 0; i < gameSession.getPlayers().size(); i++) {
            if (gameSession.getPlayers().get(i).getHand().isEmpty()) {
                String winnerMessage = "Le gagnant du jeu est " + gameSession.getPlayers().get(i).getNamePlayer() + " 🎉🎉🎉🎉🎉";
                brosdCast.broadcastMessage(winnerMessage, gameSession.getSocketSessions());
                gameSession.setGameStep(GameStep.END);
                return;
            }
        }
    }
}
