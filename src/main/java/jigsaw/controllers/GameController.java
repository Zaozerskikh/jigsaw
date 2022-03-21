package jigsaw.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jigsaw.game.GameTimer;
import jigsaw.models.GameModel;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles user actions during the game.
 */
@Component("gameController")
@FxmlView("/fxml_views/game_view.fxml")
public class GameController {
    /**
     * Constructor.
     * @param timer game timer object.
     * @param model game model object.
     */
    public GameController(@Autowired GameTimer timer, GameModel model) {
        this.timer = timer;
        this.model = model;
    }

    /**
     * Finish game button.
     */
    @FXML
    public Button finishGameButton;

    /**
     * Label that shows game timer gata.
     */
    @FXML
    public Label timerLabel;

    /**
     * Game timer that shows game duration.
     */
    private final GameTimer timer;

    /**
     * Game model (provides game buisness logic).
     */
    private final GameModel model;

    /**
     * Current stage getter.
     * @return current stage.
     */
    protected Stage getCurrentStage() {
        return (Stage) timerLabel.getScene().getWindow();
    }

    /**
     * Game starter.
     */
    public void startGame() {
        if (!model.isStarted()) {
            timer.start(timerLabel);
            model.initFigureBoard((GridPane)((Pane)((GridPane)getCurrentStage().getScene().
                    getRoot().getChildrenUnmodifiable().get(0)).getChildren().get(3)).getChildren().get(0));
            model.initGameBoard((GridPane)((Pane)getCurrentStage().getScene().
                    getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(0));
            model.startGame();
        }
    }

    /**
     * Game finisher.
     */
    public void onFinishGameButtonClick() {
        timer.stop();
        model.stopGame(getCurrentStage());
    }
}
