package UI;

import graficos.Assets;
import graficos.Text;
import input.MouseInput;
import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Buttones {

    private BufferedImage mouseOut,mouseIn;
    private boolean mouseDentro;
    private Rectangle boundingBox;
    private String texto;
    private Accion accion;

    public Buttones(BufferedImage mouseOut, BufferedImage mouseIn, int x, int y,
                    String texto, Accion accion) {
        this.mouseOut = mouseOut;
        this.mouseIn = mouseIn;
        this.texto = texto;
        boundingBox = new Rectangle(x,y,mouseIn.getWidth(),mouseIn.getHeight());
        this.accion = accion;
    }

    public void actualizar(){
        if(boundingBox.contains(MouseInput.x,MouseInput.y)){
            mouseDentro = true;
        } else {
            mouseDentro = false;
        }

        if(mouseDentro && MouseInput.MLB){
            accion.doAction();
        }
    }

    public void dibujar(Graphics graphics){

        if(mouseDentro){
            graphics.drawImage(mouseIn,boundingBox.x,boundingBox.y,null);
        } else {
            graphics.drawImage(mouseOut,boundingBox.x,boundingBox.y,null);
        }
        Text.drawText(graphics,texto,
                new Vector2D(
                        boundingBox.getX()+ boundingBox.getWidth()/2,
                        boundingBox.getY()+ boundingBox.getHeight()),
                true,Color.black, Assets.fuentepeque);
    }
}
