package com.mycompany.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.ISelectable;

// Missiles are movable objects that are fired from ship's MissileLauncher
public class Missile extends MoveableObject implements IDrawable, ICollider, ISelectable {
	// A missile's fuel level gets decremented as time passes, and when it reaches
	// zero the missile is removed from the game. All missiles have an initial fuel
	// level of 10.
	private int missileFuel;
	private int missileFlag; // 0: PS, 1: NPS
	private boolean ifSelected; // check if the Missile is selected, true: selected, false: unselected
	private Transform myTranslation, myRotation, myScale;
	private int missileBlink = 2;
	private boolean blinkLight = false;

	public Missile() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		setMissileFuel(10);
		setObjectColor(ColorUtil.rgb(255, 0, 255));
	}

	public int getMissileFuel() {
		return this.missileFuel;
	}

	public int getMissileFlag() {
		return this.missileFlag;
	}

	public int getMissileBlink() {
		return this.missileBlink;
	}

	public boolean getBlinkLight() {
		return this.blinkLight;
	}

	public void setMissileFuel(int newMissileFuel) {
		this.missileFuel = newMissileFuel;
	}

	public void setMissileFlag(int newMissileFlag) {
		this.missileFlag = newMissileFlag;
	}

	public void setMissileBlink(int newBlink) {
		this.missileBlink = newBlink;
	}

	public void setBlinkLight(boolean newBlink) {
		this.blinkLight = newBlink;
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
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); // save the original xform
		gXform.translate((float) (this.getPointX() - Game.getMapOriginX()),
				(float) (this.getPointY() - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() - Game.getMapOriginX())),
				(float) (-(this.getPointY() - Game.getMapOriginY())));

		g.setTransform(gXform);
		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		double scale = 0.5;
		g.setColor(getObjectColor());
		if (getMissileFlag() == 0) {
			if (this.isSelected()) {
				g.setColor(ColorUtil.CYAN);
				// g.fillRect(xLoc + 11, yLoc + 50, 10, 20);
			} else {
				g.setColor(getObjectColor());
				// g.fillRect(xLoc + 11, yLoc + 50, 10, 20);
			}
			// PS's missile's main body
			int r1 = (int) (16 * scale);
			g.fillArc(xLoc - r1 / 2, yLoc - r1 / 2, r1, r1, 0, 360);
			// PS's missile's four engine
			int r2 = (int) (4 * scale);
			int width = (int) (8 * scale);
			g.fillRect(xLoc + r1 / 2, yLoc - r2 / 2, width, r2);
			g.drawRect(xLoc + r1 / 2, yLoc - r2 / 2, width, r2);
			g.fillRect(xLoc - r2 / 2, yLoc - r1 / 2 - width, r2, width);
			g.drawRect(xLoc - r2 / 2, yLoc - r1 / 2 - width, r2, width);
			g.fillRect(xLoc - r2 / 2, yLoc + r1 / 2, r2, width);
			g.drawRect(xLoc - r2 / 2, yLoc + r1 / 2, r2, width);
			g.fillRect(xLoc - r1 / 2 - width, yLoc - r2 / 2, width, r2);
			g.drawRect(xLoc - r1 / 2 - width, yLoc - r2 / 2, width, r2);
			// PS's missile's exhause
			int r3 = (int) (10 * scale);
			if (this.getBlinkLight()) {
				g.setColor(ColorUtil.rgb(0, 255, 0));
			} else {
				g.setColor(ColorUtil.rgb(50, 205, 50));
			}
			g.fillArc(xLoc + r1 / 2 + width, yLoc - r3 / 2, r3, r3, 0, 360);
			g.fillArc(xLoc - r3 / 2, yLoc - r1 / 2 - width - r3, r3, r3, 0, 360);
			g.fillArc(xLoc - r1 / 2 - width - r3, yLoc - r3 / 2, r3, r3, 0, 360);
			g.fillArc(xLoc - r3 / 2, yLoc + r1 / 2 + width, r3, r3, 0, 360);

		} else {
			g.setColor(ColorUtil.rgb(0, 178, 238));
			int r1 = (int) (20 * scale);
			g.fillArc(xLoc - r1 / 2, yLoc - r1 / 2, r1, r1, 0, 360);
			g.setColor(ColorUtil.rgb(96, 123, 139));
			int r2 = (int) (10 * scale);
			g.fillArc(xLoc - r2 / 2, yLoc - r2 / 2, r2, r2, 0, 360);
			int r3 = (int) (8 * scale);
			g.fillArc(xLoc - r3 / 2, yLoc - r3 / 2, r3, r3, 0, 360);
		}
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
		} else if (go instanceof Curve) {
			otherCenterX = ((Curve) go).getCurveCenterX();
			otherCenterY = ((Curve) go).getCurveCenterY();
		}

		// find distance between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double disBetCentSqr = dx * dx + dy * dy;

		// find square of sum of radii
		// double thisRadius = this.getRadius();
		// double otherRadius = go.getRadius();
		double thisRadius = 21;
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
			Missile m = (Missile) go;
			if (m.getMissileFlag() == 0) {
				otherRadius = 21;
			} else {
				otherRadius = 10;
			}
		} else if (go instanceof Curve) {
			otherRadius = ((Curve) go).getCurveRadius();
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
			if (this.getMissileFlag() == 0) {
				((Asteroid) otherObject).setHitCase(4);
			}
		} else if (otherObject instanceof PlayerShip) {
			if (this.getMissileFlag() == 1) {
				((PlayerShip) otherObject).setHitCase(5);
			}
		} else if (otherObject instanceof NonPlayerShip) {
			if (this.getMissileFlag() == 0) {
				((NonPlayerShip) otherObject).setHitCase(6);
			}
		}
	}

	@Override
	public void setSelected(boolean yesNo) {
		this.ifSelected = yesNo;
	}

	@Override
	public boolean isSelected() {
		if (this.ifSelected) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(float[] fPtr) {
		// concatenate all LTs
		Transform concatLTs = Transform.makeIdentity();
		concatLTs.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		concatLTs.concatenate(myRotation);
		concatLTs.scale(myScale.getScaleX(), myScale.getScaleY());
		// calculate inverse of concatLTs
		Transform inverseConcatLTs = Transform.makeIdentity();
		try {
			concatLTs.getInverse(inverseConcatLTs);
		} catch (NotInvertibleException e) {
			System.out.println("Non-invertible xform!");
		}
		inverseConcatLTs.transformPoint(fPtr, fPtr);
		float[] fPtrCopy = new float[] { fPtr[0], fPtr[1] };
		int xLoc = (int) (this.getPointX() - Game.getMapOriginX());
		int yLoc = (int) (this.getPointY() - Game.getMapOriginY());
		System.out.println("xLoc: " + xLoc);
		System.out.println("yLoc: " + yLoc);
		System.out.println("pointer x: " + fPtrCopy[0]);
		System.out.println("pointer y: " + fPtrCopy[1]);
		if ((fPtrCopy[0] >= xLoc - 21) && (fPtrCopy[0] <= xLoc + 21) && (fPtrCopy[1] >= yLoc - 21)
				&& (fPtrCopy[1] <= yLoc + 21)) {
			return true;
		} else {
			return false;
		}
	}
	// public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
	// // pointer location relative to parent's origin
	// int px = pPtrRelPrnt.getX();
	// int py = Game.getMapHeight()+Game.getMapOriginY()-pPtrRelPrnt.getY();
	// // shape location relative to parent's origin
	// int xLoc = (int) (pCmpRelPrnt.getX());
	// int yLoc = (int) (pCmpRelPrnt.getY());
	//// System.out.println("px: "+px+", py: "+py);
	//// System.out.println("xLoc: "+xLoc+", yLoc: "+yLoc);
	// if ((px >= xLoc - 21) && (px <= xLoc + 21) && (py >= yLoc - 21) && (py <=
	// yLoc + 21)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

}
