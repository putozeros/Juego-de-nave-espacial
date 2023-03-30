package gameObjects;

import javax.swing.filechooser.FileSystemView;

public class Constantes {
    //botones menu
    public static String botonTextoJugar = "JUGAR";

    // dimensiones de la ventana
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    //propiedades del jugador
    public static final int FIRERATE = 100;
    public static final double DELTAANGLE = 0.1;
    public static final double ACC = 0.2;
    public static final long Flicker = 200;
    public static final long Spawning = 3000;
    public static final long GAME_OVER_TIME= 3000;

    //propiedades del laser
    public static final double LASER_SPEED = 10;

    // propiedades del asteroide
    public static final double asteroidSpeed = 2.0;
    public static final int ASTEROIDE_SCORE = 5;

    // Propiedades del Alien
    public static final int NODE_RADIO = 160;
    public static final int UFO_MAX_SPEED = 6;
    public static long UFO_FIRERATE = 600;
    public static double UFO_ANGLE_RANGE = Math.PI/2;
    public static final int UFO_SCORE = 25;

    public static final int UFO_SPAWN_RATE = 10000;


    // Guardado de datos
    public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
            "\\Juego de naves\\data.json";
    public static final String CONFIG_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
            "\\Juego de naves\\achieve.json";
}
