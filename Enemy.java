import game.Sprite;

import java.awt.Image;

public class Enemy extends Sprite {
    private Sprite target;
    private double velX = 0, velY = 0;
    private double acceleration = 0.2; //Increases max speed by 5 per 0.1
    private double glide = 0.98;
    private double followDistance = 150;

    public Enemy(Image i, double x, double y, Sprite target){
        super(i,x,y,0.3);
        this.target = target;
    }
    public void update(){
        //Very basic tracking/pathfinding

        x += velX;
        y += velY;

        velX *= glide;
        velY *= glide;

        double rad = (Math.atan2(target.getX()-x,y-target.getY()));
        rotation = Math.toDegrees(rad);

        if (Math.pow(target.getX()-x,2)+Math.pow(target.getY()-y,2) > followDistance*followDistance){
            //Add acceleration vector to velocity vector
            velX += acceleration*Math.cos(rad-Math.PI/2);
            velY += acceleration*Math.sin(rad-Math.PI/2);
        }
    }
}
