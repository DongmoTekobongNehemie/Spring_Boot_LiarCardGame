package com.nehms.game.socket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.nehms.game.controllers.BrosdCast;
import com.nehms.game.controllers.Jsonation;
import com.nehms.game.entities.Message;
import com.nehms.game.entities.Player;
import com.nehms.game.play.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Component
@Getter
@Setter
public class Communication extends TextWebSocketHandler {

	private final Game game;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		game.play(session, null);

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		game.play(session, message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		if (game.getGameSession().getSocketSessions().contains(session)) {

			Jsonation jsonation = new Jsonation();

			Message message = new Message();

			BrosdCast brosdCast = new BrosdCast();

			List<WebSocketSession> sessions = new ArrayList<>();

			List<Player> players = new ArrayList<>();

			int indexofTheLeavePlayer = 0;

			for (int i = 0; i < game.getGameSession().getSocketSessions().size(); i++) {

				if (game.getGameSession().getSocketSessions().get(i).equals(session)) {
					indexofTheLeavePlayer = i;
				}
			}

			message.setBody("Le joueur " + game.getGameSession().getPlayers().get(indexofTheLeavePlayer).getNamePlayer()
					+ " a quitter le jeu 😭😭");

			game.getGameSession().getSocketSessions().remove(session);

			game.getGameSession().getPlayers().remove(game.getGameSession().getPlayers().get(indexofTheLeavePlayer));

			sessions.addAll(game.getGameSession().getSocketSessions());

			players.addAll(game.getGameSession().getPlayers());

			game.getGameSession().reset();
			
			int i=1;
			for (Player player : players) {
				player.setNamePlayer("Player "+i);
				i++;
			}
			
			game.getGameSession().setSocketSessions(sessions);

			game.getGameSession().setPlayers(players);

			message.setType("Reset");

			brosdCast.broadcastMessage(jsonation.convertToJson(message), game.getGameSession().getSocketSessions());

			Message message2 = new Message();
			
			message2.setType("changement d'identifiant");
			
			for(int j=0; j<game.getGameSession().getSocketSessions().size(); j++) {
				message2.setNamePlayer(players.get(j).getNamePlayer());
				message2.setBody("votre nouvelle identifiant est : "+players.get(j).getNamePlayer());
				game.getGameSession().getSocketSessions().get(j).sendMessage(new TextMessage(jsonation.convertToJson(message2)));
			}
			
		}
	}
}