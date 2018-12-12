package com.mycompany.a3;

import java.util.Iterator;
import java.util.Vector;

import com.mycompany.a3.gameobjects.GameObject;
import com.mycompany.a3.interfaces.ICollection;
import com.mycompany.a3.interfaces.IIterator;

public class GameObjectCollection implements ICollection {
	private Vector<GameObject> theCollection;

	public GameObjectCollection() {
		theCollection = new Vector<GameObject>();
	}

	public Iterator<GameObject> iterator() {
		return (Iterator<GameObject>) new GameVectorIterator();
	}

	public void add(GameObject newObject) {
		theCollection.addElement(newObject);
	}

	// returns an iterator over the elements in this collection
	public IIterator getIterator() {
		return new GameVectorIterator();
	}

	// remove a specified GameObject
	public void remove(GameObject Obj) {
		theCollection.remove(Obj);
	}

	// clear all the game object
	public void clear() {
		theCollection.clear();
	}

	private class GameVectorIterator implements IIterator {
		private int currElementIndex;

		public GameVectorIterator() {
			currElementIndex = -1;
		}

		public boolean hasNext() {
			if (theCollection.size() <= 0)
				return false;
			if (currElementIndex == theCollection.size() - 1)
				return false;
			return true;
		}

		public GameObject getNext() {
			currElementIndex++;
			return (theCollection.elementAt(currElementIndex));
		}
	}
}
