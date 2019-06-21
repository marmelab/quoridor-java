package com.marmelab.quoridor.web;

import com.marmelab.quoridor.model.Position;

public class Fence {

    private final Position position;

    private final boolean horizontal;

    public Fence(final Position position, final boolean horizontal) {
        this.position = position;
        this.horizontal = horizontal;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

}
