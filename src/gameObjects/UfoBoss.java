package gameObjects;

import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;
import states.EstadoGanador;
import states.GameState;
import states.State;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UfoBoss extends MovingObject{
    private ArrayList<Vector2D> path;
    private Vector2D currentNode;
    private int index;
    private boolean following;
    private Crono fireRate;
    private int vitalidad;
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

    public UfoBoss(Vector2D posicion, Vector2D speed, double maxSpeed, BufferedImage texture,
                  ArrayList<Vector2D> path, GameState gameState, int vitalidad) {
        super(posicion, speed, maxSpeed, texture, gameState);
        this.path = path;
        index=0;
        following = true;
        fireRate = new Crono();
        fireRate.run(Constantes.UFOBIG_FIRERATE);
        this.vitalidad = vitalidad;
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
    @Override
    protected void collidesWith(){
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for(int i=0;i<movingObjects.size();i++){
            MovingObject m = movingObjects.get(i);
            if(m instanceof Player && colisionaCon(m)){
                m.damage(100);
                break;
            }
        }
    }
    public boolean colisionaCon(MovingObject m){
        double distancia = m.getCenter().substract(getCenter()).getMagnitud();
        return distancia < m.ancho/2 + ancho/2;
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

            /*double currentAngle = Math.atan2(toPlayer.getY(), toPlayer.getX());
             * Con esta función activa, el disparo SIEMPRE iría a donde esté el jugador situado. Para que funcione, simplemente
             * se debe eliminar la lógica anterior.
             * */

            LaserBoss laserBoss = new LaserBoss(
                    getCenter().add(toPlayer.escalar(ancho)),
                    toPlayer,
                    Constantes.LASER_SPEED/10,
                    currentAngle + Math.PI/2,
                    Assets.lverde,
                    gameState,400
            );

            gameState.getMovingObjects().add(0,laserBoss);
            Sonido sonido = new Sonido(Assets.disparoAlien);
            sonido.play();
            fireRate.run(400);
        }
        angle +=0.02;
        collidesWith();
        fireRate.actualizar();
    }

    public void damage(int danio){
        mensajes.add(new Mensaje(posicion,true,""+danio, Color.yellow,false,Assets.fuentepeque));
        Sonido hit = new Sonido(Assets.hit);
        hit.play();
        vitalidad -= danio;
        if(vitalidad <=0){
            Destruir();
        }
    }

    @Override
    public void Destruir(){
        gameState.playExplosion(new Vector2D(this.getCenter()));
        gameState.addpuntuacion(5000, posicion);
        Sonido sonido = new Sonido(Assets.explosion);
        sonido.play();
        gameState.musica.stop();
        State.cambiarEstado(new EstadoGanador());
        super.Destruir();
    }

    @Override
    public void dibujar(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        at = AffineTransform.getTranslateInstance(posicion.getX(),posicion.getY());

        for(int i = 0;i < mensajes.size();i++){
            mensajes.get(i).dibujar(g2d);
            if(mensajes.get(i).isDead()){
                mensajes.remove(i);
            }
        }
        at.rotate(angle,ancho/2,alto/2);
        g2d.drawImage(texture,at,null);
    }
}