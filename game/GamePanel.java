package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private int FPS = 60;

    protected KeyInput keyListener = new KeyInput();
    protected MouseInput mouseListener = new MouseInput();
    
    Thread gameThread;

    public GamePanel(int width, int height, int FPS){
        this.FPS = FPS;

        setPreferredSize(new Dimension(width, height));
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        setFocusable(true);
        setBackground(new Color(10,10,10));

    }

    public void start(){
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
  
    /** Update positions of objects on the screen */
    public void update(){    }

    public void draw(Graphics2D g2){    }

    public void keyPressed(String key){    }

    public void keyReleased(String key){    }

    public void mousePressed(int x, int y){    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better

        draw(g2);

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
            if (!isKeyDown(keyString)) GamePanel.this.keyPressed(keyString);
            keysDown.put(keyString,true);
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
            String keyString = KeyEvent.getKeyText(e.getKeyCode());
            keysDown.put(keyString,false);
            GamePanel.this.keyReleased(keyString);
            
        }
    
    }

    public class MouseInput implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            GamePanel.this.mousePressed(e.getX(),e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

    }
}

