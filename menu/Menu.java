package menu;

import game.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class Menu {
    //Screen dimensions 900x600
    //I honestly dont know what the best way to do this is
    static HashMap<String,Integer> resources = new HashMap<String,Integer>();
    static int money = 0;

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
    Button upgradeShield = new Button(new String[]{"Shields ","","Cost: "},135,110,150,50,canBuy);
    Button upgradeEngines = new Button(new String[]{"Engines ","","Cost: "},295,110,150,50,canBuy);
    Button upgradeLazer = new Button(new String[]{"Mining Lazer ","","Cost: "},455,110,150,50,canBuy);
    Button upgradeCapacity = new Button(new String[]{"Hull Capacity ","","Cost: "},615,110,150,50,canBuy);
    Button newShip = new Button(new String[]{"Buy New Ship ","","Cost:"},250,450,400,50,canBuy);


    Button[] upgrades = {newShip,upgradeShield,upgradeEngines,upgradeLazer,upgradeCapacity};
    int[] buyCosts = {1_000_000,100,400,250,900};
    int[] levelIncrease = {750_000,50,0,0,0}; //Price will increase by this amount*level
    int[] priceIncreaseConst = {500_000,0,0,0,0}; //Price will always increase by this amount
    
    int[] upgradeLevels = new int[upgrades.length]; //Current level of the upgrade
    int[] maxLevels = {10,10,10,10,10}; //Max level of the upgrade

    //Misc menu things
    Sprite exit = new Sprite(Sprite.loadImage("assets/x.png"),80,80,30,30);
    Sprite back = new Sprite(Sprite.loadImage("assets/back_arrow.png"),80,80,30,30);


    public void open(){
        open = true;
        screenID = MENU_SCREEN;
    }
    public void close(){ open = false; }
    public boolean isOpen(){ return open; }

    public void addResources(HashMap<String,Integer> store){
        for (String k : store.keySet()){
            resources.put(k,store.get(k)+resources.getOrDefault(k, 0));
        }
    }

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
                for (int i = 0; i < upgrades.length; i++){
                    if (upgrades[i].contains(x,y)){
                        if (money >= buyCosts[i]){
                            money -= buyCosts[i]; //Purchase the upgrade

                            //Increase the cost of the next upgrade
                            buyCosts[i] += priceIncreaseConst[i];
                            buyCosts[i] += upgradeLevels[i]*levelIncrease[i];

                            //Increase the level
                            upgradeLevels[i] += 1;

                            return i;
                        }
                    }
                }
            }
        }
        return -1;
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

                    //Displays the level on the buy button
                    upgrades[i].textLines[0] = upgrades[i].textLines[0].substring(0,upgrades[i].textLines[0].lastIndexOf(" ")+1)+(upgradeLevels[i]+1);
                    
                    //Update the cost displayed on the button
                    upgrades[i].textLines[2] = "Cost: "+buyCosts[i]+"";

                    upgrades[i].draw(g);
                }
            }
            else if (screenID == MARKET_SCREEN){
                MarketMenu.draw(g);
            }
        }
    }

    public static void drawCentered(Graphics2D g,String text, int x, int y){
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int X = x-((int) r.getWidth()) / 2;
        int Y = y-((int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, X, Y);
    }
}