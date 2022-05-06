import game.Sprite;

public class Player extends Sprite {
    KeyInput k; //The key input handler

    public Player(KeyInput k){
        super("assets/spaceship.png",200,200,0.1);
        this.k = k;
    }

    public void update(){
        //This is just for testing
        if (k.isKeyDown("W")) y -= 2;
        if (k.isKeyDown("S")) y += 2;
        if (k.isKeyDown("A")) x -= 2;
        if (k.isKeyDown("D")) x += 2;

    }
}
