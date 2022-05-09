import game.Sprite;

import java.awt.Image;

public class Enemy extends Sprite {
    Sprite target;

    public Enemy(Image i, double x, double y, Sprite target){
        super(i,x,y,0.3);
        this.target = target;
    }
    public void update(){
        rotation += (Math.toDegrees(Math.atan2(target.getX()-x,y-target.getY())));
    }
}
