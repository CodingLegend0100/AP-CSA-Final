import game.Sprite;

// import java.awt.Color;
// import java.awt.Graphics2D;

public class SpaceStation extends Sprite {
    private double rotationSpeed = 0.01;
    public SpaceStation(){
        super("assets/station.png",0,0);
        x = 0;
        y = 0;
        width = 200;
        height = 200;
    }    
    public void update(){
        rotation+=rotationSpeed;
    }
}