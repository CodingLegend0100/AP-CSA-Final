import game.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class GamePanel extends game.GamePanel {
    private int points = 0;
    private static final int width = 900, height = 600;
    private static final int FPS = 60;

    private Image[] enemyImages = new Image[4];

    private ArrayList<Sprite> asteroids = new ArrayList<Sprite>();
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();  
    
    Player player = new Player(keyListener);
    SpaceStation station = new SpaceStation();
    Menu shop = new Menu();


    public GamePanel(){
        super(width,height,FPS);
        
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
        boolean success = ((int)(Math.random()*60+1)) == 1 && asteroids.size() <= 49;
        
        for(int i=0;i<asteroids.size();i++){
            Sprite a = asteroids.get(i);
            
            a.update();
            
            //Delete 'old' asteroids
            double distance = Math.pow(a.getX()-player.getX(),2)+Math.pow(a.getY()-player.getY(),2);
            if (distance>=(width*3)*(width*3)){
                asteroids.remove(i);
                i--;
                continue;
            }

            //New asteroid cannot be created because it collides with an existing one
            if (create.isColliding(a)){
                success = false; 
            }

            //Player collides with the asteroid
            if (player.isColliding(a)){
                if (player.isMining()){
                    //Player mines the asteroid
                    asteroids.remove(i);
                    i--;
                    points++;
                } else {
                    //Player bounces off asteroid
                    player.bounce();
                }
            }
        }
        //Add asteroid to created if it can sucessfully be created
        if (success) asteroids.add(create);
        
    }

    /** Update positions of objects on the screen */
    public void update(){
        
        if (shop.isOpen()) return;

        manageAsteroids();
        player.update();
        station.update();

        if (player.isColliding(station)){
            player.bounce();
            shop.open();
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
        }


    }

    public void keyReleased(String key){

    }

    public void mousePressed(int x, int y){
        System.out.println(x+","+y);
    }


    /** Draw objects to the screen */
    public void draw(Graphics2D g2){
        //long drawStart = System.nanoTime();

        shop.draw(g2);

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
        g2.fillOval((int)(player.getX()+50*Math.cos(rad)),(int)(player.getY()+50*Math.sin(rad)),10,10);

        g2.translate((int)player.getX()-width/2,(int)player.getY()-height/2); //Translate origin back

        //long drawTime = System.nanoTime() - drawStart;
        //g2.drawString("Draw Time: "+drawTime,10,10);
        g2.drawString("Points: "+points,10,10);
        shop.draw(g2); //Draw shop menu

        g2.dispose(); //Get rid of the graphics when we are done
    }
}