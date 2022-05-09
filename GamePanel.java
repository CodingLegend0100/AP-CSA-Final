import game.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private final int FPS = 60;

    private Image[] enemyImages = new Image[4];
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();

    KeyInput keyListener = new KeyInput();

    Player player = new Player(keyListener);
    //Menu shop = new Menu(keyListener);

    Thread gameThread;

    public GamePanel(){

        //Load images
        for (int i = 1; i <= 4; i++){
            enemyImages[i-1] = Sprite.loadImage("assets/pirate"+i+".png");
        }

        setPreferredSize(new Dimension(900,600));
        addKeyListener(keyListener);
        setFocusable(true);
        setBackground(Color.BLACK);
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        //How this works
        //Calculate nanoseconds between frames by
        //dividing nanoseconds (1 billion) in 1 second by the FPS
        int drawInterval = 1000000000/FPS;

        long lastTime = System.nanoTime(); //The last time checked
        long currentTime; //The current time

        long delta = 0; //Nanoseconds since the last frame was drawn

        while (gameThread != null){

            currentTime = System.nanoTime(); //Get the current time in nanoseconds
            delta += currentTime - lastTime; //Add time difference to delta
            lastTime = currentTime;

            //If enough time has passed, draw the frame
            if (delta >= drawInterval){
                update();
                repaint();
                delta = 0;
            }
        }
    }

    public void createEnemy(){
        
    }

    public void createAsteroid(){

    }

    /** Update positions of objects on the screen */
    public void update(){
        //if (shop.isOpen()) return;
        player.update();

    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better

        long drawStart = System.nanoTime();
        
        for (Sprite e: enemies){
            e.draw(g2);
        }

        player.draw(g2);

        //shop.draw(g2);

        long drawTime = System.nanoTime() - drawStart;

        g2.dispose(); //Get rid of the graphics when we are done
    }
}
