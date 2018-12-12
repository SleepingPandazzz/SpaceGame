package com.mycompany.a3.commands;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobjects.GameWorld;

public class SoundCommand extends Command{
	private GameWorld gw; // reference to a GameWorld

	public SoundCommand(GameWorld gw) {
		super("Sound On");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			if (gw.getBPause() == false) {
				if (((CheckBox) e.getComponent()).isSelected()) {
					gw.playSound();
				} else {
					gw.setSound(false);
					gw.pauseSound();
				}
			}else {
				gw.pauseSound();
			}
		}		
	}
}
