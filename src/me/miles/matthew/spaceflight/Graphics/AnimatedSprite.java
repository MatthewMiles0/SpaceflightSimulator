package me.miles.matthew.spaceflight.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public class AnimatedSprite extends Sprite {

    private int frame = 0;
    private int numberOfFrames = 0;
    private int animationSpeed = 0;
    private int count = 0;
    private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

    public AnimatedSprite(Vector2d position, Vector2d location, int animationSpeed) {
        super(position, location);
        this.animationSpeed = animationSpeed;
    }

    public void addFrames(String filePath, int numberOfFrames) {
        setTexture(filePath);

        if (hasTexture) {
            for (int i = 0; i < numberOfFrames; i++) {
                // frames.add(getTexture().getSubimage(i * getTexture().getWidth() / numberOfFrames, 0, getTexture().getWidth() / numberOfFrames, getTexture().getHeight()));
                frames.add(image.getSubimage(0, i * image.getHeight()/numberOfFrames, image.getWidth(), image.getHeight()/numberOfFrames));
            }
            this.numberOfFrames = numberOfFrames;
        }
        
        // this.numberOfFrames = numberOfFrames;
        // for (int i = 0; i < numberOfFrames; i++) {
        //     frames.add(SpriteSheet.getSprite(filePath, i));
        // }
    }

    public void update() {
        if (count == animationSpeed) {
            count = 0;
            frame++;
            if (frame == numberOfFrames) {
                frame = 0;
            }
        }
        count++;
    }

    public int getFrameNum() {
        return frame;
    }

    public BufferedImage getFrame() {
        return frames.get(frame);
    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        int xPos = (int) position.x+x;
        int yPos = (int) position.y+y;

        update();

        //Make a backup so that we can reset our graphics object after using it.
        AffineTransform backup = g2.getTransform();
        //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
        //is the angle to rotate the image. If you want to rotate around the center of an image,
        //use the image's center x and y coordinates for rx and ry.
        AffineTransform a = AffineTransform.getRotateInstance(rotation, xPos+size.x/2, yPos+size.y/2); // x+image.getWidth()/2, y+image.getHeight()/2
        //Set our Graphics2D object to the transform
        g2.setTransform(a);
        //Draw our image like normal
        g2.drawImage(getFrame(), (int) xPos, (int) yPos, (int) size.x, (int) size.y, null);
        //Reset our graphics object so we can draw with it again.
        g2.setTransform(backup);
    }
}
