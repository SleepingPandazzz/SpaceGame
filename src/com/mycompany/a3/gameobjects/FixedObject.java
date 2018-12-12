package com.mycompany.a3.gameobjects;

import com.mycompany.a3.interfaces.IFixedable;

public abstract class FixedObject extends GameObject implements IFixedable {
	// Fixed objects have unique integer identification numbers
	// Every fixed object has an integer id which is different from that of every
	// other fixed object
	private int objectID;
	private static int counter = 0;

	public int getObjectID() {
		return this.objectID;
	}

	public void setObjectID() {
		this.objectID = counter;
		counter++;
	}

}
