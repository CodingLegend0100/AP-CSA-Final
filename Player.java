import game.Sprite;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Player extends Sprite {
    GamePanel.KeyInput k; //The key input handler

    Sprite beam = new Sprite("assets/beam.png",0,0,100,100);

    private boolean mining = false;
    private double turnSpeed = 2.5;
    private double acceleration = 0.2; //Increases max speed by 5 per 0.1
    private double glide = 0.98;
    private HashMap<String,Integer> inventory = new HashMap<String,Integer>();

    public Player(GamePanel.KeyInput k){
        super("assets/spaceship.png",150,150,0.1);
        this.k = k;
    }

    public boolean isMining(){ return mining; }

    //Reverses the direction of the velocity vector
    public void bounce(){
        velX *= -1;
        velY *= -1;
    }

    public void collect(HashMap<String,Integer> resources){
        for (String k : resources.keySet()){
            inventory.put(k,inventory.getOrDefault(k, 0)+resources.get(k));
        }
    }
  
    public void update(){
        super.update();

        //double velocity = Math.sqrt(velX*velX+velY*velY); //Calculate magnitude of velocity vector
        //System.out.println(velocity);

        velX *= glide;
        velY *= glide;

        if (k.isKeyDown("W")){
            //Convert direction from degrees to radians
            double rad = Math.toRadians(rotation-90); //Vector angle correction

            //Add acceleration vector to velocity vector
            velX += acceleration*Math.cos(rad);
            velY += acceleration*Math.sin(rad);
        }
        if (k.isKeyDown("S")){
            //Convert direction from degrees to radians
            double rad = Math.toRadians(rotation-90); //Vector angle correction

            //Add acceleration vector to velocity vector
            velX -= (acceleration*Math.cos(rad))/2;
            velY -= (acceleration*Math.sin(rad))/2;
        }
        
        if(k.isKeyDown("Space")){
            mining = true;
        } else { mining = false; }

        if (k.isKeyDown("A")) rotation -= turnSpeed;
        if (k.isKeyDown("D")) rotation += turnSpeed;

        beam.setX(x);
        beam.setY(y);
        beam.setRotation(rotation);
    }

    public void draw(Graphics2D g){
        super.draw(g);

        if (mining) beam.draw(g);
    }
}
