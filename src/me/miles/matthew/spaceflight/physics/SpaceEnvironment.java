package me.miles.matthew.spaceflight.physics;

import java.util.ArrayList;

public class SpaceEnvironment {
	ArrayList<PhysicsObject> bodies = new ArrayList<PhysicsObject>(); // stored in order of drawing
	int backgroundColour = 0x191D1F; // default
	
	public SpaceEnvironment() { }

	public ArrayList<PhysicsObject> getBodies() {
		return bodies;
	}
	
	public PhysicsObject[] getBodiesArray() {
		return (PhysicsObject[]) bodies.toArray();
	}

	public void setBodies(ArrayList<PhysicsObject> bodies) {
		this.bodies = bodies;
	}

	public int getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(int backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	
	public PhysicsObject getBodyAt(int val) {
		return bodies.get(val % bodies.size());
	}
	
	public int getBodyCount() {
		return bodies.size();
	}
	
	public void addBody(PhysicsObject object) {
		bodies.add(object);
	}
	
	public void addBody(CelestialBody body) {
		addBody((PhysicsObject) body);
	}
	
	public void removeBody(int pos) {
		bodies.remove(pos);
	}
	
	public void autoOrbit(CelestialBody satellite, CelestialBody centre) {
		// figure out the velocity required for the satellite to maintain a stable orbit
		double distance = Math.sqrt(Math.pow(satellite.getXPos()-centre.getXPos(), 2)+Math.pow(satellite.getYPos()-centre.getYPos(), 2));
		double velocity = Math.sqrt(PhysicsObject.GRAVITATIONAL_CONSTANT*centre.mass/distance);
		
		satellite.setXVel(centre.getXVel()+velocity);
		satellite.setYVel(centre.getYVel());
		// v = sqrt( GM/r );
	}
	
	public void physicsTick(long millisPassed, long simulationSpeed) {
		
		//S/ystem.out.println(1000f/millisPassed);
		//millisPassed /= 20d;
		for (int i = 0; i < getBodyCount(); i++) {
			PhysicsObject body = bodies.get(i);
			//body.setXVel(5);
			for (int n = 0; n < bodies.size(); n++) {
				if (i == n) continue;
				body.doGAcceleration(bodies.get(n), millisPassed, simulationSpeed);
			}
			
			body.physicsTick(millisPassed, simulationSpeed);
		}
	}
}
