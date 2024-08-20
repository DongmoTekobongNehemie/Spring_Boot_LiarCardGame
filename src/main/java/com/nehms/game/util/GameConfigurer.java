package com.nehms.game.util;

import com.nehms.game.entites.Card;
import com.nehms.game.entites.Player;
import com.nehms.game.valueobjets.Pattern;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GameConfigurer implements Configurable {
	
	@Override
    public void createCards(List<Card> cards) {

        final String[] numberCards = { "A", "7", "8", "9", "10", "J", "Q", "K" };

        for (String numberCard : numberCards) {
            cards.add(new Card(Pattern.CARREAU, numberCard));
            cards.add(new Card(Pattern.TREFLE, numberCard));
            cards.add(new Card(Pattern.COEUR, numberCard));
            cards.add(new Card(Pattern.PIQUE, numberCard));
        }
    }
	
	@Override
    public void mixCards(List<Card> cards) {
        Collections.shuffle(cards);
    }

	@Override
    public void removeCards(Card card, List<Card> cards) {
        cards.removeIf(car -> car.getPattern().equals(card.getPattern()) && car.getNumber().equals(card.getNumber()));
    }

	@Override
    public void distribute(List<Card> cards,  List<Player> players) {
        int nbrePartMain = cards.size() / players.size();
        for (Player  joueur : players) {
            for (int i = 0; i < nbrePartMain; i++) {
                int lastIndex = cards.size() - 1;
                joueur.getHand().add(cards.get(lastIndex));
                removeCards(joueur.getHand().get(i), cards);
            }
        }
    }
}
