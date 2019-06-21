package com.marmelab.quoridor.game;

import com.marmelab.quoridor.model.Position;

public class Fence {

    private Position northWestCornerTile;

    private boolean vertical;

    public Position getNorthWestCornerTile() {
        return northWestCornerTile;
    }

    public void setNorthWestCornerTile(Position northWestCornerTile) {
        this.northWestCornerTile = northWestCornerTile;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

}
