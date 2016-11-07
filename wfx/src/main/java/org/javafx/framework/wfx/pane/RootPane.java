package org.javafx.framework.wfx.pane;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RootPane extends PackPane {
	private Scene scene;
	
	public RootPane(Stage stage, double width, double height, int stackSize) {
		super(stackSize);
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(this);
		scene = new Scene(anchorPane, width, height);
		stage.setScene(scene);
		stage.show();
	}
	
	public RootPane(Stage stage, double width, double height) {
		this(stage, width, height, PackPane.DEFAULT_MAX_STACK_SIZE);
	}
}
