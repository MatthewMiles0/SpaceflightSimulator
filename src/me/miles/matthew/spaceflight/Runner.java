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
	long last = Instant.now().toEpochMilli();
	long now = 0;
	
	public static void main(String[] args) {
		new Runner();
	}
	
	public Runner() {
		
		// Create window
		window = new JFrame();
		window.setSize(1000, 1000);
		window.setTitle("Spaceflight Simulator");
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initiate space stuff
		env = new SpaceEnvironment();
		view = new SpaceView(env);
		window.add(view);
		
		// Icon image
		URL imgURL = getClass().getResource("/me/miles/matthew/spaceflight/space1.png");
		ImageIcon icon = new ImageIcon(imgURL);
		window.setIconImage(icon.getImage());
		
		// Timers
		physicsUpdate = new Timer(7, this);
		physicsUpdate.start();
		
		frameUpdate = new Timer(14, this);
		frameUpdate.start();

		// Start rendering
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == physicsUpdate) {
			last = now;
			now = Instant.now().toEpochMilli();
			long timePassedMillis = now-last;
			
			if (timePassedMillis > 1000) {
				timePassedMillis = 1;
				System.out.println("Took over a second to perform the previous tick!");
			}
			//S/ystem.out.println(timePassedMillis/1000d);
			env.physicsTick(timePassedMillis, 1000000);
		} else if (ae.getSource() == frameUpdate) {
			view.refresh();
		}
	}
}
