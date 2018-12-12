package com.mycompany.a3.views;

import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.gameobjects.*;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.interfaces.IIterator;

public class MapView extends Container implements Observer {
	private GameWorld gw;
	Transform worldToND, ndToDisplay, theVTM;
	private float winLeft, winBottom, winRight, winTop, winWidth, winHeight;
	private Point pPrevDragLoc = new Point(-1, -1);

	public MapView(GameWorld gw) {
		this.gw = gw;
		// initialize world window
		// winLeft = getAbsoluteX();
		// winBottom = getAbsoluteY();
		// winRight = this.getWidth();
		// winTop = this.getHeight();
	}

	// This will update the map eventually. For now it displays all the game objects
	public void update(Observable o, Object arg) {
		// System.out.println("Map Width: " + Game.getMapWidth() + ", Map Height: " +
		// Game.getMapHeight());
		// Casting o as a GameWorld
		IGameWorld gw = (IGameWorld) arg;
		// GameObjectCollection goc = gw.getGameObjects();
		// IIterator gameIterator = goc.getIterator();
		// if (gameIterator.hasNext()) {
		// while (gameIterator.hasNext()) {
		// System.out.println(gameIterator.getNext());
		// }
		// } else {
		// System.out.println("There's no object in the GameWorld!");
		// }
		// System.out.println();
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		winLeft = 0;
		winBottom = 0;
		winRight = this.getWidth();
		winTop = this.getHeight();
		winWidth = winRight - winLeft;
		winHeight = winTop - winBottom;

		IIterator gameIterator = gw.getGameObjects().getIterator();
		super.paint(g);

		// construct the Viewing Transformation Matrix
		worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBottom);
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND); // worldToND will be applied first to points

		// concatenate the VTM onto the g's current transformation
		Transform gXform = Transform.makeIdentity(); // initialize
		g.getTransform(gXform);
		// move the drawing coordinates back
		gXform.translate(getAbsoluteX(), getAbsoluteY());
		gXform.concatenate(theVTM);
		// gXform.translate(0, getHeight());
		// gXform.scale(1, -1);
		gXform.translate(-getAbsoluteX(), -getAbsoluteY());
		g.setTransform(gXform);
		while (gameIterator.hasNext()) {
			GameObject go = gameIterator.getNext();
			Point pCmpRelPrnt = new Point((int) go.getPointX(), (int) go.getPointY());
			Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
			// System.out.println("getAbsoulteX: "+getAbsoluteX());
			// System.out.println("getAbsoluteY: "+getAbsoluteY());
			// draw the image at the current location
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
			g.resetAffine(); // restore the xform in Graphics object
		}
		// g.resetAffine(); // restore the xform in Graphics object
	}

	private Transform buildNDToDisplayXform(float displayWidth, float displayHeight) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.translate(0, displayHeight);
		tmpXfrom.scale(displayWidth, -displayHeight);
		return tmpXfrom;
	}

	private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.scale((1 / winWidth), (1 / winHeight));
		tmpXfrom.translate(-winLeft, -winBottom);
		return tmpXfrom;
	}

	@Override
	public void pointerPressed(int x, int y) {
		float[] fPtr = new float[] { x - getAbsoluteX(), y - getAbsoluteY() };
		Transform inverseVTM = Transform.makeIdentity();
		try {
			theVTM.getInverse(inverseVTM);
		} catch (NotInvertibleException e) {
			System.out.println("Non-invertible xform!");
		}
		inverseVTM.transformPoint(fPtr,fPtr);
		IIterator gameIterator=gw.getGameObjects().getIterator();
		if (gw.getBPause()) {
			while (gameIterator.hasNext()) {
				GameObject go = gameIterator.getNext();
				Point pPtrRelPrnt = new Point(x, y);
				Point pCmpRelPrnt = new Point((int) go.getPointX(), (int) go.getPointY());
				if (go instanceof Missile) {
					Missile m = (Missile) go;
					if (m.getMissileFlag() == 0) {
						if (m.contains(fPtr)) {
							System.out.println("Selected Missile");
							m.setSelected(true);
						} else {
							System.out.println("Didn't select Missile");
							m.setSelected(false);
						}
					}
					repaint();
				}
				if (go instanceof Asteroid) {
					Asteroid a = (Asteroid) go;
					if (a.contains(fPtr)) {
						a.setSelected(true);
						System.out.println("Selected Asteroid");
					} else {
						a.setSelected(false);
						System.out.println("Didn't select Asteroid");
					}
					repaint();
				}
			}
		}
	}
	
	public void zoom(float factor) {
		// positive factor would zoom in and negative factor would zoom out
		float newWinLeft=winLeft+winWidth*factor;
		float newWinRight=winRight-winWidth*factor;
		float newWinTop=winTop-winHeight*factor;
		float newWinBottom=winBottom+winHeight*factor;
		float newWinHeight=newWinTop-newWinBottom;
		float newWinWidth=newWinRight-newWinLeft;
		if(newWinWidth<=newWinHeight&&newWinWidth>0&&newWinHeight>0) {
			winLeft=newWinLeft;
			winRight=newWinRight;
			winTop=newWinTop;
			winBottom=newWinBottom;
			winWidth=newWinWidth;
			winHeight=newWinHeight;
		}else {
			System.out.println("Cannot zoom further!");
		}
		this.repaint();
	}

	@Override
	public boolean pinch(float scale) {
		if(scale<1.0) {
			// zooming out
			// click+drag towards the top left corner of screen
			zoom(-0.05f);
		}else if(scale>1.0) {
			// zooming in
			// click+drag away from the top left corner of screen
			zoom(0.05f);
		}
		return true;
	}
	
	public void panHorizontal(double delta) {
		// positive delta would pan right and negative delta would pan left
		winLeft+=delta;
		winRight+=delta;
		this.repaint();
	}
	
	public void panVertical(double delta) {
		// positive delta would pan up (image would shift down)
		// negative delta would pan down (image would shift up)
		winBottom+=delta;
		winTop+=delta;
		this.repaint();
	}
	
	@Override
	public void pointerDragged(int x, int y) {
		if(pPrevDragLoc.getX()!=-1) {
			System.out.println("x " + x + " pPrevDragLoc.getX() " + pPrevDragLoc.getX());
			if(pPrevDragLoc.getX()<x) {
				panHorizontal(5);
				System.out.println("Pan horizontal positive 5");
			}else if(pPrevDragLoc.getX()>x) {
				panHorizontal(-5);
				System.out.println("Pan horizontal negative 5");
			}
			if(pPrevDragLoc.getY()<y) {
				panVertical(-5);
			}else if(pPrevDragLoc.getY()>y) {
				panVertical(5);
			}
		}
		pPrevDragLoc.setX(x);
		pPrevDragLoc.setY(y);
	}
	
	
//	public void pointerPressed(int x, int y) {
//		x = x - getParent().getAbsoluteX();
//		y = y - getParent().getAbsoluteY();
//		IIterator gameIterator = gw.getGameObjects().getIterator();
//		if (gw.getBPause()) {
//			while (gameIterator.hasNext()) {
//				GameObject go = gameIterator.getNext();
//				Point pPtrRelPrnt = new Point(x, y);
//				Point pCmpRelPrnt = new Point((int) go.getPointX(), (int) go.getPointY());
//				if (go instanceof Missile) {
//					Missile m = (Missile) go;
//					if (m.getMissileFlag() == 0) {
//						if (m.contains(pPtrRelPrnt, pCmpRelPrnt)) {
//							System.out.println("Selected Missile");
//							m.setSelected(true);
//						} else {
//							System.out.println("Didn't select Missile");
//							m.setSelected(false);
//						}
//					}
//					repaint();
//				}
//				if (go instanceof Asteroid) {
//					Asteroid a = (Asteroid) go;
//					if (a.contains(pPtrRelPrnt, pCmpRelPrnt)) {
//						a.setSelected(true);
//						System.out.println("Selected Asteroid");
//					} else {
//						a.setSelected(false);
//						System.out.println("Didn't select Asteroid");
//					}
//					repaint();
//				}
//			}
//		}
//	}

}
