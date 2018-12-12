package com.mycompany.a3;

import java.util.Iterator;
import java.util.Observable;

import com.mycompany.a3.gameobjects.GameWorld;
import com.mycompany.a3.interfaces.IGameWorld;

public class GameWorldProxy extends Observable implements IGameWorld {
	// code here to accept and hold a GameWorld, provide implementations of all
	// the public methods in a GameWorld, forward allowed calls to the actual
	// GameWorld, and reject calls to methods which the outside should not be able
	// to access in the GameWorld

	private GameWorld gw; // reference to a GameWorld

	public GameWorldProxy(GameWorld gw) {
		this.gw = gw;
	}

	// Returns the player score of the GameWorld
	public int getScore() {
		return gw.getScore();
	}

	// Returns the PlayerShip's Missile Count of the GameWorld
	public int getPSMissileCount() {
		return gw.getPSMissileCount();
	}

	// Returns the PlayerShip's Life of the GameWorld
	public int getPlayerLife() {
		return gw.getPlayerLife();
	}

	// Returns the On or OFF of the sound
	public boolean getSound() {
		return gw.getSound();
	}

	// Returns the elapsed time of the GameWorld
	public int getElapsedTime() {
		return gw.getElapsedTime();
	}

	// Returns the game object of the GameWorld
	public GameObjectCollection getGameObjects() {
		return gw.getGameObjects();
	}

}
