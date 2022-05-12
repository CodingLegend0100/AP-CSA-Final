import game.Sprite;

import java.awt.Graphics2D;

public class Player extends Sprite {
    GamePanel.KeyInput k; //The key input handler

    Sprite beam = new Sprite("assets/beam.png",0,0,100,100);

    private boolean mining = false;
    private double velX = 0, velY = 0;
    private double turnSpeed = 2.5;
    private double acceleration = 0.2; //Increases max speed by 5 per 0.1
    private double glide = 0.98;

    public Player(GamePanel.KeyInput k){
        super("assets/spaceship.png",150,150,0.1);
        this.k = k;
    }
  
    public void update(){
        //This is just for testing
        x += velX;
        y += velY;

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
        if(k.isKeyDown("Space")){
            mining = true;
        } else { mining = false; }

        if (k.isKeyDown("A")) rotation -= turnSpeed;
        if (k.isKeyDown("D")) rotation += turnSpeed;

        beam.setX(x);
        beam.setY(y);
        beam.setRotation(rotation);
    }
    
    public void bounce(){
        velX = -velX;
        velY = -velY;
    }

    public void draw(Graphics2D g){
        super.draw(g);

        if (mining){
            beam.draw(g);
        }
    }
    public boolean isMining(){return mining;}
}
