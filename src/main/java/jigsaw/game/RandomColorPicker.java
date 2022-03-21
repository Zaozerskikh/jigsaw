package jigsaw.game;

import javafx.scene.paint.Color;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class that picks random color.
 */
@Component("randomColorPicker")
@Scope("singleton")
public class RandomColorPicker implements ColorPicker {
    private static int lastColor = -1;

    /**
     * Picks random color.
     * @return generated color.
     */
    public Color getColor() {
        // avoiding color repeat.
        int currColor;
        do {
            currColor = ThreadLocalRandom.current().nextInt(0, 5);
        } while (currColor == lastColor);
        lastColor = currColor;

        switch (currColor) {
            case 0 -> {
                return Color.DARKGREEN;
            } case 1 -> {
                return Color.DARKRED;
            } case 2 -> {
                return Color.DARKMAGENTA;
            } case 3 -> {
                return Color.BLUE;
            }
        }
        return Color.CORAL;
    }
}
