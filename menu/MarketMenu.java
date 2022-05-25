package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class MarketMenu {

    static Color DARK_GREEN = new Color(0,150,0);

    static Font fontSize15 = new Font(Font.DIALOG,Font.PLAIN,15);
    static Font fontSize25 = new Font(Font.DIALOG,Font.PLAIN,25);

    static ButtonStyle sellStyle = new ButtonStyle().setFont(fontSize25).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcSize(10).setBorderWidth(0, 2, 0, 2);
    static ButtonStyle selectedStyle = new ButtonStyle().setFont(fontSize25).setFontColor(Color.WHITE).setBackground(Color.GRAY).setArcSize(10).setBorderColor(Color.WHITE).setBorderWidth(0, 2, 0, 2);;
    static Button sellHydrogen = new Button("Hydrogen",70,110,150,50,selectedStyle);
    static Button sellLithium = new Button("Lithium",70,170,150,50,sellStyle);
    static Button sellIron = new Button("Iron",70,230,150,50,sellStyle);
    static Button sellGold = new Button("Gold",70,290,150,50,sellStyle);
    static Button sellOsmium = new Button("Osmium",70,350,150,50,sellStyle);
    static Button sellPlatinum = new Button("Platinum",70,410,150,50,sellStyle);
    static Button sell2 = new Button("Sell",70,470,150,50,sellStyle);
    static Button[] markets = {sellHydrogen,sellLithium,sellIron,sellGold,sellOsmium,sellPlatinum,sell2};
    static int[] sellValues = {01          ,07         ,03      , 5      ,10        ,06          ,0};
    static Button sellButton = new Button("Sell - ",480,490,100,30,new ButtonStyle().setFont(fontSize15).setFontColor(Color.WHITE).setArcSize(10).setBackground(DARK_GREEN));
    static int selected = 0;
    static int sellAmount = 1;

    static ButtonStyle style3 = new ButtonStyle().setFont(fontSize15).setFontColor(Color.WHITE);
    static Button increase100 = new Button("+100",670,450,40,30,style3);
    static Button increase10 = new Button("+10",620,450,40,30,style3);
    static Button increase1 = new Button("+1",570,450,40,30,style3);
    static Button amountLabel = new Button("",490,450,80,30,new ButtonStyle().setBackground(Color.LIGHT_GRAY).setFontColor(Color.BLACK));
    static Button decrease1 = new Button("-1",450,450,40,30,style3);
    static Button decrease10 = new Button("-10",400,450,40,30,style3);
    static Button decrease100 = new Button("-100",350,450,40,30,style3);

    static Button[] sellButtons = {decrease100,decrease10,decrease1,increase1,increase10,increase100};

    public static void getInteraction(int x,int y){
        for (int i = 0; i < markets.length; i++){
            if (markets[i].contains(x,y)){
                markets[selected].style = sellStyle;
                selected = i;
                markets[i].style = selectedStyle;
            }
        }
        if (increase1.contains(x,y))        sellAmount += 1;
        else if (increase10.contains(x,y))  sellAmount += 10;
        else if (increase100.contains(x,y)) sellAmount += 100;
        else if (decrease1.contains(x,y))   sellAmount -= 1;
        else if (decrease10.contains(x,y))  sellAmount -= 10;
        else if (decrease100.contains(x,y)) sellAmount -= 100;
        if (sellAmount < 0) sellAmount = 0;
        if (sellAmount > Menu.resources.getOrDefault(markets[selected].textLines[0], 0))
            sellAmount = Menu.resources.getOrDefault(markets[selected].textLines[0], 0);

        if (sellButton.contains(x,y)){
            Menu.money += sellAmount*sellValues[selected];
            Menu.resources.put(markets[selected].textLines[0],Menu.resources.getOrDefault(markets[selected].textLines[0], 0)-sellAmount);
            sellAmount = 0;
        }
    }

    public static void draw(Graphics2D g){
        amountLabel.textLines[0] = ""+sellAmount;
        sellButton.textLines[0] = "Sell - "+(sellAmount*sellValues[selected]);
        for (Button b : markets){
            b.draw(g);
        }
        for (Button b : sellButtons){
            b.draw(g);
        }
        sellButton.draw(g);
        amountLabel.draw(g);


        g.setColor(Color.BLACK);
        g.drawRect(260,110,540,320);
        g.setFont(fontSize25);
        drawCentered(g,markets[selected].textLines[0],530,90);
        g.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        drawLeft(g,"$"+Menu.money,260,95);
        drawRight(g,"You have: "+Menu.resources.getOrDefault(markets[selected].textLines[0], 0),800,95);

    }

    private static void drawCentered(Graphics2D g,String text, int x, int y){
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int X = x-((int) r.getWidth()) / 2;
        int Y = y-((int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, X, Y);
    }

    private static void drawRight(Graphics2D g,String text, int x, int y){
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int X = x-((int) r.getWidth());
        int Y = y-((int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, X, Y);
    }

    private static void drawLeft(Graphics2D g,String text, int x, int y){
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int Y = y-((int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, Y);
    }
}
