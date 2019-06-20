package com.marmelab.quoridor.model;

public class Node {

    private final Position position;

    public Node(final Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

}
