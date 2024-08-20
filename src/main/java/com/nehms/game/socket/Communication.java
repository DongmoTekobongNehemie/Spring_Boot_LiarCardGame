package com.nehms.game.socket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.nehms.game.controllers.Broadcast;
import com.nehms.game.controllers.Jsonation;
import com.nehms.game.entites.Message;
import com.nehms.game.entites.Player;
import com.nehms.game.entites.Room;
import com.nehms.game.play.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Component
@Getter
@Controller
@Setter
public class Communication extends TextWebSocketHandler {

	private final Game game;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		game.play(session, message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		for (Room room : game.getSocketGestionner().getRoomsOftheSocket()) {

				if(room.getGameSession().getSocketSessions().contains(session)) {
					
					Jsonation jsonation = new Jsonation();

					Message message = new Message();

					Broadcast broadcast = new Broadcast();

					List<WebSocketSession> sessions = new ArrayList<>();

					List<Player> players = new ArrayList<>();

					int indexofTheLeavePlayer = 0;

					for (int i = 0; i < room.getGameSession().getSocketSessions().size(); i++) {

						if (room.getGameSession().getSocketSessions().get(i).equals(session)) {
							indexofTheLeavePlayer = i;
						}
					}

					message.setBody("Le joueur " + room.getGameSession().getPlayers().get(indexofTheLeavePlayer).getNamePlayer()
							+ " a quitter le jeu 😭😭");

					room.getGameSession().getSocketSessions().remove(session);

					room.getGameSession().getPlayers().remove(room.getGameSession().getPlayers().get(indexofTheLeavePlayer));

					sessions.addAll(room.getGameSession().getSocketSessions());

					players.addAll(room.getGameSession().getPlayers());

					room.getGameSession().reset();

					int i = 1;
					for (Player player : players) {
						player.setNamePlayer("Player " + i);
						
						i++;
					}
					
//					for (Player player : players) {
//						
//						System.out.println("la taille du paquet de carte de chacun "+player.getHand().size());
//					}
					
					room.getGameSession().setSocketSessions(sessions);

					room.getGameSession().setPlayers(players);

					message.setType("Reset");

					broadcast.broadcastMessage(jsonation.convertToJson(message), room.getGameSession().getSocketSessions());

					Message message2 = new Message();

					message2.setType("changement d'identifiant");

					for (int j = 0; j < room.getGameSession().getSocketSessions().size(); j++) {
						message2.setNamePlayer(players.get(j).getNamePlayer());
						message2.setBody("votre nouvelle identifiant est : " + players.get(j).getNamePlayer());
						room.getGameSession().getSocketSessions().get(j)
								.sendMessage(new TextMessage(jsonation.convertToJson(message2)));
					}

				}
			}

		}

}