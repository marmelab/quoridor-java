package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.model.Pawn;
import com.marmelab.quoridor.model.Position;

import java.util.List;
import java.util.stream.Collectors;

public class BoardView {

    private final Pawn pawn;

    private final List<Position> squares;

    private final List<Fence> horizontalFences;

    private final List<Fence> verticalFences;

    private final List<Fence> addHorizontalFences;

    private final List<Fence> addVerticalFences;

    public BoardView(final Game game) {
        pawn = game.getPawn();
        squares = game.getBoard().getSquares();

        final List<Fence> fences = game.getBoard().getFences();

        verticalFences = fences.stream()
                .filter(fence -> !fence.isHorizontal())
                .collect(Collectors.toList());
        horizontalFences = fences.stream()
                .filter(Fence::isHorizontal)
                .collect(Collectors.toList());
        AvailableNewFence availableNewFence = new AvailableNewFence(game.getBoard().getBoardSize(), horizontalFences, verticalFences);

        addHorizontalFences = availableNewFence.getAddHorizontalFences();
        addVerticalFences = availableNewFence.getAddVerticalFences();
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

}
