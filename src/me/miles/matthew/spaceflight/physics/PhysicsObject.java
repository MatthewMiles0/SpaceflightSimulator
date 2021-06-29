package me.miles.matthew.spaceflight.physics;

import java.awt.Dimension;
import java.awt.Graphics2D;

public abstract class PhysicsObject {
	public static double GRAVITATIONAL_CONSTANT = 0.0000000000667430d; // nm^2/kg^2
	
	protected double mass; // kg
	
	protected int xPos;
	protected int yPos;

	protected double xVel; // m/s
	protected double yVel; // m/s
	
	// Setters and getters
	
	public PhysicsObject(double mass, int xPos, int yPos) {
		this.mass = mass;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public double getXVel() {
		return xVel;
	}
	
	public void setXVel(double xVel) {
		this.xVel = xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	public void setYVel(double yVel) {
		this.yVel = yVel;
	}
	
	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public Dimension getPos() {
		return new Dimension(xPos, yPos);
	}
	
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Draws the object on screen with set screen centre position cX, cY and zoom
	 * @param g2
	 * @param cX
	 * @param cY
	 * @param zoom
	 */
	public abstract void draw(Graphics2D g2, int cX, int cY, double zoom);
	
	// METHODS
	
	/**
	 * Get the force dute to gravity of attraction to an object in Newtons
	 * @param o the other object being attracted to
	 * @return the force in newtons which is felt by each object
	 */
	public double getAttractionTo(PhysicsObject o) {
		// F = G[m1*m2/r^2]
		
		double distanceSquared = Math.pow(this.xPos-o.getXPos(), 2) + Math.pow(this.yPos-o.getYPos(), 2);
		
		return PhysicsObject.GRAVITATIONAL_CONSTANT*this.mass*o.getMass()/distanceSquared;
	}
	
	public abstract void physicsTick(long timePassedMillis);
	
}
