package com.marmelab.quoridor.model;

public class PositionTile {

    private final Position northPosition;

    private final Position eastPosition;

    private final Position southPosition;

    private final Position westPosition;

    public PositionTile(final Position center) {
        northPosition = new Position(center);
        northPosition.translateRow(-1);
        eastPosition = new Position(center);
        eastPosition.translateColumn(1);
        southPosition = new Position(center);
        southPosition.translateRow(1);
        westPosition = new Position(center);
        westPosition.translateColumn(-1);
    }

    public Position getNorthPosition() {
        return northPosition;
    }

    public Position getEastPosition() {
        return eastPosition;
    }

    public Position getSouthPosition() {
        return southPosition;
    }

    public Position getWestPosition() {
        return westPosition;
    }

}
