import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    private final int FPS = 60;

    Thread gameThread;

    public GamePanel(){
        gameThread = new Thread(this);
    }

    public void run(){
        
        int drawInterval = 1000000000/FPS;
        long lastTime = System.nanoTime();
        long currentTime;
        long delta = 0;

        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= drawInterval){
                update();
                repaint();
                delta -= drawInterval;
            }
        }
    }

    /** Update positions of objects on the screen */
    public void update(){

    }


    /** Draw objects to the screen */
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g; //Use graphics 2d because its better

    }
}
