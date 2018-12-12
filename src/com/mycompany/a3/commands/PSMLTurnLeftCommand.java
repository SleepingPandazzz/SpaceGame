package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to turn PS's Missile Launcher left
public class PSMLTurnLeftCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public PSMLTurnLeftCommand(GameWorld gw) {
		super("Left Turn MissileLauncher");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if (!gw.getBPause()) {
				gw.turnPSMissileLauncherLeft();
			}
		}
	}
}
