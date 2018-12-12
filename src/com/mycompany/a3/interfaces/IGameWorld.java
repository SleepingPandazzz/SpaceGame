package com.mycompany.a3.interfaces;

import com.mycompany.a3.GameObjectCollection;

public interface IGameWorld {
	// specifications here for all GameWorld methods

	// Returns the player score of the GameWorld
	int getScore();

	// Returns the player life of the GameWorld
	int getPlayerLife();

	// Returns the PlayerShip's Missile Count of the GameWorld
	int getPSMissileCount();

	// Returns the On or Off of sound
	boolean getSound();

	// Returns the elapsed time of the GameWorld
	int getElapsedTime();

	// Returns the game objects of the GameWorld
	GameObjectCollection getGameObjects();
}
