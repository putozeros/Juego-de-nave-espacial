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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class GameState extends State {
    public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constantes.WIDTH / 2 - Assets.jugador.getWidth() / 2,
            Constantes.HEIGHT / 2 - Assets.jugador.getHeight() / 2);

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
    private int puntuacion = 0, vidas = 1, money = 0, asteroides, waves = 1, maxUfo = 2, maxUfoBig = 1,
            ufoCounter=0, ufoBigCounter=0,UFO_SPAWN_RATE = 12000,UFOBIG_SPAWN_RATE=35000,LIMPIADOR_SPAWNRATE=45000;
    private boolean gameOver,doublefire = false,bossSpawned = false;
    public Sonido musica;
    private Crono timerGameOver, ufoSpawner, ufoBigSpawner,limpiadorSpawner;

    public GameState() {
        try{
            if(JSONParser.leerConfiguracion("doublefire")){
                player = new Player(PLAYER_START_POSITION, new Vector2D(), 6, Assets.jugador2, this, 2400);
            }
            else{
                player = new Player(PLAYER_START_POSITION, new Vector2D(), 6, Assets.jugador, this, 2400);
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        timerGameOver = new Crono();
        gameOver = false;
        movingObjects.add(player);
        musica = new Sonido(Assets.musica);
        musica.cambiarVolumen(-10);
        musica.loop();
        asteroides = 1;
        iniciarOleada();
        ufoSpawner = new Crono();
        ufoSpawner.run(UFO_SPAWN_RATE);
        ufoBigSpawner = new Crono();
        ufoBigSpawner.run(UFOBIG_SPAWN_RATE);
        limpiadorSpawner = new Crono();
        limpiadorSpawner.run(LIMPIADOR_SPAWNRATE);
    }
    public static void setDoublefire (boolean doublefire){
        doublefire = doublefire;
    }
    public void addpuntuacion(int value, Vector2D posicion) {
        puntuacion += value;
        if (puntuacion % 100 == 0) {
            spawnerPowerUp();
        }
    }

    public void addMoney(int value, Vector2D posicion) {
        money += value;
        mensajes.add(new Mensaje(posicion, true, "+" + value + " coin", Color.yellow, false, Assets.fuentepeque));
        if(money >1){
            mensajes.add(new Mensaje(posicion,true,"+"+value+" coins",Color.orange,false,Assets.fuentepeque));
        }
    }

    public void dividirAsteroide(Asteroide asteroide) {
        Size size = asteroide.getSize();

        BufferedImage[] texturas = size.texturas;

        Size newSize = null;

        switch (size) {
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
        for (int i = 0; i < size.cantidad; i++) {
            movingObjects.add(new Asteroide(
                    asteroide.getPosicion(),
                    new Vector2D(0, 1).setDireccion(Math.random() * Math.PI * 2),
                    Constantes.asteroidSpeed * Math.random() + 1,
                    texturas[(int) (Math.random() * texturas.length)],
                    this,
                    newSize,
                    6
            ));
        }
    }


    private void iniciarOleada() {
        mensajes.add(new Mensaje(new Vector2D(Constantes.WIDTH / 2, Constantes.HEIGHT / 2), true, "Oleada " + waves,
                Color.WHITE, true, Assets.fuente));

        double x, y;
        for (int i = 0; i < asteroides; i++) {
            x = 1 % 2 == 0 ? Math.random() * Constantes.WIDTH : 0;
            y = 1 % 2 == 0 ? 0 : Math.random() * Constantes.HEIGHT;

            BufferedImage texturas = Assets.grandes[(int) (Math.random() * Assets.grandes.length)];

            movingObjects.add(new Asteroide(
                    new Vector2D(x, y),
                    new Vector2D(0, 1).setDireccion(Math.random() * Math.PI * 2),
                    Constantes.asteroidSpeed * Math.random() + 1,
                    texturas,
                    this,
                    Size.BIG,
                    11
            ));
        }

        asteroides++;
        waves++;
        UFO_SPAWN_RATE -=1000;
        UFOBIG_SPAWN_RATE -= 3000;
        LIMPIADOR_SPAWNRATE -= 4500;
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
                this,
                50
        ));
    }
    private void spawnUfoBig(){
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

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH/3;
        posY = Math.random()*Constantes.HEIGHT/2 + Constantes.HEIGHT;
        path.add(new Vector2D(posX,posY));

        movingObjects.add(new UfoBig(
                new Vector2D(x,y),
                new Vector2D(),
                Constantes.UFO_MAX_SPEED/2,
                Assets.ufoBig,
                path,
                this,
                500
        ));
    }
    private void spawnLimpiador(){
        int rand = (int)(Math.random()*2);

        double x = rand == 0 ? (Math.random()*Constantes.WIDTH) : 0;
        double y = rand == 0 ? 0 : (Math.random()*Constantes.HEIGHT);

        ArrayList<Vector2D> path = new ArrayList<Vector2D>();

        double posX, posY;

        posX = Math.random()*Constantes.WIDTH/2;
        posY = Math.random()*Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));


        movingObjects.add(new Limpiador(
                new Vector2D(x,y),
                new Vector2D(),
                Constantes.LIMPIADOR_SPEED,
                Assets.limpiador,
                path,
                this,
                5
        ));
    }
    private void spawnBoss(){
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

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH/3;
        posY = Math.random()*Constantes.HEIGHT/2 + Constantes.HEIGHT;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH;
        posY = Math.random()*Constantes.HEIGHT/2 + Constantes.HEIGHT/3;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/3 + Constantes.WIDTH/3;
        posY = Math.random()*Constantes.HEIGHT + Constantes.HEIGHT;
        path.add(new Vector2D(posX,posY));

        posX = Math.random()*Constantes.WIDTH/2 + Constantes.WIDTH/3;
        posY = Math.random()*Constantes.HEIGHT/3 + Constantes.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        movingObjects.add(new UfoBoss(
                new Vector2D(x,y),
                new Vector2D(),
                Constantes.UFO_MAX_SPEED/3,
                Assets.ufoBoss,
                path,
                this,
                15000
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
                int cantidadCura = 240;
                accion = new Accion() {
                    @Override
                    public void doAction() {
                        if(player.getVitalidad() + cantidadCura >= 2400){
                            player.setVitalidad(2400);
                        }else{
                            player.setVitalidad(player.getVitalidad()+cantidadCura);
                        }
                        mensajes.add(new Mensaje(
                                posicion,false,"HEALING",Color.GREEN,false,Assets.fuentepeque));
                    }
                };
                break;
            case DAMAGE:
                final int extraDamage = 2;
                accion = new Accion() {
                    @Override
                    public void doAction() {
                        Laser.setDamageBonus(extraDamage);
                        mensajes.add(new Mensaje(
                                posicion,false,"PUTENSIA!",Color.RED,false,Assets.fuentepeque
                        ));
                    }
                };
                break;
            case ESCUDO:
                accion = new Accion() {
                    @Override
                    public void doAction() {
                        player.setEscudo();
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
                datalist.add(new DatoPuntuacion(puntuacion,money));
                JSONParser.escribirArchivo(datalist,money);
            } catch (IOException e) {
                e.printStackTrace();
            }
            State.cambiarEstado(new EstadoMenu());
        }
        if(waves == 2 && ufoCounter != maxUfo){
            if(!ufoSpawner.isRunning()){
                ufoSpawner.run(UFO_SPAWN_RATE);
                spawnUfo();
                ufoCounter++;
            }
        }else if(waves >= 3 && waves <11){
            if(!ufoSpawner.isRunning()){
                ufoSpawner.run(UFO_SPAWN_RATE);
                spawnUfo();
            }
        }
        if(waves == 2 && ufoBigCounter != maxUfoBig){
            if(!ufoBigSpawner.isRunning()){
                ufoSpawner.run(UFOBIG_SPAWN_RATE);
                spawnUfoBig();
                ufoBigCounter++;
            }
        }else if(waves >= 3 && waves <11){
            if(!ufoBigSpawner.isRunning()){
                ufoBigSpawner.run(UFOBIG_SPAWN_RATE);
                spawnUfoBig();
            }
        }
        if(waves == 11 && !bossSpawned){
            spawnBoss();
            bossSpawned = true;
        }

        if(!limpiadorSpawner.isRunning() && waves <11){
            limpiadorSpawner.run(LIMPIADOR_SPAWNRATE);
            spawnLimpiador();
        }

        timerGameOver.actualizar();
        ufoSpawner.actualizar();
        ufoBigSpawner.actualizar();
        limpiadorSpawner.actualizar();

        for(int i = 0;i<movingObjects.size();i++) {
            if (movingObjects.get(i) instanceof Asteroide) {
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
        dibujarMoney(g);
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

    public void dibujarMoney(Graphics graphics){
        Vector2D posicion = new Vector2D(1120,50);
        graphics.drawImage(Assets.money,1085,45,null);
        String scoreToString = Integer.toString(money);
        for (int i = 0;i<scoreToString.length();i++){
            graphics.drawImage(Assets.num[Integer.parseInt(scoreToString.substring(i,i+1))],(int)posicion.getX(),
                    (int)posicion.getY(),null);
            posicion.setX(posicion.getX()+20);
        }
    }

    public void agregarObjeto(MovingObject objeto,Vector2D posicion){
        objeto.setPosicion(posicion);
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
