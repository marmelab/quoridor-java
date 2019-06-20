package com.marmelab.quoridor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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




}