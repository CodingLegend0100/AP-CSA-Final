package game;

import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/** This class is intended for displaying static images onto the screen */
public class Sprite {
    public double x, y; //Represents the sprites CENTER x and y
    public int width,height;
    public double rotation; //Rotation in degrees
    private Image i;

    /**Create a sprite whose width and height will be inherited from the image's width and height */
    public Sprite(String image,double x,double y){
        this(loadImage(image),x,y);
    }

    /**Create a sprite whose width and height will be inherited from the image's width and height */
    public Sprite(Image i,double x,double y){
        this(i,x,y,i.getWidth(null),i.getHeight(null));
    }

    public Sprite(String image,double x,double y,int width,int height){
        this(loadImage(image),x,y,width,height);
    }

    public Sprite(Image i,double x,double y,int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setImage(i);
    }

    /**Loads the image from the string filepath */
    public static Image loadImage(String imagePath){
        return new ImageIcon(imagePath).getImage();
    }

    public void setImage(Image i){ this.i = i; }
    public Image getImage(){ return i; }

    /** Draws the sprite */
    public void draw(Graphics2D g){
        if (i == null) return;

        double rotate = Math.toRadians(rotation);

        g.rotate(rotate,x,y);
        
        g.drawImage(i,(int)x-width/2,(int)y-height/2,width,height,null);

        g.rotate(-rotate,x,y);

    }
}
