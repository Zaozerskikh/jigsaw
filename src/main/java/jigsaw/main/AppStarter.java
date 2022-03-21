package jigsaw.main;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class.
 */
@SpringBootApplication(scanBasePackages = "jigsaw")
public class AppStarter {
    public static void main(String[] args) {
        Application.launch(JavaFxApp.class, args);
    }
}
