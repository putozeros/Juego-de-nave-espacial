package gameObjects;

import UI.Accion;
import graficos.Text;
import math.Vector2D;
import states.GameState;

import java.awt.*;

public class Mensaje {
    private GameState gameState;
    private float alpha;
    private String texto;
    private Vector2D posicion;
    private Color color;
    private boolean centro;
    private boolean desaparecer;
    private Font font;
    private final float deltaAlfa = 0.01f;
    private boolean dead;

    public Mensaje(Vector2D posicion, boolean desaparecer, String texto, Color color, boolean centro, Font font, GameState gameState) {
        this.gameState = gameState;
        this.texto = texto;
        this.posicion = posicion;
        this.color = color;
        this.centro = centro;
        this.desaparecer = desaparecer;
        this.font = font;
        this.dead = false;

        if(desaparecer){
            alpha =1;
        }else{
            alpha =0;
        }
    }

    public Mensaje(Vector2D posicion, boolean desaparecer, String texto, Color green, boolean centro, Font fuentepeque, Accion accion) {
    }

    public void dibujar(Graphics2D g2d){

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));

        Text.drawText(g2d,texto,posicion,centro,color,font);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));

        posicion.setY(posicion.getY()-1);

        if(desaparecer){
            alpha -= deltaAlfa;
        }else{
            alpha += deltaAlfa;
        }

        if(desaparecer && alpha < 0){
            dead = true;
        }

        if(!desaparecer && alpha >1){
            desaparecer = true;
            alpha = 1;
        }
    }
    public boolean isDead(){
        return dead;
    }
}
