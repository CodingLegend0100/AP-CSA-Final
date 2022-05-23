package menu;

import java.awt.Color;
import java.awt.Font;

public class ButtonStyle {
    Font font;
    Color fontColor = Color.BLACK;
    Color background = Color.BLACK;
    Color border = Color.BLACK;
    int borderTop = 0;
    int borderBottom = 0;
    int borderLeft = 0;
    int borderRight = 0;
    int arcWidth = 0;
    int arcHeight = 0;

    public ButtonStyle setFont(Font f){
        font = f;
        return this;
    }
    public ButtonStyle setFontColor(Color c){
        fontColor = c;
        return this;
    }
    public ButtonStyle setBackground(Color c){
        background = c;
        return this;
    }
    public ButtonStyle setBorderColor(Color c){
        border = c;
        return this;
    }
    public ButtonStyle setBorderWidth(int x){
        borderTop = x;
        borderBottom = x;
        borderLeft = x;
        borderRight = x;
        return this;
    }
    public ButtonStyle setBorderWidth(int top,int bottom,int left, int right){
        borderTop = top;
        borderBottom = bottom;
        borderLeft = left;
        borderRight = right;
        return this;
    }
    public ButtonStyle setArcSize(int x){
        arcWidth = x;
        arcHeight = x;
        return this;
    }
    public ButtonStyle setArcSize(int width, int height){
        arcWidth = width;
        arcHeight = height;
        return this;
    }
    public ButtonStyle setArcWidth(int x){
        arcWidth = x;
        return this;
    }
    public ButtonStyle setArcHeight(int x){
        arcHeight = x;
        return this;
    }
}