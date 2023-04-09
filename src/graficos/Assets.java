package graficos;

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
    public static BufferedImage botonPequeRojo;
    public static BufferedImage botonPequeGris;

    //jugador
    public static BufferedImage jugador;
    public static BufferedImage jugador2;

    //Alien
    public static BufferedImage ufo;
    public static BufferedImage ufoBig;
    public static BufferedImage ufoBoss;
    public static BufferedImage limpiador;
    public static BufferedImage ufoexample;
    public static BufferedImage bigexample;
    public static BufferedImage bossexample;
    public static BufferedImage limpexample;

    // lasers
    public static BufferedImage lazul,lverde,lrojo,lpeque;

    // numeros
    public static BufferedImage[] num = new BufferedImage[11];
    public static BufferedImage vidas;
    public static BufferedImage money;
    public static BufferedImage coin;
    public static BufferedImage coin10;
    public static BufferedImage pilamonedas;

    // explosiones
    public static BufferedImage[] exp = new BufferedImage[9];
    public static BufferedImage[] fuego = new BufferedImage[8];
    public static BufferedImage[] endingPuto = new BufferedImage[7];

    //barras
    public static BufferedImage[] barraLaser = new BufferedImage[11];
    public static BufferedImage[] barraVida = new BufferedImage[101];

    // Asteroides
    public static BufferedImage[] grandes = new BufferedImage[4];
    public static BufferedImage[] medianos = new BufferedImage[2];
    public static BufferedImage[] peques = new BufferedImage[2];
    public static BufferedImage[] enanos = new BufferedImage[2];

    //power ups
    public static BufferedImage[] escudo = new BufferedImage[2];
    public static BufferedImage escudito;
    public static BufferedImage putensia;
    public static BufferedImage orbe;

    //fuentes
    public static Font fuente;
    public static Font fuentepeque;
    public static Font fuentetiny;
    public static Font fuenteTitulo;
    public static BufferedImage bocadillo;

    //controles
    public static BufferedImage teclaA;
    public static BufferedImage teclaD;
    public static BufferedImage teclaW;
    public static BufferedImage space;

    //Sonido
    public static Clip musica,explosion,lose,disparoJugador,disparoAlien,overheat,powerUpSound,monetizacion,hit,playerHit,
    musicaMenu,musicaEnding;

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
        jugador2 = Loader.imageLoader("/res/ships/player2.png");
        lazul = Loader.imageLoader("/res/lasers/laserBlue01.png");
        lverde = Loader.imageLoader("/res/lasers/laserGreen11.png");
        lrojo = Loader.imageLoader("/res/lasers/laserRed01.png");
        lpeque = Loader.imageLoader("/res/lasers/misil.png");
        ufo = Loader.imageLoader("/res/ships/ufo.png");
        vidas = Loader.imageLoader("/res/otros/vidas.png");
        botonGris = Loader.imageLoader("/res/UI/grey_button00.png");
        botonRojo = Loader.imageLoader("/res/UI/red_button00.png");
        botonPequeRojo = Loader.imageLoader("/res/UI/red_button_peque.png");
        botonPequeGris = Loader.imageLoader("/res/UI/grey_button_peque.png");
        fuente = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf" ,42);
        fuentepeque = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf",22);
        fuentetiny = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf",14);
        fuenteTitulo = Loader.loadFuente("/res/Fuentes/kenvector_future.ttf",84);
        orbe = Loader.imageLoader("/res/PowerUps/orbe.png");
        escudito = Loader.imageLoader("/res/PowerUps/scudo.png");
        money = Loader.imageLoader("/res/Money/dinieropeque.png");
        coin = Loader.imageLoader("/res/Money/coin.png");
        coin10= Loader.imageLoader("/res/Money/coin10.png");
        pilamonedas = Loader.imageLoader("/res/Money/pilamonedas.png");
        putensia = Loader.imageLoader("/res/PowerUps/power.png");
        ufoBig = Loader.imageLoader("/res/ships/ufoBig.png");
        limpiador = Loader.imageLoader("/res/ships/Limpiador.png");
        ufoBoss = Loader.imageLoader("/res/ships/ufoBoss.png");
        bocadillo = Loader.imageLoader("/res/Ending/bocadillo.png");
        bigexample = Loader.imageLoader("/res/ships/bigexample.png");
        ufoexample = Loader.imageLoader("/res/ships/ufoexample.png");
        bossexample = Loader.imageLoader("/res/ships/Bossexample.png");
        limpexample = Loader.imageLoader("/res/ships/Limpexample.png");
        teclaA = Loader.imageLoader("/res/otros/A2.png");
        teclaD = Loader.imageLoader("/res/otros/D2.png");
        teclaW = Loader.imageLoader("/res/otros/W2.png");
        space = Loader.imageLoader("/res/otros/Space.png");

        musica = Loader.loadSonido("/res/Sonidos/musica.wav");
        explosion = Loader.loadSonido("/res/Sonidos/explosion.wav");
        lose = Loader.loadSonido("/res/Sonidos/sfx_lose.wav");
        disparoJugador = Loader.loadSonido("/res/Sonidos/sfx_laser2.wav");
        disparoAlien = Loader.loadSonido("/res/Sonidos/sfx_laser1.wav");
        overheat = Loader.loadSonido("/res/Sonidos/sfx_overheat.wav");
        powerUpSound = Loader.loadSonido("/res/Sonidos/sfx_shieldUp.wav");
        monetizacion = Loader.loadSonido("/res/Sonidos/monetizar.wav");
        hit = Loader.loadSonido("/res/Sonidos/HitBajo.wav");
        playerHit = Loader.loadSonido("/res/Sonidos/PlayerHit.wav");
        musicaMenu = Loader.loadSonido("/res/Sonidos/Musicamenu.wav");
        musicaEnding = Loader.loadSonido("/res/Sonidos/endingmusic.wav");


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

        for (int i = 0; i<fuego.length;i++){
            fuego[i] = Loader.imageLoader("/res/effects/f"+i+".png");
        }

        for(int i = 0;i<barraVida.length;i++){
            barraVida[i] = Loader.imageLoader("/res/barravida/"+i+".png");
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
        for(int i = 0; i <endingPuto.length;i++){
            endingPuto[i] = Loader.imageLoader("/res/Ending/"+i+".png");
        }
    }

    public static BufferedImage[] getBarraLaser() {
        return barraLaser;
    }
    public static BufferedImage[] getBarraVida(){
        return barraVida;
    }
}
