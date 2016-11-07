package org.javafx.framework.wfx.manager;

import org.javafx.framework.wfx.pane.PackPane;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GraphicController {
	private Scene scene;
	private Stage stage;
	
	private AnchorPane root;
	private PackPane pack;
	
	public GraphicController(Stage stage) {
		root = new AnchorPane();
		pack = new PackPane();
		root.getChildren().add(pack);
	}
}
