package game;

import java.awt.Image;

public class Entity extends Sprite {
    public double velocityX, velocityY;
    public double rotationSpeed;

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

    public void update(){
        x += velocityX;
        y += velocityY;
        rotation += rotationSpeed;
    }
}
