import game.Sprite;
import java.awt.Image;
import java.util.*;

//test

public class Asteroid extends Sprite{
    
    
    private Image[] asteroidImages = new Image[3];

    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();{

    for (int i = 1; i < 4; i++){
        asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
    }
    
    asteroids.add(new Asteroid(asteroidImages[0],100,200));
    asteroids.add(new Asteroid(asteroidImages[1],150,200));
    asteroids.add(new Asteroid(asteroidImages[2],200,200));}
    
    public Asteroid(Image i, double x, double y){
        super(i,x,y);
        
    }
}
