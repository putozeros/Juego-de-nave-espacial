package gameObjects;

import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ufo extends MovingObject{

    private ArrayList<Vector2D> path;
    private Vector2D currentNode;
    private int index;
    private boolean following;

    private Crono fireRate;

    public Ufo(Vector2D posicion, Vector2D speed, double maxSpeed, BufferedImage texture,
               ArrayList<Vector2D> path, GameState gameState) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.path = path;
        index=0;
        following = true;
        fireRate = new Crono();
        fireRate.run(Constantes.UFO_FIRERATE);
    }

    private Vector2D pathFollowing(){

        currentNode = path.get(index);
        double distanceToNode = currentNode.substract(getCenter()).getMagnitud();

        if(distanceToNode < Constantes.NODE_RADIO){
            index++;
            if(index >= path.size()){
                following = false;
            }
        }
        return seekForce(currentNode);
    }

    private Vector2D seekForce(Vector2D target){
        Vector2D desiredVelocity = target.substract(getCenter());
        desiredVelocity = desiredVelocity.normalizar().escalar(maxSpeed);
        return desiredVelocity.substract(speed);
    }

    @Override
    public void actualizar(float dt) {
        Vector2D pathFollowing;
        if(following){
            pathFollowing = pathFollowing();
        }
        else{
            pathFollowing = new Vector2D();
        }

        pathFollowing = pathFollowing.escalar(0.01);
        speed = speed.add(pathFollowing);
        speed = speed.limite(maxSpeed);
        posicion = posicion.add(speed);

        if(posicion.getX() <-30 || posicion.getX() >Constantes.WIDTH+30 ||
                posicion.getY() < -30 || posicion.getY() > Constantes.HEIGHT+30){
            Destruir();
        }

        //disparos

        if(!fireRate.isRunning()){
            Vector2D toPlayer = gameState.getPlayer().getCenter().substract(getCenter());
            toPlayer = toPlayer.normalizar();

            double currentAngle = toPlayer.getAngle();
            currentAngle += Math.random()*Constantes.UFO_ANGLE_RANGE - Constantes.UFO_ANGLE_RANGE/2;

            if(toPlayer.getX() < 0){
                currentAngle = -currentAngle + Math.PI;
            }

            toPlayer = toPlayer.setDireccion(currentAngle);

            Laser laser = new Laser(
                    getCenter().add(toPlayer.escalar(ancho)),
                    toPlayer,
                    Constantes.LASER_SPEED/10,
                    currentAngle + Math.PI/2,
                    Assets.lrojo,
                    gameState
            );

            gameState.getMovingObjects().add(0,laser);
            Sonido sonido = new Sonido(Assets.disparoAlien);
            sonido.play();
            fireRate.run(Constantes.UFO_FIRERATE);
        }

        angle +=0.05;
        collidesWith();
        fireRate.actualizar();
    }

    @Override
    public void Destruir(){
        gameState.playExplosion(posicion);
        gameState.addpuntuacion(Constantes.UFO_SCORE, posicion);
        Sonido sonido = new Sonido(Assets.explosion);
        sonido.play();
        super.Destruir();
    }

    @Override
    public void dibujar(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        at = AffineTransform.getTranslateInstance(posicion.getX(),posicion.getY());
        at.rotate(angle,ancho/2,alto/2);
        g2d.drawImage(texture,at,null);
    }
}
