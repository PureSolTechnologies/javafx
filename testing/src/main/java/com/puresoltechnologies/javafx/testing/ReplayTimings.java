package com.puresoltechnologies.javafx.testing;

public class ReplayTimings {

    public enum Speed {
	SLOW, MEDIUM, FAST;
    }

    static {
	setSpeed(Speed.MEDIUM);
    }

    private static int mouseMoveDelay = 1;
    private static int mouseClickDelay = 100;
    private static int nodeRetrievalDelay = 250;

    public static void setSpeed(Speed speed) {
	switch (speed) {
	case SLOW:
	    setMouseClickDelay(1000);
	    setMouseMoveDelay(5);
	    setNodeRetrievalDelay(1000);
	    break;
	case MEDIUM:
	    setMouseClickDelay(100);
	    setMouseMoveDelay(1);
	    setNodeRetrievalDelay(250);
	    break;
	case FAST:
	default:
	    setMouseClickDelay(0);
	    setMouseMoveDelay(0);
	    setNodeRetrievalDelay(0);
	    break;
	}
    }

    public static int getMouseMoveDelay() {
	return mouseMoveDelay;
    }

    public static void setMouseMoveDelay(int mouseMoveDelay) {
	ReplayTimings.mouseMoveDelay = mouseMoveDelay;
    }

    public static int getMouseClickDelay() {
	return mouseClickDelay;
    }

    public static void setMouseClickDelay(int mouseClickDelay) {
	ReplayTimings.mouseClickDelay = mouseClickDelay;
    }

    public static int getNodeRetrievalDelay() {
	return nodeRetrievalDelay;
    }

    public static void setNodeRetrievalDelay(int nodeRetrievalDelay) {
	ReplayTimings.nodeRetrievalDelay = nodeRetrievalDelay;
    }

}
