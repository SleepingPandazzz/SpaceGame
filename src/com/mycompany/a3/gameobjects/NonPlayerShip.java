package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

// NPS(s) are movable objects that fly through space at a fixed speed and direction.
public class NonPlayerShip extends MoveableObject implements IDrawable, ICollider {
	// private int nonPlayerShipSize; // define NPS's size, small 10 or large 20
	private int npsMissileCount; // NPS can have a maximum of 2 missiles
	private Transform myTranslation, myRotation, myScale;

	// NonPlayerShip owns a Missile Launcher
	private MissileLauncher ml = new MissileLauncher();

	public NonPlayerShip() {
		Random r = new Random();
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		setObjectSize((r.nextInt(2) + 1) * 2);
		setObjectSpeed(r.nextInt(10) + 1);
		setObjectDirection(r.nextInt(360));
		setNPSMissileCount(2);
		setPointX(r.nextInt((Game.getMapWidth()-this.getObjectSize()*12))+Game.getMapOriginX()+this.getObjectSize()*4);
		setPointY(r.nextInt((Game.getMapHeight()-this.getObjectSize()*12))+Game.getMapOriginY()+this.getObjectSize()*4);
		setObjectColor(ColorUtil.rgb(255, 0, 0));
		ml.setMLDirection(getObjectDirection());
		ml.setMLSpeed(getObjectSpeed());
		// rotate(getObjectDirection()-90);
	}

	public int getNPSMissileCount() {
		return this.npsMissileCount;
	}

	public void setNPSMissileCount(int newNPSMissileCount) {
		this.npsMissileCount = newNPSMissileCount;
	}

	public String toString() {
		String s = "Non-Player Ship: ";
		s += super.toString();
		s += "size=" + getObjectSize() + " ";
		return s;
	}

	public void rotate(float degrees) {
		myRotation.rotate((float) Math.toRadians(degrees), (float) (this.getPointX()) - Game.getMapOriginX(),
				(float) (this.getPointY() - Game.getMapOriginY()));
	}

	public void translate(float tx, float ty) {
		myTranslation.translate(tx, ty);
	}

	public void scale(float sx, float sy) {
		myScale.scale(sx, sy);
	}

	// draws the object in its current color and size, at its current location
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); // save the original xform
		gXform.translate((float) ((this.getPointX() - Game.getMapOriginX())),
				(float) ((this.getPointY() - Game.getMapOriginY())));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() - Game.getMapOriginX())),
				(float) (-(this.getPointY() - Game.getMapOriginY())));
		g.setTransform(gXform);

		// draw NonPlayerShip
		double xLoc = pCmpRelPrnt.getX();
		double yLoc = pCmpRelPrnt.getY();
		// nps's outside circle
		int r1 = getObjectSize() * 8;
		g.setColor(this.getObjectColor());
		g.fillArc((int) xLoc - r1 / 2, (int) yLoc - r1 / 2, r1, r1, 0, 360);
		// nps's inside circle
		g.setColor(ColorUtil.GRAY);
		int r2 = getObjectSize() * 5;
		g.fillArc((int) xLoc - r2 / 2, (int) (yLoc - r2 / 2), r2, r2, 0, 360);
		g.setColor(ColorUtil.LTGRAY);
		int r3 = getObjectSize() * 4;
		g.fillArc((int) xLoc - r3 / 2, (int) (yLoc - r3 / 2), r3, r3, 0, 360);
		g.setColor(ColorUtil.WHITE);
		int r4 = getObjectSize() * 3;
		g.fillArc((int) xLoc - r4 / 2, (int) (yLoc - r4 / 2), r4, r4, 0, 360);
		// int xLoc = (int) (pCmpRelPrnt.getX());
		// int yLoc = (int) (pCmpRelPrnt.getY());
		// // NPS's main body
		// g.setColor(getObjectColor());
		// g.fillTriangle(xLoc, yLoc, xLoc - 3 * getObjectSize(), yLoc - 3 *
		// getObjectSize(), xLoc + 3 * getObjectSize(),
		// yLoc - 3 * getObjectSize());
		// // NPS's engine
		// int tmpW = (int) (0.4 * getObjectSize());
		// int tmpH = (int) (0.6 * getObjectSize());
		// g.fillRect(xLoc - tmpW / 2, yLoc - 3 * getObjectSize() - tmpH, tmpW, tmpH);
		// g.fillRect((int) (xLoc + 1.5 * getObjectSize() - tmpW / 2), yLoc - 3 *
		// getObjectSize() - tmpH, tmpW, tmpH);
		// g.fillRect((int) (xLoc - 1.5 * getObjectSize() - tmpW / 2), yLoc - 3 *
		// getObjectSize() - tmpH, tmpW, tmpH);
		// // NPS's pushing air
		// int tmpR = (int) (0.4 * getObjectSize());
		// g.setColor(ColorUtil.YELLOW);
		// g.fillArc(xLoc - tmpW / 2, yLoc - 3 * getObjectSize() - tmpH - tmpR * 2,
		// tmpR, tmpR, 0, 360);
		// g.fillArc((int) (xLoc + 1.5 * getObjectSize() - tmpW / 2), yLoc - 3 *
		// getObjectSize() - tmpH - 2 * tmpR, tmpR,
		// tmpR, 0, 360);
		// g.fillArc((int) (xLoc - 1.5 * getObjectSize() - tmpW / 2), yLoc - 3 *
		// getObjectSize() - tmpH - 2 * tmpR, tmpR,
		// tmpR, 0, 360);

		 g.setTransform(gOrigXform);// restore the original xform
	}

	// apply appropriate detection algorithm
	@Override
	public boolean collidesWith(ICollider obj) {
		GameObject go = (GameObject) obj;
		// find the centers
		double thisCenterX = this.getPointX();
		double thisCenterY = this.getPointY();

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
		} else if (go instanceof Missile) {
			otherCenterX = go.getPointX();
			otherCenterY = go.getPointY();
		}else if(go instanceof Curve) {
			otherCenterX=((Curve) go).getCurveCenterX();
			otherCenterY=((Curve) go).getCurveCenterY();
		}

		// find distance between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double disBetCentSqr = dx * dx + dy * dy;

		// find square of sum of radii
		double thisRadius = getObjectSize() * 4;
		double otherRadius = 0;
		if (go instanceof Asteroid) {
			otherRadius = go.getObjectSize() / 2;
		} else if (go instanceof NonPlayerShip) {
			otherRadius = go.getObjectSize() * 4;
		} else if (go instanceof PlayerShip) {
			otherRadius = 41.667;
		} else if (go instanceof SpaceStation) {
			otherRadius = 30;
		} else if (go instanceof Missile) {
			Missile m=(Missile)go;
			if(m.getMissileFlag()==0) {
				otherRadius=21;
			}else {
				otherRadius=10;
			}
		}else if(go instanceof Curve) {
			otherRadius=((Curve) go).getCurveRadius();
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
			((Asteroid) otherObject).setHitCase(2);
		} else if (otherObject instanceof Missile) {
			if (((Missile) otherObject).getMissileFlag() == 0) {
				((Missile) otherObject).setHitCase(6);
			}
		} else if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setHitCase(7);
		} else if(otherObject instanceof Curve) {
			((Curve) otherObject).setHitCase(9);
		}
	}
}
