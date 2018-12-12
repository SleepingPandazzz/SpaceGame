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
				(float) (this.getPointY() +15 - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() - Game.getMapOriginX())),
				(float) (-(this.getPointY() +15- Game.getMapOriginY())));

		// System.out.println("pCmpRelScrnX: "+pCmpRelScrn.getX());
		// System.out.println("pCmpRelScrnY: "+ pCmpRelScrn.getY());
		// System.out.println("ps x: "+this.getPointX());
		// System.out.println("ps y: "+this.getPointY());
		// float x=(float)(this.getPointX()-Game.getMapOriginX());
		// float y=(float)(this.getPointY()-Game.getMapOriginY());
		// System.out.println("x: "+x);
		// System.out.println("y: "+y);

		g.setTransform(gXform);
		// draw PS
		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		// PS's energy board
		int r1 = 60;
		g.setColor(ColorUtil.rgb(54, 54, 54));
		g.fillRect(xLoc - r1 / 2, yLoc - r1 / 2, r1, r1);
		int r2 = 5;
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
		int r3 = 70;
		int width = 20;
		int height = 30;
		g.setColor(ColorUtil.WHITE);
		g.fillTriangle(xLoc - r1 / 2 - r2 / 2, yLoc - r1 / 2 + r1 + r2, xLoc - r1 / 2 - r2 / 2,
				yLoc - r1 / 2 - r2 / 2 - r3, xLoc - r1 / 2 - r2 / 2 - width, yLoc - r1 / 2 - r2 / 2 + height);
		g.fillTriangle(xLoc + r1 / 2 + r2 / 2, yLoc - r1 / 2 + r1 + r2, xLoc + r1 / 2 + r2 / 2,
				yLoc - r1 / 2 - r2 / 2 - r3, xLoc + r1 / 2 + r2 / 2 + width, yLoc - r1 / 2 - r2 / 2 + height);
		// Loading missile place
		g.setColor(ColorUtil.LTGRAY);
		int lWidth = 15;
		int lHeight = 10;
		g.drawRect(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2, lWidth, lHeight);
		g.fillRect(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2, lWidth, lHeight);
		// Loading missile top
		g.setColor(ColorUtil.MAGENTA);
		int r4 = 10;
		g.fillTriangle(xLoc - lWidth / 2, yLoc - r1 / 2 + r1 + r2 + lHeight, xLoc + lWidth / 2,
				yLoc - r1 / 2 + r1 + r2 + lHeight, xLoc, yLoc - r1 / 2 + r1 + r2 + lHeight + r4);
		// // missile launcher
		int length = 40;
		// int mx = (int) (length * Math.cos(Math.toRadians(sml.getSMLDirection())));
		// int my = (int) (length * Math.sin(Math.toRadians(sml.getSMLDirection())));
		int mx = (int) (length * Math.cos(Math.toRadians(this.getMLAngle())));
		int my = (int) (length * Math.sin(Math.toRadians(this.getMLAngle())));
		g.drawLine(xLoc, yLoc - r1 / 2 + r1 + r2 + lHeight + r4, xLoc + mx,
				yLoc - r1 / 2 + r1 + r2 + lHeight + r4 + my);

		// // draw PS
		// int xLoc = (int) (pCmpRelPrnt.getX());
		// int yLoc = (int) (pCmpRelPrnt.getY());
		// // PS's main body
		// g.setColor(getObjectColor());
		// g.fillRect(xLoc-11, yLoc-25, 22, 50);
		// g.setColor(ColorUtil.WHITE);
		// g.drawRect(xLoc-11, yLoc-25, 22, 50);
		// // PS's top
		// g.fillTriangle(xLoc-11, yLoc+25, xLoc+11, yLoc+25, xLoc, yLoc+45);
		// // PS's two sides
		// g.setColor(ColorUtil.rgb(148, 96, 255));
		// g.fillTriangle(xLoc-44, yLoc-25, xLoc-11, yLoc-25, xLoc-11, yLoc-5);
		// g.fillTriangle(xLoc+11, yLoc-25, xLoc+44, yLoc-25, xLoc+11, yLoc-5);
		// // PS's two engines
		// g.drawRect(xLoc - 26, yLoc - 35, 10, 10);
		// g.drawRect(xLoc + 16, yLoc - 35, 10, 10);
		// // PS's two pushing gas
		// g.setColor(ColorUtil.YELLOW);
		// g.fillArc(xLoc - 26, yLoc - 60, 10, 10, 0, 360);
		// g.fillArc(xLoc + 26, yLoc - 60, 10, 10, 0, 360);
		// // draw PS's missile launcher
		// int r = 150;
		// g.setColor(ColorUtil.LTGRAY);
		// g.drawArc(xLoc - r / 2, yLoc - r / 2, r, r, 0, 360);
		//
		// g.setColor(ColorUtil.MAGENTA);
		// int xLoc2 = (int) (xLoc + 0.5 * r *
		// Math.cos(Math.toRadians(sml.getSMLDirection())));
		// int yLoc2 = (int) (yLoc + 0.5 * r *
		// Math.sin(Math.toRadians(sml.getSMLDirection())));
		// g.fillArc(xLoc2, yLoc2, 8, 8, 0, 360);

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
			Missile m=(Missile)go;
			if(m.getMissileFlag()==0) {
				otherRadius=21;
			}else {
				otherRadius=10;
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
