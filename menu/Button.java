package menu;

import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Button extends Rectangle {
    public String[] textLines;
    public ButtonStyle style = new ButtonStyle();
    
    public Button(String text, int x, int y, int width, int height, ButtonStyle style){
        super(x,y,width,height);
        textLines = new String[]{text};
        if (style != null) this.style = style;
    }
    public Button(String[] text, int x, int y, int width, int height, ButtonStyle style){
        super(x,y,width,height);
        textLines = text;
        if (style != null) this.style = style;
    }

    public void draw(Graphics2D g){

        if (style.border != null) g.setColor(style.border);
        g.fillRoundRect(x,y,width,height,style.arcWidth,style.arcHeight);


        if (style.background != null) g.setColor(style.background);
        g.fillRoundRect(x+style.borderLeft,y+style.borderTop,
                        width-style.borderLeft-style.borderRight,height-style.borderTop-style.borderBottom,
                        style.arcWidth,style.arcHeight);

        if (style.font != null) g.setFont(style.font);
        if (style.fontColor != null) g.setColor(style.fontColor);

        FontMetrics fm = g.getFontMetrics();
        int l = textLines.length;
        //Draws the text centered within the button
        for (int i = 0; i < l; i++){
            Rectangle2D r = fm.getStringBounds(textLines[i], g);
            int x = this.x+(this.width - (int) r.getWidth()) / 2;
            int y = this.y+(this.height - (int) r.getHeight())*(i+1) / (l+1) + fm.getAscent();
            g.drawString(textLines[i], x, y);
        }
    }
}