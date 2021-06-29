package me.miles.matthew.spaceflight.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import me.miles.matthew.spaceflight.physics.PhysicsObject;
import me.miles.matthew.spaceflight.physics.PlanetaryObject;
import me.miles.matthew.spaceflight.physics.SpaceEnvironment;

@SuppressWarnings("serial")
public class SpaceView extends JPanel implements MouseListener, MouseWheelListener, ActionListener {
	private SpaceEnvironment mySpace;
	private double zoom = 1d;
	private int cX = this.getWidth()/2;
	private int cY = this.getWidth()/2;
	
	public SpaceView(SpaceEnvironment space) {
		this.mySpace = space;
		
		// TEST
		mySpace.addBody((PhysicsObject) new PlanetaryObject(1000000d, 0, 0, true, 0xAF2109, 100));
		mySpace.addBody((PhysicsObject) new PlanetaryObject(1000000d, 150, 200, true, 0x1123AB, 50));
	
		// Listeners
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
	}
	
	public void refresh() {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(mySpace.getBackgroundColour()));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < mySpace.getBodyCount(); i++) {
			mySpace.getBodyAt(i).draw(g2, cX, cY, zoom);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int dir = e.getWheelRotation();
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		cX -= mouseX + this.getWidth()/2;
		cY -= mouseX + this.getWidth()/2;
		
		// https://stackoverflow.com/a/3151987/5627381
		zoom *= 1.0d-0.1d*dir;
		//zoom -= 0.1d * dir;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Down");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Up");
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
