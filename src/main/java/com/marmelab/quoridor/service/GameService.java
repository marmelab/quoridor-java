package com.marmelab.quoridor.service;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.model.Board;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Game game;

    public Game getGame() {
        return game;
    }

    public Game createGame() {
        game = new Game(new Board());
        return game;
    }

}
