package jigsaw.views;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import jigsaw.game.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class that shows all game data to user during the game.
 */
@Component("gameView")
@Scope("singleton")
public class GameView {
    /**
     * Method thad shows generated figure in the small grid 3x3.
     * @param figure generated figure.
     * @param figureBoad board where need to show figure.
     * @param currentFigureColor figure color.
     */
    public void showGeneratedFigure(Figure figure, GridPane figureBoad, Color currentFigureColor) {
        figureBoad.getChildren().forEach(x -> {
            if (x.getClass() == GameCell.class) {
                ((GameCell)x).setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
                if (figure.getFigure()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]) {
                    ((GameCell)x).setBackground(new Background(new BackgroundFill(currentFigureColor, null, null)));
                }
            }
        });
    }

    /**
     * Method that displays figure moving.
     * @param cells figure cells list.
     * @param condition drag condition.
     * @param currentFigureColor figure color for fill.
     */
    public void displayFigureMoving(ArrayList<GameCell> cells, DragCondition condition, Color currentFigureColor) {
        cells.forEach(x -> {
            if (condition == DragCondition.MOVE) {
                x.setBackground(currentFigureColor);
            } else if (condition == DragCondition.REMOVE) {
                x.setColor(x.getColor());
                x.setBackground(x.getColor());
            } else {
                x.setColor(currentFigureColor);
                x.setBackground(currentFigureColor);
            }
        });
    }
}
