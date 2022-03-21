package jigsaw.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import jigsaw.controllers.StartController;
import jigsaw.stage_builder.StageBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Class, that connects Spring Boot functional with JavaFX application.
 * Don't try running application from this entry point - Exception will be thrown.
 * Use AppStarter's entry point or "Jigsaw" run configuration.
 */
public class JavaFxApp extends Application {
    /**
     * Spring Boot application context.
     */
    private static ConfigurableApplicationContext context;

    /**
     * Getter for Spring Boot application context.
     * @return context.
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    /**
     * Application context initialization.
     */
    @Override
    public void init() {
        context = new SpringApplicationBuilder()
                .sources(AppStarter.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    /**
     * Starts JavaFX application.
     * @param stage start stage.
     */
    @Override
    public void start(Stage stage) {
        stage = JavaFxApp.getContext().getBean(StageBuilder.class).buildStage(StartController.class, "Jigsaw");
        stage.show();
    }

    /**
     * Stops JavaFX application - closes context and exits.
     */
    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
}

