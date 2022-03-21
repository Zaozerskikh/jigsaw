package jigsaw.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that builds random figure.
 */
@Component("randomFigureBuilder")
@Scope("singleton")
public class RandomFigureBuilder implements FigureBuilder {
    /**
     * Size of figure matrix.
     */
    private final int FIGURE_FIELD_SIZE = 3;

    /**
     * Last figure index to avoid repetitions of figures.
     */
    private int lastFigure = -1;

    /**
     * Figure boolean matrix.
     */
    private final boolean[][] matrix = new boolean[FIGURE_FIELD_SIZE][FIGURE_FIELD_SIZE];

    /**
     * Building random figure matrix.
     * @return random figure matrix.
     */
    private boolean[][] buildFigureMatrix() {
        // avoiding repetitions of figures
        int rand;
        do {
            rand = ThreadLocalRandom.current().nextInt(0, 31);
        } while (!checkRepetitions(rand));
        lastFigure = rand;

        clearFigureMatrix();
        if (rand < 4) {
            matrix[1][0] = true;  // * 0 0
            matrix[1][1] = true;  // * 0 *
            matrix[1][2] = true;  // * 0 *
            matrix[2][0] = true;
        } else if (rand > 3 && rand < 8) {
            matrix[1][0] = true;  // 0 0 *
            matrix[1][1] = true;  // * 0 *
            matrix[1][2] = true;  // * 0 *
            matrix[0][0] = true;
        } else if (rand > 7 && rand < 12) {
            matrix[0][0] = true;  // 0 * *
            matrix[0][1] = true;  // 0 0 *
            matrix[1][1] = true;  // * 0 *
            matrix[1][2] = true;
        } else if (rand > 11 && rand < 16) {
            matrix[2][0] = true;  // * * 0
            matrix[2][1] = true;  // * * 0
            matrix[2][2] = true;  // 0 0 0
            matrix[1][2] = true;
            matrix[0][2] = true;
        } else if (rand > 15 && rand < 20) {
            matrix[1][0] = true;  // * 0 *
            matrix[1][1] = true;  // * 0 *
            matrix[1][2] = true;  // 0 0 0
            matrix[2][2] = true;
            matrix[0][2] = true;
        } else if (rand > 19 && rand < 22) {
            matrix[0][1] = true;  // * * *
            matrix[1][1] = true;  // 0 0 0
            matrix[2][1] = true;  // * * *
        } else if (rand > 21 && rand < 26) {
            matrix[1][1] = true;  // * * *
            matrix[2][1] = true;  // * 0 0
            matrix[1][2] = true;  // * 0 *
        } else if (rand > 25 && rand < 30) {
            matrix[0][0] = true;  // 0 * *
            matrix[0][1] = true;  // 0 0 *
            matrix[0][2] = true;  // 0 * *
            matrix[1][1] = true;
        }  else {
            matrix[1][1] = true;  // * * *
            //                       * 0 *
            //                       * * *
        }
        figureRandomRotate();
        return matrix;
    }

    /**
     * Avoids figures repetition.
     * @param figureIdx generated figure idx.
     * @return does the current figure repeat the previous one.
     */
    private boolean checkRepetitions(int figureIdx) {
        if (figureIdx < 4) {
            return lastFigure >= 4;
        } else if (figureIdx > 3 && figureIdx < 8) {
            return lastFigure <= 3 || lastFigure >= 8;
        } else if (figureIdx > 7 && figureIdx < 12) {
            return lastFigure <= 7 || lastFigure >= 12;
        } else if (figureIdx > 11 && figureIdx < 16) {
            return lastFigure <= 11 || lastFigure >= 16;
        } else if (figureIdx > 15 && figureIdx < 20) {
            return lastFigure <= 15 || lastFigure >= 20;
        } else if (figureIdx > 19 && figureIdx < 22) {
            return lastFigure <= 19 || lastFigure >= 22;
        } else if (figureIdx > 21 && figureIdx < 26) {
            return lastFigure <= 21 || lastFigure >= 26;
        } else if (figureIdx > 25 && figureIdx < 30) {
            return lastFigure <= 25 || lastFigure >= 30;
        }  else {
            return lastFigure != 31;
        }
    }

    /**
     * Clears figure matrix;
     */
    private void clearFigureMatrix() {
        for (int i = 0; i < FIGURE_FIELD_SIZE; i++) {
            for (int j = 0; j < FIGURE_FIELD_SIZE; j++) {
                matrix[i][j] = false;
            }
        }
    }

    /**
     * Rotates current figure's matrix.
     * Number of rotations from 0 to 3.
     */
    private void figureRandomRotate() {
        int count = ThreadLocalRandom.current().nextInt(0, 4);
        for (int k = 0; k < count; k++) {

            // rotation.
            for (int i = 0; i < FIGURE_FIELD_SIZE; i++) {
                for (int j = i; j < FIGURE_FIELD_SIZE; j++) {
                    boolean temp= matrix[i][j];
                    matrix[i][j]= matrix[j][i];
                    matrix[j][i]= temp;
                }
            }
            for(int i = 0; i < FIGURE_FIELD_SIZE; i++) {
                int idx1 = 0;
                int idx2 = FIGURE_FIELD_SIZE - 1;
                while (idx1 < idx2) {
                    boolean temp = matrix[idx1][i];
                    matrix[idx1++][i] = matrix[idx2][i];
                    matrix[idx2--][i] = temp;
                }
            }
        }
    }

    /**
     * Builds random figure.
     * @return random figure.
     */
    @Override
    public Figure buildFigure() {
        Figure figure = new Figure();
        figure.setFigureMatrix(buildFigureMatrix());
        return figure;
    }
}
