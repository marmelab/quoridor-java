package com.marmelab.quoridor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class BoardTest {

    @Test
    @DisplayName("create default board should throw an exception, the board size must not be an even number")
    void newBoardOddNumber() {
        // When
        Throwable throwable = catchThrowable(() -> new Board(2));
        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The size must be an odd number");
    }

    @Test
    @DisplayName("create default board should throw an exception, the board size must be at least 3")
    void newBoardAtLeastThreeNumber() {
        // When
        Throwable throwable = catchThrowable(() -> new Board(-1));
        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The size must be at least 3");
    }

    @Test
    @DisplayName("by default, no fences are on the board")
    void constructorFences() {
        // Given
        Board board = new Board(3);
        // When
        List<Fence> fences = board.getFences();
        // Then
        assertThat(fences).isEmpty();
    }

    @Test
    @DisplayName("add the fence should add the fence at the right place")
    void addFenceHappyPath() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(3);
        Fence expected = new Fence(0, 0, false);
        // When
        board.addFence(new Fence(0, 0, false), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("it is not possible to add another fence at the same place")
    void addFenceNotAthTheSamePlace() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(3);
        Fence expected = new Fence(0, 0, false);
        board.addFence(new Fence(0, 0, false), pawn);
        // When
        board.addFence(new Fence(0, 0, true), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("get fences should return the right number of vertical fences")
    void getFencesReturnsTheRightNumberOfVerticalFences() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false), pawn);
        board.addFence(new Fence(0, 2, false), pawn);
        // When
        List<Fence> fences = board.getFences();
        // Then
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(new Position(0, 0), false),
                new Fence(new Position(0, 2), false));
    }

    @Test
    @DisplayName("get fences should return the right number of horizontal fences")
    void getFencesReturnsTheRightNumberOfHorizontalFences() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true), pawn);
        board.addFence(new Fence(2, 0, true), pawn);
        // When
        List<Fence> fences = board.getFences();
        // Then
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true),
                new Fence(2, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence one square after a fence")
    void addFenceNotPossibleHorizontalFenceNextSquare() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true), pawn);
        board.addFence(new Fence(1, 0, true), pawn);
        // When
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence one square after a fence")
    void addFenceNotPossibleVerticalFenceNextFence() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false), pawn);
        board.addFence(new Fence(0, 1, false), pawn);
        // When
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence one horizontal fence")
    void addFenceNotPossibleVerticalFenceOnHorizontalFence() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true), pawn);
        // When
        board.addFence(new Fence(0, 0, false), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence one vertical fence")
    void addFenceNotPossibleHorizontalFenceOnVerticalFence() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false), pawn);
        // When
        board.addFence(new Fence(0, 0, true), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence before a vertical fence")
    void addFenceNotPossibleVerticalFenceBeforeVerticalFence() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 1, false), pawn);
        // When
        board.addFence(new Fence(0, 0, false), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 1, false));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence before a horizontal fence")
    void addFenceNotPossibleHorizontalFenceBeforeHorizontalFence() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(1, 0, true), pawn);
        // When
        board.addFence(new Fence(0, 0, true), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(1, 0, true));
    }

    @Test
    @DisplayName("It should be possible to add a vertical fence between two horizontal fences")
    void addFenceBetweenTwoHorizontalFences() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true), pawn);
        board.addFence(new Fence(2, 0, true), pawn);
        // When
        board.addFence(new Fence(1, 0, false), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true),
                new Fence(2, 0, true),
                new Fence(1, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add an horizontal fence between two vertical fences")
    void addFenceBetweenTwoVerticalFences() {
        // Given
        Pawn pawn = new Pawn(0, 2);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false), pawn);
        board.addFence(new Fence(0, 2, false), pawn);
        // When
        board.addFence(new Fence(0, 1, true), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false),
                new Fence(0, 2, false),
                new Fence(0, 1, true));
    }

    @Test
    @DisplayName("It should be possible to add a fence which does not access the pawn to the goal line")
    void addFenceNotCrossable() {
        // Given
        Pawn pawn = new Pawn(0, 1);
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false), pawn);
        // When
        board.addFence(new Fence(0, 1, true), pawn);
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

}
