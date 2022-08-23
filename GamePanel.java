import game.Sprite;
import menu.Menu;
import sprites.Asteroid;
import sprites.Enemy;
import sprites.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class GamePanel extends game.GamePanel {
    private static final int width = 900, height = 600;
    private static final int FPS = 60;

    int tutorial = 0;
    int tutorialFrames = -1;
    String[] tutorialMsg = {
        "Welcome trainee! To begin piloting, press W to move forward",
        "Use the A and D keys to turn",
        "Now hold S to go backwards",
        "Great! Now you've mastered the basics of piloting.",
        "Now its time to mine",
        "Run into the nearest asteroid",
        "Now hold space to activate the mining lazer",
        "Hit an asteroid with the lazer to mine it",
        "Perfect! Follow the white dot back to the station",
        "This is the station, here you can sell materials to upgrade your ship"
    };


    private Image[] enemyImages = new Image[4];

    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();  
    
    Player player = new Player(keyListener);
    Sprite station = new Sprite("assets/station.png",0,0,200,200);


    public GamePanel(){
        super(width,height,FPS);

        station.setRotationSpeed(0.001);
        
        //Load images
        for (int i = 1; i <= 4; i++){
            enemyImages[i-1] = Sprite.loadImage("assets/pirate"+i+".png");
        }

        enemies.add(new Enemy(enemyImages[0],-75,150,player));
        enemies.add(new Enemy(enemyImages[1],-25,150,player));
        enemies.add(new Enemy(enemyImages[2],25,150,player));
        enemies.add(new Enemy(enemyImages[3],75,150,player));

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
            if (distance>=(width*3)*(width*3) || a.destroyed()){
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
                    player.mine(a);
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
        
        if (Menu.isOpen()) return;

        manageAsteroids();
        player.update();
        station.update();

        if (player.checkCollision(station)){
            player.setPos(150,0);
            player.setRotation(0);
            player.setVelocity(0,0);
            Menu.open();
            Menu.addResources(player.clearInventory());
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
            Menu.close();
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
        player.upgrade(Menu.getInteraction(x, y));
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
        Menu.draw(g2); //Draw shop menu

        g2.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        g2.setColor(Color.WHITE);
        if (tutorial < tutorialMsg.length) g2.drawString(tutorialMsg[tutorial],10,25);

        g2.dispose(); //Get rid of the graphics when we are done
    }
}