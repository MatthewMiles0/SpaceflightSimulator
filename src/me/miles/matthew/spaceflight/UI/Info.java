package me.miles.matthew.spaceflight.UI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Info {
    int iconWidth = 30;
    int windowWidth = 100;
    int windowHeight = 200;
    int x = 25;
    int y = 25;
    boolean hover = false;
    BufferedImage image = null;

    // Conversions of all the units into metres
    private static final Map<String, Double> UNITS = new HashMap<String, Double>();
	static {
		UNITS.put("1 nm",                   1E-9);
		UNITS.put("1 cm",                   0.1d);
		UNITS.put("1 m",                    1d);
		UNITS.put("1 km",                   1000d);
        UNITS.put("10 km",                  10000d);
        UNITS.put("100 km",                 100000d);
        UNITS.put("1000 km",                1000000d);
        UNITS.put("10,000 km",              10000000d);
        UNITS.put("1 lightsecond",          2.998E8);
        UNITS.put("10 lightseconds",        2.998E9);
        UNITS.put("1 lightminute",          1.799E10d);
		UNITS.put("1 astronomical unit",    149597870700d);
        UNITS.put("10 astronomical units",  1495978707000d);
        UNITS.put("100 astronomical units", 14959787070000d);
        UNITS.put("1000 astronomical units",149597870700000d);
		UNITS.put("1 lightyear",            9.4607304725808E15);
		UNITS.put("1 parsec",               3.08567758E16);
	};

    /**
     * Adds information overlays on the screen
     */
    public Info() {
        image = DrawTools.fetchTexture("../Textures/infocard.png");
    }
    
    /**
     * Draw information overlays
     * @param g2 the graphics2d object, representing the screen
     * @param windowWidth the width of the window in which informaion is being shown
     * @param windowHeight the height o fhte window in which information is being shown
     * @param zoom the zoom, used to calculate the scale indicator
     */
    public void draw(Graphics2D g2, int windowWidth, int windowHeight, double zoom) {
        // Draw the question mark symbol
        g2.setColor(new Color(100, 100, 100));
        g2.fillOval(x-iconWidth/2-3, y-iconWidth/2-3, iconWidth+6, iconWidth+6);
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(x-iconWidth/2, y-iconWidth/2, iconWidth, iconWidth);
        g2.setColor(new Color(100, 100, 100));
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString("?", x-7, y+7);

        // On hover of the question mark, show the equations image
        if (hover) {
            g2.setColor(new Color(255, 255, 255));
            g2.drawImage(image, x-iconWidth/2, y+iconWidth/2+10, image.getWidth(), image.getHeight(), null);
        }

        // draw unit measurements
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // draw the unit from UNITS which is closest to 200px long
        double ideal = 200d/zoom; // The ideal width
        double best = Double.MAX_VALUE;
        String bestUnit = "";
        for (String key : UNITS.keySet()) {
            double unit = UNITS.get(key);
            double dist = Math.abs(ideal - unit);
            if (dist < best) {
                best = dist;
                bestUnit = key;
            }
        }
        
        // Draw the scale indicator
        g2.drawString("This is "+bestUnit, 10, windowHeight-39);
        g2.drawString(bestUnit + " = " + (UNITS.get(bestUnit)) + " m", 10, windowHeight-15);
        g2.fillRect(10, windowHeight-36, 2, 10);
        g2.fillRect(10, windowHeight-32, (int)(UNITS.get(bestUnit)*zoom), 2);
        g2.fillRect(10+(int)(UNITS.get(bestUnit)*zoom), windowHeight-36, 2, 10);
    }

    /**
     * Checks if the mouse is hovering over the quetion mark icon
     * @param mouseX The x-coordinate of the mouse within the window
     * @param mouseY The y-coordinate of the mouse within the window
     */
    public void checkHover(int mouseX, int mouseY) {
        // Using pythagorean theorem to find the distance from the centre of the icon
        hover = Math.pow(mouseX-x, 2) + Math.pow(mouseY-y, 2) <= Math.pow(iconWidth/2, 2);
    }
}
