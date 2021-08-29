package me.miles.matthew.spaceflight.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;

import javax.swing.JPanel;

import me.miles.matthew.spaceflight.physics.PhysicsObject;
import me.miles.matthew.spaceflight.physics.CelestialBody;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

@SuppressWarnings("serial")
public class SpaceView extends JPanel implements MouseListener, MouseWheelListener, ActionListener, ComponentListener, KeyListener {
	private SpaceEnvironment mySpace;
	private double scale = 0.00001d;
	private double cX = 0; // the centre of the screen in space coordinates
	private double cY = 0;
	
	// previous frame mouse position for dragging
	private int prevMX;
	private int prevMY;
	private boolean drag = false;
	
	public SpaceView(SpaceEnvironment space) {
		this.mySpace = space;
		
		// TEST
		CelestialBody pluto = new CelestialBody(13090000000000000000000d, 0, 0, true, 0xFF3333, 1188300, "Pluto");
		
		mySpace.addBody((PhysicsObject) pluto);
		
		pluto.setXVel(1489004);
		
		mySpace.addBody((PhysicsObject) new CelestialBody(1596980000000000000000d*1000000000d, 20000000, 20000000, true, 0x34ace3, 606000, "Charon"));
		//mySpace.addBody((PhysicsObject) new PlanetaryObject(10d, 150, 200, true, 0x33FF33, 50));
		//mySpace.addBody((PhysicsObject) new PlanetaryObject(10d, -150, 150, true, 0x3333FF, 75));
		
		// Listeners
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
		this.addKeyListener(this);
	}
	
	public void refresh() {
		repaint();
	}
	
	/**
	 * Converts a set of screen coordinates to the relevant space coordinates, based on position
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private double[] toSpaceCoords(double xPos, double yPos) {
		xPos -= this.getWidth()/2;
		yPos -= this.getHeight()/2;
		
		xPos = xPos/scale;
		yPos = yPos/scale;
		
		xPos += cX;
		yPos += cY;
		
		return new double[] {xPos, yPos};
	}
	
	/**
	 * Converts a set of space coordinats to their position on screen (even if that position is not being rendered)
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private int[] toScreenCoords(double xPos, double yPos) { //TODO: Not tested
		xPos -= cX;
		yPos -= cY;
		
		xPos *= scale;
		yPos *= scale;
		
		xPos += this.getWidth()/2;
		yPos += this.getHeight()/2;
		
		return new int[] {(int) Math.round(xPos), (int) Math.round(yPos)};
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int middleX = this.getWidth()/2;
		int middleY = this.getHeight()/2;
		
		double tX = cX-middleX/scale;
		double lY = cY-middleY/scale;
		
		// background
		g2.setColor(new Color(mySpace.getBackgroundColour()));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < mySpace.getBodyCount(); i++) {
			mySpace.getBodyAt(i).draw(g2, tX, lY, scale);
		}
		
		// crosshairs
		g2.setColor(new Color(255, 255, 255, 200)); // 50% opacity
		g2.fillRect(middleX-12, middleY-1, 8, 2);
		g2.fillRect(middleX+4, middleY-1, 8, 2);
		g2.fillRect(middleX-1, middleY-12, 2, 8);
		g2.fillRect(middleX-1, middleY+4, 2, 8);
	}

	@Override
	public void actionPerformed(ActionEvent mwe) {}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int dir = mwe.getWheelRotation(); // can also be larger value than 1 for multiple lines scrolled if applicable
		
		if (dir == 0) return;
		
		int mouseX = mwe.getX();
		int mouseY = mwe.getY();
		
		double zoom = 1.0d-(0.1d*dir); // +-10% zoom. % = smooth zooming
		
		// figures out original and new position of mouse in terms of space coords. Finds the difference (how much it has shifted by) and undoes it afterwards.
		// anchor point (the point which should stay at the same position on the screen)
		double[] anchorCoords = toSpaceCoords(mouseX, mouseY);
		
		// scale
		scale *= zoom;
		
		// find new position of mouse
		double[] shiftedCoords = toSpaceCoords(mouseX, mouseY);
		
		// find shift required to get back to the anchor
		double shiftX = anchorCoords[0] - shiftedCoords[0];
		double shiftY = anchorCoords[1] - shiftedCoords[1];
		
		// shifts the centre position accordingly
		cX += shiftX;
		cY += shiftY;
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		/*\
		|*|
		|*| Only works if on and off in same location
		|*|
		\*/
		//S/ystem.out.println("Click");
		//S/ystem.out.println(Arrays.toString(toSpaceCoords(me.getX(), me.getY())));
	}

	@Override
	public void mousePressed(MouseEvent me) {
		drag = true;
		prevMX = me.getX();
		prevMY = me.getY();
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
	public void componentHidden(ComponentEvent ce) {}

	@Override
	public void componentMoved(ComponentEvent ce) {}

	@Override
	public void componentResized(ComponentEvent ce) {
		
	}

	@Override
	public void componentShown(ComponentEvent ce) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == ' ') { // recenter
			cX = 0;
			cY = 0;
			scale = 0.00001d;
		}
	}
}
