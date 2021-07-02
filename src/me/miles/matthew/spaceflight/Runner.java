package me.miles.matthew.spaceflight;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

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
		window = new JFrame();
		window.setSize(1000, 1000);
		window.setTitle("Spaceflight Simulator");
		
		env = new SpaceEnvironment();
		view = new SpaceView(env);
		window.add(view);
		
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		physicsUpdate = new Timer(14, this);
		physicsUpdate.start();
		
		frameUpdate = new Timer(14, this);
		frameUpdate.start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == physicsUpdate) {
			last = now;
			now = Instant.now().toEpochMilli();
			long timePassedMillis = now-last;
			//S/ystem.out.println(timePassedMillis/1000d);
			env.physicsTick(timePassedMillis);
		} else if (ae.getSource() == frameUpdate) {
			view.refresh();
		}
	}
}
