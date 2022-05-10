import java.awt.Color;
import java.awt.Graphics2D;

public class Menu {
    private boolean open = false;

    public void open(){ open = true; }
    public void close(){ open = false; }
    public boolean isOpen(){ return open; }

    public void draw(Graphics2D g){
        if (!open) return;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(50,50,800,500,50,50);
    }
}
