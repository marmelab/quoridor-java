package com.marmelab.quoridor.model;

import com.marmelab.quoridor.graph.GraphFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 9;

    private final int boardSize;

    private Graph<Position, DefaultEdge> graph;

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
        graph = GraphFactory.buildGrid(boardSize);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Position> getNodes() {
        Iterator<Position> iterator = new DepthFirstIterator<>(graph);
        List<Position> squares = new ArrayList<>();
        iterator.forEachRemaining(squares::add);
        return squares;
    }

}
