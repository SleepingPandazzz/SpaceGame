package com.mycompany.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.ISteerable;

// Player Ship is steerable objects whose speed and direction can be controlled by a player. 
// There's only one player ship in the game at any one time, and its initial location should 
// be the center of the world, with a speed of zero and a heading of zero.
public class PlayerShip extends MoveableObject implements ISteerable, IDrawable, ICollider {
	private int missileCount; // Player Ship has a maximum of 10 missiles
	// Player Ship owns a steerable Missile Launcher
	private SteerableMissileLauncher sml = new SteerableMissileLauncher();
	private Transform myTranslation, myRotation, myScale;
	private int mlangle;
	private double rX, rY;

	public PlayerShip() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		setPointX(Game.getMapOriginX() + Game.getMapWidth() / 2);
		setPointY(Game.getMapOriginY() + Game.getMapHeight() / 2);
		setObjectSpeed(0);
		setObjectDirection(90);
		setMissileCount(10);
		setObjectColor(ColorUtil.rgb(140, 96, 224));
		setMLAngle(getPSSMLDirection());
		this.setRX(this.getPointX());
		this.setRY(this.getPointY() + 55);
	}

	public int getMLAngle() {
		return this.mlangle;
	}

	public int getMissileCount() {
		return this.missileCount;
	}

	public int getPSSMLDirection() {
		return this.sml.getSMLDirection();
	}

	public double getPSSMLxPosX1() {
		return this.sml.getSMLpX1();
	}

	public double getPSSMLyPosY1() {
		return this.sml.getSMLpY1();
	}

	public double getPSSMLxPosX2() {
		return this.sml.getSMLpX2();
	}

	public double getPSSMLyPosY2() {
		return this.sml.getSMLpY2();
	}

	public double getRX() {
		return this.rX;
	}

	public double getRY() {
		return this.rY;
	}

	public void setRX(double newRX) {
		this.rX = newRX;
	}

	public void setRY(double newRY) {
		this.rY = newRY;
	}

	public void setMLAngle(int newAngle) {
		this.mlangle = newAngle;
	}

	public void setPSSMLDirection(int newDirection) {
		sml.setSMLDirection(newDirection);
	}

	public void setMissileCount(int newMissileCount) {
		this.missileCount = newMissileCount;
	}

	public void setPSSMLxPosX1(double newX) {
		this.sml.setSMLpX1(newX);
	}

	public void setPSSMLyPosY1(double newY) {
		this.sml.setSMLpY1(newY);
	}

	public void setPSSMLxPosX2(double newX) {
		this.sml.setSMLpX2(newX);
	}

	public void setPSSMLyPosY2(double newY) {
		this.sml.setSMLpY2(newY);
	}

	public void callSMLDraw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		this.sml.draw(g, pCmpRelPrnt, pCmpRelScrn);
	}

	// rotation origin is
	public void rotate(double degrees) {
		// myRotation.rotate((float) Math.toRadians(degrees),
		// (float)(this.getPointX()-Game.getMapOriginX()),
		// (float)(this.getPointY()-Game.getMapOriginY()));
		myRotation.rotate((float) Math.toRadians(degrees), (float) (Game.getMapOriginX()),
				(float) (Game.getMapOriginY()));
	}

	public void scale(double sx, double sy) {
		myScale.scale((float) sx, (float) sy);
	}

	public void translate(double tx, double ty) {
		myTranslation.translate((float) tx, (float) ty);
	}

	// draws the object in its current color and size, at its current location
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		// append the playership's local transformation to the gxForm in the Graphics
		// object
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); // save the original xform
		gXform.translate((float) (this.getPointX() - Game.getMapOriginX()),
				(float) (this.getPointY() + 15 - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() - Game.getMapOriginX())),
				(float) (-(this.getPointY() + 15 - Game.getMapOriginY())));
		g.setTransform(gXform);
		// draw PS
		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		double scale = 0.7;
		// PS's energy board
		int r1 = (int) (60 * scale);
		g.setColor(ColorUtil.rgb(54, 54, 54));
		g.fillRect(xLoc - r1 / 2, yLoc - r1 / 2, r1, r1);
		int r2 = (int) (5 * scale);
		int gap = r1 / 5;
		g.setColor(ColorUtil.GRAY);
		// vertical rectangle
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2, r2, r1);
		g.fillRect(xLoc - r1 / 2 + gap - r2 / 2, yLoc - r1 / 2, r2, r1);
		g.fillRect(xLoc - r1 / 2 + gap * 2 - r2 / 2, yLoc - r1 / 2, r2, r1);
		g.fillRect(xLoc - r1 / 2 + gap * 3 - r2 / 2, yLoc - r1 / 2, r2, r1);
		g.fillRect(xLoc - r1 / 2 + gap * 4 - r2 / 2, yLoc - r1 / 2, r2, r1);
		g.fillRect(xLoc - r1 / 2 + gap * 5 - r2 / 2, yLoc - r1 / 2, r2, r1);
		// horizontal rectangle
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 - r2 / 2, r1 + r2, r2);
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + gap - r2 / 2, r1 + r2, r2);
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + gap * 2 - r2 / 2, r1 + r2, r2);
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + gap * 3 - r2 / 2, r1 + r2, r2);
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + gap * 4 - r2 / 2, r1 + r2, r2);
		g.fillRect(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + gap * 5 - r2 / 2, r1 + r2, r2);
		// two side wing
		int r3 = (int) (70 * scale);
		int width = (int) (20 * scale);
		int height = (int) (30 * scale);
		g.setColor(ColorUtil.WHITE);
		g.fillTriangle(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + r1 + r2, xLoc - r1 / 2 - r2 / 2,
				yLoc - r1 / 2 - r2 / 2 - r3, xLoc - r1 / 2 - r2 / 2 - width, yLoc - r1 / 2 - r2 / 2 + height);
		g.fillTriangle(xLoc + r1 / 2 + r2 / 2, yLoc - r1 / 2 + r1 + r2, xLoc + r1 / 2 + r2 / 2,
				yLoc - r1 / 2 - r2 / 2 - r3, xLoc + r1 / 2 + r2 / 2 + width, yLoc - r1 / 2 - r2 / 2 + height);
		// Loading missile place
		g.setColor(ColorUtil.LTGRAY);
		int lWidth = (int) (15 * scale);
		int lHeight = (int) (10 * scale);
		g.drawRect(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2, lWidth, lHeight);
		g.fillRect(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2, lWidth, lHeight);
		// Loading missile top
		g.setColor(ColorUtil.MAGENTA);
		int r4 = (int) (10 * scale);
		g.fillTriangle(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2 + lHeight, xLoc + lWidth / 2,
				yLoc - r1 / 2 + r1 + r2 + lHeight, xLoc, yLoc - r1 / 2 + r1 + r2 + lHeight + r4);
		// missile launcher
		int length = (int) (40 * scale);
		// int mx = (int) (length * Math.cos(Math.toRadians(sml.getSMLDirection())));
		// int my = (int) (length * Math.sin(Math.toRadians(sml.getSMLDirection())));
		int mx = (int) (length * Math.cos(Math.toRadians(this.getMLAngle())));
		int my = (int) (length * Math.sin(Math.toRadians(this.getMLAngle())));
		g.drawLine(xLoc, yLoc - r1 / 2 + r1 + r2 + lHeight + r4, xLoc + mx,
				yLoc - r1 / 2 + r1 + r2 + lHeight + r4 + my);

		g.setTransform(gOrigXform); // restore the original xform
	}

	// apply appropriate detection algorithm
	@Override
	public boolean collidesWith(ICollider obj) {
		GameObject go = (GameObject) obj;
		// find the centers
		double thisCenterX = this.getPointX();
		double thisCenterY = this.getPointY() - 11.667;
		// double thisCenterY = this.getPointY();
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
			otherCenterX = go.getPointX();
			otherCenterY = go.getPointY();
		}

		// find distance between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double disBetCentSqr = dx * dx + dy * dy;

		// find square of sum of radii
		double thisRadius = 48.75;
		double otherRadius = 0;
		if (go instanceof Asteroid) {
			otherRadius = go.getObjectSize() / 2;
		} else if (go instanceof NonPlayerShip) {
			otherRadius = go.getObjectSize() * 4;
		} else if (go instanceof PlayerShip) {
			otherRadius = 41.667;
		} else if (go instanceof SpaceStation) {
			otherRadius = 40;
		} else if (go instanceof Missile) {
			Missile m = (Missile) go;
			if (m.getMissileFlag() == 0) {
				otherRadius = 21;
			} else {
				otherRadius = 10;
			}
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
		if (otherObject instanceof Asteroid) {
			((Asteroid) otherObject).setHitCase(3);
		} else if (otherObject instanceof Missile) {
			if (((Missile) otherObject).getMissileFlag() == 1) {
				((Missile) otherObject).setHitCase(5);
			}
		} else if (otherObject instanceof NonPlayerShip) {
			((NonPlayerShip) otherObject).setHitCase(7);
		} else if (otherObject instanceof SpaceStation) {
			((SpaceStation) otherObject).setHitCase(8);
		}
	}
}
