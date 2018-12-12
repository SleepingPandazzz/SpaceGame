package com.mycompany.a3.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.a3.gameobjects.GameWorld;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.styledcomponents.customLabel;

public class PointsView extends Container implements Observer {
	private Label myPointsValueLabel;
	private Label myMissileCountLabel;
	private Label myTimeCountLabel;
	private Label mySoundOnOffLabel;
	private Label myLivesCountLabel;

	public PointsView(GameWorld gw) {
		// instantiate text labels
		Label myPointsLabel = new Label("Points: ");
		new customLabel(myPointsLabel);
		myPointsValueLabel = new Label("0000");

		Label myMissileLabel = new Label("Missile Count: ");
		new customLabel(myMissileLabel);
		myMissileCountLabel = new Label("N/A");

		Label mySoundLabel = new Label("Sound: ");
		new customLabel(mySoundLabel);
		mySoundOnOffLabel = new Label("OFF");

		Label myLives = new Label("Lives: ");
		new customLabel(myLives);
		myLivesCountLabel = new Label("3");

		Label myTimeLabel = new Label("Elapsed time: ");
		new customLabel(myTimeLabel);
		myTimeCountLabel = new Label("000000");

		// adding a container with a BoxLayout
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));

		// adding all labels in order
		myContainer.add(myPointsLabel);
		myContainer.add(myPointsValueLabel);
		myContainer.add(myMissileLabel);
		myContainer.add(myMissileCountLabel);
		myContainer.add(mySoundLabel);
		myContainer.add(mySoundOnOffLabel);
		myContainer.add(myLives);
		myContainer.add(myLivesCountLabel);
		myContainer.add(myTimeLabel);
		myContainer.add(myTimeCountLabel);

		this.add(myContainer);

	}

	// update labels from data in the Observable (a GameWorldProxy)
	public void update(Observable o, Object arg) {

		// casting arg as a GameWorld
		IGameWorld gw = (IGameWorld) arg;

		// getting player score
		int myScore = gw.getScore();
		myPointsValueLabel.setText(
				"" + (myScore > 999 ? "" : "0") + (myScore > 99 ? "" : "0") + (myScore > 9 ? "" : "0") + myScore);
		this.repaint();

		// getting missile count
		int myMissileCount = gw.getPSMissileCount();
		if (myMissileCount == -1) {
			myMissileCountLabel.setText("N/A");
		} else {
			myMissileCountLabel.setText(myMissileCount > 9 ? "010" : ("00" + myMissileCount));
		}
		this.repaint();

		// getting the sound
		boolean mySound = gw.getSound();
		mySoundOnOffLabel.setText(mySound == true ? "On " : "Off");
		this.repaint();

		// getting player Lives
		int myLives = gw.getPlayerLife();
		myLivesCountLabel.setText("" + myLives);
		this.repaint();

		// getting elapsed time
		int myTime = gw.getElapsedTime();
		myTimeCountLabel
				.setText("" + (myTime > 99999 ? "" : "0") + (myTime > 9999 ? "" : "0") + (myTime > 999 ? "" : "0") + (myTime > 99 ? "" : "0") + (myTime > 9 ? "" : "0") + myTime);
		this.repaint();
	}
}
