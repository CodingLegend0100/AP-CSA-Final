package menu;

import game.Sprite;
import sprites.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class Menu {
    //Screen dimensions 900x600
    //I honestly dont know what the best way to do this is
    public static HashMap<String,Integer> resources = new HashMap<String,Integer>();
    public static int money = 0;

    private boolean open = false;
    private int screenID;

    final int MENU_SCREEN = 0;
    final int UPGRADE_SCREEN = -1;
    final int MARKET_SCREEN = 1;

    //Colors
    Color DARK_GREEN = new Color(0,150,0);
    Color DARK_RED = new Color(200,0,0);

    //Fonts
    Font fontSize50 = new Font(Font.DIALOG,Font.PLAIN,50);
    Font fontSize25 = new Font(Font.DIALOG,Font.PLAIN,25);
    Font fontSize15 = new Font(Font.DIALOG,Font.BOLD,15);

    Button upgradeBtn = new Button("Upgrades",150,250,250,100,
                                new ButtonStyle().setFont(fontSize50).setFontColor(Color.WHITE).setBackground(DARK_GREEN));

    Button marketBtn = new Button("Market",500,250,250,100,
                                new ButtonStyle().setFont(fontSize50).setFontColor(Color.WHITE).setBackground(DARK_RED));
    //Buy buttons
    ButtonStyle cantAfford = new ButtonStyle().setFont(fontSize15).setBackground(DARK_RED).setArcSize(10);
    ButtonStyle canBuy = new ButtonStyle().setFont(fontSize15).setBackground(DARK_GREEN).setArcSize(10);
    Button upgradeShield = new Button(new String[]{"Shields","","Cost: "},135,110,150,50,canBuy);
    Button upgradeEngines = new Button(new String[]{"Engines","","Cost: "},295,110,150,50,canBuy);
    Button upgradeLazer = new Button(new String[]{"Mining Lazer","","Cost: "},455,110,150,50,canBuy);
    Button upgradeCapacity = new Button(new String[]{"Hull Capacity","","Cost: "},615,110,150,50,canBuy);
    Button newShip = new Button(new String[]{"Buy New Ship","","Cost:"},250,450,400,50,canBuy);


    Button[] upgrades = {upgradeShield,upgradeEngines,upgradeLazer,upgradeCapacity,newShip};
    int[] buyCosts = {100,400,250,900,1000000};

    //Misc menu things
    Sprite exit = new Sprite(Sprite.loadImage("assets/x.png"),80,80,30,30);
    Sprite back = new Sprite(Sprite.loadImage("assets/back_arrow.png"),80,80,30,30);


    public void open(){
        open = true;
        screenID = MENU_SCREEN;
    }
    public void close(){ open = false; }
    public boolean isOpen(){ return open; }

    public int getInteraction(int x, int y){
        if (!open) return 0;

        if (screenID == MENU_SCREEN){
            if (upgradeBtn.contains(x,y)) screenID = UPGRADE_SCREEN;
            else if (marketBtn.contains(x,y)) screenID = MARKET_SCREEN;
            else if (exit.contains(x,y)) close();
        } else {
            if (back.contains(x,y)) screenID = MENU_SCREEN;
            else if (screenID == MARKET_SCREEN){
                MarketMenu.getInteraction(x,y);
            }
            else if (screenID == UPGRADE_SCREEN){
                if (upgradeShield.contains(x,y)) return Player.UPGRADE_SHIELD;
                else if (upgradeEngines.contains(x,y)) return Player.UPGRADE_SPEED;
                else if (newShip.contains(x,y)) return Player.UPGRADE_SHIP;
            }
        }
        return 0;
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
            upgradeBtn.draw(g);
            marketBtn.draw(g);
        } else {
            back.draw(g);
            if (screenID == UPGRADE_SCREEN){
                g.setFont(fontSize25);
                drawCentered(g,"Upgrades",450,80);
                for (int i = 0; i < upgrades.length; i++){
                    if (buyCosts[i] > money){
                        upgrades[i].style = cantAfford;
                    } else {
                        upgrades[i].style = canBuy;
                    }
                    upgrades[i].textLines[2] = "Cost: "+buyCosts[i]+"";
                    upgrades[i].draw(g);
                }
            }
            else if (screenID == MARKET_SCREEN){
                MarketMenu.draw(g);
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
}