package com.mycompany.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public abstract class GameObject {

	private Point2D doublePoint = new Point2D(0, 0);
	private int objectColor; // all game objects have a color, defined by a int value
	private int objectSize;
	private double centerX;
	private double centerY;
	private double radius;
	private boolean ifCol = false; // check if collides with other object, false is no, true is yes
	private int hitCase = -1; 
	
	public int getObjectColor() {
		return this.objectColor;
	}

	public double getPointX() {
		return this.doublePoint.getX();
	}

	public double getPointY() {
		return this.doublePoint.getY();
	}

	public boolean getIfCol() {
		return this.ifCol;
	}

	public int getObjectSize() {
		return this.objectSize;
	}

	public double getCenterX() {
		return this.centerX;
	}

	public double getCenterY() {
		return this.centerY;
	}

	public double getRadius() {
		return this.radius;
	}
	
	public int getHitCase() {
		return this.hitCase;
	}

	public void setObjectColor(int newObjectColor) {
		this.objectColor = newObjectColor;
	}

	public void setPointX(double newX) {
		this.doublePoint.setX(newX);
	}

	public void setPointY(double newY) {
		this.doublePoint.setY(newY);
	}

	public void setObjectSize(int newObjectSize) {
		this.objectSize = newObjectSize;
	}

	public void setGetIfCol(boolean newCol) {
		this.ifCol = newCol;
	}

	public void setCenterX(double newX) {
		this.centerX = newX;
	}

	public void setCenterY(double newY) {
		this.centerY = newY;
	}

	public void setRadius(double newR) {
		this.radius = newR;
	}
	
	public void setHitCase(int newHitCase) {
		this.hitCase = newHitCase;
	}

	public String toString() {
		String s = "loc=" + Math.round(doublePoint.getX() * 10.0) / 10.0 + ","
				+ Math.round(doublePoint.getY() * 10.0) / 10.0 + " ";
		s += "color=" + "[" + ColorUtil.red(getObjectColor()) + "," + ColorUtil.green(getObjectColor()) + ","
				+ ColorUtil.blue(getObjectColor()) + "] ";
		return s;
	}

	public abstract void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);

}
