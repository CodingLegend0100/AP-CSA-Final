import game.Sprite;

public class Player extends Sprite {
    KeyInput k; //The key input handler

    private double velX = 0, velY = 0;
    private double turnSpeed = 2.5;
    private double acceleration = 0.2;
    private double glide = 0.98;
    //private int maxSpeed = 5;

    public Player(KeyInput k){
        super("assets/spaceship.png",200,200,0.1);
        this.k = k;
    }

    public void update(){
        //This is just for testing
        x += velX;
        y += velY;

        double velocity = Math.sqrt(velX*velX+velY*velY); //Calculate magnitude of velocity vector
        System.out.println(velocity);

        velX *= glide;
        velY *= glide;

        if (k.isKeyDown("W")){
            //Add vectors
            double rad = Math.toRadians(rotation-90); //Vector angle correction

            //Create acceleration vector and add it to velocity vector
            velX += acceleration*Math.cos(rad);
            velY += acceleration*Math.sin(rad);


            //double direction = Math.atan2(velY, velX); //Calculate direction of the velocity vector
            //velocity *= 0.7; //Resistance factor

            //Cap the velocity
            //if (velocity > maxSpeed) { velocity = maxSpeed; }
        }
        if (k.isKeyDown("A")) rotation -= turnSpeed;
        if (k.isKeyDown("D")) rotation += turnSpeed;

    }
}
