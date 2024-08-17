package com.nehms.game.controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nehms.game.entites.Card;
import com.nehms.game.entites.GameSession;
import com.nehms.game.entites.GameStep;
import com.nehms.game.entites.Message;
import com.nehms.game.entites.Pattern;
import com.nehms.game.entites.Player;

public class Contestation {

    public void contestation(GameSession gameSession) throws IOException {
    	
    	Message message = new Message();
    	
    	Jsonation jsonation = new Jsonation();
    	
    	
        if (gameSession.getGameStep().equals(GameStep.CONTESTAITON)) {
            startCountdown(gameSession);

            if (gameSession.getCurrentMessage().isEmpty()) {
                return;
            }

            if ("moi".equals(gameSession.getCurrentMessage())) {
                if (verifyTheContestPlayer(gameSession)) {

                    gameSession.setCurrentMessage("");
                    
                    contestPlayer(gameSession.getPlayers().get(getIndex(gameSession)),
                            gameSession.getPlayers().get(gameSession.getIndexplayers()), gameSession.getCurrentcard(),
                            gameSession.getCurrentPattern(), gameSession.getGameTable(),
                            gameSession.getSocketSessions());

                    gameSession.setGameStep(GameStep.PLAY_CARD);
                    

					message.setBody("Votre main ♠️");
					message.setType("CARD");
					
					for (int i=0; i<gameSession.getSocketSessions().size(); i++) {
						message.setCards(gameSession.getPlayers().get(i).getHand());
						gameSession.getSocketSessions().get(i).sendMessage( new TextMessage(jsonation.convertToJson(message)));
					}
					
//                    return;g
                } else {
                	
                	message.setBody("ne pouvez pas contester");
                	message.setBody("Vous ne pouvez pas vous contester !!✖️✖️");
                	
                    gameSession.getCurrentSession()
                            .sendMessage(new TextMessage(jsonation.convertToJson(message)));
                }
            } else {
                gameSession.getCurrentSession()
                        .sendMessage(new TextMessage("Pour contester dites <<moi>> !! 🚨🚨"));
            }
        }
    }

    private void startCountdown(GameSession gameSession) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Message message = new Message();
        Jsonation jsonation = new Jsonation();
        

        scheduler.schedule(() -> {
            boolean shouldProceed;
            synchronized (gameSession) {
                shouldProceed = gameSession.getGameStep().equals(GameStep.CONTESTAITON) 
                                && gameSession.getCurrentMessage().isEmpty();
            }

            if (shouldProceed) {
                try {
                	
                	
                	message.setBody("perosnne ne conteste");
                	message.setBody("Personne n'a contesté 😒😒😒");
                	
                    
                    for (WebSocketSession session : gameSession.getSocketSessions()) {
                        session.sendMessage(new TextMessage(jsonation.convertToJson(message)));
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

    public void contestPlayer(Player contestPlayer, Player player, Card currentCard, Pattern pattern,
                           List<Card> cardsPlayed, List<WebSocketSession> list) {

        BrosdCast brosdCast = new BrosdCast(); 
        
        Message message = new Message();
        
        Jsonation jsonation= new Jsonation();

        if (currentCard.getPattern().equals(pattern)) {
            contestPlayer.getHand().addAll(cardsPlayed);
            cardsPlayed.clear();
            
            message.setBody("Le joueur " + contestPlayer.getNamePlayer() + " prend les cartes 😂😂😂");
            message.setType("prend les cartes");
            
            brosdCast.broadcastMessage(jsonation.convertToJson(message) , list);
        } else {
            player.getHand().addAll(cardsPlayed);
            cardsPlayed.clear();
            
            message.setBody("Le joueur " + player.getNamePlayer() + " prend les cartes 😂😂😂");
            message.setType("prend les cartes");
            
            brosdCast.broadcastMessage(jsonation.convertToJson(message) , list);
        }
    }
}
