package jigsaw.controllers;

import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Info stage controller (no buisness logic here - this class necessary
 * only for the correct work of the custom fxml loader).
 */
@Component("infoController")
@FxmlView("/fxml_views/info_view.fxml")
public class InfoController { }
