import game.Entity;

public class Player extends Entity {
    KeyInput k; //The key input handler

    public Player(KeyInput k){
        super("assets/spaceship.png",200,200,50,50);
        this.k = k;
    }

    public void update(){
        //This is temporary
        velocityX = 0;
        velocityY = 0;
        if (k.isKeyDown("W")) velocityY -= 2;
        if (k.isKeyDown("S")) velocityY += 2;
        if (k.isKeyDown("A")) velocityX -= 2;
        if (k.isKeyDown("D")) velocityX += 2;

        super.update(); //Use this to update the position and rotation
    }
}
