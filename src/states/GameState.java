package states;

import IO.DatoPuntuacion;
import IO.JSONParser;
import UI.Accion;
import gameObjects.*;
import graficos.Animacion;
import graficos.Assets;
import graficos.Sonido;
import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameState extends State{
    public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constantes.WIDTH/2 - Assets.jugador.getWidth()/2,
            Constantes.HEIGHT/2 - Assets.jugador.getHeight()/2);

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
    private int puntuacion = 0;
    private int vidas = 3;
    private int asteroides;
    private int waves = 1;
    private boolean gameOver;
    private Sonido musica;
    private Crono timerGameOver,ufoSpawner;
    private boolean powerUpSpawner = false;
    public GameState(){
        player=new Player(PLAYER_START_POSITION, new Vector2D(),6 , Assets.jugador,this);
        timerGameOver = new Crono();
        gameOver = false;
        movingObjects.add(player);
        musica = new Sonido(Assets.musica);
        musica.cambiarVolumen(-10);
        musica.loop();
        asteroides = 1;
        iniciarOleada();
        ufoSpawner = new Crono();
        ufoSpawner.run(Constantes.UFO_SPAWN_RATE);
    }


    public void addpuntuacion (int value, Vector2D posicion){
        puntuacion += value;
        if(puntuacion % 100 == 0){
            spawnerPowerUp();
        }
        mensajes.add(new Mensaje(posicion,true, "+"+value+" puntos",Color.WHITE,false,Assets.fuentepeque));
    }


    public void dividirAsteroide(Asteroide asteroide){
        Size size = asteroide.getSize();

        BufferedImage[] texturas = size.texturas;

        Size newSize = null;

        switch (size){
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }
        for (int i = 0; i < size.cantidad;i++){
            movingObjects.add(new Asteroide(
                    asteroide.getPosicion(),
                    new Vector2D(0,1).setDireccion(Math.random()*Math.PI*2),
                    Constantes.asteroidSpeed*Math.random() +1,
                    texturas[(int)(Math.random()*texturas.length)],
                    this,
                    newSize
            ));
        }
    }



    private void iniciarOleada(){
        mensajes.add(new Mensaje(new Vector2D(Constantes.WIDTH/2,Constantes.HEIGHT/2),true,"Oleada "+waves,
                Color.WHITE,true, Assets.fuente));

        double x,y;
        for(int i = 0; i < asteroides;i++){
            x= 1 % 2 == 0 ? Math.random()* Constantes.WIDTH : 0;
            y= 1 % 2 == 0 ? 0 : Math.random()* Constantes.HEIGHT;

            BufferedImage texturas = Assets.grandes[(int)(Math.random()*Assets.grandes.length)];

            movingObjects.add(new Asteroide(
                    new Vector2D(x,y),
                    new Vector2D(0,1).setDireccion(Math.random()*Math.PI*2),
                    Constantes.asteroidSpeed*Math.random() +1,
                    texturas,
                    this,
                    Size.BIG
            ));
        }
        asteroides++;
        waves++;
    }

    public void playExplosion(Vector2D posicion){
        explosiones.add(new Animacion(
                Assets.exp,
                60,
                posicion.substract(new Vector2D(Assets.exp[0].getWidth()/2,Assets.exp[0].getHeight()))));
    }
    private void spawnUfo(){
        int rand = (int)(Math.random()*2);

        double x = rand == 0 ? (Math.random()*Constantes.WIDTH) : 0;
        double y = rand == 0 ? 0 : (Math.random()*Constantes.HEIGHT);

        ArrayList<Vector2D> path = new ArrayList<Vector2D>();

        double posX, posY;

        posX = Math.random()*Constantes.WIDTH/2;
        posY = Math.random()*Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH/2;
        posY = Math.random()*Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/2;
        posY = Math.random()*Constantes.HEIGHT/2 + Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH/2;
        posY = Math.random()*Constantes.HEIGHT/2 + Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        movingObjects.add(new Ufo(
                new Vector2D(x,y),
                new Vector2D(),
                Constantes.UFO_MAX_SPEED,
                Assets.ufo,
                path,
                this
        ));
    }
    private void spawnerPowerUp(){
        final int x = (int) ((Constantes.WIDTH - Assets.orbe.getWidth())*Math.random());
        final int y = (int) ((Constantes.HEIGHT - Assets.orbe.getHeight())*Math.random());

        int indice = (int) (Math.random() * (PowerUpTipos.values().length));

        PowerUpTipos p = PowerUpTipos.values()[indice];

        final String texto = p.texto;
        Accion accion = null;
        Vector2D posicion = new Vector2D(x,y);

        switch(p){
            case VIDA:
                accion = new Accion() {
                    @Override
                    public void doAction() {
                        vidas ++;
                        powerUpSpawner = false;
                        mensajes.add(new Mensaje(
                                posicion,false,"1 UP",Color.GREEN,false,Assets.fuentepeque));
                    }
                };
                break;
            case ESCUDO:
                accion = new Accion() {
                    @Override
                    public void doAction() {
                        player.setEscudo();
                        powerUpSpawner = false;
                        mensajes.add(new Mensaje(
                                posicion,false,"ESCUDO",Color.RED,false,Assets.fuentepeque));
                    }
                };
                break;
            default:
                break;
        }
        this.movingObjects.add(new PowerUps(
                posicion,p.textura,accion,this
        ));
    }
    public void updatear(float dt){


        for(int i = 0;i<movingObjects.size();i++){
            movingObjects.get(i).actualizar(dt);
        }

        for(int i = 0; i<explosiones.size();i++){
            Animacion anim = explosiones.get(i);
            anim.actualizar(dt);
            if (!anim.isRunning()) {
                explosiones.remove(i);
            }
        }
        if(gameOver && !timerGameOver.isRunning()){

            try {
                ArrayList<DatoPuntuacion> datalist = JSONParser.leerArchivo();
                datalist.add(new DatoPuntuacion(puntuacion));
                JSONParser.escribirArchivo(datalist);
            } catch (IOException e) {
                e.printStackTrace();
            }
            State.cambiarEstado(new EstadoMenu());
        }



        if(!ufoSpawner.isRunning()){
            ufoSpawner.run(Constantes.UFO_SPAWN_RATE);
            spawnUfo();
        }

        timerGameOver.actualizar();
        ufoSpawner.actualizar();

        for(int i = 0;i<movingObjects.size();i++){
            if(movingObjects.get(i) instanceof Asteroide){
                return;
            }
        }

        iniciarOleada();
    }

    public void dibujar(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for(int i = 0;i < mensajes.size();i++){
            mensajes.get(i).dibujar(g2d);
            if(mensajes.get(i).isDead()){
                mensajes.remove(i);
            }
        }

        for(int i = 0;i<movingObjects.size();i++){
            movingObjects.get(i).dibujar(g);
        }
        for(int i = 0;i<explosiones.size();i++){
            Animacion anim = explosiones.get(i);
            g2d.drawImage(anim.getCurrentFrame(),(int)anim.getPosicion().getX(),(int)anim.getPosicion().getY(),null);
        }
        dibujarPuntos(g);
        dibujarVidas(g);
    }

    public void dibujarPuntos(Graphics graphics){
        Vector2D posicion = new Vector2D(1120,20);
        String scoreToString = Integer.toString(puntuacion);
        for (int i = 0;i<scoreToString.length();i++){
            graphics.drawImage(Assets.num[Integer.parseInt(scoreToString.substring(i,i+1))],(int)posicion.getX(),
                    (int)posicion.getY(),null);
            posicion.setX(posicion.getX()+20);
        }
    }

    public void dibujarVidas(Graphics graphics){
        if(vidas < 1){
            return;
        }
        Vector2D posicionVidas = new Vector2D(25,25);
        graphics.drawImage(Assets.vidas,(int)posicionVidas.getX(),(int)posicionVidas.getY(),null);
        graphics.drawImage(Assets.num[10],(int)posicionVidas.getX() +45,(int)posicionVidas.getY() +8, null);

        String vidasAString = Integer.toString(vidas);

        Vector2D posicion = new Vector2D(posicionVidas.getX(),posicionVidas.getY());

        for (int i = 0; i <vidasAString.length();i++){
            int numero = Integer.parseInt(vidasAString.substring(i,i+1));
            if (numero <=0){
                break;
            }
            graphics.drawImage(Assets.num[numero],(int)posicion.getX() +65,(int)posicion.getY()+8,null);
            posicion.setX(posicion.getX()+20);
        }
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }
    public Player getPlayer(){
        return player;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public boolean substractLife() {
        vidas--;
        return vidas > 0;
    }

    public void gameOver(){
        Mensaje perdedor = new Mensaje(PLAYER_START_POSITION,true,"GAME OVER",Color.WHITE,true,Assets.fuente);
        this.mensajes.add(perdedor);
        musica.stop();
        timerGameOver.run(Constantes.GAME_OVER_TIME);
        gameOver = true;
    }
}
