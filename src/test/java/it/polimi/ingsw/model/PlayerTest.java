package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    Game singleGame = new SinglePlayerGame();
    Game multiGame = new MultiplayerGame();
    @BeforeEach
    void setUp() {
       player = new Player("Gatto", singleGame);
       player.registerObserver((Observer)singleGame);
    }

    @Test
    void getNickname() { assertEquals("Gatto", player.getNickname());
    }

    @Disabled
    void getCurrentPosition() {
        assertEquals(0, player.getCurrentPosition());
        player.moveOnBoard(2);
        assertEquals(2, player.getCurrentPosition());
    }

    @Disabled
    void moveOnBoard() {
        player.moveOnBoard(1);
        assertEquals(1, player.getCurrentPosition());
        assertThrows(IndexOutOfBoundsException.class, () -> player.moveOnBoard(-1));
        player.moveOnBoard(23);
        assertEquals(24, player.getCurrentPosition(), "Player didn't arrived at last position");
        assertThrows(IndexOutOfBoundsException.class, () -> player.moveOnBoard(1));
    }

    @Disabled
    void registerObserver() {
        player.registerObserver((Observer)singleGame);
        assertSame(singleGame, player.getObserver(), "Got singlePlayer game wrong");
        player.registerObserver((Observer)multiGame);
        assertSame(multiGame, player.getObserver(), "Observer substitution: cannot go from single to multi");
    }

    @Disabled
    void isInVaticanReportRange(){
        player.moveOnBoard(4);
        assertFalse(player.isInVaticanReportRange(1));
        player.moveOnBoard(1);
        assertTrue(player.isInVaticanReportRange(1));
        assertFalse(player.isInVaticanReportRange(2));
        assertFalse(player.isInVaticanReportRange(3));
        player.moveOnBoard(4);
        assertTrue(player.isInVaticanReportRange(1));
    }

}