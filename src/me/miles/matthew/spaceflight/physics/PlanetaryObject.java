package me.miles.matthew.spaceflight.physics;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlanetaryObject extends PhysicsObject {
	private boolean canLandOn;
	private int colour;
	private double radius;
	private String name;
	
	public PlanetaryObject(double mass, int xPos, int yPos, boolean isLandable, int colour, double radius, String name) {
		super(mass, xPos, yPos);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.radius = radius;
		this.name = name;
	}
	
	public boolean isLandanble() {
		return canLandOn;
	}
	
	@Override
	public void draw(Graphics2D g2, int lX, int tY, double zoom) {
		//System.out.println(lX + " : " + tY);
		g2.setColor(new Color(this.colour));
		g2.fillOval(
				(int) Math.round(-lX+(xPos-radius)*zoom),
				(int) Math.round(-tY+(yPos-radius)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom));
		
	}

	@Override
	public void physicsTick(long timePassedMillis) {
		super.physicsTick(timePassedMillis);
	}
}
