import game.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceStation extends Sprite {

    double x = -100, y = -100;
    int width = 200, height = 200;

    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect((int)x,(int)y,width,height);
    }
    
}