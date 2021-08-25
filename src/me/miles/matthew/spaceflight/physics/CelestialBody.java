package me.miles.matthew.spaceflight.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CelestialBody extends PhysicsObject {
	private boolean canLandOn;
	private int colour;
	private double radius;
	private long randomSeed;
	private String name;
	
	public CelestialBody(double mass, int xPos, int yPos, boolean isLandable, int colour, double radius, String name) {
		super(mass, xPos, yPos);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.radius = radius;
		this.name = name;
		this.randomSeed = (new Random()).nextLong();
	}
	
	public boolean isLandanble() {
		return canLandOn;
	}
	
	@Override
	public void draw(Graphics2D g2, double lX, double tY, double zoom) {
		//S/ystem.out.println(lX + " : " + tY);
		g2.setColor(new Color(this.colour));
		g2.fillOval(
				(int) Math.round((xPos-radius-lX)*zoom),
				(int) Math.round((yPos-radius-tY)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom));
		
		//TODO: this code works but needs to actually have a texture assigned. Also V laggy with random numbers when zooming in so don't generate random every frame.
		/*
		BufferedImage m = new BufferedImage((int) (radius*zoom*2), (int) (radius*zoom*2), BufferedImage.TYPE_INT_ARGB);
		
		Random r = new Random(randomSeed);
		for (int x = 0; x < m.getWidth(); x++) {
			for (int y = 0; y < m.getHeight(); y++) {
				
			}
		}
		
		g2.drawImage(m, null, (int) ((xPos-radius-lX)*zoom), (int) ((yPos-radius-tY)*zoom));
		*/
	}

	@Override
	public void physicsTick(long timePassedMillis) {
		super.physicsTick(timePassedMillis);
	}
}
