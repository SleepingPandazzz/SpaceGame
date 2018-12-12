package com.mycompany.a3.interfaces;

import com.mycompany.a3.gameobjects.GameObject;

public interface IIterator {
	public boolean hasNext();

	public GameObject getNext();
}
