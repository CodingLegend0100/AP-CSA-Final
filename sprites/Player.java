package sprites;

import game.GamePanel;
import game.Sprite;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Player extends Sprite {
    GamePanel.KeyInput k; //The key input handler

    public static int UPGRADE_SHIELD = 1;
    public static int UPGRADE_SPEED = 2;
    public static int UPGRADE_MINING = 3;
    public static int UPGRADE_CAPACITY = 4;
    public static int UPGRADE_SHIP = -1;

    private Sprite beam = new Sprite("assets/beam.png",0,0,100,100);
    private boolean mining = false;
    private double turnSpeed = 2.5;
    private double acceleration = 0.2; //Increases max speed by 5 per 0.1
    private double glide = 0.98;

    private HashMap<String,Integer> inventory = new HashMap<String,Integer>();
    private int stored = 0;
    private int capacity = 100;
    private int beamLevel = 0;
    private int maxShield = 50;
    private int shield = 50;
    

    public Player(GamePanel.KeyInput k){
        super("assets/spaceship.png",150,0,0.1);
        this.k = k;
    }

    public boolean isMining(){ return mining; }

    public void upgrade(int upgrade){
        if (upgrade == UPGRADE_SPEED){
            acceleration += 0.05;
        }

        else if (upgrade == UPGRADE_SHIELD){
            shield += 10;
            maxShield += 10;
        }

        else if (upgrade == UPGRADE_MINING){
            beamLevel += 1;
        }
    }

    public void mine(Asteroid a){
        addResources(a.mine(beamLevel));

        if (!a.destroyed()){
            bounce();
        }
    }

    public void takeDamage(int damage){
        if (shield < 0){
            inventory.clear();
            setPos(150,0);
            shield = maxShield;
        } else {
            shield -= damage;
        }
    }

    //Reverses the direction of the velocity vector
    public void bounce(){
        velX *= -1;
        velY *= -1;
    }

    private void addResources(HashMap<String,Integer> resources){
        for (String k : resources.keySet()){
            int toAdd = resources.get(k);

            if (stored + toAdd > capacity){
                toAdd = capacity - stored;
            }
            inventory.put(k,toAdd+inventory.getOrDefault(k,0));
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
