package me.miles.matthew.spaceflight.physics;

import java.awt.Graphics2D;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public abstract class PhysicsObject {
	public static final double GRAVITATIONAL_CONSTANT = 0.0000000000667430d; // nm^2/kg^2
	protected double radius; // m
	protected double mass; // kg
	
	protected Vector2d position; // m
	protected Vector2d velocity; // m/s
	
	/**
	 * Creates a new physics object
	 * @param mass The mass of the object
	 * @param xPos The x position of the object (Space coords)
	 * @param yPos The y position of the object (Space coords)
	 * @param radius The radius of the object
	 */
	public PhysicsObject(double mass, double xPos, double yPos, double radius) {
		this(mass, new Vector2d(xPos, yPos), radius);
	}

	/**
	 * Creates a new physics object
	 * @param mass The mass of the object
	 * @param position The position of the object (Space coords)
	 * @param radius The radius of the object
	 */
	public PhysicsObject(double mass, Vector2d position, double radius) {
		this.mass = mass;
		this.position = position;
		this.radius = radius;
		this.velocity = new Vector2d(0, 0);
	}
	
	/**
	 * Gets if the object is being clicked on for a mouse at a certain screen coordinate
	 * @param lX The left most x coordinate of the screen
	 * @param tY The top most y coordinate of the screen
	 * @param xClick The x coordinate of the mouse
	 * @param yClick The y coordinate of the mouse
	 * @param zoom The zoom of the screen
	 * @return If the object is being clicked on
	 */
	public abstract boolean isClickedOn(double lX, double tY, int xClick, int yClick, double zoom);
	
	/**
	 * Gets the mass of the object
	 * @return The mass of the object
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * Gets the mass of the object
	 * @param mass The mass of the object
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	/**
	 * Gets the x velocity of the object
	 * @return The x velocity of the object
	 */
	public double getXVel() {
		return velocity.x;
	}
	
	/**
	 * Sets the x velocity of the object
	 * @param xVel The x velocity of the object
	 */
	public void setXVel(double xVel) {
		velocity.x = xVel;
	}
	
	/**
	 * Gets the y velocity of the object
	 * @return The y velocity of the object
	 */
	public double getYVel() {
		return velocity.y;
	}
	
	/**
	 * Sets the y velocity of the object
	 * @param yVel The y velocity of the object
	 */
	public void setYVel(double yVel) {
		velocity.y = yVel;
	}

	/**
	 * Gets the radius of the object
	 * @return
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Sets the radius of the object
	 * @param radius The radius of the object
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Gets the x position of the object
	 * @return The x position of the object
	 */
	public double getXPos() {
		return position.x;
	}

	/**
	 * Sets the x position of the object
	 * @param xPos The x position of the object
	 */
	public void setXPos(double xPos) {
		this.position.x = xPos;
	}

	/**
	 * Gets the y position of the object
	 * @return The y position of the object
	 */
	public double getYPos() {
		return position.y;
	}

	/**
	 * Sets the y position of the object
	 * @param yPos The y position of the object
	 */
	public void setYPos(double yPos) {
		this.position.y = yPos;
	}
	
	/**
	 * Gets the position of the object
	 * @return The position of the object
	 */
	public Vector2d getPos() {
		return position;
	}
	
	/**
	 * Sets the position of the object
	 * @param xPos The x position of the object
	 * @param yPos The y position of the object
	 */
	public void setPos(double xPos, double yPos) {
		this.position = new Vector2d(xPos, yPos);
	}

	/**
	 * Sets the position of the object
	 * @param pos The position of the object
	 */
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
	public abstract void draw(Graphics2D g2, double lX, double tY, int windowWidth, int windowHeight, double zoom);
	
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
	 * Get the acceleration due to gravity of attraction to an object in m/s^2
	 * @return the acceleration in m/s^2
	 */
	public double getSurfaceAcceleration() {
		return PhysicsObject.GRAVITATIONAL_CONSTANT*mass/Math.pow(radius, 2);
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
	
	/**
	 * Applies acceleration towards another body within the environment
	 * Uses the equation F = G*(m1*m2)/(r^2)
	 * @param o the other object being attracted to
	 * @param timePassedMillis the time passed since the last update in milliseconds
	 * @param simulationSpeed the number of seconds passed in game per real world second
	 */
	public void doGAcceleration(PhysicsObject o, long timePassedMillis, long simulationSpeed) {
		double xD = Math.abs(this.position.x-o.getXPos());
		double yD = Math.abs(this.position.y-o.getYPos());
		
		// F = G*(m1*m2)/(r^2)
		// F = m*a
		// a = F/m
		// a = (G*m2)/(r^2)

		double linearAcceleration = PhysicsObject.GRAVITATIONAL_CONSTANT*o.getMass()/(xD*xD+yD*yD);
		
		double angle = getAngleTo(o.getXPos(), o.getYPos());
		
		double xAcc = linearAcceleration * Math.cos(angle);
		double yAcc = linearAcceleration * Math.sin(angle);
		
		velocity.x += xAcc*timePassedMillis*simulationSpeed/1000d;
		velocity.y += yAcc*timePassedMillis*simulationSpeed/1000d;
	}
	
	/**
	 * Applies movement over a certain time period, based on the real time passed and the simulation speed
	 * @param timePassedMillis the time passed since the last update in milliseconds
	 * @param simulationSpeed the number of seconds passed in the simulation per real world second
	 */
	public void physicsTick(long timePassedMillis, long simulationSpeed) {
		this.position.x += velocity.x*timePassedMillis*simulationSpeed/1000d;
		this.position.y += velocity.y*timePassedMillis*simulationSpeed/1000d;
	}
}
