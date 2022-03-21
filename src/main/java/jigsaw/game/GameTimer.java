package jigsaw.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

/**
 * Class that implements game timer object.
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

    // String presentation for hh mm ss.
    private String strHours, strMins, strSeconds;

    /**
     * Timer.
     */
    private Timeline timer;

    /**
     * Starting timer.
     * @param timerLabel label for timer's ticks showing.
     */
    public void start(Label timerLabel) {
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
            strHours = String.valueOf(hours).length() < 2 ? "0" + hours : String.valueOf(hours);
            strMins = String.valueOf(mins).length() < 2 ? "0" + mins : String.valueOf(mins);
            strSeconds= String.valueOf(seconds).length() < 2 ? "0" + seconds : String.valueOf(seconds);
            timerLabel.setText(strHours + ":" + strMins + ":" + strSeconds);
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
