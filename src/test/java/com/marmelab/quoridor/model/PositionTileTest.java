package com.marmelab.quoridor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTileTest {

    @Test
    @DisplayName("create position tile should define the 4 positions of the cardinal directions")
    void newPositionTile() {
        // Given
        Position center = new Position(1, 0);
        // When
        PositionTile positionTile = new PositionTile(center);
        // Then
        assertThat(positionTile.getNorthPosition())
                .isEqualTo(new Position(1, -1));
        assertThat(positionTile.getEastPosition())
                .isEqualTo(new Position(2, 0));
        assertThat(positionTile.getSouthPosition())
                .isEqualTo(new Position(1, 1));
        assertThat(positionTile.getWestPosition())
                .isEqualTo(new Position(0, 0));
    }

}
