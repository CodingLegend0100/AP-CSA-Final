import game.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private final int width = 900, height = 600;
    private final int FPS = 60;

    private Image[] enemyImages = new Image[4];
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();
    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();

    KeyInput keyListener = new KeyInput();

    Player player = new Player(keyListener);
    //Menu shop = new Menu(keyListener);

    Thread gameThread;

    public GamePanel(){

        //Load images
        for (int i = 1; i <= 4; i++){
            enemyImages[i-1] = Sprite.loadImage("assets/pirate"+i+".png");
        }

        setPreferredSize(new Dimension(width, height));
        addKeyListener(keyListener);
        setFocusable(true);
        setBackground(Color.BLACK);
        
        gameThread = new Thread(this);
        gameThread.start();

        enemies.add(new Sprite(enemyImages[0],100,100,0.3));
        enemies.add(new Sprite(enemyImages[1],150,100,0.3));
        enemies.add(new Sprite(enemyImages[2],200,100,0.3));
        enemies.add(new Sprite(enemyImages[3],250,100,0.3));

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

    //TODO: Create asteroids off the edge of the screen
    //Assignee: Cole Kemp
    public void createAsteroid(){

    }

    //TODO: Remove asteroids too far off the edge of the screen
    //Assignee: Cole Kemp
    public void removeAsteroids(){

    }

    /** Update positions of objects on the screen */
    public void update(){
        //if (shop.isOpen()) return;
        createAsteroid();
        player.update();
        removeAsteroids();

    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better

        long drawStart = System.nanoTime();

        g2.translate(-player.getX()+width/2,-player.getY()+height/2); //Keep player in the center of the window
        player.draw(g2);

        for (Sprite e: enemies){
            e.draw(g2);
        }

        //shop.draw(g2);

        long drawTime = System.nanoTime() - drawStart;

        g2.dispose(); //Get rid of the graphics when we are done
    }
}
