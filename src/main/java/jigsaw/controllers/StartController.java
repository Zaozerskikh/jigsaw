package jigsaw.controllers;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import jigsaw.stage_builder.StageBuilder;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Class that handles user actions on the start screen.
 */
@Component("startController")
@FxmlView("/fxml_views/start_view.fxml")
public class StartController {
    /**
     * Spring Boot application context.
     */
    private ConfigurableApplicationContext context;

    /**
     * Setter for Spring Boot application context.
     * @param context Spring Boot application context.
     */
    @Autowired
    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

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
        context.getBean(StageBuilder.class).buildStage(GameController.class, "Game").show();
    }

    /**
     * Shows shows info about this application.
     */
    public void onInfoBittonClick() {
        context.getBean(StageBuilder.class).buildStage(InfoController.class, "About").show();
    }
}
