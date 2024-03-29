package com.marmelab.quoridor.model;

import java.util.Objects;

public class Position {

    private int column;

    private int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position(final Position position) {
        this.column = position.getColumn();
        this.row = position.getRow();
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void translateColumn(int deltaColumn) {
        this.column += deltaColumn;
    }

    public void translateRow(int deltaRow) {
        this.row += deltaRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return column == position.column &&
                row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Position{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }

}
