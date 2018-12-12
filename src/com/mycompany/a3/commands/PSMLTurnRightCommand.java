package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to turn PS's Missile Launcher Right.
public class PSMLTurnRightCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public PSMLTurnRightCommand(GameWorld gw) {
		super("Right Turn MissileLauncher");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if (!gw.getBPause()) {
				gw.turnPSMissileLauncherRight();
			}
		}
	}
}
