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
    final int UPGRADE_SCREEN = -1;
    final int MARKET_SCREEN = 1;

    //Colors
    Color DARK_GREEN = new Color(0,150,0);

    //Fonts
    Font fontSize50 = new Font(Font.DIALOG,Font.PLAIN,50);
    Font fontSize25 = new Font(Font.DIALOG,Font.PLAIN,25);
    Font fontSize15 = new Font(Font.DIALOG,Font.PLAIN,15);

    Button upgrade = new Button("Upgrades",150,250,250,100,
                                new ButtonStyle().setFont(fontSize50).setFontColor(Color.WHITE).setBackground(DARK_GREEN)
                                );

    Button market = new Button("Market",500,250,250,100,
                                new ButtonStyle().setFont(fontSize50).setFontColor(Color.WHITE).setBackground(new Color(200,0,0))
                                );
    
    //Sell Buttons
    ButtonStyle sellStyle = new ButtonStyle().setFont(fontSize25).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcSize(10).setBorderWidth(0, 2, 0, 2);
    ButtonStyle selectedStyle = new ButtonStyle().setFont(fontSize25).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcSize(10).setBorderColor(Color.WHITE).setBorderWidth(0, 2, 0, 2);;
    Button sellHydrogen = new Button("Hydrogen",110,95,150,50,selectedStyle);
    Button sellLithium = new Button("Lithium",110,155,150,50,sellStyle);
    Button sellIron = new Button("Iron",110,215,150,50,sellStyle);
    Button sellGold = new Button("Gold",110,275,150,50,sellStyle);
    Button sellOsmium = new Button("Osmium",110,335,150,50,sellStyle);
    Button sellPlatinum = new Button("Platinum",110,395,150,50,sellStyle);
    Button sell2 = new Button("Sell",110,455,150,50,sellStyle);
    Button[] markets = {sellHydrogen,sellLithium,sellIron,sellGold,sellOsmium,sellPlatinum,sell2};
    int selected = 0;
    int sellAmount = 1;

    //Chart window (x 300, y 100, w 500, h 350)

    ButtonStyle style3 = new ButtonStyle().setFont(fontSize15).setFontColor(Color.WHITE);
    Button increaseAmount = new Button("+1",590,470,30,30,style3);
    Button decreaseAmount = new Button("-1",480,470,30,30,style3);

    //Buy buttons
    int money = 0;
    ButtonStyle cantAfford = new ButtonStyle().setFont(fontSize15).setBackground(Color.RED).setArcSize(10);
    ButtonStyle canBuy = new ButtonStyle().setFont(fontSize15).setBackground(DARK_GREEN).setArcSize(10);
    Button upgradeHull = new Button(new String[]{"Upgrade Hull","","Cost: "},110,95,150,50,canBuy);
    Button[] upgrades = {upgradeHull};
    int[] buyCosts = {100};

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
            if (upgrade.contains(x,y)) screenID = UPGRADE_SCREEN;
            else if (market.contains(x,y)) screenID = MARKET_SCREEN;
            else if (exit.contains(x,y)) close();
        } else {
            if (back.contains(x,y)) screenID = MENU_SCREEN;
            else if (screenID == MARKET_SCREEN){
                for (int i = 0; i < markets.length; i++){
                    if (markets[i].contains(x,y)){
                        markets[selected].style = sellStyle;
                        selected = i;
                        markets[i].style = selectedStyle;
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
        g.setFont(fontSize50);

        if (screenID == MENU_SCREEN){
            drawCentered(g,"Space Station",450,90);
            exit.draw(g);
            upgrade.draw(g);
            market.draw(g);
        } else {
            back.draw(g);
            if (screenID == UPGRADE_SCREEN){
                g.setColor(Color.GREEN);
                g.fillOval(450,300,10,10);
                for (int i = 0; i < upgrades.length; i++){
                    if (buyCosts[i] > money){
                        upgrades[i].style = cantAfford;
                    } else {
                        upgrades[i].style = canBuy;
                    }
                    upgrades[i].textLines[upgrades[i].textLines.length-1] = "Cost: "+buyCosts[i]+"";
                    upgrades[i].draw(g);
                }
            }
            else if (screenID == MARKET_SCREEN){
                g.setColor(Color.DARK_GRAY);
                for (Button b : markets){
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
        String[] textLines;
        ButtonStyle style = new ButtonStyle();
        
        public Button(String text, int x, int y, int width, int height, ButtonStyle style){
            super(x,y,width,height);
            textLines = new String[]{text};
            if (style != null) this.style = style;
        }
        public Button(String[] text, int x, int y, int width, int height, ButtonStyle style){
            super(x,y,width,height);
            textLines = text;
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

            FontMetrics fm = g.getFontMetrics();
            int l = textLines.length;
            //Draws the text centered within the button
            for (int i = 0; i < l; i++){
                Rectangle2D r = fm.getStringBounds(textLines[i], g);
                int x = this.x+(this.width - (int) r.getWidth()) / 2;
                int y = this.y+(this.height - (int) r.getHeight())*(i+1) / (l+1) + fm.getAscent();
                g.drawString(textLines[i], x, y);
            }
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
        public ButtonStyle setArcSize(int x){
            arcWidth = x;
            arcHeight = x;
            return this;
        }
        public ButtonStyle setArcSize(int width, int height){
            arcWidth = width;
            arcHeight = height;
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
