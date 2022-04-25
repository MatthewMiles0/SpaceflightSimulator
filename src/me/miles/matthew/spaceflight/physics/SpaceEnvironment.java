package me.miles.matthew.spaceflight.physics;

import java.util.ArrayList;

public class SpaceEnvironment {
	ArrayList<PhysicsObject> bodies = new ArrayList<PhysicsObject>(); // stored in order of drawing
	int backgroundColour = 0x191D1F; // default

	/**
	 * Creates a new space environment
	 */
	public SpaceEnvironment() { }

	/**
	 * Adds a new body to the environment
	 * @param body The body to add
	 */
	public ArrayList<PhysicsObject> getBodies() {
		return bodies;
	}
	
	/**
	 * Get an array of all bodies in the environment
	 * @return An array of all bodies in the environment
	 */
	public PhysicsObject[] getBodiesArray() {
		return (PhysicsObject[]) bodies.toArray();
	}

	/**
	 * Sets the entire list of bodies in the environment
	 * @param bodies The list of bodies to set
	 */
	public void setBodies(ArrayList<PhysicsObject> bodies) {
		this.bodies = bodies;
	}

	/**
	 * get the background colour
	 * @return The background colour
	 */
	public int getBackgroundColour() {
		return backgroundColour;
	}

	/**
	 * Set the background colour
	 * @param backgroundColour The colour to set
	 */
	public void setBackgroundColour(int backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	
	/**
	 * Gets a body based on the order it was added to the environment
	 * @param index The index of the body to get
	 */
	public PhysicsObject getBodyAt(int index) {
		return bodies.get(index % bodies.size());
	}
	
	/**
	 * Get the number of bodies in the environment
	 * @return The number of bodies in the environment
	 */
	public int getBodyCount() {
		return bodies.size();
	}
	
	/**
	 * Add a body to the environment
	 * @param object The body to add
	 */
	public void addBody(PhysicsObject object) {
		bodies.add(object);
	}
	
	/**
	 * Add a body to the environment
	 * @param object The body to add
	 */
	public void addBody(CelestialBody body) {
		addBody((PhysicsObject) body);
	}
	
	/**
	 * Remove a body from the environment
	 * @param object The index of the body to remove
	 */
	public void removeBody(int pos) {
		bodies.remove(pos);
	}
	
	/**
	 * Automatically gives the necessary x and y velocity to orbit a body
	 * @param satellite The satellite being put in orbit
	 * @param centre The body being orbited
	 */
	public void autoOrbit(CelestialBody satellite, CelestialBody centre) {
		// figure out the velocity required for the satellite to maintain a stable orbit

		// find the distance from the centre to the satellite
		double distance = Math.sqrt(Math.pow(satellite.getXPos()-centre.getXPos(), 2)
			+ Math.pow(satellite.getYPos()-centre.getYPos(), 2));
		// v = sqrt( GM/r );
		double velocity = Math.sqrt(PhysicsObject.GRAVITATIONAL_CONSTANT*centre.mass/distance);
		
		// set the x and y velocities appropriately
		satellite.setXVel(centre.getXVel()+velocity);
		satellite.setYVel(centre.getYVel());
	}

	/**
	 * Automatically gives the necessary x and y velocity to orbit a body which is in turn orbiting another body
	 * @param satellite The satellite being put in orbit
	 * @param centre The body being orbited
	 * @param master The body that is being orbited by the body the satellite is orbitting
	 */
	public void autoDoubleOrbit(CelestialBody satellite, CelestialBody centre, CelestialBody master) {
		// figure out the velocity required for the satellite to maintain a stable orbit
		autoOrbit(satellite, master);

		double distance = Math.sqrt(Math.pow(satellite.getXPos()-centre.getXPos(), 2)+Math.pow(satellite.getYPos()-centre.getYPos(), 2));
		double velocity = Math.sqrt(PhysicsObject.GRAVITATIONAL_CONSTANT*centre.mass/distance);
		
		satellite.setXVel(satellite.getXVel()+velocity);
		satellite.setYVel(satellite.getYVel());
		// v = sqrt( GM/r );
	}
	
	/**
	 * Updates the environment and applies acceleration after a certain time passed
	 * @param millisPassed The time passed since the last update
	 * @param simulationSpeed The speed of the simulation
	 */
	public void physicsTick(long millisPassed, long simulationSpeed) {
		for (int i = 0; i < getBodyCount(); i++) {
			PhysicsObject body = bodies.get(i);
			
			for (int n = 0; n < bodies.size(); n++) {
				if (i == n) continue;
				body.doGAcceleration(bodies.get(n), millisPassed, simulationSpeed);
			}
			
			body.physicsTick(millisPassed, simulationSpeed);
		}
	}
}
