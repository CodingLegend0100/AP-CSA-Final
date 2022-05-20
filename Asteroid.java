import game.Sprite;
import java.awt.Image;

public class Asteroid extends Sprite {

    private static Image[] asteroidImages = new Image[3];
    static {  
        //Load images 
        for (int i = 1; i < 4; i++){
            asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
        }
    }
    
    public Asteroid(double x, double y){
        
        super(
            asteroidImages[(int)(Math.random()*asteroidImages.length)], //Random asteroid image
            x,y,   //Position
            Math.random()*.3+.3 //Scale
        );
        
        rotation = Math.random()*360+1;
        rotationSpeed = Math.random()*2-1;
    }
    //iron gold litium 
}
