package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to quit the game.
public class QuitGameCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public QuitGameCommand(GameWorld gw) {
		super("Quit Game");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			System.out.println("----------------- Quit Game -----------------");
			gw.quitGame();
		}
	}
}
