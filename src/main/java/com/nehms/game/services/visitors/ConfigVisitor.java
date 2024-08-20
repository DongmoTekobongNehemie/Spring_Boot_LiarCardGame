package com.nehms.game.services.visitors;

import com.nehms.game.entites.GameSession;

public interface ConfigVisitor {

    void createCards(GameSession session);

    void mixCards(GameSession session);

    void distribute(GameSession session);
}
