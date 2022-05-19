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

    Button buyButton = new Button("Upgrades",150,250,250,100,
                                new ButtonStyle().setFont(font).setFontColor(Color.WHITE).setBackground(new Color(0,150,0))
                                );

    Button sellButton = new Button("Market",500,250,250,100,
                                new ButtonStyle().setFont(font).setFontColor(Color.WHITE).setBackground(new Color(200,0,0))
                                );
    
    //Sell Buttons
    ButtonStyle sellStyle = new ButtonStyle().setFont(font1).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcWidth(10).setArcHeight(10).setBorderWidth(0, 2, 0, 2);
    ButtonStyle selectedStyle = new ButtonStyle().setFont(font1).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcWidth(10).setArcHeight(10).setBorderColor(Color.WHITE).setBorderWidth(0, 2, 0, 2);;
    Button sellHydrogen = new Button("Hydrogen",110,95,150,50,selectedStyle);
    Button sellLithium = new Button("Lithium",110,155,150,50,sellStyle);
    Button sellIron = new Button("Iron",110,215,150,50,sellStyle);
    Button sellGold = new Button("Gold",110,275,150,50,sellStyle);
    Button sellOsmium = new Button("Osmium",110,335,150,50,sellStyle);
    Button sell1 = new Button("Sell",110,395,150,50,sellStyle);
    Button sell2 = new Button("Sell",110,455,150,50,sellStyle);
    Button[] sellButtons = {sellHydrogen,sellLithium,sellIron,sellGold,sellOsmium,sell1,sell2};
    int selected = 0;
    int sellAmount = 1;

    //Chart window (x 300, y 100, w 500, h 350)

    ButtonStyle style3 = new ButtonStyle().setFont(new Font(Font.DIALOG,Font.PLAIN,15)).setFontColor(Color.WHITE);
    Button increaseAmount = new Button("+1",590,470,30,30,style3);
    Button decreaseAmount = new Button("-1",480,470,30,30,style3);

    //Buy buttons
    int money = 0;
    ButtonStyle cantAfford = new ButtonStyle();
    ButtonStyle canBuy = new ButtonStyle();
    Button[] buyButtons = {};
    int[] buyCosts = {};

    //Misc menu things
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
            else if (screenID == SELL_SCREEN){
                for (int i = 0; i < sellButtons.length; i++){
                    if (sellButtons[i].contains(x,y)){
                        sellButtons[selected].style = sellStyle;
                        selected = i;
                        sellButtons[i].style = selectedStyle;
                    }
                }
                if (increaseAmount.contains(x,y)){

                }
                else if (decreaseAmount.contains(x,y)){

                }
            }
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

        if (screenID == MENU_SCREEN){
            drawCentered(g,"Space Station",450,90);
            exit.draw(g);
            buyButton.draw(g);
            sellButton.draw(g);
        } else {
            back.draw(g);
            if (screenID == BUY_SCREEN){
                g.setColor(Color.GREEN);
                g.fillOval(450,300,10,10);
                for (int i = 0; i < buyButtons.length; i++){
                    if (buyCosts[i] < money){
                        buyButtons[i].style = cantAfford;
                    } else {
                        buyButtons[i].style = canBuy;
                    }
                }
            }
            else if (screenID == SELL_SCREEN){
                g.setColor(Color.DARK_GRAY);
                for (Button b : sellButtons){
                    b.draw(g);
                }
                increaseAmount.draw(g);
                decreaseAmount.draw(g);
                g.setColor(Color.BLACK);
                g.drawRect(300,100,500,350);
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
        String text;
        ButtonStyle style = new ButtonStyle();
        
        public Button(String msg, int x, int y, int width, int height, ButtonStyle style){
            super(x,y,width,height);
            text = msg;
            if (style != null) this.style = style;
        }

        public void draw(Graphics2D g){

            if (style.border != null) g.setColor(style.border);
            g.fillRoundRect(x,y,width,height,style.arcWidth,style.arcHeight);


            if (style.background != null) g.setColor(style.background);
            g.fillRoundRect(x+style.borderLeft,y+style.borderTop,
                            width-style.borderLeft-style.borderRight,height-style.borderTop-style.borderBottom,
                            style.arcWidth,style.arcHeight);

            if (style.font != null) g.setFont(style.font);
            if (style.fontColor != null) g.setColor(style.fontColor);

            //Draws the text centered within the button
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(text, g);
            int x = this.x+(this.width - (int) r.getWidth()) / 2;
            int y = this.y+(this.height - (int) r.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
        }
    }

    public class ButtonStyle {
        Font font;
        Color fontColor = Color.BLACK;
        Color background = Color.BLACK;
        Color border = Color.BLACK;
        int borderTop = 0;
        int borderBottom = 0;
        int borderLeft = 0;
        int borderRight = 0;
        int arcWidth = 0;
        int arcHeight = 0;

        public ButtonStyle setFont(Font f){
            font = f;
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
        public ButtonStyle setBorderWidth(int x){
            borderTop = x;
            borderBottom = x;
            borderLeft = x;
            borderRight = x;
            return this;
        }
        public ButtonStyle setBorderWidth(int top,int bottom,int left, int right){
            borderTop = top;
            borderBottom = bottom;
            borderLeft = left;
            borderRight = right;
            return this;
        }
        public ButtonStyle setArcWidth(int x){
            arcWidth = x;
            return this;
        }
        public ButtonStyle setArcHeight(int x){
            arcHeight = x;
            return this;
        }
    }
}
