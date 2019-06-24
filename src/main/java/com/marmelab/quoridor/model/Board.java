package com.marmelab.quoridor.model;

import com.marmelab.quoridor.graph.GraphFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 9;

    private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    private final int boardSize;

    private List<Fence> fences;

    private Graph<Position, DefaultEdge> graph;

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
        graph = GraphFactory.buildGrid(boardSize);
        fences = new ArrayList<>();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Position> getSquares() {
        final Iterator<Position> iterator = new DepthFirstIterator<>(graph);
        final List<Position> squares = new ArrayList<>();
        iterator.forEachRemaining(squares::add);
        return squares;
    }

    public void addFence(final Fence fence, final Pawn pawn) {
        final FenceBlock fenceBlock = new FenceBlock(fence.getNorthwestTile());
        final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
        if (hasAlreadyAFenceAtTheSamePosition(fence.getNorthwestTile()) || hasNeighbourFence(fence.isHorizontal(), positionTile)) {
            LOGGER.info("Intersection: {}", fence);
        } else {
            addFence(fence, fenceBlock, pawn);
        }
    }

    private void addFence(final Fence fence, final FenceBlock fenceBlock, final Pawn pawn) {
        if (fence.isHorizontal()) {
            final Edge westEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getSouthwestTile());
            final Edge eastEdge = new Edge(fenceBlock.getNortheastTile(), fenceBlock.getSoutheastTile());
            addFence(fence, pawn, westEdge, eastEdge);
        } else {
            final Edge northEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getNortheastTile());
            final Edge southEdge = new Edge(fenceBlock.getSouthwestTile(), fenceBlock.getSoutheastTile());
            addFence(fence, pawn, northEdge, southEdge);
        }
    }

    private void addFence(final Fence fence, final Pawn pawn, final Edge edge1, final Edge edge2) {
        graph.removeEdge(edge1.getFirst(), edge1.getSecond());
        graph.removeEdge(edge2.getFirst(), edge2.getSecond());
        if (isCrossable(pawn)) {
            fences.add(new Fence(fence));
            LOGGER.info("Added: {}", fence);
        } else {
            graph.addEdge(edge1.getFirst(), edge1.getSecond());
            graph.addEdge(edge2.getFirst(), edge2.getSecond());
            LOGGER.info("No more access to goal line: {}", fence);
        }
    }

    private boolean hasAlreadyAFenceAtTheSamePosition(final Position position) {
        final Optional<Fence> already = fences.stream()
                .filter(
                        fence -> fence.getNorthwestTile().equals(position))
                .findFirst();
        return already.isPresent();
    }

    private boolean hasNeighbourFence(final boolean isHorizontal, final PositionTile positionTile) {
        final Predicate<Fence> neighbourPredicate;
        if (isHorizontal) {
            neighbourPredicate = fence -> fence.isHorizontal()
                    && (fence.getNorthwestTile().equals(positionTile.getEastPosition())
                    || fence.getNorthwestTile().equals(positionTile.getWestPosition())
            );
        } else {
            neighbourPredicate = fence -> !fence.isHorizontal()
                    && (fence.getNorthwestTile().equals(positionTile.getNorthPosition())
                    || fence.getNorthwestTile().equals(positionTile.getSouthPosition())
            );
        }
        final Optional<Fence> exist = fences.stream()
                .filter(neighbourPredicate)
                .findFirst();
        return exist.isPresent();
    }

    public List<Fence> getFences() {
        return fences;
    }

    private boolean isCrossable(final Pawn pawn) {
        final DijkstraShortestPath<Position, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
        boolean crossable = false;
        int column = boardSize - 1;
        int row = 0;
        while (!crossable && row < boardSize) {
            GraphPath<Position, DefaultEdge> path = shortestPath.getPath(pawn.getPosition(), new Position(column, row));
            crossable = path != null;
            row++;
        }
        return crossable;
    }

}
