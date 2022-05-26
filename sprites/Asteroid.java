package sprites;
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

    private HashMap<String,Integer> startingResources = new HashMap<String,Integer>(); 
    private HashMap<String,Integer> resources = new HashMap<String,Integer>();
    private int initialWidth, initialHeight;
    private int timesMined;

    public Asteroid(double x, double y){
        
        super(
            asteroidImages[(int)(Math.random()*asteroidImages.length)], //Random asteroid image
            x,y,   //Position
            Math.random()*.3+.3 //Image Scale
        );

        initialWidth = width;
        initialHeight = height;
        
        rotation = Math.random()*360+1;
        rotationSpeed = Math.random()*2-1;

        //Valuable materials have larger amounts further from the station?
        startingResources.put("Iron",(int)(Math.random()*10+1));
        startingResources.put("Gold",(int)(Math.random()*10+1));
        startingResources.put("Hydrogen",(int)(Math.random()*10+1));
        startingResources.put("Osmium",(int)(Math.random()*10+1));
        startingResources.put("Lithium",(int)(Math.random()*10+1));
        startingResources.put("Platinum",(int)(Math.random()*10+1));

        for (String k : startingResources.keySet()){
            resources.put(k,startingResources.get(k));
        }
    }

    public HashMap<String,Integer> mine(int strength){
        //Mine the asteroid for a portion of its resources
        HashMap<String,Integer> mined = new HashMap<String,Integer>();
        for (String k : startingResources.keySet()){
            int toAdd = startingResources.getOrDefault(k, 0) * strength / 10 + 1; //Calculate resources to mine
            toAdd = Math.min(toAdd,resources.getOrDefault(k,0));
            mined.put(k,toAdd);
            resources.put(k,resources.getOrDefault(k,0)-toAdd);
        }

        //Decrease size of the asteroid
        timesMined += strength;
        width -= strength * initialWidth / 25;
        height -= strength * initialHeight / 25;

        //Return the resources collected from mining
        return mined;
    }

    public boolean destroyed(){
        return timesMined >= 20;
    }
}
