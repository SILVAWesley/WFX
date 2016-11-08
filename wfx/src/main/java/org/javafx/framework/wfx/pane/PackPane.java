package org.javafx.framework.wfx.pane;

import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.javafx.framework.wfx.controller.PackControlled;
import org.javafx.framework.wfx.momentum.Momentum;
import org.javafx.framework.wfx.util.Screen;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class PackPane extends StackPane {
	public static final int DEFAULT_MAX_STACK_SIZE = 1;
	
	private List<Screen> loadedScreens;
	private Deque<String> showIdStack;
	private StringProperty showingPaneId;
	private int stackSize;
	
	public PackPane(int stackSize) {
		this.loadedScreens = new LinkedList<>();
		this.showingPaneId = new SimpleStringProperty();
		this.showIdStack = new LinkedList<String>();
		this.stackSize = stackSize;
	}
	
	public PackPane() {
		this(DEFAULT_MAX_STACK_SIZE);
	}
	
	public boolean loadScreen(String id, URL fxmlUrl) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
			Pane pane = (Pane) fxmlLoader.load();
			PackControlled controller = fxmlLoader.getController();
			Screen screen = new Screen(id, pane, controller);
			screen.invokeMomentum(Momentum.BEFORE_LOAD);
			controller.setPackController(this);
			loadedScreens.add(screen);
			screen.invokeMomentum(Momentum.AFTER_LOAD);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void loadScreen(String id, Pane pane) {
		if (pane instanceof PackControlled) {
			PackControlled slidedPane = (PackControlled) pane;
			Screen screen = new Screen(id, pane, slidedPane);
			screen.invokeMomentum(Momentum.BEFORE_LOAD);
			slidedPane.setPackController(this);
			loadedScreens.add(screen);
			screen.invokeMomentum(Momentum.AFTER_LOAD);
		}
	}
	
	public boolean loadScreens(String[] ids, URL[] fxmlUrls) {
		boolean returnValue = true;
		
		for (int i = 0; i < ids.length; i++) {
			returnValue = loadScreen(ids[i], fxmlUrls[i]) && returnValue;
		}
		
		return returnValue;
	}
	
	public void loadScreens(String[] ids, Pane[] panes) {
		for (int i = 0; i < ids.length; i++) {
			loadScreen(ids[i], panes[i]);
		}
	}
	
	public void loadNShowScreen(String id, URL fxmlUrl) {
		loadScreen(id, fxmlUrl);
		showScreen(id);
	}
	
	public void loadNShowScreen(String id, Pane pane) {
		loadScreen(id, pane);
		showScreen(id);
	}
	
	public boolean showScreen(String id) {
		Screen screen = findById(id);
		
		if (screen == null) return false;
		screen.invokeMomentum(Momentum.BEFORE_SHOW);
		if (showIdStack.size() > 0) findById(showIdStack.getFirst()).invokeMomentum(Momentum.BEFORE_UNSHOW);
		
		if (getChildren().isEmpty()) {
			getChildren().add(screen.getPane());
		} else {
			getChildren().remove(0);
			getChildren().add(screen.getPane());
		}
		
		lruClean();
		
		if (showIdStack.size() > 0) findById(showIdStack.getFirst()).invokeMomentum(Momentum.AFTER_UNSHOW);
		showIdStack.addFirst(id);
		showingPaneId.set(id);
		screen.invokeMomentum(Momentum.AFTER_SHOW);
		
		return true;
	}
	
	public void unloadScreen(String id) {
		Screen screen = findById(id);
		screen.invokeMomentum(Momentum.BEFORE_UNLOAD);
		loadedScreens.remove(screen);
		screen.invokeMomentum(Momentum.AFTER_UNLOAD);
	}
	
	public void forceUnloadScreen(String id) {
		while(showIdStack.remove(id));
		unloadScreen(id);
	}
	
	public void setOnScreenIdChange(ChangeListener<String> changeListener) {
		showingPaneId.addListener(changeListener);
	}
	
	private void lruClean() {
		while (showIdStack.size() > stackSize) showIdStack.removeLast();
	}
	
	private Screen findById(String id) {
		for (Screen screen : loadedScreens) {
			if (screen.getId().equals(id)) {
				return screen;
			}
		}
		
		throw new RuntimeException();
	}
}

