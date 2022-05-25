import game.Sprite;
import menu.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
//import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

public class GamePanel extends game.GamePanel {
    private static final int width = 900, height = 600;
    private static final int FPS = 60;

    int tutorial = 0;
    int tutorialFrames = -1;

    //private Image[] enemyImages = new Image[4];

    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();  
    
    Player player = new Player(keyListener);
    SpaceStation station = new SpaceStation();
    Menu shop = new Menu();


    public GamePanel(){
        super(width,height,FPS);
        
        //Load images
        //for (int i = 1; i <= 4; i++){
        //    enemyImages[i-1] = Sprite.loadImage("assets/pirate"+i+".png");
        //}

        //enemies.add(new Enemy(enemyImages[0],-75,150,player));
        //enemies.add(new Enemy(enemyImages[1],-25,150,player));
        //enemies.add(new Enemy(enemyImages[2],25,150,player));
        //enemies.add(new Enemy(enemyImages[3],75,150,player));

        start();
    }

    public void createEnemy(){
        
    }

    //Create asteroids off the edge of the screen, if its touching anything it doesnt actually make one.
    public void manageAsteroids(){

        //Calculate where to place new asteroid
        double angle = Math.toRadians(player.getRotation()-90+(Math.random()*60-30));
        double X = player.getX()+Math.cos(angle)*(height); //X coordinate of new asteroid
        double Y = player.getY()+Math.sin(angle)*(height); //Y coordinate of new asteroid
        Sprite create = new Asteroid(X,Y); //The asteroid being created

        //The asteroid will be created
                        //  Random chance to create    Limit total asteroid count   Prevent generation near station
        boolean success = ((int)(Math.random()*60+1)) == 1 && asteroids.size() <= 49 && X*X+Y*Y >= 250000;
        
        for(int i=0;i<asteroids.size();i++){
            Asteroid a = (Asteroid) asteroids.get(i);
            
            a.update();
            
            //Delete 'old' asteroids
            double distance = Math.pow(a.getX()-player.getX(),2)+Math.pow(a.getY()-player.getY(),2);
            if (distance>=(width*3)*(width*3)){
                asteroids.remove(i);
                i--;
                continue;
            }

            //New asteroid cannot be created because it collides with an existing one
            if (create.checkCollision(a)){
                success = false; 
            }

            //Player collides with the asteroid
            if (player.checkCollision(a)){
                if (player.isMining()){
                    //Player mines the asteroid
                    HashMap<String,Integer> resources = a.getResources();
                    for (String k : resources.keySet()){
                        Menu.resources.put(k,Menu.resources.getOrDefault(k, 0)+resources.get(k));
                    }
                    asteroids.remove(i);
                    i--;
                    if (tutorial == 7) tutorial++;
                } else {
                    //Player bounces off asteroid
                    player.bounce();
                    if (tutorial == 5) tutorial++;
                }
            }
        }
        //Add asteroid to created if it can sucessfully be created
        if (success) asteroids.add(create);
        
    }

    /** Update positions of objects on the screen */
    public void update(){
        if (tutorialFrames > -1) tutorialFrames--;
        if (tutorialFrames == 0){
            tutorial++;
            if (tutorial == 4) tutorialFrames = 120;
        }
        
        if (shop.isOpen()) return;

        manageAsteroids();
        player.update();
        station.update();

        if (player.checkCollision(station)){
            player.setPos(150,0);
            player.setRotation(0);
            player.setVelocity(0,0);
            shop.open();
            if (tutorial == 8) tutorial++;
        }

        for (Sprite e : enemies){
            e.update();
        }

        //System.out.println(asteroids.size());
    }

    public void keyPressed(String key){
        //System.out.println(key);
        if (key.equals("Escape")){
            shop.close();
            if (tutorial == 9) tutorial++;
        }

        else if (key.equals("W") && tutorial == 0) tutorial++;
        else if ((key.equals("A") || key.equals("D")) && tutorial == 1) tutorial++;
        else if (key.equals("S") && tutorial == 2){
            tutorial++;
            tutorialFrames = 180;
        }
        else if (key.equals("Space") && tutorial == 6) tutorial++;


    }

    public void keyReleased(String key){

    }

    public void mousePressed(int x, int y){
        player.upgrade(shop.getInteraction(x, y));
    }


    /** Draw objects to the screen */
    public void draw(Graphics2D g2){
        //long drawStart = System.nanoTime();

        g2.translate(-(int)player.getX()+width/2,-(int)player.getY()+height/2); //Keep player in the center of the window
        player.draw(g2);
        
        for (Sprite a: asteroids){
            a.draw(g2);
        }

        for (Sprite e: enemies){
            e.draw(g2);
        }

        station.draw(g2);

        //Draw indicator pointing toward enemies
        g2.setColor(Color.RED);
        for (Sprite e : enemies){
            double rad = Math.atan2(e.getY()-player.getY(),e.getX()-player.getX());
            g2.fillOval((int)(player.getX()+50*Math.cos(rad)),(int)(player.getY()+50*Math.sin(rad)),10,10);
        }

        //Draw indicator pointing to space station
        g2.setColor(Color.WHITE);
        double rad = Math.atan2(-player.getY(),-player.getX());
        g2.fillOval((int)(player.getX()+55*Math.cos(rad)),(int)(player.getY()+55*Math.sin(rad)),10,10);

        g2.translate((int)player.getX()-width/2,(int)player.getY()-height/2); //Translate origin back

        //long drawTime = System.nanoTime() - drawStart;
        //g2.drawString("Draw Time: "+drawTime,10,10);
        shop.draw(g2); //Draw shop menu

        g2.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        if (tutorial == 0) g2.drawString("Welcome trainee! To begin piloting, press W to move forward",10,25);
        if (tutorial == 1) g2.drawString("Use the A and D keys to turn",10,25);
        if (tutorial == 2) g2.drawString("Now hold S to go backwards",10,25);
        if (tutorial == 3) g2.drawString("Great! Now you've mastered the basics of piloting.",10,25);
        if (tutorial == 4) g2.drawString("Now its time to mine",10,25);
        if (tutorial == 5) g2.drawString("Run into the nearest asteroid",10,25);
        if (tutorial == 6) g2.drawString("Now hold space to activate the mining lazer",10,25);
        if (tutorial == 7) g2.drawString("Hit an asteroid with the lazer to mine it",10,25);
        if (tutorial == 8) g2.drawString("Perfect! Follow the white dot back to the station",10,25);
        if (tutorial == 9) g2.drawString("This is the station, here you can sell materials to upgrade your ship",10,25);


        g2.dispose(); //Get rid of the graphics when we are done
    }
}