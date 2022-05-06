package me.miles.matthew.spaceflight;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.Instant;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

import me.miles.matthew.spaceflight.UI.SpaceView;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

public class Runner implements ActionListener {
	JFrame window;
	SpaceView view;
	SpaceEnvironment env;
	Timer physicsUpdate;
	Timer frameUpdate;
	long lastPhysicsTickMillis = Instant.now().toEpochMilli();
	
	public static void main(String[] args) {
		new Runner();
	}
	
	/**
	 * Creates a new runner
	 */
	public Runner() {
		// Create JFrame window
		window = new JFrame();
		window.setSize(1200, 800);
		window.setTitle("Spaceflight Simulator");
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initiate space environment
		env = new SpaceEnvironment();
		view = new SpaceView(env);
		window.add(view);
		
		// Window icon image
		URL imgURL = getClass().getResource("/me/miles/matthew/spaceflight/space1.png");
		ImageIcon icon = new ImageIcon(imgURL);
		window.setIconImage(icon.getImage());
		
		// Timers
		physicsUpdate = new Timer(7, this);
		physicsUpdate.start();
		
		frameUpdate = new Timer(14, this);
		lastPhysicsTickMillis = Instant.now().toEpochMilli();
		frameUpdate.start();

		// Start rendering
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == physicsUpdate) {
			// Calculate the time since the last physics update
			
			long now = Instant.now().toEpochMilli();
			long timePassedMillis = now-lastPhysicsTickMillis;
			
			// If the time passed is too large, don't update
			if (timePassedMillis > 1000) {
				timePassedMillis = 1;
				System.out.println("Took over a second to perform the previous tick!");
			}
			env.physicsTick(timePassedMillis, 100000/**100*/);
			lastPhysicsTickMillis = now;
		} else if (ae.getSource() == frameUpdate) {
			// Update the view
			view.refresh();
		}
	}
}
