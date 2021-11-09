package me.miles.matthew.spaceflight.physics;

import java.awt.Graphics2D;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public abstract class PhysicsObject {
	public static double GRAVITATIONAL_CONSTANT = 0.0000000000667430d; // nm^2/kg^2
	
	protected double mass; // kg
	
	protected Vector2d position; // m

	protected double xVel; // m/s
	protected double yVel; // m/s
	
	// Setters and getters
	
	public PhysicsObject(double mass, double xPos, double yPos) {
		this(mass, new Vector2d(xPos, yPos));
	}

	public PhysicsObject(double mass, Vector2d position) {
		this.mass = mass;
		this.position = position;
	}
	
	public abstract boolean isClickedOn(double lX, double tY, int xClick, int yClick, double zoom);
	
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
		return position.x;
	}

	public void setXPos(double xPos) {
		this.position.x = xPos;
	}

	public double getYPos() {
		return position.y;
	}

	public void setYPos(double yPos) {
		this.position.y = yPos;
	}
	
	public Vector2d getPos() {
		return position;
	}
	
	public void setPos(double xPos, double yPos) {
		this.position = new Vector2d(xPos, yPos);
	}

	public void setPos(Vector2d pos) {
		this.position = pos;
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
		
		double distanceSquared = Math.pow(this.position.x-o.getXPos(), 2) + Math.pow(this.position.y-o.getYPos(), 2);
		
		return PhysicsObject.GRAVITATIONAL_CONSTANT*this.mass*o.getMass()/distanceSquared;
	}
	
	/**
	 * Returns the angle to another object in degrees
	 * @param targetX
	 * @param targetY
	 * @return angle in degrees
	 */
	public double getAngleTo(double targetX, double targetY) {
	    double angle = Math.atan2(targetY - this.getYPos(), targetX - this.getXPos());

	    return angle;
	}
	
	public void doGAcceleration(PhysicsObject o, long timePassedMillis, long simulationSpeed) {
		double xD = Math.abs(this.position.x-o.getXPos());
		double yD = Math.abs(this.position.y-o.getYPos());
		
		double force = PhysicsObject.GRAVITATIONAL_CONSTANT*o.getMass()/(xD*xD+yD*yD);
		
		double angle = getAngleTo(o.getXPos(), o.getYPos());
		
		double xAcc = force * Math.cos(angle);
		double yAcc = force * Math.sin(angle);
		
		/* This isn't how gravity works:
		double xAcc = massProd / Math.pow(this.xPos-o.getXPos(), 2);
		double yAcc = massProd / Math.pow(this.yPos-o.getYPos(), 2);
		*/
		
		if (this.mass == 1586000000000000000000d) System.out.println(angle);
		
		// F = G*(m1*m2)/(r^2)
		// F = m*a
		// a = F/m
		// a = (G*m2)/(r^2)
		
		xVel += xAcc*timePassedMillis*simulationSpeed/1000d;
		yVel += yAcc*timePassedMillis*simulationSpeed/1000d;
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
	
	public void physicsTick(long timePassedMillis, long simulationSpeed) {
		this.position.x += xVel*timePassedMillis*simulationSpeed/1000d;
		//S/ystem.out.println(xPos);
		this.position.y += yVel*timePassedMillis*simulationSpeed/1000d;
	};
	
}
