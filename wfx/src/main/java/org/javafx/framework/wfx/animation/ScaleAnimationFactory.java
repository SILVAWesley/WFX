package org.javafx.framework.wfx.animation;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleAnimationFactory implements AnimationFactory {
	private Node target;
	private double amount;
	private double cycleTime;
	
	public ScaleAnimationFactory(Node target, double amount,  double cycleTime) {
		this.target = target;
		this.amount = amount;
		this.cycleTime = cycleTime;
	}

	@Override
	public Animation createAnimation() {
		ScaleTransition transition = new ScaleTransition(Duration.seconds(cycleTime), target);
		transition.setToY(amount);
		transition.setToX(amount);
		return transition;
	}
}
