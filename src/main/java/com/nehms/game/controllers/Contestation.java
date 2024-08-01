package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.nehms.game.entities.Card;
import com.nehms.game.entities.GameSession;
import com.nehms.game.entities.GameStep;
import com.nehms.game.entities.Pattern;
import com.nehms.game.entities.Player;

public class Contestation {

    public void contestation(GameSession gameSession) throws IOException {
        if (gameSession.getGameStep().equals(GameStep.CONTESTAITON)) {
            startCountdown(gameSession);

            if (gameSession.getCurrentMessage().isEmpty()) {
                return;
            }

            if ("moi".equals(gameSession.getCurrentMessage())) {
                if (verifyTheContestPlayer(gameSession)) {

                    gameSession.setCurrentMessage("");

                    contradict(gameSession.getPlayers().get(getIndex(gameSession)),
                            gameSession.getPlayers().get(gameSession.getIndexplayers()), gameSession.getCurrentcard(),
                            gameSession.getCurrentPattern(), gameSession.getGameTable(),
                            gameSession.getSocketSessions());

                    gameSession.setGameStep(GameStep.PLAY_CARD);
                    return;
                } else {
                    gameSession.getCurrentSession()
                            .sendMessage(new TextMessage("Vous ne pouvez pas vous contester !!✖️✖️"));
                }
            } else {
                gameSession.getCurrentSession()
                        .sendMessage(new TextMessage("Pour contester dites <<moi>> !! 🚨🚨"));
            }
        }
    }

    private void startCountdown(GameSession gameSession) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            boolean shouldProceed;
            synchronized (gameSession) {
                shouldProceed = gameSession.getGameStep().equals(GameStep.CONTESTAITON) 
                                && gameSession.getCurrentMessage().isEmpty();
            }

            if (shouldProceed) {
                try {
                    for (WebSocketSession session : gameSession.getSocketSessions()) {
                        session.sendMessage(new TextMessage("Personne n'a contesté 😒😒😒"));
                    }
                    synchronized (gameSession) {
                        gameSession.setGameStep(GameStep.PLAY_CARD);
                    }
                } catch (IOException e) {
                    // Log the exception appropriately
                } finally {
                    shutdownScheduler(scheduler);
                }
            } else {
                shutdownScheduler(scheduler);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private void shutdownScheduler(ScheduledExecutorService scheduler) {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            scheduler.shutdownNow();
        }
    }

    public int getIndex(GameSession gameSession) {
        for (int j = 0; j < gameSession.getSocketSessions().size(); j++) {
            if (gameSession.getSocketSessions().get(j).equals(gameSession.getCurrentSession())) {
                return j;
            }
        }
        return 0;
    }

    public boolean verifyTheContestPlayer(GameSession gameSession) {
        return (gameSession.getCurrentindexOfsessionCard() - 1) != getIndex(gameSession);
    }

    public void contradict(Player contestPlayer, Player player, Card currentCard, Pattern pattern,
                           List<Card> cardsPlayed, List<WebSocketSession> list) {

        BrosdCast brosdCast = new BrosdCast();

        if (currentCard.getPattern().equals(pattern)) {
            contestPlayer.getHand().addAll(cardsPlayed);
            cardsPlayed.clear();
            brosdCast.broadcastMessage("Le joueur " + contestPlayer.getNamePlayer() + " prend les cartes 😂😂😂", list);
        } else {
            player.getHand().addAll(cardsPlayed);
            cardsPlayed.clear();
            brosdCast.broadcastMessage("Le joueur " + player.getNamePlayer() + " prend les cartes 😂😂😂", list);
        }
    }
}
