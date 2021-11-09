package me.miles.matthew.spaceflight.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class PlanetGen {
    // Generates an image of a planet with a given radius, type and colour and saves it to an image file
    
    /**
     * Generates an image of a planet with a given radius, type and colour and saves it to an image file
     * @param radius The radius of the planet
     * @param type The type of planet
     * @param color The colour of the planet
     * @param fileName The name of the file to save the image to
     */
    public static void generatePlanet(int radius, String type, int color, String fileName) {
        // Generates an image of a planet with a given radius, type and colour and saves it to an image file
        // radius: radius of the planet
        // type: type of planet (terrestrial, gas, ice)
        // colour: colour of the planet (red, green, blue)
        // fileName: name of the image file to save the planet to
        
        // Create a new image
        BufferedImage planet = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        
        // Create a new graphics context
        Graphics2D g2 = (Graphics2D) planet.getGraphics();
        
        // Set the colour of the graphics context
        g2.setColor(new Color(color));
        
        // Draw the planet
        g2.fillOval(0, 0, radius*2, radius*2);

        planet = layerImage(getWorleyNoise(radius*2, radius*2, 10), planet);

        // Save the planet to an image file
        try {
            ImageIO.write(planet, "png", new File(fileName));
        } catch (Exception e) {
            System.out.println("Error saving planet image");
            e.printStackTrace();
        }
    }

    private static int getRandomInt(int min, int max) {
        // Returns a random integer between min and max
        // min: minimum value
        // max: maximum value
        
        // Return a random integer between min and max
        return (int) (Math.random() * (max - min)) + min;
    }

    private static BufferedImage getWorleyNoise(int sizeX, int sizeY, int density) {
        int freqX = sizeX/density;
        int freqY = sizeY/density;
        
        int[][][] points = new int[density][density][2];
        
        for (int x = 0; x < sizeX; x+=freqX) {
            for (int y = 0; y < sizeY; y+=freqY) {
                points[x/freqX][y/freqY][0] = getRandomInt(x, Math.min(x+freqX, sizeX));
                points[x/freqX][y/freqY][1] = getRandomInt(y, Math.min(y+freqY, sizeY));
            }
        }


        BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                int quadX = (int) (x / freqX);
                int quadY = (int) (y / freqY);
                int bestDist = Integer.MAX_VALUE;
                for (int i = quadX-1; i < quadX+2; i++) {
                    for (int j = quadY-1; j < quadY+2; j++) {
                        if (i >= 0 && i < points.length && j >= 0 && j < points[0].length) {
                            int dx = x - points[i][j][0];
                            int dy = y - points[i][j][1];
                            int dist = (int) Math.sqrt(dx*dx + dy*dy);
                            if (dist < bestDist) {
                                bestDist = dist;
                            }
                        }
                    }
                }
                int val = 0xFF*bestDist/(freqX*3);
                image.setRGB(x, y, 0xFF000000+val+0x100*val+0x10000*val);
            }
        }
        return image;
    }

    private static BufferedImage layerImage(BufferedImage image1, BufferedImage image2) {
        // Layers an image on top of another image
        // image1: image to be layered on top of image2
        // image2: image to be layered on top of image1
        // x: x position of image1
        // y: y position of image1
        
        // Create a new image
        BufferedImage image = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // System.out.println(image1.getRGB(0, 0)+" "+image2.getRGB(0, 0));
        // System.out.println(((image1.getRGB(0, 0) & 0xFF000000) >> 24)+" "+((image2.getRGB(0, 0) & 0xFF000000) >> 24));

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                // System.out.println(Integer.toHexString(rgb1));
                // System.out.println(Integer.toHexString(rgb2));
                // System.out.println(Integer.toHexString((rgb1 & 0xFF000000) >> 24));

                // Color c1 = new Color(
                //     (rgb1 & 0xFF),
                //     (rgb1 & 0xFF00) >> 8,
                //     (rgb1 & 0xFF0000) >> 16,
                //     (rgb1 & 0xFF000000) >> 24 & 0xFF
                // );

                int c = (
                    ((((rgb1 & 0xFF) + (rgb2 & 0xFF)) >> 1) & 0xFF) +
                    ((((rgb1 & 0xFF00) + (rgb2 & 0xFF00)) >> 1) & 0xFF00) +
                    ((((rgb1 & 0xFF0000) + (rgb2 & 0xFF0000)) >> 1) & 0xFF0000) +
                    ((rgb1 & 0xFF000000) & (rgb2 & 0xFF000000))
                );
                // System.out.println(Integer.toHexString(rgb1)+" + "+Integer.toHexString(rgb2)+" = "+Integer.toHexString(c));

                // Color col1 = new Color(rgb1);
                // Color col2 = new Color(rgb2);
                
                

                /*image.setRGB(x, y, new Color(
                    (pixel1.getRed()+pixel2.getRed())/2,
                    (pixel1.getGreen()+pixel2.getGreen())/2,
                    (pixel1.getBlue()+pixel2.getBlue())/2
                    ).getRGB() + (Math.min(image1.getRGB(x, y), image2.getRGB(x, y)) & 0xFF000000));*/

                image.setRGB(x, y, c);
            }
        }

        return image;
    }
}
