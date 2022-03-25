package me.miles.matthew.spaceflight.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import me.miles.matthew.spaceflight.UI.DrawTools;
import me.miles.matthew.spaceflight.Utils.Vector2d;

public class CelestialBody extends PhysicsObject {
	private boolean canLandOn;
	private int colour;
	private long randomSeed;
	private String name;
	private BufferedImage texture = null;
	private double spinSpeed = 0;
	private double spinAngle = 0;
	private boolean focussed = false;

	private ArrayList<Point2D.Double> trail = new ArrayList<Point2D.Double>();
	private int maxTrailSize = 3000;
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
		super(mass, position, radius);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.name = name;
		this.randomSeed = (new Random()).nextLong();
		this.type = type;

		
		texture = DrawTools.fetchTexture("../Textures/SolarSystem/"+name.toLowerCase()+".png");

		if (!name.toLowerCase().equals("saturn"))
			spinSpeed = (new Random()).nextDouble() * 0.125d * Math.PI;
	}
	
	public boolean isLandanble() {
		return canLandOn;
	}
	
	@Override
	public void draw(Graphics2D g2, double lX, double tY, int windowWidth, int windowHeight, double zoom) {
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
		g2.setColor(new Color(this.colour));
		g2.drawPolyline(xs, ys, xs.length);
		
		// Draw the planet
		// g2.setColor(new Color(this.colour));
		if (this.texture == null || 2*radius*zoom < 5) {
			g2.fillOval(
				(int) Math.round((this.position.x-radius-lX)*zoom),
				(int) Math.round((this.position.y-radius-tY)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom));
		} else {
			// BufferedImage rotated = DrawTools.rotateImage(texture, spinAngle);
			g2.drawImage(texture/*rotated*/,
				(int) Math.round((this.position.x-radius-lX)*zoom),
				(int) Math.round((this.position.y-radius-tY)*zoom),
				(int) Math.round(2*radius*zoom),
				(int) Math.round(2*radius*zoom), null);
		}
		
		// if object is very small, circle and label it
		if (2*radius*zoom < 10 && (this.type <= 2 || 2*radius*zoom > 2)) {
			g2.setColor(new Color(0xAAAAAA));
			g2.setStroke(new BasicStroke(1));
			g2.drawOval((int) Math.round((this.position.x-lX)*zoom-10), (int) Math.round((this.position.y-tY)*zoom-10), 20, 20);
			g2.drawString(this.name, (int) Math.round((this.position.x-lX)*zoom+10), (int) Math.round((this.position.y-tY)*zoom-10));
		}
		
		if (this.focussed) {
			BufferedImage cardBackground = DrawTools.fetchTexture("../Textures/planetcard.png");
			// draw the image on the right side of the screen
			g2.drawImage(cardBackground, (int) (windowWidth-cardBackground.getWidth()), (int) (windowHeight/2 - cardBackground.getHeight()/2), null);
			g2.setColor(new Color(0xFFFFFF));
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.drawString(this.name, (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 40));
			g2.setFont(new Font("Arial", Font.PLAIN, 12));
			g2.drawString("Mass: " + this.mass + " kg", (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 60));
			g2.drawString("Radius: " + this.radius + " m", (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 80));
			g2.drawString("Surface gravity: " + Math.round(this.getSurfaceAcceleration()*100)/100d + " m/s²", (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 100));
			g2.drawString("Velocity: " + Math.round(Math.sqrt(this.getXVel()*this.getXVel()+this.getYVel()*this.getYVel())) + " m/s", (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 120));
			g2.drawString("You "+(this.isLandable() ? "can" : "can't")+" land on "+this.getName(), (int) (windowWidth-cardBackground.getWidth()+20), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 140));
			g2.drawImage(this.getTexture(), (int) (windowWidth-cardBackground.getWidth()+35), (int) (windowHeight/2 - cardBackground.getHeight()/2 + 140), 100, 100, null);
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
		spinAngle += spinSpeed * timePassedMillis / 1000d;
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


	public boolean isCanLandOn() {
		return this.canLandOn;
	}

	public boolean getCanLandOn() {
		return this.canLandOn;
	}

	public void setCanLandOn(boolean canLandOn) {
		this.canLandOn = canLandOn;
	}

	public BufferedImage getTexture() {
		return this.texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public double getSpinSpeed() {
		return this.spinSpeed;
	}

	public void setSpinSpeed(double spinSpeed) {
		this.spinSpeed = spinSpeed;
	}

	public double getSpinAngle() {
		return this.spinAngle;
	}

	public void setSpinAngle(double spinAngle) {
		this.spinAngle = spinAngle;
	}

	public boolean isFocussed() {
		return this.focussed;
	}

	public boolean getFocus() {
		return this.focussed;
	}

	public void setFocus(boolean isFocussed) {
		this.focussed = isFocussed;
	}

	public ArrayList<Point2D.Double> getTrail() {
		return this.trail;
	}

	public void setTrail(ArrayList<Point2D.Double> trail) {
		this.trail = trail;
	}

	public int getMaxTrailSize() {
		return this.maxTrailSize;
	}

	public void setMaxTrailSize(int maxTrailSize) {
		this.maxTrailSize = maxTrailSize;
	}

	public float getPointsPerSecond() {
		return this.pointsPerSecond;
	}

	public void setPointsPerSecond(float pointsPerSecond) {
		this.pointsPerSecond = pointsPerSecond;
	}

	public long getTimeSinceLastPointMillis() {
		return this.timeSinceLastPointMillis;
	}

	public void setTimeSinceLastPointMillis(long timeSinceLastPointMillis) {
		this.timeSinceLastPointMillis = timeSinceLastPointMillis;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	@Override
	/**
	 * Checks if the cursor is clicking the object.
	 * If the object is small enough, it will be checked if the cursor is clicking within 20 pixels of the object.
	 * @param lX The leftmost x coordinate of the screen.
	 * @param tY The topmost y coordinate of the screen.
	 * @param xClick The x coordinate of the cursor (screen space).
	 * @param yClick The y coordinate of the cursor (screen space).
	 * @param zoom The zoom level of the screen.
	 */
	public boolean isClickedOn(double lX, double tY, int xClick, int yClick, double zoom) {
		double distanceSquared = Math.pow((this.position.x-lX)*zoom - xClick, 2) + Math.pow((this.position.y-tY)*zoom - yClick, 2);
		// if (this.type == 1) S/ystem.out.println("Distance to "+this.name+" is "+(int) Math.sqrt(distanceSquared));
		return distanceSquared <= Math.pow(Math.max(20, zoom*this.radius), 2);
	}
}
