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

    private final List<Pawn> pawns;

    private final Board board;

    private final List<Fence> fences;

    private final Graph<Position, DefaultEdge> graph;

    private boolean over;

    private int playerTurn;

    public Game(final Board board) {
        this.board = board;
        int boardSize = this.board.getBoardSize();
        int lineCenter = (boardSize - 1) / 2;
        pawns = List.of(
                new Pawn(new Position(0, lineCenter), CardinalDirection.WEST),
                new Pawn(new Position(boardSize -1, lineCenter), CardinalDirection.EAST)
        );
        graph = GraphFactory.buildGrid(boardSize);
        fences = new ArrayList<>();
        over = false;
        playerTurn = 1;
    }

    public Board getBoard() {
        return board;
    }

    public List<Pawn> getPawns() {
        return pawns;
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
        if (over) {
            throw new GameException("Game is over, unable to add a fence");
        }
        final FenceBlock fenceBlock = new FenceBlock(fence.getNorthwestTile());
        final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
        if (hasAlreadyAFenceAtTheSamePosition(fence.getNorthwestTile()) || hasNeighbourFence(fence.isHorizontal(), positionTile)) {
            LOGGER.info("Intersection: {}", fence);
        } else {
            addFenceWithEdges(fence, fenceBlock);
        }
    }

    private void addFenceWithEdges(final Fence fence, final FenceBlock fenceBlock) {
        if (fence.isHorizontal()) {
            final Edge westEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getSouthwestTile());
            final Edge eastEdge = new Edge(fenceBlock.getNortheastTile(), fenceBlock.getSoutheastTile());
            addFenceIfCrossable(fence, westEdge, eastEdge);
        } else {
            final Edge northEdge = new Edge(fenceBlock.getNorthwestTile(), fenceBlock.getNortheastTile());
            final Edge southEdge = new Edge(fenceBlock.getSouthwestTile(), fenceBlock.getSoutheastTile());
            addFenceIfCrossable(fence, northEdge, southEdge);
        }
    }

    private void addFenceIfCrossable(final Fence fence, final Edge edge1, final Edge edge2) {
        graph.removeEdge(edge1.getFirst(), edge1.getSecond());
        graph.removeEdge(edge2.getFirst(), edge2.getSecond());
        if (isCrossableForAllPawns()) {
            fences.add(new Fence(fence));
            LOGGER.info("Added: {}", fence);
            nextPlayer();
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

    private boolean isCrossableForAllPawns() {
        boolean crossable = true;
        final Iterator<Pawn> pawnIterator = pawns.iterator();
        while (crossable && pawnIterator.hasNext()) {
            Pawn next = pawnIterator.next();
            crossable = isCrossable(next);
        }
        return crossable;
    }

    private boolean isCrossable(final Pawn pawn) {
        final DijkstraShortestPath<Position, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
        boolean crossable = false;
        int column = pawn.getGoal() == CardinalDirection.WEST ? board.getBoardSize() - 1 : 0;
        int row = 0;
        while (!crossable && row < board.getBoardSize()) {
            GraphPath<Position, DefaultEdge> path = shortestPath.getPath(pawn.getPosition(), new Position(column, row));
            crossable = path != null;
            row++;
        }
        return crossable;
    }

    public void movePawn(final CardinalDirection direction) {
        if (over) {
            throw new GameException("Game is over, unable to move a pawn");
        }
        Pawn pawn = getTurnPawn();
        switch (direction) {
            case NORTH:
                move(0, -1);
                break;
            case EAST:
                move(1, 0);
                break;
            case SOUTH:
                move(0, 1);
                break;
            case WEST:
                move(-1, 0);
                break;
        }
        over = isAVictory(pawn);
    }

    private boolean canMove(final int position, final int row) {
        int newRow = position + row;
        return newRow >= 0 && newRow < board.getBoardSize();
    }

    private void move(final int column, final int row) {
        final Position position = getTurnPawn().getPosition();
        if (canMove(position.getColumn(), column) && canMove(position.getRow(), row)) {
            position.translateColumn(column);
            position.translateRow(row);
            nextPlayer();
        }
    }

    private boolean isAVictory(final Pawn pawn) {
        boolean victory;
        Position position = pawn.getPosition();
        if (pawn.getGoal() == CardinalDirection.EAST) {
            victory = position.getColumn() == 0;
        } else if (pawn.getGoal() == CardinalDirection.WEST) {
            victory = position.getColumn() == board.getBoardSize() -1;
        } else {
            throw new IllegalArgumentException("Goal direction not supported");
        }
        return victory;
    }

    public boolean isOver() {
        return over;
    }

    private void nextPlayer() {
        if (playerTurn + 1 > pawns.size()) {
            playerTurn = 1;
        } else {
            playerTurn++;
        }
    }

    public Pawn getTurnPawn() {
        return pawns.get(playerTurn -1);
    }

    public int getTurn() {
        return playerTurn;
    }

}
