package com.marmelab.quoridor.model;

import com.marmelab.quoridor.game.Fence;
import org.junit.jupiter.api.Disabled;
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
    @DisplayName("create default board should throw an exception, the board size must be positive")
    void newBoardPositiveNumber() {
        // When
        Throwable throwable = catchThrowable(() -> new Board(-1));
        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The size must be positive");
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
        Board board = new Board(3);
        Fence expected = new Fence(0, 0, false);
        // When
        board.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("it is not possible to add another fence at the same place")
    void addFenceNotAthTheSamePlace() {
        // Given
        Board board = new Board(3);
        Fence expected = new Fence(0, 0, false);
        board.addFence(new Fence(0, 0, false));
        // When
        board.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsOnly(expected);
    }

    @Test
    @DisplayName("get fences should return the right number of vertical fences")
    void getFencesReturnsTheRightNumberOfVerticalFences() {
        // Given
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false));
        board.addFence(new Fence(0, 2, false));
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
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true));
        board.addFence(new Fence(2, 0, true));
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
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true));
        board.addFence(new Fence(1, 0, true));
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
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false));
        board.addFence(new Fence(0, 1, false));
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
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, true));
        // When
        board.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, true));
    }

    @Test
    @DisplayName("It should not be possible to add a horizontal fence one vertical fence")
    void addFenceNotPossibleHorizontalFenceOnVerticalFence() {
        // Given
        Board board = new Board(5);
        board.addFence(new Fence(0, 0, false));
        // When
        board.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 0, false));
    }

    @Test
    @DisplayName("It should not be possible to add a vertical fence before a vertical fence")
    @Disabled
    void addFenceNotPossibleVerticalFenceBeforeVerticalFence() {
        // Given
        Board board = new Board(5);
        board.addFence(new Fence(0, 1, false));
        // When
        board.addFence(new Fence(0, 0, false));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(0, 1, false));
    }

    @Test
    @DisplayName("It should not be possible to add a horizontal fence before a horizontal fence")
    @Disabled
    void addFenceNotPossibleHorizontalFenceBeforeHorizontalFence() {
        // Given
        Board board = new Board(5);
        board.addFence(new Fence(1, 0, true));
        // When
        board.addFence(new Fence(0, 0, true));
        // Then
        List<Fence> fences = board.getFences();
        assertThat(fences).containsExactlyInAnyOrder(
                new Fence(1, 0, true));
    }

}
