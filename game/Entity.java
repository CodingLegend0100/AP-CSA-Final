package game;

import java.awt.Image;

public class Entity extends Sprite {
    protected double velocityX, velocityY;
    protected double rotationSpeed; //Degrees per frame

    public Entity(String image, double x, double y) {
        super(image, x, y);
    }
    public Entity(Image i, double x, double y) {
        super(i, x, y);
    }
    public Entity(String image, double x, double y, int width, int height) {
        super(image, x, y, width, height);
    }
    public Entity(Image i, double x, double y, int width, int height) {
        super(i, x, y, width, height);
    }

    //Getters
    public double getVelX(){ return velocityX; }
    public double getVelY(){ return velocityY; }
    public double getRotationSpeed(){ return rotationSpeed; }

    //Setters
    public void setVelX(double velX){ velocityX = velX; }
    public void setVelY(double velY){ velocityY = velY; }
    public void setRotationSpeed(double speed){ rotationSpeed = speed; }

    public void update(){
        x += velocityX;
        y += velocityY;
        rotation += rotationSpeed;
    }
}
