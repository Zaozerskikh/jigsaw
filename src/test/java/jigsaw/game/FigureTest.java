package jigsaw.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    @Test
    void getFigureSizeTest() {
        var figure = new Figure();
        figure.setFigureMatrix(new boolean[][] {{true, true, true}, {true, false, false}, {false, false, false}});
        assertEquals(4, figure.getFigureSize());
    }

    @Test
    void getAndSetFigureTest() {
        var figure = new Figure();
        boolean[][] matrix = new boolean[][] {{true, true, true}, {true, false, false}, {false, false, false}};
        figure.setFigureMatrix(matrix);
        assertEquals(matrix, figure.getFigure());
    }
}