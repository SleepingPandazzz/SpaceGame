package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.ISelectable;

// Asteroids are movable objects that tumble through space at a fixed speed, direction, and attribute size.
public class Asteroid extends MoveableObject implements IDrawable, ICollider, ISelectable {
	// private int asteroidSize; // a fixed attribute size ranging from 6-30
	private boolean ifSelected; // check if the Missile is selected, true: selected, false: unselected
	// private int asteroidSize; // a fixed attribute size ranging from 6-30
	private Transform myTranslation, myRotation, myScale;

	public Asteroid() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		Random r = new Random();
		setObjectSize((r.nextInt(25) + 6) * 5);
		setObjectSpeed(r.nextInt(10) + 1);
		setObjectDirection(r.nextInt(360));
		// setPointX(r.nextInt(Game.getMapWidth() - this.getObjectSize()*2) +
		// Game.getMapOriginX());
		// setPointY(r.nextInt(Game.getMapHeight() - this.getObjectSize()*2) +
		// Game.getMapOriginY());
		setPointX(r.nextInt((Game.getMapWidth() - this.getObjectSize()*3)) + Game.getMapOriginX()+this.getObjectSize());
		setPointY(r.nextInt((Game.getMapHeight() - this.getObjectSize()*3)) + Game.getMapOriginY()+this.getObjectSize());
		setObjectColor(ColorUtil.rgb(265, 50, 113));
		setRadius(getObjectSize() / 2);
		setCenterX(this.getPointX() + getObjectSize() / 2);
		setCenterY(this.getPointY() + getObjectSize() / 2);
	}

	public void rotate(double degrees) {
		myRotation.rotate((float) Math.toRadians(degrees), (float) (this.getPointX() + this.getObjectSize() / 2),
				(float) (this.getPointY() + this.getObjectSize() / 2));
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
		gXform.translate((float) (this.getPointX() + getObjectSize() / 2 - Game.getMapOriginX()),
				(float) (this.getPointY() + getObjectSize() / 2 - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getPointX() + getObjectSize() / 2 - Game.getMapOriginX())),
				(float) (-(this.getPointY() + getObjectSize() / 2 - Game.getMapOriginY())));
		g.setTransform(gXform);
		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		int tmpR = 20;
		g.setColor(ColorUtil.YELLOW);
		// top center circle
		g.fillArc(xLoc + getObjectSize() / 2 - tmpR / 2, yLoc - tmpR / 2, tmpR, tmpR, 0, 360);
		// right center circle
		g.fillArc(xLoc + getObjectSize() - tmpR / 2, yLoc + getObjectSize() / 2 - tmpR / 2, tmpR, tmpR, 0, 360);
		// bottom center circle
		g.fillArc(xLoc + getObjectSize() / 2 - tmpR / 2, yLoc + getObjectSize() - tmpR / 2, tmpR, tmpR, 0, 360);
		// left center circle
		g.fillArc(xLoc - tmpR / 2, yLoc + getObjectSize() / 2 - tmpR / 2, tmpR, tmpR, 0, 360);
		// 45 degree circle
		g.fillArc((int) (xLoc + 0.8536 * getObjectSize() - 0.5 * tmpR),
				(int) (yLoc + 0.1464 * getObjectSize() - 0.5 * tmpR), tmpR, tmpR, 0, 360);
		// 135 degree circle
		g.fillArc((int) (xLoc + 0.1464 * getObjectSize() - 0.5 * tmpR),
				(int) (yLoc + 0.1464 * getObjectSize() - 0.5 * tmpR), tmpR, tmpR, 0, 360);
		// 225 degree circle
		g.fillArc((int) (xLoc + 0.1464 * getObjectSize() - 0.5 * tmpR),
				(int) (yLoc + 0.854 * getObjectSize() - 0.5 * tmpR), tmpR, tmpR, 0, 360);
		// 315 degree circle
		g.fillArc((int) (xLoc + 0.854 * getObjectSize() - 0.5 * tmpR),
				(int) (yLoc + 0.854 * getObjectSize() - 0.5 * tmpR), tmpR, tmpR, 0, 360);
		g.setTransform(gOrigXform);// restore the original xform
		if (this.isSelected()) {
			g.setColor(ColorUtil.GREEN);

		} else {
			g.setColor(ColorUtil.rgb(265, 50, 113));
		}
		g.fillArc(xLoc, yLoc, getObjectSize(), getObjectSize(), 0, 360);
		// eyes
		g.setColor(ColorUtil.BLACK);
		double r1 = getObjectSize() * 0.3;
		g.fillArc((int) (xLoc + getObjectSize() / 3 - r1 / 2), (int) (yLoc + getObjectSize() / 2 - r1), (int) r1,
				(int) r1, 0, 360);
		g.fillArc((int) (xLoc + getObjectSize() * 2 / 3 - r1 / 2), (int) (yLoc + getObjectSize() / 2 - r1), (int) r1,
				(int) r1, 0, 360);
		g.setColor(ColorUtil.WHITE);
		double r = getObjectSize() * 0.1;
		g.fillArc((int) (xLoc + getObjectSize() / 3 - r / 2), (int) (yLoc + getObjectSize() / 2 - r), (int) r, (int) r,
				0, 360);
		g.fillArc((int) (xLoc + getObjectSize() * 2 / 3 - r / 2), (int) (yLoc + getObjectSize() / 2 - r), (int) r,
				(int) r, 0, 360);
	}

	// apply appropriate detection algorithm
	@Override
	public boolean collidesWith(ICollider obj) {
		GameObject go = (GameObject) obj;

		// find the centers
		double thisCenterX = this.getPointX() + getObjectSize() / 2;
		double thisCenterY = this.getPointY() + getObjectSize() / 2;
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
		} else if(go instanceof Curve) {
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
		double thisRadius = this.getObjectSize() / 2;
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
		} else if(go instanceof Curve) {
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
			((Asteroid) otherObject).setHitCase(1);
		} else if (otherObject instanceof NonPlayerShip) {
			((NonPlayerShip) otherObject).setHitCase(2);
		} else if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setHitCase(3);
		} else if (otherObject instanceof Missile) {
			if (((Missile) otherObject).getMissileFlag() == 0) {
				((Missile) otherObject).setHitCase(4);
			}
		} else if(otherObject instanceof Curve) {
			((Curve) otherObject).setHitCase(9);
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

	public boolean contains(float[] fPtr) {
		// concatenate all LTs
		Transform concatLTs=Transform.makeIdentity();
		concatLTs.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		concatLTs.concatenate(myRotation);
		concatLTs.scale(myScale.getScaleX(), myScale.getScaleY());
		//calculate inverse of concatLTs
		Transform inverseConcatLTs=Transform.makeIdentity();
		try {
			concatLTs.getInverse(inverseConcatLTs);
		}catch(NotInvertibleException e) {
			System.out.println("Non-invertible xform!");
		}
		inverseConcatLTs.transformPoint(fPtr,fPtr);
		float[] fPtrCopy=new float[] {
				fPtr[0],fPtr[1]
		};
		int xLoc=(int) (this.getPointX()+getObjectSize()/2-Game.getMapOriginX());
		int yLoc=(int) (this.getPointY()+getObjectSize()/2-Game.getMapOriginY());
		if((fPtrCopy[0]>=xLoc-getObjectSize()/2)&&(fPtrCopy[0]<=xLoc+getObjectSize()/2)&&(fPtrCopy[1]>=yLoc-getObjectSize()/2)&&(fPtrCopy[1]<=yLoc+getObjectSize()/2)) {
			return true;
		}else {
			return false;
		}
	}	
//		public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {	
//		// pointer location relative to parent's origin
//		int px = pPtrRelPrnt.getX();
//		int py = Game.getMapHeight() + Game.getMapOriginY() - pPtrRelPrnt.getY();
//		System.out.println("pointer");
//		System.out.println(px);
//		System.out.println(py);
//		// shape location relative to parent's origin
//		int xLoc = pCmpRelPrnt.getX() + getObjectSize() / 2;
//		int yLoc = pCmpRelPrnt.getY() + getObjectSize() / 2;
//		System.out.println("px: " + px + ", py: " + py);
//		System.out.println("xLoc: " + xLoc + ", yLoc: " + yLoc);
//		if ((px >= xLoc - getObjectSize() / 2) && (px <= xLoc + getObjectSize() / 2)
//				&& (py >= yLoc - getObjectSize() / 2) && (py <= yLoc + getObjectSize() / 2)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
