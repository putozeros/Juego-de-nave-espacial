package graficos;

import math.Vector2D;

import java.awt.image.BufferedImage;

public class Animacion {
    private BufferedImage[] frames;
    private int velocidad;
    private int index;
    private boolean running;
    private Vector2D posicion;
    private long time,lastTime;

    public Animacion(BufferedImage[] frames, int velocidad, Vector2D posicion){
        this.frames = frames;
        this.velocidad = velocidad;
        this.posicion = posicion;
        index = 0;
        running = true;
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    public void actualizar(float dt){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (time > velocidad){
            time = 0;
            index++;
            if (index >= frames.length){
                running = false;
                index = 0;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2D getPosicion() {
        return posicion;
    }

    public  BufferedImage getCurrentFrame(){
        return frames[index];
    }
}
