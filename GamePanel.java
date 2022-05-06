import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private final int FPS = 60;

    KeyInput keyListener = new KeyInput();

    Player player = new Player(keyListener);

    Thread gameThread;

    public GamePanel(){

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

    /** Update positions of objects on the screen */
    public void update(){

        player.update();

    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better
        
        player.draw(g2);

        g2.dispose(); //Get rid of the graphics when we are done
    }
}
