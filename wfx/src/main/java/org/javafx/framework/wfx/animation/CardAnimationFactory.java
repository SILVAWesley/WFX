package org.javafx.framework.wfx.animation;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public class CardAnimationFactory implements AnimationFactory {
	private Node outTarget;
	private Node inTarget;
	double hPos;
	
	public CardAnimationFactory(Node inTarget, Node outTarget, double hPos) {
		this.inTarget = inTarget;
		this.outTarget = outTarget;
		this.hPos = hPos;
	}

	@Override
	public Animation createAnimation() {
		KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(inTarget.translateXProperty(), hPos),
                new KeyValue(outTarget.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(inTarget.translateXProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(outTarget.translateXProperty(), hPos, Interpolator.EASE_BOTH));
        
        Animation slide = new Timeline(start, end);
        
		return slide;
	}
	
	
}
