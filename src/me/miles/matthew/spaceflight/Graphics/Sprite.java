package me.miles.matthew.spaceflight.Graphics;

import java.awt.Graphics2D;
// affline transform and afflinetransformop
import java.awt.geom.AffineTransform;
// Imports
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public class Sprite {
    // A class which represents a sprite on the screen.
    // It has a position, a size, a rotation, and a texture.
    
    private BufferedImage image;
    private Vector2d position;

    // The size of the sprite, in pixels.
    private Vector2d size;
    private float rotation;
    // private BufferedImage displayImage;

    public Sprite(String path) {
        File file = new File(path);
        try {
           image = ImageIO.read(file);
        } catch (Exception e) {
            // generate errorimage
           image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
           image.setRGB(0, 0, 0xFFFF00FF);
           image.setRGB(1, 1, 0xFFFF00FF);
           image.setRGB(0, 1, 0xFF000000);
           image.setRGB(1, 0, 0xFF000000);
        }

        // this.displayImage = image;
        this.position = new Vector2d(0, 0);
        this.size = new Vector2d(image.getWidth(), image.getHeight());
    }

    public Sprite(String path, Vector2d position, Vector2d size) {
        this(path);
        this.position = position;
        this.size = size;
    }

    public void rotate(float angle) {
        // Rotate the sprite by the given angle in radians.
        rotation += angle;
        rotation %= 2 * Math.PI;
        System.out.println("Rotation: " + rotation);
    }

    public void draw(Graphics2D g2, int x, int y) {
        // Draw the sprite to the screen.
        // System.out.println("drawing sprite");
        // System.out.println("x: " + x + " y: " + y);
        // System.out.println("position: " + position.x + " " + position.y);
        // System.out.println("size: " + size.x + " " + size.y);
        // System.out.println("image: " + image.getWidth() + " " + image.getHeight());
        // System.out.println("displayImage: " + displayImage.getWidth() + " " + displayImage.getHeight());
        // System.out.println(displayImage.getRGB(0, 0));

        int xPos = (int) position.x+x;
        int yPos = (int) position.y+y;

        //Make a backup so that we can reset our graphics object after using it.
        AffineTransform backup = g2.getTransform();
        //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
        //is the angle to rotate the image. If you want to rotate around the center of an image,
        //use the image's center x and y coordinates for rx and ry.
        AffineTransform a = AffineTransform.getRotateInstance(rotation, xPos+image.getWidth()/2, yPos+image.getHeight()/2);
        //Set our Graphics2D object to the transform
        g2.setTransform(a);
        //Draw our image like normal
        g2.drawImage(image, xPos, yPos, (int) size.x, (int) size.y, null);
        //Reset our graphics object so we can draw with it again.
        g2.setTransform(backup);
    }
}
