package me.miles.matthew.spaceflight.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.miles.matthew.spaceflight.Utils.DrawTools;
import me.miles.matthew.spaceflight.Utils.Queue;
import me.miles.matthew.spaceflight.Utils.Vector2d;

public class CelestialBody extends PhysicsObject {
	private boolean canLandOn;
	private int colour;
	private String name;
	private BufferedImage texture = null;
	private boolean focussed = false;

	private Queue<Vector2d> trail = new Queue<Vector2d>();
	private int trailSize = 3000;
	private float pointsPerSecond = 20f;
	private long timeSinceLastPointMillis = 0l;
	
	public final static int BLACK_HOLE = 0;
	public final static int STAR = 1;
	public final static int PLANET = 2;
	public final static int MOON = 3;
	public final static int ASTEROID = 4;
	
	private int type;
	
	/**
	 * Creates a new celestial body
	 * @param mass the mass of the celestial body
	 * @param xPos the x position of the celestial body (Space coordinates)
	 * @param yPos the y position of the celestial body (Space coordinates)
	 * @param isLandable whether or not the celestial body could be landed on
	 * @param colour the colour of the celestial body
	 * @param radius the radius of the celestial body
	 * @param name the name of the celestial body
	 * @param type the type of celestial body
	 */
	public CelestialBody(double mass, double xPos, double yPos,
			boolean isLandable, int colour, double radius, String name, int type) {
		this(mass, new Vector2d(xPos, yPos), isLandable, colour, radius, name, type);
	}

	/**
	 * Creates a new celestial body
	 * @param mass the mass of the celestial body
	 * @param position the position of the celestial body (Space coordinates)
	 * @param isLandable whether or not the celestial body could be landed on
	 * @param colour the colour of the celestial body
	 * @param radius the radius of the celestial body
	 * @param name the name of the celestial body
	 * @param type the type of celestial body
	 */
	public CelestialBody(double mass, Vector2d position, boolean isLandable,
			int colour, double radius, String name, int type) {
		super(mass, position, radius);
		
		this.colour = colour;
		this.canLandOn = isLandable;
		this.name = name;
		this.type = type;

		
		texture = DrawTools.fetchTexture("../Textures/SolarSystem/"+name.toLowerCase()+".png");
	}
	
	@Override
	/**
	 * Draws the celestial body on the given graphics object
	 * @param g2 The graphics object to draw on
	 * @param lX The leftmost x coordinate of the screen in space coordinates
	 * @param tY The topmost y coordinate of the screen in space coordinates
	 * @param windowWidth The width of the screen in pixels
	 * @param windowHeight The height of the screen in pixels
	 * @param zoom The zoom level of the view
	 */
	public void draw(Graphics2D g2, double lX, double tY, int windowWidth, int windowHeight, double zoom) {
		// Add to the end of the trail if enough time has passed since the last point
		if (timeSinceLastPointMillis/1000f >= 1f/pointsPerSecond) {
			timeSinceLastPointMillis = 0;
			trail.add(new Vector2d(this.position));
		}
		
		// Remove the oldest point if the trail is too long
		if (trail.size() > trailSize) {
			trail.pop();
		}
		
		// Calculate the position of each point in screen coordinates
		int[] xs = new int[trail.size()+1];
		int[] ys = new int[trail.size()+1];

		// System.out.println("Trail debug for "+this.name+":\nSize: "+trail.size()+"\n0 Position: ("+trail.get(0).x+", "+trail.get(0).y+")");

		for (int i = 0; i < trail.size(); i++) {
			Vector2d point = trail.get(i);
			xs[i] = (int) Math.round((point.x-lX)*zoom);
			ys[i] = (int) Math.round((point.y-tY)*zoom);
		}
		
		// Add the current location so that the trail is always connected
		xs[xs.length-1] = (int) Math.round((this.position.x-lX)*zoom);
		ys[ys.length-1] = (int) Math.round((this.position.y-tY)*zoom); 
		
		// Draw the trail
		g2.setColor(new Color(this.colour));
		g2.drawPolyline(xs, ys, xs.length);
		
		// Draw the planet
		// g2.setColor(new Color(this.colour));
		if (-this.radius * zoom < (this.position.x-lX)*zoom
				&& (this.position.x-lX)*zoom < windowWidth + this.radius * zoom
				&& -this.radius * zoom < (this.position.y-tY)*zoom
				&& (this.position.y-tY)*zoom < windowHeight + this.radius * zoom) {
			
			if (this.texture == null || 2*radius*zoom < 5) {
				g2.fillOval(
					(int) Math.round((this.position.x-radius-lX)*zoom),
					(int) Math.round((this.position.y-radius-tY)*zoom),
					(int) Math.round(2*radius*zoom),
					(int) Math.round(2*radius*zoom));
			} else {
				g2.drawImage(texture,
					(int) Math.round((this.position.x-radius-lX)*zoom),
					(int) Math.round((this.position.y-radius-tY)*zoom),
					(int) Math.round(2*radius*zoom),
					(int) Math.round(2*radius*zoom), null);
			}
		}
		
		// if object is very small, circle and label it
		if (2*radius*zoom < 10 && (this.type <= 2 || 2*radius*zoom > 2)) {

			// Set brush
			g2.setColor(new Color(0xAAAAAA));
			g2.setStroke(new BasicStroke(1));
			
			// Find position of planet on screen
			int screenXPos = (int) Math.round((this.position.x-lX)*zoom);
			int screenYPos = (int) Math.round((this.position.y-tY)*zoom);

			// Draw the oval
			g2.drawOval(screenXPos-10, screenYPos-10, 20, 20);
			g2.drawString(this.name, screenXPos+10, screenYPos-10);
		}
		
		if (this.focussed) {
			BufferedImage cardBackground = DrawTools.fetchTexture("../Textures/planetcard.png");
			// draw the background image
			g2.drawImage(cardBackground, (int) (windowWidth-cardBackground.getWidth()),
				(int) (windowHeight/2 - cardBackground.getHeight()/2), null);

			// set the text colour and font to heading
			g2.setColor(new Color(0xFFFFFF));
			g2.setFont(new Font("Arial", Font.BOLD, 20));

			// Draw the name of the body
			g2.drawString(this.name, (int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 40));
			
			// set the text font to body
			g2.setFont(new Font("Arial", Font.PLAIN, 12));

			// Draw the mass of the body
			g2.drawString("Mass: " + this.mass + " kg", (int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 60));
			
			// Draw the radius of the body
			g2.drawString("Radius: " + this.radius + " m", (int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 80));
			
			// Draw the surface gravity of the body
			g2.drawString("Surface gravity: " + Math.round(this.getSurfaceAcceleration()*100)/100d + " m/s²",
				(int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 100));
			
			// Draw the velocity of the body
			g2.drawString("Velocity: "
				+ Math.round(Math.sqrt(this.getXVel()*this.getXVel()+this.getYVel()*this.getYVel())) + " m/s",
				(int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 120));
			
			// Draw whether you can land on the body
			g2.drawString("You "+(this.isLandable() ? "can" : "can't")+" land on "+this.getName(),
				(int) (windowWidth-cardBackground.getWidth()+20),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 140));
			
			// Draw the body's texture
			g2.drawImage(this.getTexture(), (int) (windowWidth-cardBackground.getWidth()+35),
				(int) (windowHeight/2 - cardBackground.getHeight()/2 + 140), 100, 100, null);
		}
	}

	@Override
	public void physicsTick(long timePassedMillis, long simulationSpeed) {
		super.physicsTick(timePassedMillis, simulationSpeed);
		timeSinceLastPointMillis += timePassedMillis;
	}

	// Getters and Setters

	/**
	 * Find if the body could be landed on by a spacecraft 
	 * @return true if the body can be landed on, false otherwise
	 */
	public boolean isLandable() {
		return canLandOn;
	}

	/**
	 * Set whether the body can be landed on by a spacecraft
	 * @param canLandOn true if the body can be landed on, false otherwise
	 */
	public void setLandable(boolean canLandOn) {
		this.canLandOn = canLandOn;
	}

	/**
	 * Get the primary colour of the body
	 * @return the primary colour of the body
	 */
	public int getColour() {
		return this.colour;
	}

	/**
	 * Set the primary colour of the body
	 * @param colour the primary colour of the body
	 */
	public void setColour(int colour) {
		this.colour = colour;
	}

	/**
	 * Get the name of the body
	 * @return the name of the body
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the body
	 * @param name the name of the body
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set if the body can be landed on by a spacecraft
	 * @param canLandOn true if the body can be landed on, false otherwise 
	 */
	public void setCanLandOn(boolean canLandOn) {
		this.canLandOn = canLandOn;
	}

	/**
	 * Get the texture of the body
	 * @return the texture of the body
	 */
	public BufferedImage getTexture() {
		return this.texture;
	}

	/**
	 * Set the texture of the body
	 * @param texture the texture of the body
	 */
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	/**
	 * Get if the body is focussed on
	 * @return true if the body is focussed on, false otherwise
	 */
	public boolean isFocussed() {
		return this.focussed;
	}

	/**
	 * Set if the body is focussed on
	 * @param focussed true if the body is focussed on, false otherwise
	 */
	public void setFocus(boolean isFocussed) {
		this.focussed = isFocussed;
	}

	/**
	 * Gets a list of points that make up the trail of the body
	 * @return a list of points that make up the trail of the body
	 */
	public Vector2d[] getTrail() {
		return this.trail.getQueue();
	}

	/**
	 * Set the entire trail of the body
	 * @param trail a queue containing the entire trail of the body
	 */
	public void setTrail(Queue<Vector2d> trail) {
		this.trail = trail;
	}

	/**
	 * Get the trail length
	 * @return the trail length
	 */
	public int getTrailSize() {
		return this.trailSize;
	}

	/**
	 * Set the trail length
	 * @param trailSize the trail length
	 */
	public void setTrailSize(int trailSize) {
		this.trailSize = trailSize;
	}

	/** 
	 * Get the number of points that are added to the trail per second
	 * @return the number of points that are added to the trail per second
	 */
	public float getPointsPerSecond() {
		return this.pointsPerSecond;
	}

	/**
	 * Set the number of points that are added to the trail per second
	 * @param pointsPerSecond the number of points that are added to the trail per second
	 */
	public void setPointsPerSecond(float pointsPerSecond) {
		this.pointsPerSecond = pointsPerSecond;
	}

	/**
	 * Get the type of the body
	 * @return the type of the body
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Set the type of the body
	 * @param type the type of the body
	 */
	public void setType(int type) {
		this.type = type;
	}

	@Override
	/**
	 * Checks if the cursor is clicking the object.
	 * If the object is small enough, it will be checked if
	 * the cursor is clicking within 20 pixels of the object.
	 * @param lX The leftmost x coordinate of the screen.
	 * @param tY The topmost y coordinate of the screen.
	 * @param xClick The x coordinate of the cursor (screen space).
	 * @param yClick The y coordinate of the cursor (screen space).
	 * @param zoom The zoom level of the screen.
	 */
	public boolean isClickedOn(double lX, double tY, int xClick, int yClick, double zoom) {
		double distanceSquared = Math.pow((this.position.x-lX)*zoom - xClick, 2)
			+ Math.pow((this.position.y-tY)*zoom - yClick, 2);
		return distanceSquared <= Math.pow(Math.max(20, zoom*this.radius), 2);
	}
}
