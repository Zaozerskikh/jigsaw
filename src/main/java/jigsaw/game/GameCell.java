package jigsaw.game;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class that implements game cell object.
 */
@Component("gameCell")
@Scope("prototype")
public class GameCell extends Pane {
    /**
     * Cell color.
     */
    private Color color;

    /**
     * Getter for color.
     * @return color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for color.
     * @param color new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Changes background cell color.
     * @param color new background color.
     */
    public void setBackground(Color color) {
        this.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Checks if there is figure in cell or no.
     * @return cell condition.
     */
    public boolean isFree() {
        return color == Color.LIGHTGRAY || color == Color.GHOSTWHITE;
    }
}
