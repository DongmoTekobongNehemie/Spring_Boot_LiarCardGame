package com.nehms.game.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSession {

	private int maxPlayer = 3;
	private List<Player> players;
	private GameStep gameStep;
	private Player currentPlayer;
	private boolean isContestable;
	private List<Card> cards;
	private Player currentPlayerContest ;
	private List<Card> gameTable;
	private Pattern currentPattern;
	private String currentMessage;
	private WebSocketSession currentSession;
	private List<WebSocketSession> socketSessions;
	private int nbreOkfOk;
	private List<WebSocketSession> socketOfOk;
	private int currentindexOfsessionCard;
	private Card currentcard;
	private boolean okToPlay;
	private int nbreTour;
	private int indexplayers;
	private String message;

	public GameSession() {
		
		this.setGameStep(GameStep.CREATE_PLAYER);
		this.setCurrentPlayer(new Player(""));
		this.setContestable(false);
		this.setCards(new ArrayList<>());
		this.setCurrentPlayerContest(new Player(""));
		this.setGameTable(new ArrayList<>());
		this.setCurrentPattern(Pattern.COEUR);
		this.setCurrentMessage("");
		this.setPlayers(new ArrayList<>());
		this.setCurrentSession(null);
		this.setSocketSessions(new ArrayList<>());
		this.setSocketOfOk(new ArrayList<>());
		this.setNbreOkfOk(0);
		this.setCurrentindexOfsessionCard(0);
		this.setCurrentcard(new Card());
		this.setOkToPlay(false);
		this.nbreTour=0;
		this.indexplayers=0;
		this.setMessage("");
	}

}
