package me.miles.matthew.spaceflight.physics;

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
	
	public double getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public int[] getPos() {
		return new int[] {xPos, yPos};
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
	public abstract void draw(Graphics2D g2, double lX, double tY, double zoom);
	
	// METHODS
	
	/**
	 * Get the force due to gravity of attraction to an object in Newtons
	 * @param o the other object being attracted to
	 * @return the force in newtons which is experienced by each object
	 */
	public double getAttractionTo(PhysicsObject o) {
		// F = G[m1*m2/r^2]
		
		double distanceSquared = Math.pow(this.xPos-o.getXPos(), 2) + Math.pow(this.yPos-o.getYPos(), 2);
		
		return PhysicsObject.GRAVITATIONAL_CONSTANT*this.mass*o.getMass()/distanceSquared;
	}
	
	/**
	 * Returns the angle to another object in degrees
	 * @param targetX
	 * @param targetY
	 * @return angle in degrees
	 */
	public float getAngleTo(double targetX, double targetY) {
	    float angle = (float) Math.toDegrees(Math.atan2(targetY - this.getYPos(), targetX - this.getXPos()));

	    if (angle < 0) angle += 360;

	    return angle;
	}
	
	public void doGAcceleration(PhysicsObject o, long timePassedMillis) {
		double xD = Math.abs(this.xPos-o.getXPos());
		double yD = Math.abs(this.yPos-o.getYPos());
		
		double force = PhysicsObject.GRAVITATIONAL_CONSTANT*o.getMass()/(Math.sqrt(xD*xD+yD*yD));
		
		//double angle = getAngleTo(o.getXPos(), o.getYPos());
		
		//TODO: Trig
		double xAcc = force * xD / (xD + yD);
		double yAcc = force * yD / (xD + yD);
		
		/* This isn't how gravity works:
		double xAcc = massProd / Math.pow(this.xPos-o.getXPos(), 2);
		double yAcc = massProd / Math.pow(this.yPos-o.getYPos(), 2);
		*/
				
		if (this.mass == 13090000000000000000000d) System.out.println(xAcc);
		
		// F = G*(m1*m2)/(r^2)
		// F = m*a
		// a = F/m
		// a = (G*m2)/(r^2)
		
		xVel += xAcc*timePassedMillis/1000d;
		yVel += yAcc*timePassedMillis/1000d;
	}
	
	/*
	/**
	 * Gets the angle to another object in radians from π to -π,
	 * @param o
	 * @return angle in radians -π < θ < π
	 * /
	public double getAngleTo(PhysicsObject o) {
		double angle = Math.toDegrees(Math.atan2(o.getYPos() - yPos, o.getXPos() - xPos));
		
		return angle;
	}
	
	public void applyForceVector(double magnitude, double angle) {
		
	}
	
	public void applyFoceComponents(double x, double y) {
		
	}*/
	
	public void physicsTick(long timePassedMillis) {
		xPos += xVel*timePassedMillis/1000d;
		//S/ystem.out.println(xPos);
		yPos += yVel*timePassedMillis/1000d;
	};
	
}
