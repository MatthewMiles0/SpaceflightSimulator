package me.miles.matthew.spaceflight.physics;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlanetaryObject extends PhysicsObject {
	private boolean canLandOn;
	private int colour = 0x00AA55;
	private int radius;
	
	public PlanetaryObject(double mass, int xPos, int yPos, boolean isLandable, int colour, int radius) {
		super(mass, xPos, yPos);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.radius = radius;
	}
	
	public boolean isLandanble() {
		return canLandOn;
	}
	
	@Override
	public void draw(Graphics2D g2, int cX, int cY, double zoom) {
		g2.setColor(new Color(this.colour));
		g2.fillOval((int) Math.round(cX+(xPos-radius)*zoom),
				(int) Math.round(cY+(yPos-radius)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom));
		
	}

	@Override
	public void physicsTick(long timePassedMillis) {
		
	}
}
