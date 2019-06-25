package com.marmelab.quoridor.model;

import java.util.Objects;

public class Pawn {

    private final Position position;

    private final CardinalDirection goal;

    public Pawn(int column, int row, CardinalDirection goal) {
        this(new Position(column, row), goal);
    }

    public Pawn(Position position, final CardinalDirection goal) {
        this.position = position;
        this.goal = goal;
    }

    public Position getPosition() {
        return position;
    }

    public CardinalDirection getGoal() {
        return goal;
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

    @Override
    public String toString() {
        return "Pawn{" +
                "position=" + position +
                '}';
    }

}
