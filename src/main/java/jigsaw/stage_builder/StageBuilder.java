package jigsaw.stage_builder;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jigsaw.controllers.StartController;
import jigsaw.game.GameCell;
import jigsaw.models.GameModel;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Class that builds application stages.
 */
@Component("stageBuilder")
@Scope("singleton")
public class StageBuilder {
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
     * @param controllerClass stage controller class.
     * @param stageName stage title.
     * @param <T> type param for controller class.
     * @return builded stage.
     */
    public <T> Stage buildStage(Class<T> controllerClass, String stageName) {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        try {
            stage.setScene(new Scene(context.getBean(FxWeaver.class).loadView(controllerClass)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!stageName.equals("About Jigsaw")) {
            stage.setResizable(false);
        }
        if (stageName.equals("Game")) {
            // Game stage close confirmation.
            stage.setOnCloseRequest(closeEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation");
                alert.setContentText("Do you really want to leave game?");
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        context.getBean(GameModel.class).setStarted(false);
                        this.buildStage(StartController.class, "Jigsaw").show();
                    } else {
                        closeEvent.consume();
                    }
                });
            });

            // Game board and small figure board initialization.
            ((Pane)stage.getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildren().add(buildGrid(9));
            ((Pane)((GridPane)stage.getScene().getRoot().getChildrenUnmodifiable().get(0)).getChildren().get(3)).getChildren().add(buildGrid(3));
        }

        // stage decoration.
        stage.getScene().getStylesheets().add(Objects.requireNonNull(StageBuilder.class.getResource("/stylesheets/scene.css")).toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(StageBuilder.class.getResource("/icons/icon.png")).toExternalForm()));

        return stage;
    }

    /**
     * Building game grid and figure grid (small grid where figures generates).
     * @param size grid size (3 or 9).
     * @return builded grid.
     */
    private GridPane buildGrid(int size) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GameCell cell = context.getBean(GameCell.class);
                cell.setFocusTraversable(false);
                cell.setMinSize(56, 56);
                cell.setColor(Color.GHOSTWHITE);
                if ((i > 2 && i < 6 && (j < 3 || j > 5) || j > 2 && j < 6 && (i < 3 || i > 5)) || size == 3) {
                    cell.setColor(Color.LIGHTGRAY);
                }
                cell.setBackground(cell.getColor());
                gridPane.add(cell, i, j);
            }
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }
}
