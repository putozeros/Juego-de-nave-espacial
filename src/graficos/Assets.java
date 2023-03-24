package graficos;

import states.EstadoMenu;
import states.EstadoOpciones;
import states.State;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    //Modos
    public static boolean tripsMode;
    public static boolean putoMode;

    //fondo
    public static BufferedImage fondo;

    //botones
    public static BufferedImage botonGris;
    public static BufferedImage botonRojo;

    //jugador
    public static BufferedImage jugador;

    //Alien
    public static BufferedImage ufo;

    // efectos
    public static BufferedImage velocidad;

    // lasers
    public static BufferedImage lazul,lverde,lrojo;

    // numeros
    public static BufferedImage[] num = new BufferedImage[11];
    public static BufferedImage vidas;
    public static BufferedImage money;
    public static BufferedImage coin;

    // explosiones
    public static BufferedImage[] exp = new BufferedImage[9];

    //barralaser
    public static BufferedImage[] barraLaser = new BufferedImage[11];

    // Asteroides
    public static BufferedImage[] grandes = new BufferedImage[4];
    public static BufferedImage[] medianos = new BufferedImage[2];
    public static BufferedImage[] peques = new BufferedImage[2];
    public static BufferedImage[] enanos = new BufferedImage[2];

    //power ups
    public static BufferedImage[] escudo = new BufferedImage[2];
    public static BufferedImage escudito;
    public static BufferedImage orbe;

    //fuentes
    public static Font fuente;
    public static Font fuentepeque;



    //Sonido
    public static Clip musica,explosion,lose,disparoJugador,disparoAlien,overheat,powerUpSound,monetizacion;
    public static void cambiarModoTrips(){
        tripsMode = !tripsMode;
        putoMode = false;
        init();
    }
    public static void cambiarPutoMode(){
        putoMode = !putoMode;
        tripsMode = false;
        init();
    }
    public static void cambiarNormal(){
        putoMode = false;
        tripsMode = false;
        init();
    }
    public static void init(){
        fondo = Loader.imageLoader("/res/Fondo/blue.png");
        jugador = Loader.imageLoader("/res/ships/player.png");
        velocidad = Loader.imageLoader("/res/effects/fire08.png");
        lazul = Loader.imageLoader("/res/lasers/laserBlue01.png");
        lverde = Loader.imageLoader("/res/lasers/laserGreen11.png");
        lrojo = Loader.imageLoader("/res/lasers/laserRed01.png");
        ufo = Loader.imageLoader("/res/ships/ufo.png");
        vidas = Loader.imageLoader("/res/otros/vidas.png");
        botonGris = Loader.imageLoader("/res/UI/grey_button00.png");
        botonRojo = Loader.imageLoader("/res/UI/red_button00.png");
        fuente = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf" ,42);
        fuentepeque = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf",22);
        orbe = Loader.imageLoader("/res/PowerUps/orbe.png");
        escudito = Loader.imageLoader("/res/PowerUps/scudo.png");
        money = Loader.imageLoader("/res/Money/dinieropeque.png");
        coin = Loader.imageLoader("/res/Money/coin.png");

        musica = Loader.loadSonido("/res/Sonidos/musica.wav");
        explosion = Loader.loadSonido("/res/Sonidos/explosion.wav");
        lose = Loader.loadSonido("/res/Sonidos/sfx_lose.wav");
        disparoJugador = Loader.loadSonido("/res/Sonidos/sfx_laser2.wav");
        disparoAlien = Loader.loadSonido("/res/Sonidos/sfx_laser1.wav");
        overheat = Loader.loadSonido("/res/Sonidos/sfx_overheat.wav");
        powerUpSound = Loader.loadSonido("/res/Sonidos/sfx_shieldUp.wav");
        monetizacion = Loader.loadSonido("/res/Sonidos/monetizar.wav");

        if(tripsMode){
            for(int i = 0;i<grandes.length;i++){
                grandes[i] = Loader.imageLoader("/res/asteroides/tripsteroide_big.png");
            }
            for(int i = 0;i<medianos.length;i++){
                medianos[i] = Loader.imageLoader("/res/asteroides/tripsteroide_mid.png");
            }
            for(int i = 0;i<peques.length;i++){
                peques[i] = Loader.imageLoader("/res/asteroides/tripsteroide_little.png");
            }
            for(int i = 0;i<enanos.length;i++){
                enanos[i] = Loader.imageLoader("/res/asteroides/tripsteroide_tiny.png");
            }
        }
        if(putoMode){
            for(int i = 0;i<grandes.length;i++){
                grandes[i] = Loader.imageLoader("/res/asteroides/putocarabig.png");
            }
            for(int i = 0;i<medianos.length;i++){
                medianos[i] = Loader.imageLoader("/res/asteroides/putocaramid.png");
            }
            for(int i = 0;i<peques.length;i++){
                peques[i] = Loader.imageLoader("/res/asteroides/putocaralil.png");
            }
            for(int i = 0;i<enanos.length;i++){
                enanos[i] = Loader.imageLoader("/res/asteroides/putocaratiny.png");
            }
        }
        if(!tripsMode && !putoMode){
            for(int i = 0;i<grandes.length;i++){
                grandes[i] = Loader.imageLoader("/res/asteroides/meteorGrey_big3.png");
            }
            for(int i = 0;i<medianos.length;i++){
                medianos[i] = Loader.imageLoader("/res/asteroides/meteorGrey_med1.png");
            }
            for(int i = 0;i<peques.length;i++){
                peques[i] = Loader.imageLoader("/res/asteroides/meteorGrey_small1.png");
            }
            for(int i = 0;i<enanos.length;i++){
                enanos[i] = Loader.imageLoader("/res/asteroides/meteorGrey_tiny1.png");
            }
        }

        for(int i = 0;i<barraLaser.length;i++){
            barraLaser[i] = Loader.imageLoader("/res/barraLaser/"+i+".png");
        }

        for(int i = 0;i < exp.length; i++){
            exp[i] = Loader.imageLoader("/res/Explosiones/"+i+".png");
        }
        for(int i = 0;i < num.length; i++) {
            num[i] = Loader.imageLoader("/res/numeros/"+i+".png");
        }
        for(int i = 0;i < escudo.length; i++) {
            escudo[i] = Loader.imageLoader("/res/PowerUps/escudo/"+i+".png");
        }
    }

    public static BufferedImage[] getBarraLaser() {
        return barraLaser;
    }
}
