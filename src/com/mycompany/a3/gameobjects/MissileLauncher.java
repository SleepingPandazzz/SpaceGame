package com.mycompany.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// PS and NPS own Missile Launcher. 
public class MissileLauncher extends MoveableObject {
	private int mlSpeed; // the speed of a Missile Launcher
	private int mlDirection; // the direction of a Missile Launcher

	public MissileLauncher() {
		setObjectColor(ColorUtil.rgb(35, 90, 0));
	}

	public int getMLSpeed() {
		return this.mlSpeed;
	}

	public int getMLDirection() {
		return this.mlDirection;
	}

	public void setMLSpeed(int newMLSpeed) {
		this.mlSpeed = newMLSpeed;
	}

	public void setMLDirection(int newMLDirection) {
		this.mlDirection = newMLDirection;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		
	}
}