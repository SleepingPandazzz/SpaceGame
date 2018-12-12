package com.mycompany.a3.interfaces;

import com.mycompany.a3.gameobjects.GameObject;

public interface ICollection {

	// adding an object to collection
	public void add(GameObject newObject);

	// obtaining an iterator over the collection
	public IIterator getIterator();

	// removing an object from collection
	public void remove(GameObject object);
}
