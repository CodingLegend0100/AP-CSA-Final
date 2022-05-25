import game.Sprite;
import java.awt.Image;
import java.util.HashMap;

public class Asteroid extends Sprite {

    private static Image[] asteroidImages = new Image[3];
    static {  
        //Load images 
        for (int i = 1; i < 4; i++){
            asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
        }
    }
    
    private HashMap<String,Integer> resources = new HashMap<String,Integer>(); 

    public Asteroid(double x, double y){
        
        super(
            asteroidImages[(int)(Math.random()*asteroidImages.length)], //Random asteroid image
            x,y,   //Position
            Math.random()*.3+.3 //Image Scale
        );
        
        rotation = Math.random()*360+1;
        rotationSpeed = Math.random()*2-1;

        //Valuable materials have larger amounts further from the station?
        resources.put("Iron",(int)(Math.random()*10+1));
        resources.put("Gold",(int)(Math.random()*10+1));
        resources.put("Hydrogen",(int)(Math.random()*10+1));
        resources.put("Osmium",(int)(Math.random()*10+1));
        resources.put("Lithium",(int)(Math.random()*10+1));
        resources.put("Platinum",(int)(Math.random()*10+1));
    }

    public HashMap<String,Integer> getResources(){
        return resources;
    }
}
