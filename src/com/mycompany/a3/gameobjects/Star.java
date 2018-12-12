package com.mycompany.a3.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

public class Star extends GameObject implements IDrawable, ICollider {
	private Transform myTranslation, myRotation, myScale;

	public Star() {
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		Random r = new Random();
		setObjectSize(r.nextInt(3) + 1);
		setPointX(r.nextInt((Game.getMapWidth() - this.getObjectSize())) + Game.getMapOriginX()+this.getObjectSize());
		setPointY(r.nextInt((Game.getMapHeight() - this.getObjectSize())) + Game.getMapOriginY()+this.getObjectSize());
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
				(float) (-(this.getPointY()- Game.getMapOriginY())));
		g.setColor(ColorUtil.rgb(255,246,143));
		int xLoc = (int) (pCmpRelPrnt.getX());
		int yLoc = (int) (pCmpRelPrnt.getY());
		int tmpr=3*getObjectSize();
		g.setColor(ColorUtil.rgb(255,20,147));
		g.fillArc(xLoc + getObjectSize() / 2 - tmpr / 2, yLoc - tmpr / 2, tmpr, tmpr, 0, 360);
		int tmpR = 2*getObjectSize();
		g.setColor(ColorUtil.rgb(255, 0, 0));
		g.fillArc(xLoc + getObjectSize() / 2 - tmpR / 2, yLoc - tmpR / 2, tmpR, tmpR, 0, 360);
	}

	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		
	}

}
