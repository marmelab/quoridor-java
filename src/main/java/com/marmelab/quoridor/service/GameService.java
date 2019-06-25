package com.marmelab.quoridor.service;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.game.GameException;
import com.marmelab.quoridor.model.Board;
import com.marmelab.quoridor.model.Pawn;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    private Map<String, Pawn> players;

    private Game game;

    public Game getGame() {
        return game;
    }

    public Game createGame() {
        game = new Game(new Board());
        players = new HashMap<>();
        return game;
    }

    public boolean isGameReady() {
        return players.size() == game.getPawns().size();
    }

    public void joinGame(final String identifier) {
        if (isGameReady()) {
            throw new GameException("Game is already set");
        }
        players.put(identifier, game.getPawns().get(players.size()));
    }

    public Pawn isMyTurn(final String identifier) {
        Pawn playerTurn = game.getTurnPawn();
        Pawn pawn = players.get(identifier);
        if (playerTurn.equals(pawn) && isGameReady()) {
            return pawn;
        } else {
            return null;
        }
    }

}
