import game.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceStation extends Sprite {

    public SpaceStation(){
        x = 0;
        y = 0;
        width = 200;
        height = 200;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect((int)x-width/2,(int)y-height/2,width,height);
    }
    
}