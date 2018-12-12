package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

public class PausePlayCommand extends Command{
	private GameWorld gw; // reference to a GameWorld
	
	public PausePlayCommand(GameWorld gw) {
		super("Pause/Play");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.pausePlayGame();
		}		
	}
}
