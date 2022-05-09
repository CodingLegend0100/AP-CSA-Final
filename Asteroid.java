import game.Sprite;
import java.awt.Image;
import java.util.*;

//test

public class Asteroid extends Sprite{
    
    
    private Image[] asteroidImages = new Image[3];

    {   
        for (int i = 1; i < 4; i++){
            asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
        }
    }
    
    public Asteroid(Image i, double x, double y){
        super(i,x,y);
        
    }
}
