import game.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceStation extends Sprite {

    public SpaceStation(){
        super("",0,0);
    }

    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect(-100,-100,200,200);
    }
    
}