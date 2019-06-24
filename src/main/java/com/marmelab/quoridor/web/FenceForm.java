package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.model.Position;

public class FenceForm {

    private int row;

    private int column;

    private boolean horizontal;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public Fence toFence() {
        return new Fence(new Position(column, row), horizontal);
    }

}
