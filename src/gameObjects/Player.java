package gameObjects;

import graficos.Animacion;
import graficos.Assets;
import graficos.Sonido;
import input.Keyboard;
import math.Vector2D;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MovingObject{

    private long shieldTime;
    private Vector2D heading, aceleracion;
    private Crono fireRate, spawntime,flickering;
    private boolean spawning=true, visible, shieldOn, shotAllowed, accelerating=false;
    private int contador = 0,vitalidad;
    private Timer temporizador;
    private Animacion shieldEffect;

    public Player(Vector2D posicion, Vector2D speed,double maxSpeed, BufferedImage texture, GameState gameState,int vitalidad) {
        super(posicion, speed, maxSpeed, texture, gameState);
        heading = new Vector2D(0,1);
        this.gameState = gameState;
        this.vitalidad = vitalidad;
        aceleracion = new Vector2D();
        fireRate = new Crono();
        spawntime = new Crono();
        flickering = new Crono();
        temporizador = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disminuirContador();
            }
        });
        temporizador.start();
        shieldEffect = new Animacion(Assets.escudo,60,null);
        shotAllowed = true;
    }

    @Override
    public void actualizar(float dt) {

        if(shieldOn){
            shieldTime += dt;
        }
        if(shieldTime > 12000){
            shieldTime = 0;
            shieldOn = false;
        }
        if(!spawntime.isRunning()){
            spawning=false;
            visible = true;
        }
        if(spawning){
            if(!flickering.isRunning()){
                flickering.run(Constantes.Flicker);
                visible = !visible;
            }
        }

        if(shotAllowed){
            if(Keyboard.DISPARO && !fireRate.isRunning() && !spawning){
                gameState.getMovingObjects().add(new Laser(getCenter().add(heading.escalar(ancho-30)), heading,
                        Constantes.LASER_SPEED, angle, Assets.lazul, gameState,5));
                fireRate.run(Constantes.FIRERATE);
                Sonido sonido = new Sonido(Assets.disparoJugador);
                sonido.cambiarVolumen(-13);
                sonido.play();
                actualizarContador();

                if(contador >=10){
                    Sonido overheat = new Sonido(Assets.overheat);
                    overheat.play();
                    shotAllowed=false;
                }
            }
        }
        if (contador ==0){
            shotAllowed=true;
        }
        if(Keyboard.DERECHA){
            angle += Constantes.DELTAANGLE;
        }
        if(Keyboard.IZQUIERDA){
            angle -= Constantes.DELTAANGLE;
        }
        if(Keyboard.ARRIBA){
            aceleracion = heading.escalar(Constantes.ACC);
            accelerating = true;
        } else{
            if(speed.getMagnitud() != 0){
                aceleracion = (speed.escalar(-1).normalizar()).escalar(Constantes.ACC/2);
            }
            accelerating = false;
        }

        speed = speed.add(aceleracion);

        speed = speed.limite(maxSpeed);

        heading = heading.setDireccion(angle-Math.PI/2);

        posicion = posicion.add(speed);

        if(posicion.getX() > Constantes.WIDTH){
            posicion.setX(-70);
        }
        if(posicion.getY() >Constantes.HEIGHT){
            posicion.setY(-60);
        }
        if (posicion.getX()<-70){
            posicion.setX(Constantes.WIDTH);
        }
        if (posicion.getY()<-60){
            posicion.setY(Constantes.HEIGHT);
        }
        if(shieldOn){
            shieldEffect.actualizar(dt);
        }
        fireRate.actualizar();
        spawntime.actualizar();
        flickering.actualizar();
        collidesWith();

    }
    @Override
    protected void collidesWith(){
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for(int i=0;i<movingObjects.size();i++){
            MovingObject m = movingObjects.get(i);
            if(m instanceof Asteroide && colisionaCon(m)){
                damage(5);
                break;
            }
        }
    }
    public boolean colisionaCon(MovingObject m){
        double distancia = m.getCenter().substract(getCenter()).getMagnitud();
        return distancia < m.ancho/2 + ancho/2;
    }
    public void damage(int danio){
        vitalidad -= danio;
        Sonido hit = new Sonido(Assets.playerHit);
        hit.play();
        if(vitalidad<=0){
            Destruir();
        }
    }
    public void setEscudo(){
        if(shieldOn){
            shieldTime = 0;
        }
        shieldOn=true;
    }

    private void actualizarContador(){
        if(contador < 10){
            contador++;
        }
    }
    private void disminuirContador(){
        if(contador > 0){
            contador --;
        }
    }

    @Override
    public void Destruir(){
        spawning = true;
        spawntime.run(Constantes.Spawning);
        Sonido sonido = new Sonido(Assets.lose);
        sonido.play();
        if(!gameState.substractLife()){
            gameState.gameOver();
            super.Destruir();
        }
        resetValues();

    }

    private void resetValues(){
        angle = 0;
        speed = new Vector2D();
        posicion = new Vector2D(Constantes.WIDTH/2 - Assets.jugador.getWidth()/2,
                Constantes.HEIGHT/2 - Assets.jugador.getHeight()/2);
    }
    @Override
    public void dibujar(Graphics graphics) {
        if(!visible){
            return;
        }
        Graphics2D g2d = (Graphics2D) graphics;

        AffineTransform at1 = AffineTransform.getTranslateInstance(posicion.getX() + ancho/2 + 32,
                posicion.getY() + alto/2 + 20);
        AffineTransform at2 = AffineTransform.getTranslateInstance(posicion.getX()+2, posicion.getY() + alto/2+20);

        at1.rotate(angle,-32,-20);
        at2.rotate(angle,ancho/2 -2,-20);


        if(accelerating){
            g2d.drawImage(Assets.velocidad,at1,null);
            g2d.drawImage(Assets.velocidad,at2,null);
        }
        if(shieldOn){
            BufferedImage currentFrame = shieldEffect.getCurrentFrame();
            AffineTransform at3 = AffineTransform.getTranslateInstance(
                    posicion.getX() - currentFrame.getWidth()/2 + ancho/2,
                    posicion.getY() - currentFrame.getHeight()/2 + alto/2
            );
            at3.rotate(angle,currentFrame.getWidth()/2,currentFrame.getHeight()/2);
            g2d.drawImage(shieldEffect.getCurrentFrame(),at3,null);
        }

        at = AffineTransform.getTranslateInstance(posicion.getX(),posicion.getY());

        at.rotate(angle, ancho/2, alto/2);

        g2d.drawImage(texture, at, null);
        BufferedImage barra = Assets.getBarraLaser()[contador];
        graphics.drawImage(barra,1150,550,null);

        BufferedImage barraVida= Assets.getBarraVida()[vitalidad/24];
        graphics.drawImage(barraVida,10,10,null);

    }

    public boolean isSpawning(){
        return spawning;
    }
    public boolean isShieldOn(){
        return shieldOn;
    }

    public int getVitalidad() {
        return vitalidad;
    }

    public void setVitalidad(int vitalidad) {
        this.vitalidad = vitalidad;
    }
}
