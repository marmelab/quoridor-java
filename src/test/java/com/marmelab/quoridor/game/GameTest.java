package com.marmelab.quoridor.game;

import com.marmelab.quoridor.model.Board;
import com.marmelab.quoridor.model.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    @DisplayName("Create default game should place pawn in the center of his base line")
    public void newGamePlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(0, 4);
        Board board = new Board();
        //When
        Game game = new Game(board);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

}
