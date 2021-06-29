package me.miles.matthew.spaceflight.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import me.miles.matthew.spaceflight.physics.PhysicsObject;
import me.miles.matthew.spaceflight.physics.PlanetaryObject;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

@SuppressWarnings("serial")
public class SpaceView extends JPanel {
	private SpaceEnvironment mySpace;
	
	public SpaceView(SpaceEnvironment space) {
		this.mySpace = space;
		
		// TEST
		mySpace.addBody((PhysicsObject) new PlanetaryObject(1000000d, 100, 200, true, 0xAF2109, 100));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(mySpace.getBackgroundColour()));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < mySpace.getBodyCount(); i++) {
			mySpace.getBodyAt(i).draw(g2, this.getWidth()/2, this.getHeight()/2, 0.5d);
		}
	}
}
