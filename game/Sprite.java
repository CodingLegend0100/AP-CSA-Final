package game;

import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

import java.awt.Color;

/** This class is intended for displaying static images onto the screen */
public class Sprite {
    private double x, y; //Represents the sprites CENTER x and y
    private int width,height;
    private int rotation; //Rotation in degrees
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
        rotation = 10;
    }

    /**Loads the image from the string filepath */
    public static Image loadImage(String imagePath){
        return new ImageIcon(imagePath).getImage();
    }

    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
    public void setWidth(int width){ this.width = width; }
    public void setHeight(int height){ this.height = height; }
    public void setImage(Image i){ this.i = i; }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public Image getImage(){ return i; }

    /** Draws the sprite */
    public void draw(Graphics2D g){

        double rotate = Math.toRadians(rotation);

        g.rotate(rotate,x,y);
        g.drawImage(i,(int)x-width/2,(int)y-height/2,width,height,null);

        g.rotate(-rotate,x,y);

        g.setColor(Color.RED);
        g.fillOval((int)x,(int)y,5,5);

    }
}
