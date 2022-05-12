import game.Sprite;
import java.awt.Image;

public  class Beam extends Sprite {
    GamePanel.KeyInput k;
    boolean draw = false;
    private static Image beam = Sprite.loadImage("beam.png");
    public Beam(double x, double y,GamePanel.KeyInput k){
        super(beam,x,y);
        this.k=k;
    }
    
    public void update(){
        if(k.isKeyDown("Space"))
            draw=true;
        else draw = false;
    }
    //getter for draw
    public boolean getDraw(){return draw;}

}
