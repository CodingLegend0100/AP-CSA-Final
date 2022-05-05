package game;

import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Sprite {
    private double x, y;
    private int width,height;
    private int rotation; //Rotation in degrees
    private Image i;

    /**Create a sprite whose width and height will be inherited from the image's width and height */
    public Sprite(String image,int x,int y){
        this(loadImage(image),x,y);
    }

    /**Create a sprite whose width and height will be inherited from the image's width and height */
    public Sprite(Image i,int x,int y){
        this(i,x,y,i.getWidth(null),i.getHeight(null));
    }

    public Sprite(String image,int x,int y,int width,int height){
        this(loadImage(image),x,y,width,height);
    }

    public Sprite(Image i,int x,int y,int width, int height){
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

    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
    public void setWidth(int width){ this.width = width; }
    public void setHeight(int height){ this.height = height; }
    public void setImage(Image i){ this.i = i; }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public Image getImage(){ return i; }
    public int getCenterX(){ return (int)x+width/2; }
    public int getCenterY(){ return (int)y+height/2; }

    /** Draws the sprite */
    public void draw(Graphics2D g){

        int centerX = getCenterX();
        int centerY = getCenterY();
        double rotate = Math.toRadians(rotation);

        g.rotate(rotate,centerX,centerY);
        g.drawImage(i,(int)x,(int)y,width,height,null);
        g.rotate(-rotate,centerX,centerY);

    }
}
