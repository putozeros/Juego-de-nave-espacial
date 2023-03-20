package graficos;

import math.Vector2D;

import java.awt.*;

public class Text {
    public static void drawText(Graphics g, String text, Vector2D posi, boolean centro, Color color, Font font){
        g.setColor(color);
        g.setFont(font);
        Vector2D posicion = new Vector2D(posi.getX(),posi.getY());

        if(centro){
            FontMetrics fm = g.getFontMetrics();
            posicion.setX(posicion.getX() - fm.stringWidth(text)/2);
            posicion.setY(posicion.getY() - fm.getHeight()/2);
        }
        g.drawString(text,(int)posicion.getX(),(int)posicion.getY());
    }
}
