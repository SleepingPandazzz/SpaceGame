package com.mycompany.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.ISteerable;

// This is a steerable Missile Launcher that allows other objects to tell them to change their directions
public class SteerableMissileLauncher extends MissileLauncher implements ISteerable, IDrawable {
	private int smlSpeed;
	private int smlDirection;
	private double smlposX1;
	private double smlposY1;
	private double smlposX2;
	private double smlposY2;
	private Transform myTranslation, myRotation, myScale;

	public SteerableMissileLauncher() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		setSMLSpeed(0);	
		// setSMLDirection(0);
	}

	public int getSMLSpeed() {
		return this.smlSpeed;
	}

	public int getSMLDirection() {
		return this.smlDirection;
	}

	public double getSMLpX1() {
		return this.smlposX1;
	}

	public double getSMLpY1() {
		return this.smlposY1;
	}
	
	public double getSMLpX2() {
		return this.smlposX2;
	}

	public double getSMLpY2() {
		return this.smlposY2;
	}

	public void setSMLSpeed(int newSMLSpeed) {
		this.smlSpeed = newSMLSpeed;
	}

	public void setSMLDirection(int newSMLDirection) {
		this.smlDirection = newSMLDirection;
	}

	public void setSMLpX1(double newX) {
		this.smlposX1 = newX;
	}

	public void setSMLpY1(double newY) {
		this.smlposY1 = newY;
	}
	
	public void setSMLpX2(double newX) {
		this.smlposX2 = newX;
	}

	public void setSMLpY2(double newY) {
		this.smlposY2 = newY;
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
//		Transform gXform = Transform.makeIdentity();
//		g.getTransform(gXform);
//		Transform gOrigXform = gXform.copy(); // save the original xform
//		gXform.translate((float) (this.getPointX() - Game.getMapOriginX()),
//				(float) (this.getPointY() - 11.667 - Game.getMapOriginY()));
//		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
//		gXform.concatenate(myRotation);
//		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
//		gXform.translate((float) (-(this.getPointX() - Game.getMapOriginX())),
//				(float) (-(this.getPointY() - 11.667 - Game.getMapOriginY())));
//		g.setTransform(gXform);

		// missile launcher
//		int length = 40;
//		int xLoc1 = (int) (pCmpRelPrnt.getX());
//		int yLoc1 = (int) (pCmpRelPrnt.getY())+55;
//		int mx = (int) (length * Math.cos(Math.toRadians(getSMLDirection())));
//		int my = (int) (length * Math.sin(Math.toRadians(getSMLDirection())));
//		int xLoc2=xLoc1+mx;
//		int yLoc2=yLoc1+my;
////		g.drawLine(xLoc1, yLoc1, xLoc2, yLoc2);
//		g.drawLine((int)(xLoc1-55*Math.sin(Math.toRadians(15))), (int)(yLoc1+55*Math.cos(Math.toRadians(15))), 0, 0);
//		System.out.println("x"+55*Math.sin(Math.toRadians(15)));
//		System.out.println("y"+55*Math.cos(Math.toRadians(15)));
//		int xLoc1 = (int) this.getSMLpX2();
//		int yLoc1 = (int) this.getSMLpY2();
//		int r1 = 60, r2 = 5, r4 = 10, lHeight = 10;
//		g.drawLine(xLoc, yLoc - r1 / 2 + r1 + r2 + lHeight + r4, xLoc1 + mx,
//				yLoc1 - r1 / 2 + r1 + r2 + lHeight + r4 + my);
//		g.drawLine((int)(this.getSMLpX1()), (int)(this.getSMLpY1()), (int)(this.getSMLpX2()), (int)(this.getSMLpY2()));
	}

}
