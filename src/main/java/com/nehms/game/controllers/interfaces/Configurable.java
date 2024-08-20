package com.nehms.game.controllers.interfaces;

import java.util.List;

import com.nehms.game.entites.Card;
import com.nehms.game.entites.Player;

public interface Configurable {
	
    public void createCards(List<Card> cards);
    public void mixCards(List<Card> cards);
    public void distribute(List<Card> cards,  List<Player> players);
    public void removeCards(Card card, List<Card> cards);

}
