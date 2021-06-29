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
	
	public void removeBody(int pos) {
		bodies.remove(pos);
	}
}
