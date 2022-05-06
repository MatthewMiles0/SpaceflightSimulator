package me.miles.matthew.spaceflight.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.time.Instant;

import javax.swing.JPanel;

import me.miles.matthew.spaceflight.Utils.DrawTools;
import me.miles.matthew.spaceflight.Utils.Vector2d;
import me.miles.matthew.spaceflight.physics.CelestialBody;
import me.miles.matthew.spaceflight.physics.PhysicsObject;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

@SuppressWarnings("serial")
public class SpaceView extends JPanel implements MouseListener, MouseWheelListener, ActionListener, KeyListener {
	private SpaceEnvironment mySpace;
	private double scale = 0.00001d; // pixels per metre
	private double cX = 0; // the centre of the screen in space coordinates
	private double cY = -57.9E9;
	CelestialBody focus = null; // The object the camera is moving relative to
	Vector2d lastFocusPos = null;
	private Info info = new Info();
	private BufferedImage backImg = null;
	private long timeOfLastFrame;
	
	// previous frame mouse position for dragging
	private int prevMX;
	private int prevMY;
	private boolean drag = false;

	public SpaceView(SpaceEnvironment space) {
		this.mySpace = space;
		
		timeOfLastFrame = Instant.now().toEpochMilli();
		
		/*
		// TEST
		// CelestialBody xa = new CelestialBody (1d, 100, 0, true, 0xFF3333, 1, "x", 1);
		CelestialBody pluto = new CelestialBody(13090000000000000000000d, 0, 0, true, 0xFF3333, 1188300, "Pluto", 2);
		CelestialBody charon = new CelestialBody(1586000000000000000000d, 0, -19640000, true, 0x34ace3, 606000, "Charon", 2);
		CelestialBody bharon = new CelestialBody(1586000000000000000000d, 10000, -19640000*2, true, 0xace334, 606000, "Bharon", 2);
		
		// mySpace.addBody(xa);
		mySpace.addBody((PhysicsObject) pluto);
		
		
		//pluto.setXVel(1489004);
		
		mySpace.addBody((PhysicsObject) charon);
		
		mySpace.addBody((PhysicsObject) bharon);
		
		// orbital period of 153 hours
		// radius = 19640 km 
		
		double angularVel = 2*Math.PI/(153*60*60);
		
		charon.setXVel(angularVel*19640000d);
		bharon.setXVel(-angularVel*20635000d/1.5d);
		// mySpace.autoDoubleOrbit(bharon, pluto, charon);

		this.setFocus(pluto);
		this.goTo(pluto);*/

		
		// Solar System
		// https://nssdc.gsfc.nasa.gov/planetary/factsheet/
		// https://www.physicsforums.com/threads/current-velocities-of-planets.114165/
		CelestialBody sun 		= new CelestialBody(1.989E30d, 	0, 0, 			false, 	0xFFFF00, 696340000, 	"Sun",		1);
		CelestialBody mercury 	= new CelestialBody(0.33E24, 	0, -57.9E9, 	true, 	0xA8A3A0, 4879000/2, 	"Mercury", 	2);
		CelestialBody venus 	= new CelestialBody(4.87E24, 	0, -108.2E9, 	true, 	0xD2B49F, 12104000/2, 	"Venus", 	2);
		CelestialBody earth 	= new CelestialBody(5.97E24, 	0, -149.6E9, 	true, 	0x0ab6bf, 12756000/2, 	"Earth",	2);
		CelestialBody moon 		= new CelestialBody(0.073E24,	0,	-149.984E9,	true, 	0xAAAAAA, 3475000/2, 	"The Moon", 3);
		CelestialBody mars 		= new CelestialBody(0.642E24, 	0, -228.0E9,	true, 	0xD68A5A, 3396000, 		"Mars", 	2);
		CelestialBody jupiter	= new CelestialBody(1898E24, 	0, -778.5E9,	false, 	0xDE4E07, 142984000/2, 	"Jupiter", 	2);
		CelestialBody saturn	= new CelestialBody(568E24, 	0, -1432E9,		false, 	0xC9AD57, 120536000/2, 	"Saturn", 	2);
		CelestialBody uranus	= new CelestialBody(1898E24, 	0, -2867.5E9,	false, 	0x6FCED1, 25559000, 	"Uranus", 	2);
		CelestialBody neptune	= new CelestialBody(1898E24, 	0, -4515.5E9,	false, 	0x3F55AB, 49528000/2, 	"Neptune", 	2);
		
		// CelestialBody sun2 		= new CelestialBody(1.989E30d, 	0, -1E12, 		false, 	0xFFFF00, 696340000, 	"Sun 2",		1);
		// CelestialBody sun3 		= new CelestialBody(1.989E30d, 	0, -2E12, 		false, 	0xFFFF00, 1396340000, 	"Sun 3",		1);
		

		mySpace.addBody(sun);
		// mySpace.addBody(sun2);
		// mySpace.addBody(sun3);
		mySpace.addBody(mercury);
		mySpace.addBody(venus);
		mySpace.addBody(earth);
		mySpace.addBody(moon);
		mySpace.addBody(mars);
		mySpace.addBody(jupiter);
		mySpace.addBody(saturn);
		mySpace.addBody(uranus);
		mySpace.addBody(neptune);
		
		mySpace.autoOrbit(mercury, sun);
		mySpace.autoOrbit(venus, sun);
		mySpace.autoOrbit(earth, sun);
		mySpace.autoDoubleOrbit(moon, earth, sun);
		mySpace.autoOrbit(mars, sun);
		mySpace.autoOrbit(jupiter, sun);
		mySpace.autoOrbit(saturn, sun);
		mySpace.autoOrbit(uranus, sun);
		mySpace.autoOrbit(neptune, sun);
		
		// mySpace.autoDoubleOrbit(sun2, jupiter, sun);
		
		this.setFocus(earth);
		this.goTo(earth);
		

		backImg = DrawTools.fetchTexture("../Textures/background.png");
		
		//mySpace.addBody((PhysicsObject) new PlanetaryObject(10d, 150, 200, true, 0x33FF33, 50));
		//mySpace.addBody((PhysicsObject) new PlanetaryObject(10d, -150, 150, true, 0x3333FF, 75));
		
		// Listeners
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
	}
	
	/**
	 * Refreshes the view
	 */
	public void refresh() {
		repaint();
	}
	
	/**
	 * Converts a set of screen coordinates to the relevant space coordinates, based on position
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private Vector2d toSpaceCoords(double xPos, double yPos) {
		xPos -= this.getWidth()/2;
		yPos -= this.getHeight()/2;
		
		xPos = xPos/scale;
		yPos = yPos/scale;
		
		xPos += cX;
		yPos += cY;
		
		return new Vector2d(xPos, yPos);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		
		Point mousePosScreen = this.getMousePosition();
		boolean mouseOnScreen = mousePosScreen != null;
		
		long timePassed = System.currentTimeMillis() - timeOfLastFrame;
		timeOfLastFrame = Instant.now().toEpochMilli();
		
		if (focus != null) {
			cX += focus.getXPos()-lastFocusPos.x;
			cY += focus.getYPos()-lastFocusPos.y;
			lastFocusPos = new Vector2d(focus.getXPos(), focus.getYPos());
		}

		if (mousePos == null) {
			drag = false;
		}

		if (drag) {
			double deltaX = prevMX-mousePos.x;
			double deltaY = prevMY-mousePos.y;
			
			cX += deltaX/scale;
			cY += deltaY/scale;
			
			prevMX = mousePos.x;
			prevMY = mousePos.y;
		}

		int middleX = this.getWidth()/2;
		int middleY = this.getHeight()/2;
		
		double lX = cX-middleX/scale; // lx in space coords?
		double tY = cY-middleY/scale;
		
		// background
		g2.setColor(new Color(mySpace.getBackgroundColour()));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		// draw background
		g2.drawImage(backImg, 0, 0, this.getWidth(), this.getHeight(), null);

		
		for (int i = 0; i < mySpace.getBodyCount(); i++) {
			mySpace.getBodyAt(i).draw(g2, lX, tY, this.getWidth(), this.getHeight(), scale);
			
			if (this.drag && mouseOnScreen) {
				//S/ystem.out.println("Mouse at "+mousePosScreen.x+", "+mousePosScreen.y);
				if (mySpace.getBodyAt(i).isClickedOn(lX, tY, mousePosScreen.x, mousePosScreen.y, scale)) {
					//S/ystem.out.println("Clicked on!");
					try {
						this.setFocus((CelestialBody) mySpace.getBodyAt(i));
					} catch (ClassCastException e) {
						System.out.println("Not a celestial body!");
					}
				}
			}

			if (mouseOnScreen) {
				info.checkHover(mousePosScreen.x, mousePosScreen.y);
			}
		}

		info.draw(g2, this.getWidth(), this.getHeight(), scale);

		g2.drawString("FPS: "+Math.round(1000d/timePassed), this.getWidth()-50, 15);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int dir = mwe.getWheelRotation(); // Can also be larger value than 1 for multiple lines scrolled if applicable
		
		if (dir == 0) return; // Don't do calculations if no change
		
		int mouseX = mwe.getX();
		int mouseY = mwe.getY();
		
		double zoom = 1.0d-(0.1d*dir); // +-10% zoom. % = smooth zooming
		
		// Figures out original and new position of mouse in terms of space coords.
		// Finds the difference (how much it has shifted by) and undoes it afterwards.
		// Anchor point (the point which should stay at the same position on the screen)
		Vector2d anchorCoords = toSpaceCoords(mouseX, mouseY);
		
		// scale
		scale *= zoom;
		
		// find new position of mouse
		Vector2d shiftedCoords = toSpaceCoords(mouseX, mouseY);
		
		// find shift required to get back to the anchor
		double shiftX = anchorCoords.x - shiftedCoords.x;
		double shiftY = anchorCoords.y - shiftedCoords.y;
		
		// shifts the centre position accordingly
		cX += shiftX;
		cY += shiftY;
		
	}

	/**
	 * Sets the focus of the camera to the given CelestialBody.
	 * @param focus The CelestialBody to focus on.
	 */
	public void setFocus(CelestialBody focus) {
		try { this.focus.setFocus(false); } catch (NullPointerException e) { };
		this.focus = focus;
		this.lastFocusPos = new Vector2d(focus.getXPos(), focus.getYPos());
		focus.setFocus(true);
		drag = false;
	}
	
	/**
	 * Sets the camera to follow a given PhysicsObject.
	 * @param obj The PhysicsObject to follow.
	 */
	public void goTo(PhysicsObject obj) {
		this.cX = obj.getXPos();
		this.cY = obj.getYPos();
		this.scale = 200/(obj.getRadius()*2);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {}

	@Override
	public void mousePressed(MouseEvent me) {
		drag = true;
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		prevMX = mousePos.x;
		prevMY = mousePos.y;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		drag = false;
	}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == ' ') { // recenter
			this.goTo(this.focus);
			// cX = 0;
			// cY = 0;
			// scale = 0.00001d;
		}
	}
}
