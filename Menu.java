import game.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
    //Screen dimensions 900x600
    //I honestly dont know what the best way to do this is

    private boolean open = false;
    private int screenID;

    final int MENU_SCREEN = 0;
    final int BUY_SCREEN = -1;
    final int SELL_SCREEN = 1;

    Rectangle buyButton = new Rectangle(200,250,200,100);
    Rectangle sellButton = new Rectangle(500,250,200,100);

    Sprite exit = new Sprite(Sprite.loadImage("assets/x.png"),80,80,30,30);
    Sprite back = new Sprite(Sprite.loadImage("assets/back_arrow.png"),80,80,30,30);


    public void open(){
        open = true;
        screenID = MENU_SCREEN;
    }
    public void close(){ open = false; }
    public boolean isOpen(){ return open; }

    public void getInteraction(int x, int y){
        if (!open) return;

        if (screenID == MENU_SCREEN){
            if (buyButton.contains(x,y)) screenID = BUY_SCREEN;
            else if (sellButton.contains(x,y)) screenID = SELL_SCREEN;
            else if (exit.contains(x,y)) close();
        } else {
            if (back.contains(x,y)) screenID = MENU_SCREEN;
        }
    }

    public void draw(Graphics2D g){
        if (!open) return;

        //Draw the window
        g.setColor(Color.WHITE);
        g.fillRoundRect(50,50,800,500,10,10);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(60,60,780,480);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        g.drawString("Space Station",400,80);

        if (screenID == MENU_SCREEN){
            exit.draw(g);

            g.setColor(new Color(0,150,0));
            g.fill(buyButton);

            g.setColor(new Color(200,0,0));
            g.fill(sellButton);

            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG,Font.PLAIN,50));
            g.drawString("Buy",centerX(buyButton)-40,centerY(buyButton)+15);
            g.drawString("Sell",centerX(sellButton)-40,centerY(sellButton)+15);
        } else {
            back.draw(g);
            if (screenID == BUY_SCREEN){
                g.setColor(Color.GREEN);
                g.fillOval(450,300,10,10);
            }
            else if (screenID == SELL_SCREEN){
                g.setColor(Color.RED);
                g.fillOval(450,300,10,10);
            }
        }
    }

    private int centerX(Rectangle r){
        return r.x+r.width/2;
    }

    private int centerY(Rectangle r){
        return r.y+r.height/2;
    }
}
