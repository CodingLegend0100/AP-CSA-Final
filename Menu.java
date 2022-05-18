import game.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Menu {
    //Screen dimensions 900x600
    //I honestly dont know what the best way to do this is

    private boolean open = false;
    private int screenID;

    final int MENU_SCREEN = 0;
    final int BUY_SCREEN = -1;
    final int SELL_SCREEN = 1;

    Font font = new Font(Font.DIALOG,Font.PLAIN,50);
    Font font1 = new Font(Font.DIALOG,Font.PLAIN,25);

    Button buyButton = new Button("Buy",200,250,200,100,
                                Color.WHITE,new Color(0,150,0),
                                font);

    Button sellButton = new Button("Sell",500,250,200,100,
                                Color.WHITE,new Color(200,0,0),
                                font);
    
    ButtonStyle sellStyle = new ButtonStyle().setFont(font1).setFontColor(Color.WHITE).setBackground(Color.GRAY);
    Button sellHydrogen = new Button("Hydrogen",110,90,150,50,sellStyle);
    Button sellLithium = new Button("Lithium",110,150,150,50,sellStyle);
    Button sellIron = new Button("Iron",110,210,150,50,sellStyle);
    Button sellGold = new Button("Gold",110,270,150,50,sellStyle);
    Button sellOsmium = new Button("Osmium",110,330,150,50,sellStyle);
    Button sell1 = new Button("Sell",110,390,150,50,sellStyle);
    Button sell2 = new Button("Sell",110,450,150,50,sellStyle);
    Button[] sellButtons = {sellHydrogen,sellLithium,sellIron,sellGold,sellOsmium,sell1,sell2};


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
        g.setFont(font);
        drawCentered(g,"Space Station",450,90);

        if (screenID == MENU_SCREEN){
            exit.draw(g);
            buyButton.draw(g);
            sellButton.draw(g);
        } else {
            back.draw(g);
            if (screenID == BUY_SCREEN){
                g.setColor(Color.GREEN);
                g.fillOval(450,300,10,10);
            }
            else if (screenID == SELL_SCREEN){
                g.setColor(Color.DARK_GRAY);
                for (Button b : sellButtons){
                    b.draw(g);
                }
            }
        }
    }

    private void drawCentered(Graphics2D g,String text, int x, int y){
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int X = x-((int) r.getWidth()) / 2;
        int Y = y-((int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, X, Y);
    }

    public class Button extends Rectangle {
        Font font;
        String text;
        Color bg;
        Color tc;
        
        public Button(String msg, int x, int y, int width, int height, ButtonStyle style){
            super(x,y,width,height);
            text = msg;
            if (style != null){
                font = style.f;
                tc = style.fontColor;
                bg = style.background;
            }
        }

        public Button(String msg, int x, int y, int width, int height,Color textColor,Color background,Font f){
            super(x,y,width,height);
            text = msg;
            bg = background;
            tc = textColor;
            font = f;
        }

        public void draw(Graphics2D g){
            if (bg != null) g.setColor(bg);
            
            g.fill(this);

            if (font != null) g.setFont(font);
            if (tc != null) g.setColor(tc);


            FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(text, g);
            int x = this.x+(this.width - (int) r.getWidth()) / 2;
            int y = this.y+(this.height - (int) r.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
        }
    }

    public class ButtonStyle {
        Font f;
        Color fontColor;
        Color background;
        Color border;
        int borderWidth;
        public ButtonStyle setFont(Font f){
            this.f = f;
            return this;
        }
        public ButtonStyle setFontColor(Color c){
            fontColor = c;
            return this;
        }
        public ButtonStyle setBackground(Color c){
            background = c;
            return this;
        }
        public ButtonStyle setBorderColor(Color c){
            border = c;
            return this;
        }
        public ButtonStyle setBorderWidth(int w){
            borderWidth = w;
            return this;
        }
    }
}
