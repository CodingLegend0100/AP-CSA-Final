import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Menu {
    private boolean open = false;

    public void open(){ open = true; }
    public void close(){ open = false; }
    public boolean isOpen(){ return open; }

    public void draw(Graphics2D g){
        if (!open) return;

        g.setColor(Color.WHITE);
        g.fillRoundRect(50,50,800,500,10,10);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(60,60,780,480);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        g.drawString("Space Station",400,80);
    }
}
