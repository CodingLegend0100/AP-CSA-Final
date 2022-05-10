import game.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private final int width = 900, height = 600;
    private final int FPS = 60;

    private Image[] enemyImages = new Image[4];
    
    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();   

    KeyInput keyListener = new KeyInput();

    Player player = new Player(keyListener);
    SpaceStation station = new SpaceStation();
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
        setBackground(new Color(10,10,10));
        
        gameThread = new Thread(this);
        gameThread.start();

        enemies.add(new Enemy(enemyImages[0],100,100,player));
        enemies.add(new Enemy(enemyImages[1],150,100,player));
        enemies.add(new Enemy(enemyImages[2],200,100,player));
        enemies.add(new Enemy(enemyImages[3],250,100,player));
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
        if(((int)(Math.random()*70+1))!=1||asteroids.size()>59) return;
        double vert = player.getX()+height/2;
        double horz = player.getY()+width/2;
        asteroids.add(new Asteroid(vert,horz));
        
        //Check if it is colliding with another asteroid
        
        //Add it to asteroids list
        
        
    }

    //TODO: Remove asteroids too far off the edge of the screen
    //Assignee: Cole Kemp
    public void removeAsteroids(){

    }

    /** Update positions of objects on the screen */
    public void update(){
        //if (shop.isOpen()) return;

        createAsteroid(); //Try creating an asteroid

        player.update();

        removeAsteroids(); //Clear asteroids

        for(Sprite a:asteroids){
            a.update();
        }

        for (Sprite e : enemies){
            e.update();
        }
        
        for(Sprite x:asteroids){
            if(player.isColliding(x)){
                player.bounce();
            }

        }
    }

    public void keyPressed(String key){

    }

    public void keyReleased(String key){

    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better

        long drawStart = System.nanoTime();

        g2.translate(-player.getX()+width/2,-player.getY()+height/2); //Keep player in the center of the window
        player.draw(g2);
        for (Sprite a: asteroids){
            a.draw(g2);
        }

        for (Sprite e: enemies){
            e.draw(g2);
        }

        //shop.draw(g2);
        station.draw(g2);

        long drawTime = System.nanoTime() - drawStart;
        g2.drawString("Draw time (nano): "+drawTime,(int)player.getX()-width/2+5,(int)player.getY()-height/2+10);

        g2.dispose(); //Get rid of the graphics when we are done
    }

    public class KeyInput implements KeyListener {
        private HashMap<String,Boolean> keysDown = new HashMap<String,Boolean>();
    
        public boolean isKeyDown(String key){
            return keysDown.getOrDefault(key, false);
        }
    
        @Override
        public void keyTyped(KeyEvent e) {
            //Not used
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            String keyString = KeyEvent.getKeyText(e.getKeyCode());
            keysDown.put(keyString,true);
            GamePanel.this.keyPressed(keyString);
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
            String keyString = KeyEvent.getKeyText(e.getKeyCode());
            keysDown.put(keyString,false);
            GamePanel.this.keyReleased(keyString);
            
        }
    
    }
}
