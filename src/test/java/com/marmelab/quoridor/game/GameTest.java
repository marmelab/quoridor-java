package com.marmelab.quoridor.game;

import com.marmelab.quoridor.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GameTest {

    @Test
    @DisplayName("Create default game should place the pawn in the center of its base line")
    public void newGamePlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(0, 4);
        Board board = new Board();
        //When
        Game game = new Game(board);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("by default, no fences are on the board")
    void constructorFences() {
        // Given
        Board board = new Board(3);
        Game game = new Game(board);
        // When
        List<Fence> fences = game.getFences();
        // Then
        assertThat(fences).isEmpty();
    }

    @Test
    @DisplayName("add the fence should add the fence at the right place")
    void addFenceHappyPath() {
        // Given
        Board board = new Board(3);
        Game game = new Game(board);
        Fence expected = new Fence(0, 0, false);
        // When
        game.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("it is not possible to add another fence at the same place")
    void addFenceNotAthTheSamePlace() {
        // Given
        Board board = new Board(3);
        Game game = new Game(board);
        Fence expected = new Fence(0, 0, false);
        game.addFence(new Fence(0, 0, false));
        // When
        game.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("get fences should return the right number of vertical fences")
    void getFencesReturnsTheRightNumberOfVerticalFences() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, false));
        game.addFence(new Fence(0, 2, false));
        // When
        List<Fence> fences = game.getFences();
        // Then
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(new Position(0, 0), false),
                new Fence(new Position(0, 2), false));
    }

    @Test
    @DisplayName("get fences should return the right number of horizontal fences")
    void getFencesReturnsTheRightNumberOfHorizontalFences() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, true));
        game.addFence(new Fence(2, 0, true));
        // When
        List<Fence> fences = game.getFences();
        // Then
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true),
                new Fence(2, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence one square after a fence")
    void addFenceNotPossibleHorizontalFenceNextSquare() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, true));
        game.addFence(new Fence(1, 0, true));
        // When
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence one square after a fence")
    void addFenceNotPossibleVerticalFenceNextFence() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, false));
        game.addFence(new Fence(0, 1, false));
        // When
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence one horizontal fence")
    void addFenceNotPossibleVerticalFenceOnHorizontalFence() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, true));
        // When
        game.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence one vertical fence")
    void addFenceNotPossibleHorizontalFenceOnVerticalFence() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, false));
        // When
        game.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence before a vertical fence")
    void addFenceNotPossibleVerticalFenceBeforeVerticalFence() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 1, false));
        // When
        game.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 1, false));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence before a horizontal fence")
    void addFenceNotPossibleHorizontalFenceBeforeHorizontalFence() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(1, 0, true));
        // When
        game.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(1, 0, true));
    }

    @Test
    @DisplayName("It should be possible to add a vertical fence between two horizontal fences")
    void addFenceBetweenTwoHorizontalFences() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, true));
        game.addFence(new Fence(2, 0, true));
        // When
        game.addFence(new Fence(1, 0, false));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true),
                new Fence(2, 0, true),
                new Fence(1, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence between two vertical fences")
    void addFenceBetweenTwoVerticalFences() {
        // Given
        Board board = new Board(5);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, false));
        game.addFence(new Fence(0, 2, false));
        // When
        game.addFence(new Fence(0, 1, true));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false),
                new Fence(0, 2, false),
                new Fence(0, 1, true));
    }

    @Test
    @DisplayName("It should not be possible to add a fence which closes the access to the goal line")
    void addFenceNotCrossable() {
        // Given
        Board board = new Board(3);
        Game game = new Game(board);
        game.addFence(new Fence(0, 0, false));
        // When
        game.addFence(new Fence(0, 1, true));
        // Then
        List<Fence> fences = game.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("move east pawn should place the pawn in the column on the right")
    public void moveEastPawnPlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(1, 4);
        Board board = new Board();
        Game game = new Game(board);
        //When
        game.movePawn(CardinalDirection.EAST);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move north pawn should place the pawn in the row on the top")
    public void moveNorthPawnPlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(0, 3);
        Board board = new Board();
        Game game = new Game(board);
        //When
        game.movePawn(CardinalDirection.NORTH);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move south pawn should place the pawn down in the row on the bottom")
    public void moveSouthPawnPlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(0, 5);
        Board board = new Board();
        Game game = new Game(board);
        //When
        game.movePawn(CardinalDirection.SOUTH);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move south pawn should place the pawn down in the row on the bottom")
    public void moveWestPawnPlacesWellPawn() {
        //Given
        Pawn expected = new Pawn(0, 4);
        Board board = new Board();
        Game game = new Game(board);
        game.movePawn(CardinalDirection.EAST);
        //When
        game.movePawn(CardinalDirection.WEST);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move pawn in a forbidden direction should not move pawn")
    public void moveForbiddenPawnPlacesNoMove() {
        //Given
        Pawn expected = new Pawn(0, 4);
        Board board = new Board();
        Game game = new Game(board);
        //When
        game.movePawn(CardinalDirection.WEST);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move pawn should cross fences")
    public void movePawnCrossFence() {
        //Given
        Pawn expected = new Pawn(1, 4);
        Board board = new Board();
        Game game = new Game(board);
        game.addFence(new Fence(0, 4, false));
        //When
        game.movePawn(CardinalDirection.EAST);
        //Then
        assertThat(game.getPawn()).isEqualTo(expected);
    }

    @Test
    @DisplayName("move pawn should finish the game it arrives at his goal line")
    public void isOverWhenPawnArrivesGoalLinePawnCrossFence() {
        //Given
        Board board = new Board(3);
        Game game = new Game(board);
        game.movePawn(CardinalDirection.EAST);
        //When
        game.movePawn(CardinalDirection.EAST);
        //Then
        assertThat(game.isOver()).isTrue();
        assertThat(game.getPawn()).isEqualTo(new Pawn(2, 1));
    }

    @Test
    @DisplayName("move pawn should not finish the game it does not arrive at his goal line")
    public void isNotOver() {
        //Given
        Board board = new Board(3);
        Game game = new Game(board);
        //When
        game.movePawn(CardinalDirection.EAST);
        //Then
        assertThat(game.isOver()).isFalse();
    }

    @Test
    @DisplayName("move pawn should finish the game no more move")
    public void isOverNoMoreMove() {
        //Given
        Board board = new Board(3);
        Game game = new Game(board);
        game.movePawn(CardinalDirection.EAST);
        game.movePawn(CardinalDirection.EAST);
        //When
        Throwable throwable = catchThrowable(() -> game.movePawn(CardinalDirection.EAST));
        //Then
        assertThat(throwable)
                .isInstanceOf(GameException.class)
                .hasMessage("Game is over, unable to move a pawn");
    }

    @Test
    @DisplayName("move pawn should finish the game no more fence addition")
    public void isOverNoMoreFenceAddition() {
        //Given
        Board board = new Board(3);
        Game game = new Game(board);
        game.movePawn(CardinalDirection.EAST);
        game.movePawn(CardinalDirection.EAST);
        //When
        Throwable throwable = catchThrowable(() -> game.addFence(new Fence(0, 0, false)));
        //Then
        assertThat(throwable)
                .isInstanceOf(GameException.class)
                .hasMessage("Game is over, unable to add a fence");
    }

}
