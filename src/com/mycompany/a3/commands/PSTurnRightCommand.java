package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to turn PS's direction right.
public class PSTurnRightCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public PSTurnRightCommand(GameWorld gw) {
		super("Right Turn");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if (!gw.getBPause()) {
				gw.turnPlayerShipRight();
			}
		}
	}
}
