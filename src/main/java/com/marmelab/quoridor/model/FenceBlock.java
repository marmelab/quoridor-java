package com.marmelab.quoridor.model;

public class FenceBlock {

    private final Position northwestTile;

    private final Position northeastTile;

    private final Position southwestTile;

    private final Position southeastTile;

    public FenceBlock(final Position northwestTile) {
        this.northwestTile = northwestTile;

        northeastTile = new Position(northwestTile);
        northeastTile.translateColumn(1);

        southwestTile = new Position(northwestTile);
        southwestTile.translateRow(1);

        southeastTile = new Position(northwestTile);
        southeastTile.translateColumn(1);
        southeastTile.translateRow(1);
    }

    public Position getNorthwestTile() {
        return northwestTile;
    }

    public Position getNortheastTile() {
        return northeastTile;
    }

    public Position getSouthwestTile() {
        return southwestTile;
    }

    public Position getSoutheastTile() {
        return southeastTile;
    }

}
