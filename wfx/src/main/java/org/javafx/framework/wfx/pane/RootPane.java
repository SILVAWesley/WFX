package org.javafx.framework.wfx.pane;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RootPane extends PackPane {
	private Scene scene;
	
	public RootPane(Stage stage, double width, double height, int stackSize) {
		super(stackSize);
		scene = new Scene(this, width, height);
		stage.setScene(scene);
	}
	
	public RootPane(Stage stage, double width, double height) {
		this(stage, width, height, PackPane.DEFAULT_MAX_STACK_SIZE);
	}
}
