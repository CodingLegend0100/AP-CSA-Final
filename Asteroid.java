import game.Sprite;
import java.awt.Image;


//test

public class Asteroid extends Sprite{
    Sprite target;

    public Asteroid(Image i, double x, double y, Sprite target){
        super(i,x,y);
        this.target = target;
    }
}
