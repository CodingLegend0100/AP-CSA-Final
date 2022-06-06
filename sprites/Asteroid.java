package sprites;
import game.Sprite;
import java.awt.Image;
import java.util.HashMap;

public class Asteroid extends Sprite {

    private static String[] resourceTypes = {"Iron","Gold","Lithium","Osmium","Hydrogen","Platinum"};
    private static Image[] asteroidImages = new Image[3];
    static {  
        //Load images 
        for (int i = 1; i < 4; i++){
            asteroidImages[i-1] = loadImage("assets/asteroid"+i+".png");
        }
    }

    private HashMap<String,Integer> startingResources = new HashMap<String,Integer>(); 
    private HashMap<String,Integer> resources = new HashMap<String,Integer>();
    private int resourceCount;
    private int initialWidth, initialHeight;

    public Asteroid(double x, double y){
        
        super(
            asteroidImages[(int)(Math.random()*asteroidImages.length)], //Random asteroid image
            x,y   //Position
        );

        double scale = Math.random()*.3+.3; //Set the scale

        //Scale the width and height
        width *= scale;
        height *= scale;

        initialWidth = width;
        initialHeight = height;
        
        rotation = Math.random()*360+1;
        rotationSpeed = Math.random()*2-1;

        //Use the scale to generate larger amounts of resources
        scale *= 10;
        scale /= 3;
        //System.out.println(scale);

        //Valuable materials have larger amounts further from the station
        //Generate more resources further from the station
        double distanceBonus = 1 + (x*x+y*y) / 1_000_000_000;
        for (String r : resourceTypes){
            int amount = (int)(Math.random()*10*scale*distanceBonus+1);
            startingResources.put(r,amount);
            resources.put(r,amount);
            resourceCount += amount;
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
            resourceCount -= toAdd;
        }

        //Decrease size of the asteroid
        width -= strength * initialWidth / 25;
        height -= strength * initialHeight / 25;

        //Return the resources collected from mining
        return mined;
    }

    public boolean destroyed(){
        return resourceCount == 0;
    }
}
