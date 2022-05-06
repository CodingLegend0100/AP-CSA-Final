import game.Entity;

public class Player extends Entity {
    KeyInput k; //The key input handler

    public Player(KeyInput k){
        super("assets/spaceship.png",200,200,0.1);
        this.k = k;
    }

    public void update(){
        super.update();
        //This is just for testing
        rotationSpeed = 1;
        velocityX = 0;
        velocityY = 0;
        if (k.isKeyDown("W")) velocityY -= 2;
        if (k.isKeyDown("S")) velocityY += 2;
        if (k.isKeyDown("A")) velocityX -= 2;
        if (k.isKeyDown("D")) velocityX += 2;

    }
}
