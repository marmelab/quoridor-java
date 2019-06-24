package com.marmelab.quoridor.model;

public class Edge {

    private final Position first;

    private final Position second;

    public Edge(Position first, Position second) {
        this.first = first;
        this.second = second;
    }

    public Position getFirst() {
        return first;
    }

    public Position getSecond() {
        return second;
    }

}
