package me.miles.matthew.spaceflight.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public class CelestialBody extends PhysicsObject {
	private boolean canLandOn;
	private int colour;
	private double radius;
	private long randomSeed;
	private String name;
	
	private ArrayList<Point2D.Double> trail = new ArrayList<Point2D.Double>();
	private int maxTrailSize = 100;
	private float pointsPerSecond = 20f;
	private long timeSinceLastPointMillis = 0l;
	
	public final static int BLACK_HOLE = 0;
	public final static int STAR = 1;
	public final static int PLANET = 2;
	public final static int MOON = 3;
	public final static int ASTEROID = 4;
	
	private int type;
	

	public CelestialBody(double mass, double xPos, double yPos,
			boolean isLandable, int colour, double radius, String name, int type) {
		this(mass, new Vector2d(xPos, yPos), isLandable, colour, radius, name, type);
	}

	public CelestialBody(double mass, Vector2d position, boolean isLandable,
			int colour, double radius, String name, int type) {
		super(mass, position);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.radius = radius;
		this.name = name;
		this.randomSeed = (new Random()).nextLong();
		this.type = type;
	}
	
	public boolean isLandanble() {
		return canLandOn;
	}
	
	@Override
	public void draw(Graphics2D g2, double lX, double tY, double zoom) {
		//S/ystem.out.println(lX + " : " + tY);
		
		// update trail

		//S/ystem.out.println(timeSinceLastPointMillis/1000f+">="+1f/pointsPerSecond);
		
		if (timeSinceLastPointMillis/1000f >= 1f/pointsPerSecond) {
			timeSinceLastPointMillis = 0;
			trail.add(new Point2D.Double(this.position.x, this.position.y));
		}
		
		if (trail.size() > maxTrailSize) {
			trail.remove(0);
		}
		
		int[] xs = new int[trail.size()+1];
		int[] ys = new int[trail.size()+1];
		
		for (int i = 0; i < trail.size(); i++) {
			Point2D.Double point = trail.get(i);
			xs[i] = (int) Math.round((point.x-lX)*zoom);
			ys[i] = (int) Math.round((point.y-tY)*zoom);
		}
		
		xs[xs.length-1] = (int) Math.round((this.position.x-lX)*zoom);
		ys[ys.length-1] = (int) Math.round((this.position.y-tY)*zoom); 
		
		// draw trail
		g2.drawPolyline(xs, ys, xs.length);
		
		g2.setColor(new Color(this.colour));
		g2.fillOval(
				(int) Math.round((this.position.x-radius-lX)*zoom),
				(int) Math.round((this.position.y-radius-tY)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom));
		
		// if object is very small, circle and label it
		if (2*radius*zoom < 10 && (this.type <= 2 || 2*radius*zoom > 2)) {
			g2.setColor(new Color(0xAAAAAA));
			g2.setStroke(new BasicStroke(1));
			g2.drawOval((int) Math.round((this.position.x-lX)*zoom-10), (int) Math.round((this.position.y-tY)*zoom-10), 20, 20);
			g2.drawString(this.name, (int) Math.round((this.position.x-lX)*zoom+10), (int) Math.round((this.position.y-tY)*zoom-10));
		}
		
		
		
		//TODO: this code works but needs to actually have a texture assigned. Also V laggy with random numbers when zooming in so don't generate random every frame.
		/*
		BufferedImage m = new BufferedImage((int) (radius*zoom*2), (int) (radius*zoom*2), BufferedImage.TYPE_INT_ARGB);
		
		Random r = new Random(randomSeed);
		for (int x = 0; x < m.getWidth(); x++) {
			for (int y = 0; y < m.getHeight(); y++) {
				
			}
		}
		
		g2.drawImage(m, null, (int) ((this.position.x-radius-lX)*zoom), (int) ((this.position.y-radius-tY)*zoom));
		*/
	}

	@Override
	public void physicsTick(long timePassedMillis, long simulationSpeed) {
		super.physicsTick(timePassedMillis, simulationSpeed);
		timeSinceLastPointMillis += timePassedMillis;
	}

	// Getters and Setters

	public boolean isLandable() {
		return this.canLandOn;
	}

	public void setLandable(boolean canLandOn) {
		this.canLandOn = canLandOn;
	}

	public int getColour() {
		return this.colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public long getRandomSeed() {
		return this.randomSeed;
	}

	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isClickedOn(double lX, double tY, int xClick, int yClick, double zoom) {
		double distanceSquared = Math.pow((this.position.x-lX)*zoom, 2) + Math.pow((this.position.y-tY)*zoom, 2);
		System.out.println("Distance to "+this.name+" is "+(int) Math.sqrt(distanceSquared));
		return distanceSquared <= Math.pow(Math.max(20, zoom*this.radius), 2);
	}
}
