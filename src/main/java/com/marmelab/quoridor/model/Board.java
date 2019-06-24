package com.marmelab.quoridor.model;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.graph.GraphFactory;
import org.jgrapht.Graph;
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
        } else if (boardSize < 0) {
            throw new IllegalArgumentException("The size must be positive");
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

    public void addFence(final Fence fence) {
        final FenceBlock fenceBlock = new FenceBlock(fence.getNorthwestTile());
        final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
        if (hasAlreadyAFenceAtTheSamePosition(fence.getNorthwestTile()) || hasNeighbourFence(fence.isHorizontal(), positionTile)) {
            LOGGER.info("Intersection: {}", fence);
        } else {
            addFence(fence, fenceBlock);
        }
    }

    private void addFence(final Fence fence, final FenceBlock fenceBlock) {
        fences.add(new Fence(fence));
        if (fence.isHorizontal()) {
            graph.removeEdge(fenceBlock.getNorthwestTile(), fenceBlock.getSouthwestTile());
            graph.removeEdge(fenceBlock.getNortheastTile(), fenceBlock.getSoutheastTile());
        } else {
            graph.removeEdge(fenceBlock.getNorthwestTile(), fenceBlock.getNortheastTile());
            graph.removeEdge(fenceBlock.getSouthwestTile(), fenceBlock.getSoutheastTile());
        }
        LOGGER.info("Added : {}", fence);
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

}
