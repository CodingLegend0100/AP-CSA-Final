import game.Sprite;

public class SpaceStation extends Sprite {
    private double rotationSpeed = 0.01;

    public SpaceStation(){
        super("assets/station.png",0,0,200,200);
    }
    
    public void update(){
        rotation+=rotationSpeed;
    }
}