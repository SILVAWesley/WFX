package org.javafx.framework.wfx.manager;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.javafx.framework.wfx.controller.PackControlled;
import org.javafx.framework.wfx.momentum.Momentum;

import javafx.scene.layout.Pane;

public class Screen {
	private String id;
	private Pane pane;
	private PackControlled controller;
	private List<Momentum> momentum;
	
	public Screen(String id, Pane pane, PackControlled controller, List<Momentum> momentum) {
		this.id = id;
		this.pane = pane;
		this.controller = controller;
		this.momentum = momentum;
	}
	
	public Screen(String id, Pane pane, PackControlled controller) {
		this(id, pane, controller, new LinkedList<Momentum>());
		loadMomentum(controller);
	}
	
	public String getId() {
		return id;
	}
	
	public Pane getPane() {
		return pane;
	}
	
	public void setPane(Pane pane) {
		this.pane = pane;
	}
	
	public PackControlled getController() {
		return controller;
	}
	
	public void setController(PackControlled controller) {
		this.controller = controller;
	}
	
	public List<Momentum> getMomentum() {
		return momentum;
	}
	
	public void setMomentum(List<Momentum> momentum) {
		this.momentum = momentum;
	}
	
	public void invokeMomentum(Momentum m) {
		if (momentum != null && momentum.contains(m)) {
			Momentum.invokeMomentum(controller, m);
		}
	}
	
	private void loadMomentum(PackControlled controller) {
		Method[] methods = controller.getClass().getDeclaredMethods();
		
		for (Method method : methods) {
			method.setAccessible(true);
			Momentum methodMomentum = Momentum.searchForMomentum(method);
			
			if (methodMomentum != null) {
				momentum.add(methodMomentum);
			}
		}
	}
}
