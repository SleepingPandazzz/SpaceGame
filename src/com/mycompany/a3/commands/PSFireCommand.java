package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to fire PS's missile.
public class PSFireCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public PSFireCommand(GameWorld gw) {
		super("Fire Missile");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if (!gw.getBPause()) {
				gw.firePSMissile();
			}
		}
	}
}
