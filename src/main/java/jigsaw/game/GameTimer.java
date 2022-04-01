package jigsaw.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

/**
 * Class that implements game timer gui object.
 */
@Component("gameTimer")
@Scope("singleton")
public class GameTimer {
    /**
     * Start time for timer.
     */
    private Instant startTime;

    // hh mm ss.
    private long hours, mins, seconds;

    /**
     * Timer.
     */
    private Timeline timer;

    /**
     * Starting timer.
     * @param timerLabel label for timer's ticks showing.
     */
    public void start(Label timerLabel) {
        DecimalFormat df = new DecimalFormat("00");
        this.startTime = Instant.now();
        timer = new Timeline(new KeyFrame(new javafx.util.Duration(1000), event -> {
            seconds = Duration.between(startTime, Instant.now()).getSeconds();
            while (seconds > 3599) {
                ++hours;
                seconds -= 3600;
                startTime = startTime.plusSeconds(3600);
            }
            while (seconds > 59) {
                ++mins;
                seconds -= 60;
                startTime = startTime.plusSeconds(60);
            }
            timerLabel.setText(df.format(hours) + ":" + df.format(mins) + ":" + df.format(seconds));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /**
     * Stops timer.
     */
    public void stop() {
        timer.stop();
    }
}
