package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

public class Curve extends GameObject implements IDrawable, ICollider {
	// private Point[] controlPointVector = new Point[4];
	private Transform myTranslation, myRotation, myScale;
	private int curveDir = 0;
	Point2D[] controlPointVector = new Point2D[4];
	private Point2D centerPoint;
	private double curveRadius = 0;
	private int curveFuel;

	public Curve(Point2D[] vector) {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		controlPointVector[0] = vector[0];
		controlPointVector[1] = vector[1];
		controlPointVector[2] = vector[2];
		controlPointVector[3] = vector[3];
		centerPoint = new Point2D(this.getCurveCenterPnt(controlPointVector).getX(),
				this.getCurveCenterPnt(controlPointVector).getY());
		setCurveFuel(5);
	}

	public int getCurveDir() {
		return this.curveDir;
	}

	public double getCurveCenterX() {
		return this.centerPoint.getX();
	}

	public double getCurveCenterY() {
		return this.centerPoint.getY();
	}

	public double getCurveRadius() {
		return this.curveRadius;
	}

	public int getCurveFuel() {
		return this.curveFuel;
	}

	public void setCurveFuel(int newFuel) {
		this.curveFuel = newFuel;
	}

	public void setCurveDir(int dir) {
		this.curveDir = dir;
	}

	public void setCurveCenterX(double newX) {
		this.centerPoint.setX(newX);
	}

	public void setCurveCenterY(double newY) {
		this.centerPoint.setY(newY);
	}

	public void setCurveRadius(double newR) {
		this.curveRadius = newR;
	}

	// rotation origin is
	public void rotate(double degrees) {
		myRotation.rotate((float) Math.toRadians(degrees), (float) (Game.getMapOriginX()),
				(float) (Game.getMapOriginY()));
	}

	public void scale(double sx, double sy) {
		myScale.scale((float) sx, (float) sy);
	}

	public void translate(double tx, double ty) {
		myTranslation.translate((float) tx, (float) ty);
	}

	@Override
	public boolean collidesWith(ICollider obj) {
		GameObject go = (GameObject) obj;
		// find the centers
		double thisCenterX = this.getCurveCenterX();
		double thisCenterY = this.getCurveCenterY();
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
		} else if (go instanceof Missile) {
			otherCenterX = go.getPointX();
			otherCenterY = go.getPointY();
		}

		// find distance between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double disBetCentSqr = dx * dx + dy * dy;

		// find square of sum of radii
		double thisRadius = this.getCurveRadius();
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

	@Override
	public void handleCollision(ICollider otherObject) {
		((GameObject) otherObject).setGetIfCol(true);
		if (otherObject instanceof Asteroid) {
			((Asteroid) otherObject).setHitCase(9);
		} else if (otherObject instanceof NonPlayerShip) {
			((NonPlayerShip) otherObject).setHitCase(9);
		}
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); // save the original xform
		gXform.translate((float) (this.getCurveCenterX() - Game.getMapOriginX()),
				(float) (this.getCurveCenterY() - Game.getMapOriginY()));
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float) (-(this.getCurveCenterX() - Game.getMapOriginX())),
				(float) (-(this.getCurveCenterY() - Game.getMapOriginY())));
		g.setTransform(gXform);
		g.setColor(ColorUtil.GREEN);

		drawBezierCurve(controlPointVector, g);

		g.setTransform(gOrigXform); // restore the original xform
	}

	// public void drawBezierCurve(Point2D[] controlPointArray, Graphics g) {
	// Point2D currentPoint = controlPointArray[0];
	// double t = 0;
	// while (t <= 1) {
	// Point2D nextPoint = new Point2D(0, 0);
	// for (int i = 0; i <= 3; i++) {
	// nextPoint.setX(nextPoint.getX() + (blendingFunction(i, t) *
	// controlPointArray[i].getX()));
	// nextPoint.setY(nextPoint.getY() + (blendingFunction(i, t) *
	// controlPointArray[i].getY()));
	// }
	// g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(), (int)
	// nextPoint.getX(),
	// (int) nextPoint.getY());
	// currentPoint = nextPoint;
	// t = t + 0.01;
	// }
	// }

	public void move() {
		Point2D[] tmp = new Point2D[4];
		int s = 10;
		tmp[0] = new Point2D(this.controlPointVector[0].getX() + s * Math.cos(Math.toRadians(this.getCurveDir())),
				this.controlPointVector[0].getY() + s * Math.sin(Math.toRadians(this.getCurveDir())));
		tmp[1] = new Point2D(this.controlPointVector[1].getX() + s * Math.cos(Math.toRadians(this.getCurveDir())),
				this.controlPointVector[1].getY() + s * Math.sin(Math.toRadians(this.getCurveDir())));
		tmp[2] = new Point2D(this.controlPointVector[2].getX() + s * Math.cos(Math.toRadians(this.getCurveDir())),
				this.controlPointVector[2].getY() + s * Math.sin(Math.toRadians(this.getCurveDir())));
		tmp[3] = new Point2D(this.controlPointVector[3].getX() + s * Math.cos(Math.toRadians(this.getCurveDir())),
				this.controlPointVector[3].getY() + s * Math.sin(Math.toRadians(this.getCurveDir())));
		controlPointVector[0] = new Point2D(tmp[0].getX(), tmp[0].getY());
		controlPointVector[1] = new Point2D(tmp[1].getX(), tmp[1].getY());
		controlPointVector[2] = new Point2D(tmp[2].getX(), tmp[2].getY());
		controlPointVector[3] = new Point2D(tmp[3].getX(), tmp[3].getY());
		centerPoint = new Point2D(this.getCurveCenterPnt(controlPointVector).getX(),
				this.getCurveCenterPnt(controlPointVector).getY());
		double r = Math.sqrt((controlPointVector[0].getX() - this.centerPoint.getX())
				* (controlPointVector[0].getX() - this.centerPoint.getX())
				+ (controlPointVector[0].getY() - this.centerPoint.getY())
						* (controlPointVector[0].getY() - this.centerPoint.getY()));
		this.setCurveRadius(r);
	}

	public Point2D getCurveCenterPnt(Point2D[] v) {
		Point2D tmp = new Point2D((v[0].getX() + v[1].getX() + v[2].getX() + v[3].getX()) / 4,
				(v[0].getY() + v[1].getY() + v[2].getY() + v[3].getY()) / 4);
		return tmp;
	}

//	public double blendingFunction(int i, double t) {
//		switch (i) {
//		case 0:
//			return ((1 - t) * (1 - t) * (1 - t));
//		case 1:
//			return (3 * t * (1 - t) * (1 - t));
//		case 2:
//			return (3 * t * t * (1 - t));
//		case 3:
//			return t * t * t;
//		}
//		return 1;
//	}

	public void drawBezierCurve(Point2D[] c, Graphics g) {
		if (straightEnough(c)) {
			g.drawLine((int) c[0].getX(), (int) c[0].getY(),
					(int) c[3].getX(), (int) c[3].getY());
		} else {
			Point2D[] left = new Point2D[4];
			Point2D[] right = new Point2D[4];
			subdivideCurve(c, left, right);
			drawBezierCurve(left, g);
			drawBezierCurve(right, g);
		}
	}

	private void subdivideCurve(Point2D[] controlPointVector, Point2D[] leftSubVector, Point2D[] rightSubVector) {
		leftSubVector[0] = new Point2D(controlPointVector[0].getX(), controlPointVector[0].getY());
		leftSubVector[1] = new Point2D((controlPointVector[0].getX() + controlPointVector[1].getX()) / 2,
				(controlPointVector[0].getY() + controlPointVector[1].getY()) / 2);
		leftSubVector[2] = new Point2D(
				leftSubVector[1].getX() / 2 + (controlPointVector[1].getX() + controlPointVector[2].getX()) / 4,
				leftSubVector[1].getY() / 2 + (controlPointVector[1].getY() + controlPointVector[2].getY()) / 4);
		rightSubVector[3] = new Point2D(controlPointVector[3].getX(), controlPointVector[3].getY());
		rightSubVector[2] = new Point2D((controlPointVector[2].getX() + controlPointVector[3].getX()) / 2,
				(controlPointVector[2].getY() + controlPointVector[3].getY()) / 2);
		rightSubVector[1] = new Point2D(
				(controlPointVector[1].getX() + controlPointVector[2].getX()) / 4 + rightSubVector[2].getX() / 2,
				(controlPointVector[1].getY() + controlPointVector[2].getY()) / 4 + rightSubVector[2].getY() / 2);
		leftSubVector[3] = new Point2D((leftSubVector[2].getX() + rightSubVector[1].getX()) / 2,
				(leftSubVector[2].getY() + rightSubVector[1].getY()) / 2);
		rightSubVector[0] = new Point2D(leftSubVector[3].getX(), leftSubVector[3].getY());
	}

	private boolean straightEnough(Point2D[] c) {
		double d01 = calDistance(c[0], c[1]);
		double d12 = calDistance(c[1], c[2]);
		double d23 = calDistance(c[2], c[3]);
		double d1 = d01 + d12 + d23;
		double d2 = Math.sqrt((c[0].getX() - c[3].getX()) * (c[0].getX() - c[3].getX())
				+ (c[0].getY() - c[3].getY()) * (c[0].getY() - c[3].getY()));
		if (Math.abs(d1 - d2) < 0.01) {
			return true;
		} else {
			return false;
		}
	}
	
	public double calDistance(Point2D a, Point2D b) {
		double tmp=Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
		return tmp;
	}

}
