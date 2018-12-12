package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.commands.*;
import com.mycompany.a3.gameobjects.*;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.styledcomponents.customButton;
import com.mycompany.a3.views.*;

public class Game extends Form implements Runnable {
	private GameWorld gw; // reference to a GameWorld
	private static MapView mv; // reference to a MapView
	private PointsView pv; // reference to a PointsView
	private Container leftContainer;
	private static int h;
	// public BGSound bgSound = new
	// BGSound("384583__coolrealistic__atmosphere-1.wav");

	public Game() {
		gw = new GameWorld(); // create "Observable"
		mv = new MapView(gw); // create an "Observer" for the map
		mv.getAllStyles().setBorder(Border.createLineBorder(2));
		mv.getAllStyles().setBgColor(ColorUtil.rgb(0,0,0));
		mv.getAllStyles().setBgTransparency(255);
		pv = new PointsView(gw); // create an "Observer" for the points;
		pv.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		pv.getAllStyles().setBgTransparency(255);
		gw.addObserver(mv); // register the map Observer
		gw.addObserver(pv); // register the points Observer

		/* A form with a Toolbar */
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);

		// the content for the overflow menu

		// New
		Command newFile = new Command("New");
		myToolbar.addCommandToOverflowMenu(newFile);

		// Save
		Command saveFile = new Command("Save");
		myToolbar.addCommandToOverflowMenu(saveFile);

		// Undo
		Command undoFile = new Command("Undo");
		myToolbar.addCommandToOverflowMenu(undoFile);

		// onOffSound
		// Command onOffSound = new Command("Sound");
		// myToolbar.addCommandToOverflowMenu(onOffSound);

		// About
		// Command aboutGame = new Command("About");
		// myToolbar.addCommandToOverflowMenu(aboutGame);

		// Quit
		// Command quitGame = new Command("Quit");
		// myToolbar.addCommandToOverflowMenu(quitGame);

		// the content for the hambuger menu
		// this is the file menu
		Command myFile = new Command("File");
		myToolbar.addCommandToSideMenu(myFile);

		// Button newFileButton - create new file
		Button newFileButton = new Button("New");
		newFileButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		myFile.putClientProperty("SideComponent", newFileButton);
		myToolbar.addComponentToSideMenu(newFileButton);

		// Button saveFileButton - save file
		Button saveFileButton = new Button("Save");
		saveFileButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		myFile.putClientProperty("SideComponent", saveFileButton);
		myToolbar.addComponentToSideMenu(saveFileButton);

		// Button undoButton - undo
		Button undoButton = new Button("Undo");
		undoButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		myFile.putClientProperty("SideComponent", undoButton);
		myToolbar.addComponentToSideMenu(undoButton);

		// CheckBox soundCheckBox - include a check box showing the current state of the
		// "sound" attribute
		CheckBox soundCheckBox = new CheckBox("Sound On");
		soundCheckBox.getAllStyles().setFgColor(ColorUtil.WHITE);
		soundCheckBox.setSelected(false);
		SoundCommand mySoundCommand = new SoundCommand(gw);
		soundCheckBox.setCommand(mySoundCommand);
		myFile.putClientProperty("SideComponent", soundCheckBox);
		myToolbar.addComponentToSideMenu(soundCheckBox);

		// Button aboutButton - display a dialog box giving game information
		Button aboutButton = new Button("About");
		aboutButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		aboutButton.addActionListener((e) -> Dialog.show("About",
				"Author: Tingting He\n" + "Course: CSC133-05\nVersion 3\nProfessor: Doan Nguyen\n", "Ok", null));
		myFile.putClientProperty("SideComponent", aboutButton);
		myToolbar.addComponentToSideMenu(aboutButton);

		// Button quitButton - prompt graphically for confirmation and then exit the
		// program
		Button quitButton = new Button("Quit");
		quitButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		QuitGameCommand myQuitGameCommand = new QuitGameCommand(gw);
		quitButton.setCommand(myQuitGameCommand);
		myFile.putClientProperty("SideComponent", quitButton);
		myToolbar.addComponentToSideMenu(quitButton);

		// Command menu: invoke the commands just as if the button of the same name had
		// been pushed
		Command myCommands = new Command("Commands");
		myToolbar.addCommandToSideMenu(myCommands);

		// Button pp - pause or play the game
		Button pp = new Button("Pause/Play");
		pp.getAllStyles().setFgColor(ColorUtil.WHITE);
		PausePlayCommand myPausePlayCommand = new PausePlayCommand(gw);
		pp.setCommand(myPausePlayCommand);
		myCommands.putClientProperty("SideComponent", pp);
		myToolbar.addComponentToSideMenu(pp);

		// Button < - turn player ship's missile launcher left
		Button mll = new Button();
		mll.getAllStyles().setFgColor(ColorUtil.WHITE);
		PSMLTurnLeftCommand myPSMLTurnLeftCommand = new PSMLTurnLeftCommand(gw);
		mll.setCommand(myPSMLTurnLeftCommand);
		myCommands.putClientProperty("SideComponent", mll);
		myToolbar.addComponentToSideMenu(mll);

		// Button > - turn player ship's missile launcher right
		Button mlr = new Button();
		mlr.getAllStyles().setFgColor(ColorUtil.WHITE);
		PSMLTurnRightCommand myPSMLTurnRightCommand = new PSMLTurnRightCommand(gw);
		mlr.setCommand(myPSMLTurnRightCommand);
		myCommands.putClientProperty("SideComponent", mlr);
		myToolbar.addComponentToSideMenu(mlr);
		
		// Button w - shoot a bezier curves
		Button mw = new Button();
		mw.getAllStyles().setFgColor(ColorUtil.WHITE);
		BezierCurvesCommand myBezierCurvesCommand = new BezierCurvesCommand(gw);
		mw.setCommand(myBezierCurvesCommand);
		myCommands.putClientProperty("SideComponent", mw);
		myToolbar.addComponentToSideMenu(mw);

		// Button q - quit the game
		Button q = new Button("Quit Game");
		q.getAllStyles().setFgColor(ColorUtil.WHITE);
		q.setCommand(myQuitGameCommand);
		myCommands.putClientProperty("SideComponent", q);
		myToolbar.addComponentToSideMenu(q);

		Label gameTitle = new Label("Asteroid Game");
		gameTitle.getAllStyles().setFgColor(ColorUtil.BLACK);
		gameTitle.getAllStyles().setPadding(1, 1, 10, 10);
		myToolbar.setTitleComponent(gameTitle);
		/* Code end here for a form with a Toolbar */

		this.setLayout(new BorderLayout());

		// adding PointsView and MapView to the Form
		this.add(BorderLayout.NORTH, pv);
		mv.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		this.add(BorderLayout.CENTER, mv);
		// create timer and provide a runnable
		UITimer timer = new UITimer(this);
		// make the timer tick every second and bind it to this form
		timer.schedule(200, true, this);

		// left Container with the BoxLayout positioned on the west
		leftContainer = new Container();

		leftContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.setScrollableY(true);

		leftContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		leftContainer.getAllStyles().setBgColor(ColorUtil.BLACK);
		leftContainer.getAllStyles().setBgTransparency(255);
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.GRAY));
		
		Label cm = new Label("Commands: ");
		cm.getAllStyles().setPadding(Component.TOP, 0);
		cm.getAllStyles().setPadding(Component.BOTTOM, 0);
		leftContainer.add(cm);

		// Button i - increase the speed of the player ship by 1
		Button c_i = new Button();
		AccelerateCommand myAccelerateCommand = new AccelerateCommand(gw);
		c_i.setCommand(myAccelerateCommand);
		new customButton(c_i);
		leftContainer.add(c_i);
		// binding the up arrow to increase the speed of the player ship
		addKeyListener(-91, myAccelerateCommand);

		// Button d - decrease the speed of the player ship by 1
		Button c_d = new Button();
		DecelerateCommand myDecelerateCommand = new DecelerateCommand(gw);
		c_d.setCommand(myDecelerateCommand);
		new customButton(c_d);
		leftContainer.add(c_d);
		// binding the down arrow to decrease the speed of the player ship
		addKeyListener(-92, myDecelerateCommand);

		// Button l - turn player ship left
		Button c_l = new Button();
		PSTurnLeftCommand myPSTurnLeftCommand = new PSTurnLeftCommand(gw);
		c_l.setCommand(myPSTurnLeftCommand);
		new customButton(c_l);
		leftContainer.add(c_l);
		// binding the left arrow to turn the player ship left
		addKeyListener(-93, myPSTurnLeftCommand);

		// Button r - turn the player ship right
		Button c_r = new Button();
		PSTurnRightCommand myPSTurnRightCommand = new PSTurnRightCommand(gw);
		c_r.setCommand(myPSTurnRightCommand);
		new customButton(c_r);
		leftContainer.add(c_r);
		// binding the right arrow to turn the player ship right
		addKeyListener(-94, myPSTurnRightCommand);

		// Button < - turn player ship's missile launcher left
		Button c_mll = new Button();
		c_mll.setCommand(myPSMLTurnLeftCommand);
		new customButton(c_mll);
		leftContainer.add(c_mll);
		// binding the ',' to turn player ship's missile launcher left
		addKeyListener(44, myPSMLTurnLeftCommand);

		// Button > - turn player ship's missile launcher right
		Button c_mlr = new Button();
		c_mlr.setCommand(myPSMLTurnRightCommand);
		new customButton(c_mlr);
		leftContainer.add(c_mlr);
		// binding the '.' to turn player ship's missile launcher right
		addKeyListener(46, myPSMLTurnRightCommand);

		// Button f - fire a missile out the front of the PS
		Button c_f = new Button();
		PSFireCommand myPSFireCommand = new PSFireCommand(gw);
		c_f.setCommand(myPSFireCommand);
		new customButton(c_f);
		leftContainer.add(c_f);
		// binding the space bar to fire a ship missile, this also binds the enter key
		addKeyListener(-90, myPSFireCommand);
		
		// Button w - fire a bezier curves out the front of the ps
		Button c_w = new Button();
		c_w.setCommand(myBezierCurvesCommand);
		new customButton(c_w);
		leftContainer.add(c_w);
		// binding the 'w' to fire a bezier curve
		addKeyListener('w', myBezierCurvesCommand);

		// Button j - the player ship instantly jump through hyperspace
		Button c_j = new Button();
		JumpCommand myJumpCommand = new JumpCommand(gw);
		c_j.setCommand(myJumpCommand);
		new customButton(c_j);
		leftContainer.add(c_j);
		// binding the 'j' to instantly jump through hyperspace
		addKeyListener('j', myJumpCommand);

		// Button p - pause or play the game
		Button c_p = new Button();
		c_p.setCommand(myPausePlayCommand);
		new customButton(c_p);
		leftContainer.add(c_p);
		// binding the 'p' to pause or play the game
		addKeyListener('p', myPausePlayCommand);
		
		// Button rm - refuel PS's Missile Fuel
		Button c_rm = new Button();
		Command myRefuelCommand=new RefuelCommand(gw);
		c_rm.setCommand(myRefuelCommand);
		new customButton(c_rm);
		leftContainer.add(c_rm);
		

		// Button q - ask if quit the game
		Button c_q = new Button();
		c_q.setCommand(myQuitGameCommand);
		new customButton(c_q);
		leftContainer.add(c_q);

		leftContainer.getAllStyles().setPadding(Component.TOP, 10);

		this.add(BorderLayout.WEST, leftContainer);
		this.show();
		
		h=this.getHeight();

		System.out.println("width, " + getMapWidth());
		System.out.println("height, " + getMapHeight());
		System.out.println("ox, " + getMapOriginX());
		System.out.println("oy, " + getMapOriginY());
		System.out.println(this.getWidth());
		System.out.println(getTotalHeight());
	}

	public static int getMapWidth() {
		return mv.getWidth();
	}

	public static int getMapHeight() {
		return mv.getHeight();
	}

	public static int getMapOriginX() {
		return mv.getX();
	}

	public static int getMapOriginY() {
		return mv.getY();
	}
	
	
	public static int getTotalHeight() {
		return h;
	}

	// this method is entered on each Time tick; it moves the objects the object,
	// checks for collision and invokes the collision handler, then repaints the
	// display panel.
	@Override
	public void run() {
//		IIterator gameIteratorr = gw.getGameObjects().getIterator();
//		while (gameIteratorr.hasNext()) {
//			GameObject go=gameIteratorr.getNext();
//			if(go instanceof NonPlayerShip) {
//				((NonPlayerShip) go).updateLTs();
//				mv.repaint();
//			}
//		}
		if (gw.getBPause() == false) {
			if (gw.getPSNum() == 0) {
				gw.addPlayerShip();
				gw.setPSNum(1);
			}
			// if SpaceStation is less than 2, it automatically add Space Station
			if (gw.getSSNum() < 2) {
				gw.addBlinkingSpaceStation();
				gw.setSSNum(gw.getSSNum() + 1);
			}
			// add 10 star
			if(gw.getSNum()<10) {
				gw.setSNum(10);
				for(int i=0;i<gw.getSNum();i++) {
					gw.addStar();
				}
			}
			IIterator gameIterator = gw.getGameObjects().getIterator();
			while (gameIterator.hasNext()) {
				ICollider curObj = (ICollider) gameIterator.getNext(); // get a collidable object
				// check if this object collides with any other object
				IIterator gameIterator2 = gw.getGameObjects().getIterator();
				while (gameIterator2.hasNext()) {
					ICollider otherObj = (ICollider) gameIterator2.getNext(); // get a collidable obejct
					if (otherObj != curObj) { // make sure it's not the same object
						if (curObj.collidesWith(otherObj)) {
							curObj.handleCollision(otherObj);
						}
					}
				}
			}
			gw.removeColObjects();
			gw.gameClockTicked();
		} else {

		}
		mv.repaint(); // redraw the world

	}
}
