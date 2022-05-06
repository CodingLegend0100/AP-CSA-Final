import game.Sprite;

import javax.swing.JFrame;

public class Main {

    //Useful Links
    //https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
    //https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
    public static void main(String[] args){

        //Initialize the game window
        JFrame window = new JFrame("Space Miner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(new GamePanel());
        window.pack();
        window.setIconImage(Sprite.loadImage("assets/spaceship.png"));
        window.setVisible(true);
    }
}