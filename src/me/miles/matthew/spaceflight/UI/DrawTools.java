package me.miles.matthew.spaceflight.UI;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DrawTools {

    /**
     * Fetches a texture using a relative path
     * @param filePath The relative path to the texture
     * @return A bufferedimage of the texture
     */
    public static BufferedImage fetchTexture(String filePath) {
        // get the file
        File file = new File(filePath);
        // read the image
        BufferedImage image = null;
        try {
            InputStream iconstream = DrawTools.class.getResourceAsStream(filePath);
            image = ImageIO.read(iconstream);
        } catch (Exception e) {
            System.out.println("Error loading texture: " + filePath);
            return null;
        }

        return image;
    }

    /**
     * Rotates an image by a specified angle
     * @param inputImage The image to be rotated
     * @param angle THe angle in radians
     * @return The rotated image (same size)
     */
    public static BufferedImage rotateImage(BufferedImage inputImage, double angle) {
        // get the image dimensions
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        // create a new image with the same dimensions
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        // get the graphics context
        Graphics2D g2d = outputImage.createGraphics();
        // set the transform
        g2d.rotate(angle, width / 2, height / 2);
        // draw the image
        g2d.drawImage(inputImage, 0, 0, null);
        // dispose of the graphics context and return the image
        g2d.dispose();
        return outputImage;
    }

    /**
     * sclaes an image to a certain size
     * @param inputImg The image to be scaled
     * @param scale The scale factor
     * @return The scaled bufferedimage
     */
    public static BufferedImage scaleImage(BufferedImage inputImg, double scale) {
        if (scale < 1) {
            scale = 1;
        }
        // get the image dimensions
        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        // create a new image with the same dimensions
        BufferedImage outputImage = new BufferedImage((int) (width * scale), (int) (height * scale), inputImg.getType());
        // get the graphics context
        Graphics2D g2d = outputImage.createGraphics();
        // set the transform
        g2d.scale(scale, scale);
        // draw the image
        g2d.drawImage(inputImg, 0, 0, null);
        // dispose of the graphics context and return the image
        g2d.dispose();
        return outputImage;
    }
}
