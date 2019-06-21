package com.marmelab.quoridor.game;

import com.marmelab.quoridor.model.Position;

import java.util.Objects;

public class Fence {

    private final Position northwestTile;

    private final boolean horizontal;

    public Fence(Position northwestTile, boolean horizontal) {
        this.northwestTile = northwestTile;
        this.horizontal = horizontal;
    }

    public Fence(int northwestTileColumn, int northwestRow, boolean horizontal) {
        this(new Position(northwestTileColumn, northwestRow), horizontal);
    }

    public Fence(final Fence fence) {
        this(new Position(fence.getNorthwestTile()), fence.isHorizontal());
    }

    public Position getNorthwestTile() {
        return northwestTile;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fence fence = (Fence) o;
        return horizontal == fence.horizontal &&
                Objects.equals(northwestTile, fence.northwestTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(northwestTile, horizontal);
    }

    @Override
    public String toString() {
        return "Fence{" +
                "northwestTile=" + northwestTile +
                ", horizontal=" + horizontal +
                '}';
    }

}

