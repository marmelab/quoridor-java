package com.marmelab.quoridor.game;

import com.marmelab.quoridor.graph.GraphFactory;
import com.marmelab.quoridor.model.*;
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

public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final Pawn pawn;

    private final Board board;

    private final List<Fence> fences;

    private final Graph<Position, DefaultEdge> graph;

    public Game(final Board board) {
        this.board = board;
        int boardSize = this.board.getBoardSize();
        pawn = new Pawn(new Position(0, (boardSize - 1 )/ 2));
        graph = GraphFactory.buildGrid(boardSize);
        fences = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public List<Fence> getFences() {
        return fences;
    }

    public List<Position> getSquares() {
        final Iterator<Position> iterator = new DepthFirstIterator<>(graph);
        final List<Position> squares = new ArrayList<>();
        iterator.forEachRemaining(squares::add);
        return squares;
    }

    public void addFence(final Fence fence) {
        addFence(fence, pawn);
    }

    private void addFence(final Fence fence, final Pawn pawn) {
        final FenceBlock fenceBlock = new FenceBlock(fence.getNorthwestTile());
        final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
        if (hasAlreadyAFenceAtTheSamePosition(fence.getNorthwestTile()) || hasNeighbourFence(fence.isHorizontal(), positionTile)) {
            LOGGER.info("Intersection: {}", fence);
        } else {
            addFenceWithEdges(fence, fenceBlock, pawn);
        }
    }

    private void addFenceWithEdges(final Fence fence, final FenceBlock fenceBlock, final Pawn pawn) {
        if (fence.isHorizontal()) {
            final Edge westEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getSouthwestTile());
            final Edge eastEdge = new Edge(fenceBlock.getNortheastTile(), fenceBlock.getSoutheastTile());
            addFenceIfCrossable(fence, pawn, westEdge, eastEdge);
        } else {
            final Edge northEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getNortheastTile());
            final Edge southEdge = new Edge(fenceBlock.getSouthwestTile(), fenceBlock.getSoutheastTile());
            addFenceIfCrossable(fence, pawn, northEdge, southEdge);
        }
    }

    private void addFenceIfCrossable(final Fence fence, final Pawn pawn, final Edge edge1, final Edge edge2) {
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

    private boolean isCrossable(final Pawn pawn) {
        final DijkstraShortestPath<Position, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
        boolean crossable = false;
        int column = board.getBoardSize() - 1;
        int row = 0;
        while (!crossable && row < board.getBoardSize()) {
            GraphPath<Position, DefaultEdge> path = shortestPath.getPath(pawn.getPosition(), new Position(column, row));
            crossable = path != null;
            row++;
        }
        return crossable;
    }

    public void movePawn(final CardinalDirection direction) {
         switch (direction) {
            case NORTH:
                moveNorth();
                break;
            case EAST:
               moveEast();
                break;
            case SOUTH:
                moveSouth();
                break;
            case WEST:
                moveWest();
                break;
        }
    }

    private boolean canMove(final int position, final int row) {
        int newRow = position + row;
        return newRow >= 0 && newRow < board.getBoardSize();
    }

    private void moveNorth() {
        final Position position = pawn.getPosition();
        if (canMove(position.getRow(), -1)) {
            position.translateRow(-1);
        }
    }

    private void moveEast() {
        final Position position = pawn.getPosition();
        if (canMove(position.getColumn(), 1)) {
            position.translateColumn(1);
        }
    }

    private void moveSouth() {
        final Position position = pawn.getPosition();
        if (canMove(position.getRow(), 1)) {
            position.translateRow(1);
        }
    }

    private void moveWest() {
        final Position position = pawn.getPosition();
        if (canMove(position.getColumn(), -1)) {
            position.translateColumn(-1);
        }
    }

}
