package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.IMoveable;

// define how they move through the world when told to do so
public abstract class MoveableObject extends GameObject implements IMoveable {
	private int objectSpeed; // the object's speed ranging from 0-10
	private int objectDirection; // the object's direction ranging from 0-359

	public int getObjectSpeed() {
		return this.objectSpeed;
	}

	public int getObjectDirection() {
		return this.objectDirection;
	}

	public void setObjectSpeed(int newObjectSpeed) {
		this.objectSpeed = newObjectSpeed;
	}

	public void setObjectDirection(int newObjectDirection) {
		this.objectDirection = newObjectDirection;
	}

	public void move() {
		setPointX(getPointX() + Math.cos(Math.toRadians(getObjectDirection())) * getObjectSpeed());
		setPointY(getPointY() + Math.sin(Math.toRadians(getObjectDirection())) * getObjectSpeed());
	}

	public String toString() {
		String s = super.toString();
		s += "speed=" + getObjectSpeed() + " ";
		s += "dir=" + getObjectDirection() + " ";
		return s;
	}
}