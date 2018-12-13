package com.mycompany.a3.gameobjects;

import java.io.InputStream;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import java.lang.Object;

import com.codename1.charts.util.ColorUtil;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.BGSound;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObjectCollection;
import com.mycompany.a3.GameWorldProxy;
import com.mycompany.a3.Sound;
import com.mycompany.a3.gameobjects.*;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.interfaces.IMoveable;
import com.codename1.util.MathUtil;

public class GameWorld extends Observable implements IGameWorld {
	// All state variables are stored here
	private GameObjectCollection theGameCollection = null;
	public BGSound bgSound = new BGSound("384583__coolrealistic__atmosphere-1.wav");
	private Vector<Observable> myObserverList = new Vector<Observable>();

	private int playerLife;
	private int score = 0;
	private int elapsedTime = 0;
	private boolean soundOnOff = false; // false is off, true is on
	private boolean bPause = false; // false is play, true is pause
	private int asteroidNum = 0;
	private int npsNum = 0;
	private int ssNum = 0;
	private int psNum = 0;
	private int sNum=0;

	// This constructor sets all state value appropriately. This also creates the
	// collection necessary to hold all game objects.
	public GameWorld() {
		theGameCollection = new GameObjectCollection();
		setPlayerLife(3);
		setScore(0);
		setElapsedTime(0);
		this.init(); // initialize the world
	}

	public int getPlayerLife() {
		return this.playerLife;
	}

	public int getScore() {
		return this.score;
	}

	public int getElapsedTime() {
		return this.elapsedTime;
	}

	public boolean getSound() {
		return this.soundOnOff;
	}

	public boolean getBPause() {
		return this.bPause;
	}

	public int getAsteroidNum() {
		return this.asteroidNum;
	}

	public int getNPSNum() {
		return this.npsNum;
	}

	public int getSSNum() {
		return this.ssNum;
	}

	public int getPSNum() {
		return this.psNum;
	}
	
	public int getSNum() {
		return this.sNum;
	}
	
	public void setSNum(int newNum) {
		this.sNum=newNum;
	}

	public void setPlayerLife(int newPlayerLife) {
		this.playerLife = newPlayerLife;
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	public void setScore(int newScore) {
		this.score = newScore;
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	public void setElapsedTime(int newElapsedTime) {
		this.elapsedTime = newElapsedTime;
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	public void setSound(boolean mySound) {
		this.soundOnOff = mySound;
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	public void setBPause(boolean newBPause) {
		this.bPause = newBPause;
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	public void setAsteroidNum(int newANum) {
		this.asteroidNum = newANum;
	}

	public void setNPSNum(int newNPSNum) {
		this.npsNum = newNPSNum;
	}

	public void setSSNum(int newSSNum) {
		this.ssNum = newSSNum;
	}

	public void setPSNum(int newPSNum) {
		this.psNum = newPSNum;
	}

	// get the player ship's missile count
	public int getPSMissileCount() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (ps != null) {
			return ps.getMissileCount();
		} else {
			return -1;
		}
	}

	// command a - add a new asteroid to the world
	public void addAsteroid() {
		theGameCollection.add(new Asteroid());
		// System.out.println("Added a new asteroid to the world.");
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}
	
	// add a star to the world
	public void addStar() {
		theGameCollection.add(new Star());
		
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command y - add a NPS to the world
	public void addNonPlayerShip() {
		NonPlayerShip nps = new NonPlayerShip();
		// nps.rotate(-nps.getObjectDirection());
		System.out.println("nps Direction : " + nps.getObjectDirection());
		float x = (float) (nps.getPointX());
		float y = (float) (nps.getPointY());
		// System.out.println("nps location x: " + x);
		// System.out.println("nps location y: " + y);
		theGameCollection.add(nps);
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command b - add a new blinking space station to the world
	public void addBlinkingSpaceStation() {
		theGameCollection.add(new SpaceStation());
		// System.out.println("Added a new blinking space station to the world.");
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command s - add a PS to the world
	public void addPlayerShip() {
		boolean ifPlayerShip = false; // false not exist, true exist
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			if (theElements.getNext() instanceof PlayerShip) {
				ifPlayerShip = true;
				// System.out.println("Error: Cannot execute 's' - Player Ship has already
				// existed!");
				break;
			}
		}
		if (!ifPlayerShip) {
			PlayerShip ps = new PlayerShip();
			ps.setPSSMLxPosX2(ps.getPointX() + 40);
			ps.setPSSMLyPosY2(ps.getPointY() + 55);
			theGameCollection.add(ps);
			// ps.setPSSMLxPosX1(ps.getPointX());
			// ps.setPSSMLyPosY1(ps.getPointY() + 53.334);
			// ps.setPSSMLxPosX2(ps.getPointX() + 40);
			// ps.setPSSMLyPosY2(ps.getPointY() + 53.334);
			// System.out.println("Added a Player Ship in the world");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command i - increase the speed of the PS by a small amount
	public void increasePlayerShipSpeed() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getObjectSpeed() < 10) {
				ps.setObjectSpeed(ps.getObjectSpeed() + 1);
				// System.out.println("Increased the Player Ship's Speed");
			} else {
				// System.out.println("Error: Cannot execute 'i' - the Player Ship is at its
				// maximum speed!");
			}
		} else {
			// System.out.println("Error: Cannot execute 'i' - no Player Ship in the Game
			// World!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command d - decrease the speed of the PS by a small amount, as long as doing
	// so doesn't make the speed go negative
	public void decreasePlayerShipSpeed() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getObjectSpeed() > 0) {
				ps.setObjectSpeed(ps.getObjectSpeed() - 1);
				// System.out.println("Decreased the Player Ship's Speed");
			} else {
				// System.out.println("Error: Cannot execute 'd' - the Player Ship's speed
				// cannot go negative!");
			}
		} else {
			// System.out.println("Error: Cannot execute 'd' - no Player Ship in the Game
			// World!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command l - turn PS left by a small amount (15 degree)
	public void turnPlayerShipLeft() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getObjectDirection() == 0) {
				ps.setObjectDirection(345);
			} else {
				ps.setObjectDirection(ps.getObjectDirection() - 15);
			}
			ps.rotate(-15);
			ps.setPSSMLDirection(ps.getPSSMLDirection() - 15);

		} else {
			// ps.setRX(ps.getPointX()+66.667*Math.sin(Math.toRadians(times*15)));
			// ps.setRY(ps.getPointY()-11.667+66.667*Math.cos(Math.toRadians(times*15)));
		}
//		System.out.println("degrees : " + ps.getObjectDirection());
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command r - turn PS right by 15 degree
	public void turnPlayerShipRight() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getObjectDirection() == 345) {
				ps.setObjectDirection(0);
			} else {
				ps.setObjectDirection(ps.getObjectDirection() + 15);
			}
			ps.rotate(15);
			ps.setPSSMLDirection(ps.getPSSMLDirection() + 15);

		} else {
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command < - a user controls the direction of the PS's MissileLauncher by
	// revolving it about the center of the player ship in a counterclockwise
	// direction. This command turns the MissileLauncher by 10 degree.
	public void turnPSMissileLauncherLeft() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getPSSMLDirection() < 15 && ps.getPSSMLDirection() >= 0) {
				ps.setPSSMLDirection(ps.getPSSMLDirection() + 345);
			} else {
				ps.setPSSMLDirection(ps.getPSSMLDirection() - 15);
			}
			ps.setMLAngle(ps.getMLAngle() - 15);
		} else {
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command > - a user controls the direction of the PS's MissileLauncher by
	// revolving it about the center of the player ship in a clockwise direction.
	// This command turns the MissileLauncher by 10 degree.
	public void turnPSMissileLauncherRight() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getPSSMLDirection() >= 345 && ps.getPSSMLDirection() <= 360) {
				ps.setPSSMLDirection(360 - ps.getPSSMLDirection());
			} else {
				ps.setPSSMLDirection(ps.getPSSMLDirection() + 15);
			}
			ps.setMLAngle(ps.getMLAngle() + 15);
			// System.out.println("Turned the missileLauncher right 10 degree.");
		} else {
			// System.out.println("Error: Cannot execute '>' - no Player Ship in the Game
			// World!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command f - fire a missile out the front of the PS. If the ship has no more
	// missiles, an error message should be printed; otherwise, add to the world a
	// new missile with a location, speed, and launcher's heading determined by the
	// ship.
	public void firePSMissile() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			if (ps.getMissileCount() <= 0) {
				// System.out.println("Error: Cannot execute 'f' - Player Ship does not have
				// enough Missile!");
			} else {
				Missile m = new Missile();
				// shooting from the center
				m.setPointX(ps.getPointX());
				m.setPointY(ps.getPointY() - 11.667);
				m.setObjectSpeed(ps.getObjectSpeed() + 1);
				m.setObjectDirection(ps.getPSSMLDirection());
				m.setMissileFlag(0);
				theGameCollection.add(m);
				ps.setMissileCount(ps.getMissileCount() - 1);
				if (getSound()) {
					fireMissileSound();
				}
				// System.out.println("The player ship fired one missile.");
			}
		} else {
			// System.out.println("Error: Cannot execute 'f' - no Player Ship in the Game
			// World!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command w - fire a bezier curve out the front of the PS
	public void fireCurve() {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (checkPS) {
			Random r = new Random();
			Point2D[] controlPointVector = new Point2D[4];
			controlPointVector[0] = new Point2D(ps.getPointX() - r.nextInt(200), ps.getPointY() + r.nextInt(150));
			controlPointVector[1] = new Point2D(ps.getPointX(), ps.getPointY());
			int tmp1 = r.nextInt(200);
			controlPointVector[2] = new Point2D(ps.getPointX() + tmp1, ps.getPointY() - r.nextInt(400));
			controlPointVector[3] = new Point2D(ps.getPointX() + r.nextInt(100) + tmp1,
					ps.getPointY() + r.nextInt(300));
			Curve c = new Curve(controlPointVector);
			c.setCurveDir(ps.getPSSMLDirection());
			// c.getCurveCenterPnt(controlPointVector);
			// System.out.println("x curve: "+c.getCurveCenterX());
			// System.out.println("y curve: "+c.getCurveCenterY());
			int tmp = r.nextInt(100);
			double x = ps.getPointX() + ps.getPSSMLDirection() * tmp;
			double y = ps.getPointY() + ps.getPSSMLDirection() * tmp;
			c.setPointX(x);
			c.setPointY(y);
			theGameCollection.add(c);
			if (getSound()) {
				fireMissileSound();
			}
		}
	}

	// command L - Launch a missile out the front of the NPS. If the ship has no
	// more missiles, an error message should be printed; otherwise, add to the
	// world a new missile with a location, speed, and launcher's heading determined
	// by the ship.
	public void launchNPSMissile(NonPlayerShip nps) {
		boolean checkPS = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				checkPS = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		double psX = ps.getPointX();
		double psY = ps.getPointY();
		double npsX = nps.getPointX();
		double npsY = nps.getPointY();
		double tmp = Math.abs((psX - npsX) / (psY - npsY));
		int dir = (int) Math.toDegrees(MathUtil.atan(tmp));
		if (npsX < psX && npsY > psY) {
			dir = 270 + dir;
		} else if (npsX < psX && npsY < psY) {
			dir = 90 - dir;
		} else if (npsX > psX && npsY < psY) {
			dir = 90 + dir;
		} else if (npsX > psX && npsY > psY) {
			dir = 270 - dir;
		} else {

		}
		if (checkPS) {
			if (nps.getNPSMissileCount() > 0) {
				Missile m = new Missile();
				m.setPointX(nps.getPointX());
				m.setPointY(nps.getPointY());
				m.setObjectSpeed(nps.getObjectSpeed() + 1);
				m.setObjectDirection(dir);
				m.setMissileFlag(1);
				theGameCollection.add(m);
				if (getSound()) {
					fireMissileSound();
				}
				// System.out.println("The non-player ship fired one missile.");
			}
		} else {
			// System.out.println("Error: Cannot execute 'L' - no Non-Player Ship in the
			// game world!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command j - jump through hyperspace. This command causes the ship to
	// instantly jump to the initial default position in the middle of the screen,
	// regardless of its current position.
	public void psJumpHyperspace() {
		boolean isPlayerShip = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				isPlayerShip = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (isPlayerShip) {
			ps.setPointX(Game.getMapOriginX() + Game.getMapWidth() / 2);
			ps.setPointY(Game.getMapOriginY() + Game.getMapHeight() / 2);
			isPlayerShip = true;
			// System.out.println("The player ship jumped through hyperspace.");
		} else {
			// System.out.println("Error: Cannot execute 'j' - no Player Ship in the game
			// world!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command n - load a new supply of missiles into the PS. This increase the
	// player ship's missile supply to maximum. It is permissible to reload missiles
	// before all missiles have been fired, but it is not allowed for a ship to
	// carry more than the maximum number of missiles.
	public void psLoadMissile() {
		boolean isPlayerShip = false;
		PlayerShip ps = null;
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = theElements.getNext();
			if (go instanceof PlayerShip) {
				isPlayerShip = true;
				ps = (PlayerShip) go;
				break;
			}
		}
		if (isPlayerShip) {
			if (getSound()) {
				loadMissileSound();
			}
			ps.setMissileCount(10);
			isPlayerShip = true;
			// System.out.println("Loaded a new supply of missiles into the player ship");
		} else {
			// System.out.println("Error: Cannot execute 'n' - no Player Ship in the game
			// world!");
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command k - a PS's missile has struck and killed an asteroid; tell the game
	// world to remove a missile and an asteroid and to increment the player's score
	// by 5 points.
	public void psKilledAsteroid(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		setScore(getScore() + 5);

		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command w - a PS's curve has killed the objects instead of space station
	public void psCurveKilledObject(GameObject a, GameObject b) {
		if(a instanceof Curve) {
			theGameCollection.remove(b);
		}
		if(b instanceof Curve) {
			theGameCollection.remove(a);
		}
		if (getSound()) {
			mHitAsteroidSound();
		}
		setScore(getScore()+3);
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command e - a PS's missile has struck and eliminated a NPS; tell the game
	// world to remove a missile and a NPS and to increment the player's score by 5
	// points.
	public void psKilledNPS(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		// System.out.println("A Player Ship's Missile has struck and eliminated a
		// Non-player Ship.");
		setScore(getScore() + 5);
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command E - a NPS's missile has struck and Exploded a PS; tell the game world
	// to remove a missile and a PS.
	public void npsMissileKilledPS(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		if (getPlayerLife() >= 1) {
			setPlayerLife(getPlayerLife() - 1);
			setPSNum(0);
			// if there's no PlayerShip, it automatically add PS
			if (getPSNum() == 0) {
				addPlayerShip();
				setPSNum(1);
			}
			// if SpaceStation is less than 2, it automatically add Space Station
			if (getSSNum() < 2) {
				addBlinkingSpaceStation();
				setSSNum(getSSNum() + 1);
			}
		}
		if (getPlayerLife() == 0) {
			System.out.println("Game Over");
			Boolean bOk = Dialog.show("Game Over", "Do you want to restart the game?", "Yes", "Cancel");
			if (bOk) {
				theGameCollection.clear();
				setPSNum(0);
				setNPSNum(0);
				setSSNum(0);
				setAsteroidNum(0);
				setPlayerLife(3);
				setScore(0);
				setElapsedTime(0);
				setSNum(0);
				// if there's no PlayerShip, it automatically add PS
				if (getPSNum() == 0) {
					addPlayerShip();
					setPSNum(1);
				}
				// if SpaceStation is less than 2, it automatically add Space Station
				if (getSSNum() < 2) {
					addBlinkingSpaceStation();
					setSSNum(getSSNum() + 1);
				}
				// System.out.println();
				// System.out.println("-------------------------------");
				// System.out.println("Starting an new game: ");
				// System.out.println("-------------------------------");
				// System.out.println();
			} else {
				Display.getInstance().exitApplication();
			}
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command c - the PS has crashed into an asteroid; tell the game world to
	// remove the player ship and an asteroid and to decrement the count of lives
	// left; if no lives are left then the game is over.
	public void asteroidCrashedPS(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		System.out.println("Here life -1");
		if (getPlayerLife() >= 1) {
			setPSNum(0);
			setPlayerLife(getPlayerLife() - 1);
			// if there's no PlayerShip, it automatically add PS
			if (getPSNum() == 0) {
				addPlayerShip();
				setPSNum(1);
			}
			// if SpaceStation is less than 2, it automatically add Space Station
			if (getSSNum() < 2) {
				addBlinkingSpaceStation();
				setSSNum(getSSNum() + 1);
			}
		}
		if (getPlayerLife() == 0) {
			System.out.println("Game Over");
			Boolean bOk = Dialog.show("Game Over", "Do you want to restart the game?", "Yes", "Cancel");
			if (bOk) {
				theGameCollection.clear();
				setPSNum(0);
				setNPSNum(0);
				setSSNum(0);
				setAsteroidNum(0);
				setPlayerLife(3);
				setScore(0);
				setElapsedTime(0);
				setSNum(0);
				// if there's no PlayerShip, it automatically add PS
				if (getPSNum() == 0) {
					addPlayerShip();
					setPSNum(1);
				}
				// if SpaceStation is less than 2, it automatically add Space Station
				if (getSSNum() < 2) {
					addBlinkingSpaceStation();
					setSSNum(getSSNum() + 1);
				}
				// System.out.println();
				// System.out.println("-------------------------------");
				// System.out.println("Starting an new game: ");
				// System.out.println("-------------------------------");
				// System.out.println();
			} else {
				Display.getInstance().exitApplication();
			}
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command h - the PS has hit a NPS; tell the game world to remove the NPS and
	// to decrement the count of lives left; if no lives are left then the game is
	// over.
	public void psHitNPS(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		// System.out.println("Here life -1");
		if (getPlayerLife() >= 1) {
			setPSNum(0);
			setPlayerLife(getPlayerLife() - 1);
			// if there's no PlayerShip, it automatically add PS
			if (getPSNum() == 0) {
				addPlayerShip();
				setPSNum(1);
			}
			// if SpaceStation is less than 2, it automatically add Space Station
			if (getSSNum() < 1) {
				addBlinkingSpaceStation();
				setSSNum(getSSNum() + 1);
			}
		}
		if (getPlayerLife() == 0) {
			System.out.println("Game Over");
			Boolean bOk = Dialog.show("Game Over", "Do you want to restart the game?", "Yes", "Cancel");
			if (bOk) {
				theGameCollection.clear();
				setPSNum(0);
				setNPSNum(0);
				setSSNum(0);
				setAsteroidNum(0);
				setPlayerLife(3);
				setScore(0);
				setElapsedTime(0);
				setSNum(0);
				// if there's no PlayerShip, it automatically add PS
				if (getPSNum() == 0) {
					addPlayerShip();
					setPSNum(1);
				}
				// if SpaceStation is less than 2, it automatically add Space Station
				if (getSSNum() < 2) {
					addBlinkingSpaceStation();
					setSSNum(getSSNum() + 1);
				}
				// System.out.println();
				// System.out.println("-------------------------------");
				// System.out.println("Starting an new game: ");
				// System.out.println("-------------------------------");
				// System.out.println();
			} else {
				Display.getInstance().exitApplication();
			}
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command x - two asteroids have collided with and exterminated each other;
	// tell the game world to remove two asteroids from the game.
	public void asteroidCollidedAsteroid(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command I - one asteroid have collided and impacted the NPS; tell the game
	// world to remove them from the game.
	public void asteroidCollidedNPS(GameObject a, GameObject b) {
		theGameCollection.remove(a);
		theGameCollection.remove(b);
		if (getSound()) {
			mHitAsteroidSound();
		}

		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command t - tell the game world that the "game clock has ticked". Each tick
	// of the game clock has the following effects: 1. all moveable objects are told
	// to update their positions according to their current direction and speed 2.
	// every missile's fuel level is reduced by one and any missiles which are now
	// out of fuel are removed from the game 3. each space station toggles its
	// blinking light if the tick count modulo the station's blink rate is zero 4.
	// the "elapsed game time" is incremented by one
	public void gameClockTicked() {
		if (this.getBPause() == false) {
			// if there's no PlayerShip, it automatically add PS
			if (getPSNum() == 0) {
				addPlayerShip();
				setPSNum(1);
			}
			Random r = new Random();
			int roll = r.nextInt(100) + 1;
			// if NPS is less than 2, it automatically add NPS
			if (getNPSNum() < 5) {
				// addNonPlayerShip();
				// setNPSNum(getNPSNum() + 1);
				if (roll % 10 == 0) {
					addNonPlayerShip();
					setNPSNum(getNPSNum() + 1);
				}
			}
			// if Asteroid is less than 2, it automatically add Asteroid
			if (getAsteroidNum() < 3) {
				// addAsteroid();
				// setAsteroidNum(getAsteroidNum()+1);
				if (roll % 20 == 0) {
					addAsteroid();
					setAsteroidNum(getAsteroidNum() + 1);
				}
			}
			// // if SpaceStation is less than 2, it automatically add Space Station
			// if (getSSNum() < 2) {
			// addBlinkingSpaceStation();
			// setSSNum(getSSNum() + 1);
			// }
			setElapsedTime(getElapsedTime() + 1);
			// System.out.println("Time passed one second.");
			IIterator theElements = theGameCollection.getIterator();
			Vector<GameObject> tmp = new Vector<GameObject>();
			while (theElements.hasNext()) {
				GameObject o = (GameObject) theElements.getNext();
				if (o instanceof SpaceStation) {
					SpaceStation ss = (SpaceStation) o;
					if (ss.getBlinkRate() != 0) {
						if (((getElapsedTime() / ss.getBlinkRate()) + 1) % 2 == 0) {
							if (ss.getBlinkLight() == true) {
								ss.setBlinkLight(false);
							} else {
								ss.setBlinkLight(true);
							}
						}
					} else {
						ss.setBlinkLight(false);
					}
				}
				if (o instanceof Curve) {
					Curve c = (Curve) o;
					c.move();
					if(getElapsedTime()%5==0) {
						c.setCurveFuel(c.getCurveFuel()-1);
					}
					if(c.getCurveFuel()<=0) {
						tmp.add(c);
					}
				}
				if (o instanceof IMoveable) {
					IMoveable mObj = (IMoveable) o;
					mObj.move();
					if (mObj instanceof PlayerShip) {
						PlayerShip ps = (PlayerShip) mObj;
						//
					}
					if (mObj instanceof Missile) {
						Missile m = (Missile) o;
						if (getElapsedTime() % 5 == 0) {
							m.setMissileFuel(m.getMissileFuel() - 1);
						}
						if (m.getMissileFuel() <= 0) {
							tmp.add(m);
						}
						if (((getElapsedTime() / m.getMissileBlink()) + 1) % 2 == 0) {
							if (m.getBlinkLight() == true) {
								m.setBlinkLight(false);
							} else {
								m.setBlinkLight(true);
							}
						}
						// m.rotate(15);
					}
					if (mObj instanceof NonPlayerShip) {
						NonPlayerShip nps = (NonPlayerShip) mObj;
						if (nps.getNPSMissileCount() > 0) {
							int tmpT = r.nextInt(50) + 50;
							if (getElapsedTime() % tmpT == 0) {
								launchNPSMissile(nps);
							}
						}
					}
				}
			}
			for (int i = 0; i < tmp.size(); i++) {
				theGameCollection.remove(tmp.elementAt(i));
			}
		} else {

		}

		checkOutOfBoundary();
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// remove collide objects
	public void removeColObjects() {
		IIterator theElements = theGameCollection.getIterator();
		Vector<GameObject> tmp = new Vector<GameObject>();
		Vector<GameObject> tmp2 = new Vector<GameObject>();

		while (theElements.hasNext()) {
			GameObject o = (GameObject) theElements.getNext();
			if (o.getIfCol() == true) {
				tmp.add(o);
			}
		}
		for (int i = 0; i < tmp.size(); i++) {
			for (int j = 1; j < tmp.size(); j++) {
				if (tmp.elementAt(i) != tmp.elementAt(j)) {
					// case 1 - Asteroid killed Asteroid
					if (tmp.elementAt(i).getHitCase() == 1 && tmp.elementAt(j).getHitCase() == 1) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						asteroidCollidedAsteroid(tmp.elementAt(i), tmp.elementAt(j));
						setAsteroidNum(getAsteroidNum() - 2);
					}
					// case 2 - Asteroid killed NPS
					if (tmp.elementAt(i).getHitCase() == 2 && tmp.elementAt(j).getHitCase() == 2) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						asteroidCollidedNPS(tmp.elementAt(i), tmp.elementAt(j));
						setAsteroidNum(getAsteroidNum() - 1);
						setNPSNum(getNPSNum() - 1);
					}
					// case 3 - Asteroid killed PS
					else if (tmp.elementAt(i).getHitCase() == 3 && tmp.elementAt(j).getHitCase() == 3) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						asteroidCrashedPS(tmp.elementAt(i), tmp.elementAt(j));
						setAsteroidNum(getAsteroidNum() - 1);
						setPSNum(getPSNum() - 1);
					}
					// case 4 - PS's Missile killed Asteroid
					else if (tmp.elementAt(i).getHitCase() == 4 && tmp.elementAt(j).getHitCase() == 4) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						psKilledAsteroid(tmp.elementAt(i), tmp.elementAt(j));
						setAsteroidNum(getAsteroidNum() - 1);
					}
					// case 5 - NPS's Missile killed PS
					else if (tmp.elementAt(i).getHitCase() == 5 && tmp.elementAt(j).getHitCase() == 5) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						npsMissileKilledPS(tmp.elementAt(i), tmp.elementAt(j));
					}
					// case 6 - PS's Missile killed NPS
					else if (tmp.elementAt(i).getHitCase() == 6 && tmp.elementAt(j).getHitCase() == 6) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						psKilledNPS(tmp.elementAt(i), tmp.elementAt(j));
						setNPSNum(getNPSNum() - 1);
					}
					// case 7 - PS Hit NPS
					else if (tmp.elementAt(i).getHitCase() == 7 && tmp.elementAt(j).getHitCase() == 7) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(j).setHitCase(-1);
						psHitNPS(tmp.elementAt(i), tmp.elementAt(j));
						setNPSNum(getNPSNum() - 1);
						setPSNum(getPSNum() - 1);
					}
					// case 8 - PS load missiles by colliding with Space Station
					else if (tmp.elementAt(i).getHitCase() == 8 && tmp.elementAt(j).getHitCase() == 8) {
						if (tmp.elementAt(i) instanceof SpaceStation) {
							if ((((SpaceStation) tmp.elementAt(i))).getBlinkLight() == true) {
								tmp.elementAt(i).setHitCase(-1);
								tmp.elementAt(j).setHitCase(-1);
								psLoadMissile();
							}
						}
						if (tmp.elementAt(j) instanceof SpaceStation) {
							if ((((SpaceStation) tmp.elementAt(j))).getBlinkLight() == true) {
								tmp.elementAt(i).setHitCase(-1);
								tmp.elementAt(j).setHitCase(-1);
								psLoadMissile();
							}
						}
					}
					// case 9 - PS's curve collide with other objects instead of space station
					else if (tmp.elementAt(i).getHitCase() == 9 && tmp.elementAt(j).getHitCase() == 9) {
						tmp.elementAt(i).setHitCase(-1);
						tmp.elementAt(i).setHitCase(-1);
						this.psCurveKilledObject(tmp.elementAt(i), tmp.elementAt(j));
						if (tmp.elementAt(i) instanceof Curve) {
							if (tmp.elementAt(j) instanceof NonPlayerShip) {
								setNPSNum(getNPSNum() - 1);
							} else if (tmp.elementAt(j) instanceof Asteroid) {
								setAsteroidNum(getAsteroidNum() - 1);
							} else if (tmp.elementAt(j) instanceof Missile) {
								Missile m = (Missile) tmp.elementAt(j);
								if (m.getMissileFlag() == 1) {

								}
							}
						}
						if (tmp.elementAt(j) instanceof Curve) {
							if (tmp.elementAt(i) instanceof NonPlayerShip) {
								setNPSNum(getNPSNum() - 1);
							} else if (tmp.elementAt(i) instanceof Asteroid) {
								setAsteroidNum(getAsteroidNum() - 1);
							} else if (tmp.elementAt(i) instanceof Missile) {
								Missile m = (Missile) tmp.elementAt(i);
								if (m.getMissileFlag() == 1) {

								}
							}
						}
					}
				}
			}
		}
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command p - print a display giving the current game state values, including
	// 1. current score (number of points the player has earned) 2. number of
	// missiles currently in the ship 3. current elapsed time
	public void printStateDisplay() {
		boolean isPlayerShip = false;
		// System.out.println();
		// System.out.println("---------------------------");
		// System.out.println("Your current score: " + getScore());

		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject o = (GameObject) theElements.getNext();
			if (o instanceof PlayerShip) {
				isPlayerShip = true;
				PlayerShip ps = (PlayerShip) o;
				System.out.println("The number of missiles your player ship currently have: " + ps.getMissileCount());
			}
		}
		if (isPlayerShip == false) {
			System.out.println("The number of missiles your player ship currently have: 0");
		}

		System.out.println("Your current elapsed time: " + getElapsedTime());
		System.out.println("-------------------------------");
		this.setChanged();
		notifyObservers(new GameWorldProxy(this));
	}

	// command m - print a "map" showing the current world state
	public void printMap() {
		System.out.println();
		System.out.println("------------------------------------------------------");
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			System.out.println(go);
		}
		System.out.println("------------------------------------------------------");
		System.out.println();
	}

	// command q - quit the game by first confirming the user's intent to wuit
	// before quitting
	public void quitGame() {
		Boolean bOk = Dialog.show("Confirm to quit", "Are you sure you want to quit?", "Yes", "Cancel");
		if (bOk) {
			Display.getInstance().exitApplication();
		}
	}

	public void init() {
		// initial game objects/setup

	}

	public void addObserver(Observable o) {
		myObserverList.add(o);
	}

	// Returns the game objects of the GameWorld
	public GameObjectCollection getGameObjects() {
		return theGameCollection;

	}

	// This method helps to check if game objects are out of the boundary
	// If the game object is out of boundary, it reset a random location to the game
	// object
	public void checkOutOfBoundary() {
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject o = (GameObject) theElements.getNext();
			double tmpX = o.getPointX();
			double tmpY = o.getPointY();
			if (o instanceof SpaceStation) {

			}
			if (o instanceof IMoveable) {
				MoveableObject mo = (MoveableObject) o;
				if (mo instanceof PlayerShip) {
					PlayerShip ps = (PlayerShip) mo;
					// hit the left screen
					if (tmpX - 85 < Game.getMapOriginX()) {
						mo.setObjectDirection(180 - mo.getObjectDirection());
						ps.rotate(-540 + mo.getObjectDirection() * 2);
						ps.setPSSMLDirection(ps.getPSSMLDirection() - 540 + mo.getObjectDirection() * 2);
					}
					// hit the right screen
					if (tmpX + 80 > (Game.getMapOriginX() + Game.getMapWidth())) {
						mo.setObjectDirection(540 - mo.getObjectDirection());
						ps.rotate(2 * mo.getObjectDirection() - 540);
						ps.setPSSMLDirection(ps.getPSSMLDirection() + 2 * mo.getObjectDirection() - 540);
					}
					// hit the top screen
					if (tmpY + 20 > (Game.getMapHeight() + Game.getMapOriginY())) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						ps.rotate(mo.getObjectDirection() * 2);
						ps.setPSSMLDirection(ps.getPSSMLDirection() + mo.getObjectDirection() * 2);
					}
					// hit the bottom screen
					if (tmpY - 120 < Game.getMapOriginY()) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						ps.rotate(-720 + mo.getObjectDirection() * 2);
						ps.setPSSMLDirection(ps.getPSSMLDirection() - 720 + mo.getObjectDirection() * 2);
					}
				} else if (mo instanceof NonPlayerShip) {
					NonPlayerShip nps = (NonPlayerShip) mo;
					// hit the left screen
					if (tmpX - 4 * nps.getObjectSize() < Game.getMapOriginX()) {
						mo.setObjectDirection(180 - mo.getObjectDirection());
						// nps.rotate(-540 + mo.getObjectDirection() * 2);
						System.out.println("case 1");
					}
					// hit the right screen
					if (tmpX + 4 * nps.getObjectSize() > (Game.getMapOriginX() + Game.getMapWidth())) {
						mo.setObjectDirection(540 - mo.getObjectDirection());
						// nps.rotate(mo.getObjectDirection() * 2);
						System.out.println("case 2");
					}
					// hit the top screen
					if (tmpY > (Game.getMapHeight() + Game.getMapOriginY())) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						// nps.rotate(mo.getObjectDirection() * 2);
						System.out.println("case 3");
					}
					// hit the bottom screen
					if (tmpY - 4 * nps.getObjectSize() < Game.getMapOriginY()) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						// nps.rotate(-720 + mo.getObjectDirection() * 2);
						System.out.println("case 4");
					}
					// ((NonPlayerShip) mo).rotate(mo.getObjectDirection());
				} else if (mo instanceof Asteroid) {
					Asteroid a = (Asteroid) mo;
					// hit the left screen
					if (tmpX - a.getObjectSize() / 2 < Game.getMapOriginX()) {
						mo.setObjectDirection(180 - mo.getObjectDirection());
					}
					// hit the right screen
					if (tmpX + a.getObjectSize() > (Game.getMapOriginX() + Game.getMapWidth())) {
						mo.setObjectDirection(540 - mo.getObjectDirection());
					}
					// hit the bottom screen
					if (tmpY + a.getObjectSize() > (Game.getMapHeight() + Game.getMapOriginY())) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
					}
					// hit the top screen
					if (tmpY + a.getObjectSize() < Game.getMapOriginY()) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
					}
				} else if (mo instanceof Missile) {
					Missile m = (Missile) mo;
					// hit the left screen
					if (tmpX < Game.getMapOriginX()) {
						mo.setObjectDirection(180 - mo.getObjectDirection());
						// m.rotate(-540+mo.getObjectDirection()*2);
					}
					// hit the right screen
					if (tmpX + 10 > (Game.getMapOriginX() + Game.getMapWidth())) {
						mo.setObjectDirection(540 - mo.getObjectDirection());
						// m.rotate(mo.getObjectDirection()*2);
					}
					// hit the top screen
					if (tmpY + 20 > (Game.getMapHeight() + Game.getMapOriginY())) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						// m.rotate(mo.getObjectDirection()*2);
					}
					// hit the bottom screen
					if (tmpY < Game.getMapOriginY()) {
						mo.setObjectDirection(360 - mo.getObjectDirection());
						// m.rotate(-720+mo.getObjectDirection()*2);
					}
				}
			}
		}
	}

	// fire missile sound
	public void fireMissileSound() {
		Sound s = new Sound("fireMissile.wav");
		s.play();
	}

	// load missile sound
	public void loadMissileSound() {
		Sound s = new Sound("loadMissile.wav");
		s.play();
	}

	public void mHitAsteroidSound() {
		Sound s = new Sound("missileHitAsteroid.wav");
		s.play();
	}

	public void pausePlayGame() {
		setBPause(!getBPause());
		if (getBPause()) {
			if (getSound()) {
				bgSound.pause();
			} else {

			}
		} else {
			if (getSound()) {
				bgSound.play();
			} else {

			}
		}
	}

	public void playSound() {
		setSound(true);
		bgSound.play();
	}

	public void pauseSound() {
		bgSound.pause();
	}

	// if PS's Missile is selected, refuel PS's missile fuel
	public void refuelPSMissile() {
		IIterator theElements = theGameCollection.getIterator();
		while (theElements.hasNext()) {
			GameObject o = (GameObject) theElements.getNext();
			if (o instanceof Missile) {
				Missile m = (Missile) o;
				if (m.getMissileFlag() == 0) {
					if (m.isSelected()) {
						m.setMissileFuel(10);
					}
				}
			}
		}
	}
}
