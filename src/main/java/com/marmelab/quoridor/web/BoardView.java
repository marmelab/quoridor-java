package com.marmelab.quoridor.web;

import com.marmelab.quoridor.model.*;
import com.marmelab.quoridor.game.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardView {

    private final Pawn pawn;

    private final List<Position> squares;

    private final List<Fence> horizontalFences;

    private final List<Fence> verticalFences;

    private final List<Fence> addHorizontalFences;

    private final List<Fence> addVerticalFences;

    private final Map<Position, CardinalDirection> possibleMoves;

    public BoardView(final Game game) {
        pawn = game.getPawn();
        squares = game.getSquares();

        final List<Fence> fences = game.getFences();

        verticalFences = fences.stream()
                .filter(fence -> !fence.isHorizontal())
                .collect(Collectors.toList());
        horizontalFences = fences.stream()
                .filter(Fence::isHorizontal)
                .collect(Collectors.toList());
        AvailableNewFence availableNewFence = new AvailableNewFence(game.getBoard().getBoardSize(), horizontalFences, verticalFences);

        addHorizontalFences = availableNewFence.getAddHorizontalFences();
        addVerticalFences = availableNewFence.getAddVerticalFences();

        possibleMoves = buildPossibleMoves(game.getBoard().getBoardSize());
        squares.removeAll(possibleMoves.entrySet());
    }

    private Map<Position, CardinalDirection> buildPossibleMoves(final int boardSize) {
        Map<Position, CardinalDirection> possiblePositions = new HashMap<>();
        final Position position = pawn.getPosition();
        final PositionTile positionTile = new PositionTile(position);
        final Position northPosition = positionTile.getNorthPosition();
        if (isInsideBoard(northPosition, boardSize)) {
            possiblePositions.put(northPosition, CardinalDirection.NORTH);
        }
        final Position eastPosition = positionTile.getEastPosition();
        if (isInsideBoard(eastPosition, boardSize)) {
            possiblePositions.put(eastPosition, CardinalDirection.EAST);
        }
        final Position southPosition = positionTile.getSouthPosition();
        if (isInsideBoard(southPosition, boardSize)) {
            possiblePositions.put(southPosition, CardinalDirection.SOUTH);
        }
        final Position westPosition = positionTile.getWestPosition();
        if (isInsideBoard(westPosition, boardSize)) {
            possiblePositions.put(westPosition, CardinalDirection.WEST);
        }
        return possiblePositions;
    }

    private boolean isInsideBoard(final Position position, final int boardSize) {
        return isInsideBoardSize(position.getColumn(), boardSize) && isInsideBoardSize(position.getRow(), boardSize);
    }

    private boolean isInsideBoardSize(final int position, final int boardSize) {
        return position >= 0 && position < boardSize;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public List<Position> getSquares() {
        return squares;
    }

    public List<Fence> getHorizontalFences() {
        return horizontalFences;
    }

    public List<Fence> getVerticalFences() {
        return verticalFences;
    }

    public List<Fence> getAddHorizontalFences() {
        return addHorizontalFences;
    }

    public List<Fence> getAddVerticalFences() {
        return addVerticalFences;
    }

    public Map<Position, CardinalDirection> getPossibleMoves() {
        return possibleMoves;
    }

}
