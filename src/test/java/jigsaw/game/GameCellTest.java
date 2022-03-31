package jigsaw.game;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCellTest {

    @Test
    void getAndSetColorTest() {
        var cell = new GameCell();
        cell.setColor(Color.CORAL);
        assertEquals(Color.CORAL, cell.getColor());
    }

    @Test
    void setBackgroundTest() {
        var cell = new GameCell();
        cell.setBackground(Color.CORAL);
        assertEquals(cell.getBackground().getFills().get(0).getFill(), Color.CORAL);
    }

    @Test
    void isFreeTrueTest() {
        var cell = new GameCell();
        cell.setColor(Color.GHOSTWHITE);
        assertTrue(cell.isFree());
    }

    @Test
    void isFreeFalseTest() {
        var cell = new GameCell();
        cell.setColor(Color.BLUE);
        assertFalse(cell.isFree());
    }
}