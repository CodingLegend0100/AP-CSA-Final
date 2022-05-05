import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){

        //Initialize the game window
        JFrame window = new JFrame("Game Window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400,600);
        window.add(new GamePanel());
        window.setVisible(true);
    }
}