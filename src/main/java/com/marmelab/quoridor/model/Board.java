package com.marmelab.quoridor.model;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 9;

    private final int boardSize;

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    public Board(int boardSize) {
        if (boardSize % 2 == 0) {
            throw new IllegalArgumentException("The size must be an odd number");
        } else if (boardSize < 3) {
            throw new IllegalArgumentException("The size must be at least 3");
        }
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

}
