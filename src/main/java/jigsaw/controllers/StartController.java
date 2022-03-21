package jigsaw.controllers;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import jigsaw.main.JavaFxApp;
import jigsaw.stage_builder.StageBuilder;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Class that handles user actions on the start screen.
 */
@Component("startController")
@FxmlView("/fxml_views/start_view.fxml")
public class StartController {
    /**
     * Button that starts new game.
     */
    public Button newGameButton;

    /**
     * Button that shows info about this application.
     */
    public Button infoButton;

    /**
     * Getter for current stage.
     * @return current app stage.
     */
    private Stage getCurrentStage() {
        return (Stage)newGameButton.getScene().getWindow();
    }

    /**
     * Starts new game.
     */
    public void onNewGameButtonClick() {
        getCurrentStage().close();
        JavaFxApp.getContext().getBean(StageBuilder.class).buildStage(GameController.class, "Game").show();
    }

    /**
     * Shows shows info about this application.
     */
    public void onInfoBittonClick() {
        JavaFxApp.getContext().getBean(StageBuilder.class).buildStage(InfoController.class, "About").show();
    }
}
