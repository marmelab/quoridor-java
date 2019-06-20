package com.marmelab.quoridor.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 9;

    private final int boardSize;

    private List<Node> nodes;

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    public Board(int boardSize) {
        if (boardSize % 2 == 0) {
            throw new IllegalArgumentException("The size must be an odd number");
        } else if (boardSize < 0) {
            throw new IllegalArgumentException("The size must be positive");
        }
        this.boardSize = boardSize;
        nodes = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                nodes.add(new Node(new Position(i, j)));
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Node> getNodes() {
        return List.copyOf(nodes);
    }

}
