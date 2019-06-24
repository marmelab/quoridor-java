package com.marmelab.quoridor.graph;

import com.marmelab.quoridor.model.Position;

import java.util.function.Supplier;

public class PositionSupplier implements Supplier<Position> {

    private final int boardSize;

    private int column = -1;

    private int row = 0;

    public PositionSupplier(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public Position get() {
        column++;
        if (column == boardSize) {
            column = 0;
            row++;
        }
        return new Position(column, row);
    }

}
