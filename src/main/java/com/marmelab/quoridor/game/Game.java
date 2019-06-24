package com.marmelab.quoridor.game;

import com.marmelab.quoridor.model.Board;
import com.marmelab.quoridor.model.Fence;
import com.marmelab.quoridor.model.Pawn;
import com.marmelab.quoridor.model.Position;

public class Game {

    private Pawn pawn;

    private Board board;

    public Game(final Board board) {
        this.board = board;
        pawn = new Pawn(new Position(0, (this.board.getBoardSize() - 1 )/ 2));
    }

    public Board getBoard() {
        return board;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void addFence(final Fence fence) {
        board.addFence(fence, pawn);
    }

}
