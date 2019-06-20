package com.marmelab.quoridor.model;

import java.util.Objects;

public class Pawn {

    private final Position position;

    public Pawn(int x, int y) {
        this(new Position(x, y));
    }

    public Pawn(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return Objects.equals(position, pawn.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
