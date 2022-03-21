package jigsaw.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class that implemets Figure object.
 */
@Component("figure")
@Scope("prototype")
public class Figure {
    /**
     * Figure matrix.
     */
    private boolean[][] figureMatrix;

    /**
     * Count of filled blocks in figureMatrix's.
     */
    private int figureSize;

    /**
     * Setter for figure matrix.
     * @param figureMatrix figureMatrix.
     */
    public void setFigureMatrix(boolean[][] figureMatrix) {
        this.figureMatrix = figureMatrix;
        calculateFigureSize();
    }

    /**
     * Getter for figureMatrix size.
     * @return figureMatrix size.
     */
    public int getFigureSize() {
        return figureSize;
    }

    // pre-calculating figureMatrix size for better perfomance.
    private void calculateFigureSize() {
        figureSize = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (figureMatrix[i][j]) {
                    ++figureSize;
                }
            }
        }
    }

    /**
     * Getter for figureMatrix matrix.
     * @return figureMatrix matrix.
     */
    public boolean[][] getFigure() {
        return this.figureMatrix;
    }
}
