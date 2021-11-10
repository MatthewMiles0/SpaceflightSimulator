package me.miles.matthew.spaceflight.Graphics;

// import graphics and graphics2d
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.miles.matthew.spaceflight.Utils.Vector2d;

public class SpriteTest {
    // This class is used to test the Sprite class.
    // It is not used in the game.

    class SpriteTestDisplay extends JPanel implements ActionListener {
        // This class is used to display the sprite.
        // It is not used in the game.
        Sprite sprite;

        public SpriteTestDisplay(Sprite sprite) {
            // Constructor.
            this.sprite = sprite;
            Timer t = new Timer(1, this);
            t.start();
        }

        public void paintComponent(Graphics g) {
            // This method is used to display the sprite.
            // It is not used in the game.
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // for (int x = 0; x < this.getWidth(); x+=20) {
            //     for (int y = 0; y < this.getHeight(); y+=20) {
            //         Color c = new Color((int) (255f*x/this.getWidth()), (int) (255f*y/this.getHeight()), 0);
            //         g2.setColor(c);
            //         g2.fillRect(x, y, 20, 20);
            //     }
            // }

            
            
            // Draw the sprite.
            sprite.draw(g2, 200, 200);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // sprite.rotate(0.1f);
            repaint();
        }
    }
    
    public static void main(String[] args) {
        new SpriteTest();
    }

    // constructor
    public SpriteTest() {
        // create a new sprite
        Sprite sprite = new Sprite(new Vector2d(), new Vector2d(100, 100));
        sprite.setTexture("src/me/miles/matthew/spaceflight/Textures/Planets/PlaceholderPlanet.png");

        AnimatedSprite sprite2 = new AnimatedSprite(new Vector2d(), new Vector2d(600, 200), 3);
        sprite2.addFrames("src/me/miles/matthew/spaceflight/Textures/Planets/Earth.png", 4);
        //sprite.rotate(45);

        Sprite custom = new Sprite(new Vector2d(), new Vector2d(300, 300));
        PlanetGen.generatePlanet(100, "", 0xFFFF00, 0xD6550B, "src/me/miles/matthew/spaceflight/Textures/Planets/x.png");
        custom.setTexture("src/me/miles/matthew/spaceflight/Textures/Planets/x.png");
        
        // create a JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setTitle("Sprite Test");

        // create a JPanel
        SpriteTestDisplay panel = new SpriteTestDisplay(custom);

        frame.add(panel);
        frame.setVisible(true);
    }
}
