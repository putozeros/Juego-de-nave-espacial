package UI;

import graficos.Assets;
import graficos.Text;
import input.MouseInput;
import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Buttones {

    private BufferedImage mouseOut,mouseIn;
    private boolean mouseDentro;
    private Rectangle boundingBox;
    private String texto;
    private Accion accion;
    private int identificador;

    public Buttones(BufferedImage mouseOut, BufferedImage mouseIn, int x, int y,
                    String texto, Accion accion, int identificador) {
        this.mouseOut = mouseOut;
        this.mouseIn = mouseIn;
        this.texto = texto;
        boundingBox = new Rectangle(x,y,mouseIn.getWidth(),mouseIn.getHeight());
        this.accion = accion;
        this.identificador = identificador;
    }

    public void actualizar(){
        if(boundingBox.contains(MouseInput.x,MouseInput.y)){
            mouseDentro = true;
        } else {
            mouseDentro = false;
        }

        if(mouseDentro && MouseInput.MLB){
            try {
                accion.doAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public int getIdentificador() {
        return identificador;
    }
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
}
