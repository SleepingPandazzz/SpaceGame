package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameWorldProxy;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

// Space stations are fixed objects and house an unlimited supply of missiles.
public class SpaceStation extends FixedObject implements IDrawable, ICollider {
	// each space station blinks on and off at this rate
	private int blinkRate;
	private boolean blinkLight = false; // false is off, true is on
	private Transform myTranslation, myRotation, myScale;

	public SpaceStation() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		Random r = new Random();
		setPointX(r.nextInt(Game.getMapWidth() + 1 - 80) + Game.getMapOriginX());
		setPointY(r.nextInt(Game.getMapHeight() + 1 - 80) + Game.getMapOriginY());
		setObjectColor(ColorUtil.rgb(0, 0, 255));
		setBlinkRate(r.nextInt(5));
		setObjectID();
	}

	public int getBlinkRate() {
		return this.blinkRate;
	}

	public boolean getBlinkLight() {
		return this.blinkLight;
	}

	public void setBlinkLight(boolean newBlinkLight) {
		this.blinkLight = newBlinkLight;
	}

	public void setBlinkRate(int newBlinkRate) {
		this.blinkRate = newBlinkRate;
	}

	public String toString() {
		String s = "Space Station: ";
		s += super.toString();
		s += "rate: " + getBlinkRate() + " ";
		return s;
	}

	// draws the object in its current color and size, at its current location
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); // save the original xform
		gXform.translate((float) (this.getPointX() + 40 - Game.getMapOriginX()),
				(float) (this.getPointY() + 40 - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() + 40 - Game.getMapOriginX())),
				(float) (-(this.getPointY() + 40 - Game.getMapOriginY())));
		g.setTransform(gXform);

		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		double scale = 0.5;
		g.setColor(getObjectColor());
		int r1 = (int) (80 * scale);
		int r2 = (int) (70 * 0.5);
		g.fillArc(xLoc, yLoc, r1, r1, 0, 360);
		g.setColor(ColorUtil.BLACK);
		g.fillArc(xLoc + r1 / 2 - r2 / 2, yLoc + r1 / 2 - r2 / 2, r2, r2, 0, 360);
		int r3 = (int) (20 * 0.5);
		if (getBlinkLight() == true) {
			g.setColor(ColorUtil.GREEN);
		} else {
			g.setColor(ColorUtil.MAGENTA);
		}
		g.fillArc(xLoc + r1 / 2 - r3 / 2, yLoc + r1 / 2 - r3 / 2, r3, r3, 0, 360);

		g.setTransform(gOrigXform); // restore the original xform
	}

	// apply appropriate detection algorithm
	@Override
	public boolean collidesWith(ICollider obj) {
		GameObject go = (GameObject) obj;
		// find the centers
		double thisCenterX = this.getPointX() + 30;
		double thisCenterY = this.getPointY() + 30;
		double otherCenterX = 0;
		double otherCenterY = 0;
		if (go instanceof Asteroid) {
			otherCenterX = go.getPointX() + go.getObjectSize() / 2;
			otherCenterY = go.getPointY() + go.getObjectSize() / 2;
		} else if (go instanceof NonPlayerShip) {
			otherCenterX = go.getPointX();
			otherCenterY = go.getPointY();
		} else if (go instanceof PlayerShip) {
			otherCenterX = go.getPointX();
			otherCenterY = go.getPointY() - 11.667;
		} else if (go instanceof SpaceStation) {
			otherCenterX = go.getPointX() + 40;
			otherCenterY = go.getPointY() + 40;
		} else if (go instanceof Missile) {
			otherCenterX = go.getPointX() + 5;
			otherCenterY = go.getPointY() + 10;
		}

		// find distance between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double disBetCentSqr = dx * dx + dy * dy;

		// find square of sum of radii
		double thisRadius = 40;
		double otherRadius = 0;
		if (go instanceof Asteroid) {
			otherRadius = go.getObjectSize() / 2;
		} else if (go instanceof NonPlayerShip) {
			otherRadius = this.getObjectSize() * 4;
		} else if (go instanceof PlayerShip) {
			otherRadius = 41.667;
		} else if (go instanceof SpaceStation) {
			otherRadius = 40;
		}
		double radiiSqr = thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius;

		if (disBetCentSqr <= radiiSqr) {
			return true;
		} else {
			return false;
		}
	}

	// apply appropriate response algorithm
	@Override
	public void handleCollision(ICollider otherObject) {
		((GameObject) otherObject).setGetIfCol(true);
		if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setHitCase(8);
		}
	}

}
