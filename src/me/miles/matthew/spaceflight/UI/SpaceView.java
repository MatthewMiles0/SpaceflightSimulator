package me.miles.matthew.spaceflight.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

import javax.swing.JPanel;

import me.miles.matthew.spaceflight.physics.PhysicsObject;
import me.miles.matthew.spaceflight.physics.PlanetaryObject;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

@SuppressWarnings("serial")
public class SpaceView extends JPanel implements MouseListener, MouseWheelListener, ActionListener, ComponentListener, KeyListener {
	private SpaceEnvironment mySpace;
	private double scale = 0.00001d;
	private int cX = 0; // the centre of the screen in space coordinates
	private int cY = 0;
	
	// previous frame mouse position for dragging
	private int prevMX;
	private int prevMY;
	private boolean drag = false;
	
	public SpaceView(SpaceEnvironment space) {
		this.mySpace = space;
		
		// TEST
		mySpace.addBody((PhysicsObject) new PlanetaryObject(13090000000000000000000d, 0, 0, true, 0xFF3333, 0xe33440, "Pluto"));
		mySpace.addBody((PhysicsObject) new PlanetaryObject(11090000000000000000000d, 20000000, 20000000, true, 0x34ace3, 9883000, "Bluto"));
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
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		int tX = (int) (cX-this.getWidth()/2);
		int lY = (int) (cY-this.getHeight()/2);
		
		// background
		g2.setColor(new Color(mySpace.getBackgroundColour()));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < mySpace.getBodyCount(); i++) {
			mySpace.getBodyAt(i).draw(g2, tX, lY, scale);
		}
	}

	@Override
	public void actionPerformed(ActionEvent mwe) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int dir = mwe.getWheelRotation();
		//System.out.println(dir);
		if (dir == 0) return;
		
		int mouseX = mwe.getX();
		int mouseY = mwe.getY();
		
		double zoom = 1.0d-(0.1d*dir);
		
		// mouse pos in space coords:
		int msx = (int) (mouseX / scale - cX);
		int msy = (int) (mouseY / scale - cX);
		
		cX -= msx;
		cY -= msy;
		
		System.out.println(cX+" : "+cY);
		
		
		/*
		// translate mouse pos to centre
		
		cX -= (mouseX - this.getWidth()/2) * scale;
		cY -= (mouseY - this.getHeight()/2) * scale;
		
		// alter scale
		
		scale *= zoom;
		
		// translate back with new zoom
		
		cX += (mouseX - this.getWidth()/2) * scale;
		cY += (mouseY - this.getHeight()/2) * scale;
		
		
		//cX *= a;
		
		//cX -= mouseX / (scale * zoom) - mouseX/scale;
		//cY -= mouseY / (scale * zoom) - mouseY/scale;
		
		// (cX, cY) is now the top left corner of the screen in space coordinates
		
		//cX -= (mouseX - this.getWidth()/2d) * scale;
		//cY -= (mouseY - this.getHeight()/2d) * scale;
		
		//scale *= zoom;
		
		// https://stackoverflow.com/a/3151987/5627381
		//zoom -= 0.1d * dir;
		
		
		*/
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.println("Click");
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
