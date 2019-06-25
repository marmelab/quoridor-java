package com.marmelab.quoridor.web;

import com.marmelab.quoridor.model.*;
import com.marmelab.quoridor.game.Game;

import java.util.*;
import java.util.stream.Collectors;

public class BoardView {

    private final List<Pawn> pawns;

    private final List<Position> squares;

    private final List<Fence> horizontalFences;

    private final List<Fence> verticalFences;

    private final List<Fence> addHorizontalFences;

    private final List<Fence> addVerticalFences;

    private final Map<Position, CardinalDirection> possibleMoves;

    public BoardView(final Game game, final Pawn pawnTurn) {
        pawns = game.getPawns();
        squares = game.getSquares();

        final List<Fence> fences = game.getFences();
        verticalFences = fences.stream()
                .filter(fence -> !fence.isHorizontal())
                .collect(Collectors.toList());
        horizontalFences = fences.stream()
                .filter(Fence::isHorizontal)
                .collect(Collectors.toList());
        if (pawnTurn == null) {
            addHorizontalFences = new ArrayList<>();
            addVerticalFences = new ArrayList<>();
            possibleMoves = new HashMap<>();
        } else {
            AvailableNewFence availableNewFence = new AvailableNewFence(game.getBoard().getBoardSize(), horizontalFences, verticalFences);
            addHorizontalFences = availableNewFence.getAddHorizontalFences();
            addVerticalFences = availableNewFence.getAddVerticalFences();
            possibleMoves = buildPossibleMoves(pawnTurn, game.getBoard().getBoardSize());
            squares.removeAll(possibleMoves.keySet());
        }
    }

    private Map<Position, CardinalDirection> buildPossibleMoves(final Pawn pawn, final int boardSize) {
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

    public List<Pawn> getPawns() {
        return pawns;
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
