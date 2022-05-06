package game;

import java.awt.Image;

public class Entity extends Sprite {
    private double velocityX, velocityY;
    private double rotationSpeed;

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

    public double getVelocityX(){ return velocityX; }
    public double getVelocityY(){ return velocityY; }
    public double getRotationSpeed(){ return rotationSpeed; }

    public void setVelocityX(double velocityX){ this.velocityX = velocityX; }
    public void setVelocityY(double velocityY){ this.velocityY = velocityY; }
    public void setRotationSpeed(double rotationSpeed){ this.rotationSpeed = rotationSpeed; }

    public void update(){
        setX(getX()+velocityX);
        setY(getY()+velocityY);
    }
}
