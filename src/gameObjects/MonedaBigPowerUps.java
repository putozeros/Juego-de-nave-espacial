package gameObjects;

import UI.Accion;
import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MonedaBigPowerUps extends PowerUps{
    private long duracion;
    private Accion accion;
    private Sonido monetizar;

    public MonedaBigPowerUps(Vector2D posicion, BufferedImage textura, Accion accion, GameState gameState) {
        super(posicion, textura, accion, gameState);
        this.accion = accion;
        duracion = 0;
        monetizar = new Sonido(Assets.monetizacion);
    }

    void hacerAccion(){
        monetizar.play();
        gameState.addMoney(10,this.getPosicion());
    }

    @Override
    public void actualizar(float dt) {
        duracion += dt;
        if(duracion > 10000){
            this.Destruir();
        }
        collidesWith();
    }

    @Override
    public void dibujar(Graphics graphics) {
        graphics.drawImage(Assets.coin10,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}
