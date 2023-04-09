package gameObjects;

import UI.Accion;
import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;
import states.GameState;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PowerUps extends MovingObject{
    private long duration;
    private Accion accion;
    private Sonido recoger;
    private BufferedImage tipoTextura;

    public PowerUps(Vector2D posicion, BufferedImage textura, Accion accion, GameState gameState) {
        super(posicion, new Vector2D(),0, Assets.orbe, gameState);

        this.accion = accion;
        this.tipoTextura = textura;
        duration = 0;
        recoger = new Sonido(Assets.powerUpSound);
    }

    void ejecutarAccion(){
        try {
            accion.doAction();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recoger.play();
    }

    @Override
    public void actualizar(float dt) {
        angle += 0.05;
        duration += dt;
        if(duration > 10000){
            this.Destruir();
        }
        collidesWith();
    }

    @Override
    public void dibujar(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        at = AffineTransform.getTranslateInstance(
                posicion.getX() + Assets.orbe.getWidth()/2 - tipoTextura.getWidth()/2,
                posicion.getY() + Assets.orbe.getHeight()/2 - tipoTextura.getHeight()/2);

        at.rotate(angle,
                tipoTextura.getWidth()/2,
                tipoTextura.getHeight()/2);

        graphics.drawImage(Assets.orbe, (int)posicion.getX(),(int)posicion.getY(),null);
        g2d.drawImage(tipoTextura,at,null);
    }
}
