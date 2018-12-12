package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

// This is the Command to decelerate Player Ship's speed.
public class DecelerateCommand extends Command {
	private GameWorld gw; // reference to a GameWorld

	public DecelerateCommand(GameWorld gw) {
		super("Decelerate");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if(!gw.getBPause()) {
				gw.decreasePlayerShipSpeed();
			}
		}
	}
}
