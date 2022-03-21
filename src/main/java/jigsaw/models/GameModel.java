package jigsaw.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jigsaw.controllers.GameController;
import jigsaw.controllers.StartController;
import jigsaw.game.*;
import jigsaw.main.JavaFxApp;
import jigsaw.stage_builder.StageBuilder;
import jigsaw.views.GameView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

/**
 * Class, that provides game buisness-logic.
 */
@Component("gameModel")
@Scope("singleton")
public class GameModel {
    /**
     * Constructor.
     * @param view game view object.
     * @param figureBuilder class, that builds figure every move.
     * @param colorPicker class that selects random color for figures every new game.
     */
    public GameModel(@Autowired GameView view, FigureBuilder figureBuilder, ColorPicker colorPicker) {
        this.view = view;
        this.figureBuilder = figureBuilder;
        this.colorPicker = colorPicker;
    }

    /**
     * Game-view object.
     */
    private final GameView view;

    /**
     * Current figure object.
     */
    private Figure currentFigure;

    /**
     * Figure color in current game.
     */
    private Color currentFigureColor;

    /**
     * Random figure color generator.
     */
    private final ColorPicker colorPicker;

    /**
     * Figure builder.
     */
    private final FigureBuilder figureBuilder;

    /**
     * Board where generated figure shows.
     */
    private GridPane figureBoard;

    /**
     * Initializes figure boards.
     * @param figureBoard figure board.
     */
    public void initFigureBoard(GridPane figureBoard) {
        this.figureBoard = figureBoard;
    }

    /**
     * Board where game is played.
     */
    private GridPane gameBoard;

    /**
     * Initializates game board object.
     * @param gameBoard game board object.
     */
    public void initGameBoard(GridPane gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * If game is started or not.
     */
    private boolean isStarted = false;

    /**
     * Setter for isStarted.
     * @param started new game condition (started or not).
     */
    public void setStarted(boolean started) {
        this.isStarted = started;
    }

    /**
     * Getter for isStarted.
     * @return isStarted.
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Count of user moves.
     */
    private int movesCount;

    /**
     * Count of filled cells.
     */
    private int userScore;

    /**
     * Starts game.
     */
    public void startGame() {
        movesCount = 0;
        userScore = 0;
        isStarted = true;
        currentFigureColor = colorPicker.getColor();
        addDragAndDropEventsHandlers();
        generateFigure();
    }

    /**
     * Generates random figure.
     */
    public void generateFigure() {
        currentFigure = figureBuilder.buildFigure();
        view.showGeneratedFigure(currentFigure, figureBoard, currentFigureColor);
    }

    /**
     * Adds drag&drop events handlers.
     */
    public void addDragAndDropEventsHandlers() {
        // drag starts.
        figureBoard.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = figureBoard.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();

            // Content for drag&drop correctly works.
            content.putString("some string");
            db.setContent(content);
        });

        // drag event.
        figureBoard.setOnMouseDragged((MouseEvent event) -> event.setDragDetect(true));

        // drag over game board.
        gameBoard.setOnDragOver(event -> {
            if (event.getGestureSource() != gameBoard && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        // drag over - drag exited on each game cell.
        gameBoard.getChildren().forEach(node -> {
            if (node.getClass() == GameCell.class) {
                node.setOnDragOver(y -> moveFigure((GameCell)node, DragCondition.MOVE));
                node.setOnDragExited(y -> {
                    if (!y.isDropCompleted()) {
                        moveFigure((GameCell)node, DragCondition.REMOVE);
                    } else {
                        if (moveFigure((GameCell)node, DragCondition.PUT)) {
                            userScore += currentFigure.getFigureSize();
                            generateFigure();
                        }
                    }
                });
            }
        });

        // put figure on game board (if user's move ended successfully).
        gameBoard.setOnDragDropped((DragEvent event) -> {
            event.setDropCompleted(true);
            ++movesCount;
            event.consume();
        });
    }

    /**
     * Figure moving.
     * @param cell cell above which cursor is located.
     * @param condition darg condition.
     * @return is moving successfull or not.
     * Warnings are suppressed because checking before "get()" in line 213 is not required,
     * because this code already locates in the "try" block.
     */
    @SuppressWarnings("all")
    private boolean moveFigure(GameCell cell, DragCondition condition) {
        var cells = new ArrayList<GameCell>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentFigure.getFigure()[i][j]) {
                    var currGameCellsStream = gameBoard.getChildren().stream().filter(x -> x.getClass() == GameCell.class);

                    // final wrappers for indexes.
                    int finalI = i;
                    int finalJ = j;

                    try {
                        // try to find new cell to move.
                        var find = (GameCell)currGameCellsStream.filter(x -> GridPane.getColumnIndex(x) == GridPane.getColumnIndex(cell) + finalI - 1
                                && GridPane.getRowIndex(x) == GridPane.getRowIndex(cell) + finalJ - 1).findFirst().get();
                        cells.add(find);
                    } catch (Exception e) {
                        // if coud not find cell - no need to do anything; just skip this situation.
                    }
                }
            }
        }

        // if part of figure out of game field bounds -> rollback.
        if (currentFigure.getFigureSize() != cells.size()) {
            return false;
        }

        // if figures combinibg -> rollback.
        for (GameCell gameCell : cells) {
            if (!gameCell.isFree()) {
                return false;
            }
        }

        // else succesfully "moving" figure -> filling cells.
        view.displayFigureMoving(cells, condition, currentFigureColor);
        return true;
    }

    /**
     * Stops game.
     * @param currentStage current game stage.
     */
    public void stopGame(Stage currentStage) {
        isStarted = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Game Over!");
        alert.setContentText("Your number of moves: " + movesCount +
                "\nYour score: " + userScore + "\nDo you want to play again?");
        alert.showAndWait().ifPresent(x -> processNewGameDialog(x, currentStage));
    }

    /**
     * New game dialog processing.
     * @param buttonType type of button that was pressed in the dialog window.
     * @param currStage current game stage.
     */
    private void processNewGameDialog(ButtonType buttonType, Stage currStage) {
        if (buttonType == ButtonType.OK) {
            // starting new game.
            currStage.close();
            currStage = JavaFxApp.getContext().getBean(StageBuilder.class).buildStage(GameController.class, "Game");
            currStage.show();
            JavaFxApp.getContext().getBean(GameController.class).startGame();
        } else {
            // else exit to the main menu.
            currStage.close();
            setStarted(false);
            JavaFxApp.getContext().getBean(StageBuilder.class).buildStage(StartController.class, "Jigsaw").show();
        }
    }
}
