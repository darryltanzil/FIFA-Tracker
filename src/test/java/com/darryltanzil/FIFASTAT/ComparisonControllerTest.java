package com.darryltanzil.FIFASTAT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for ComparisonControllerTest
 * Tests methods within the ComparisonController Class, nothing that is Spring Framework specific
 */
class ComparisonControllerTest {

    ComparisonController controller;

    @BeforeEach
    void init() {
         controller = new ComparisonController(); // Arrange
    }

    @Test
    void compareHeight() {

        // CASE 1: Player 2 taller than player 1
        // Arrange
        Player player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 178, 68);
        Player player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 185, 77);

        // Act
        Comparison response = controller.compareHeight(player1, player2);

        // Assert
        assertEquals(new Comparison(1, "Thomas Partey", "Martin Ødegaard",
                player2.name() + " is 7 cm taller then " +  player1.name() + "."), response);


        // CASE 2: Player 1 taller than player 2
        // Arrange
        Player case2Player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 185, 68);
        Player case2Player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 178, 77);

        // Act
        Comparison response2 = controller.compareHeight(case2Player1, case2Player2);

        // Assert
        assertEquals(new Comparison(2, "Martin Ødegaard", "Thomas Partey",
                "Martin Ødegaard is 7 cm taller then Thomas Partey."), response2);


        // Case 3: Both players are the same weight

        // Arrange
        Player case3Player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 185, 68);
        Player case3Player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 185, 77);

        // Act
        Comparison response3 = controller.compareHeight(case3Player1, case3Player2);

        // Assert
        assertEquals(new Comparison(3,
                "Neither", "Neither",
                case3Player1.name() + " is the same height as " + case3Player2.name() + "."), response3);
    }

    @Test
    void compareWeight() {
        // CASE 1: Player 1 heavier than player 2
        // Arrange
        Player player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 178, 68);
        Player player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 185, 77);

        // Act
        Comparison response = controller.compareWeight(player1, player2);

        // Assert
        assertEquals(new Comparison(1, "Thomas Partey", "Martin Ødegaard",
                player2.name() + " is 9 kg heavier then " +  player1.name() + "."), response);


        // CASE 2: Player 2 heavier than player 1
        // Arrange
        Player case2Player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 185, 77);
        Player case2Player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 178, 68);

        // Act
        Comparison response2 = controller.compareWeight(case2Player1, case2Player2);

        // Assert
        assertEquals(new Comparison(2, "Martin Ødegaard", "Thomas Partey",
                player1.name() + " is 9 kg heavier then " +  player2.name() + "."), response2);


        // Case 3: Both players are the same weight

        // Arrange
        Player case3Player1 = new Player(1, "Martin", "Ødegaard", "Martin Ødegaard", 185, 68);
        Player case3Player2 = new Player(1, "Thomas", "Partey", "Thomas Partey", 185, 68);

        // Act
        Comparison response3 = controller.compareWeight(case3Player1, case3Player2);

        // Assert
        assertEquals(new Comparison(3,
                "Neither", "Neither",
                case3Player1.name() + " is the same weight as " + case3Player2.name() + "."), response3);
    }
}