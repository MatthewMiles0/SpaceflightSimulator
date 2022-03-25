package me.miles.matthew.spaceflight.UI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Info {
    int buttonWidth = 30;
    int windowWidth = 100;
    int windowHeight = 200;
    int x = 25;
    int y = 25;
    boolean hover = false;
    BufferedImage image = null;

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
        UNITS.put("100,000 km",             100000000d);
        UNITS.put("1 megametre",            1000000000d);
        UNITS.put("1 lightminute",          1.799E10d);
		UNITS.put("1 astronomical unit",    149597870700d);
        UNITS.put("10 astronomical units",  1495978707000d);
        UNITS.put("100 astronomical units", 14959787070000d);
        UNITS.put("1000 astronomical units",149597870700000d);
		UNITS.put("1 lightyear",            9.4607304725808E15);
		UNITS.put("1 parsec",               3.08567758E16);
	};

    public Info() {
        image = DrawTools.fetchTexture("../Textures/infocard.png");
    }
    
    public void draw(Graphics2D g2, int windowWidth, int windowHeight, double zoom) {
        g2.setColor(new Color(100, 100, 100));
        g2.fillOval(x-buttonWidth/2-3, y-buttonWidth/2-3, buttonWidth+6, buttonWidth+6);
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(x-buttonWidth/2, y-buttonWidth/2, buttonWidth, buttonWidth);
        g2.setColor(new Color(100, 100, 100));
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString("?", x-7, y+7);

        if (hover) {
            g2.setColor(new Color(255, 255, 255));
            g2.drawImage(image, x-buttonWidth/2, y+buttonWidth/2+10, image.getWidth(), image.getHeight(), null);
            //g2.fillRect(x-buttonWidth/2, y+buttonWidth/2+10, windowWidth, windowHeight);
        }

        // draw unit measurements
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        // S/ystem.out.println("Each 100 pixels is "+100/zoom+" meters");
        
        // draw the unit from UNITS which is closest to 100px long
        double ideal = 200d/zoom;
        // System.out.println(zoom);
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
        
        g2.drawString("This is "+bestUnit, 10, windowHeight-39);
        g2.drawString(bestUnit + " = " + (UNITS.get(bestUnit)) + " m", 10, windowHeight-15);
        g2.fillRect(10, windowHeight-36, 2, 10);
        g2.fillRect(10, windowHeight-32, (int)(UNITS.get(bestUnit)*zoom), 2);
        g2.fillRect(10+(int)(UNITS.get(bestUnit)*zoom), windowHeight-36, 2, 10);
    }

    public void checkHover(int mouseX, int mouseY) {
        hover = Math.pow(mouseX-x, 2) + Math.pow(mouseY-y, 2) <= Math.pow(buttonWidth/2, 2);
    }
}
