import game.Sprite;
import java.awt.Image;

public class Asteroid extends Sprite {

    private static Image[] asteroidImages = new Image[3];
    private double velX, velY, rotationSpeed;
    static {   
        for (int i = 1; i < 4; i++){
            asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
        }
    }
    
    public Asteroid(double x, double y,double scale){
        super(asteroidImages[(int)(Math.random()*asteroidImages.length)],x,y,scale);
        rotation = Math.random()*360+1;
        rotationSpeed = Math.random()*100;
    }

    public void update(){
        x += velX;
        y += velY;
        rotation += rotationSpeed;
    }
}
