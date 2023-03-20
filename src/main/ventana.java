package main;

import gameObjects.Constantes;
import graficos.Assets;
import input.Keyboard;
import input.MouseInput;
import states.EstadoMenu;
import states.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class ventana extends JFrame implements Runnable {

    private Canvas canvas;
    private Thread thread;
    private boolean running=false;
    private BufferStrategy buffer;
    private Graphics graphics;
    private final int frames = 60;
    private double targettime = 1000000000/frames;
    private double delta = 0;
    private int AVGFPS = frames;
    private Keyboard keyboard;
    private MouseInput mouseInput;

    public ventana(){
        setTitle("Naves");
        setSize(Constantes.WIDTH, Constantes.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);



        canvas = new Canvas();
        keyboard = new Keyboard();
        mouseInput = new MouseInput();

        canvas.setPreferredSize(new Dimension(Constantes.WIDTH, Constantes.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constantes.WIDTH, Constantes.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constantes.WIDTH, Constantes.HEIGHT));
        canvas.setFocusable(true);

        add(canvas);
        canvas.addKeyListener(keyboard);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ventana().start();
    }

    private void actualizar(float dt){
        keyboard.actualizar();
        State.getEstadoActual().updatear(dt);
    }

    private void dibujar(){
        buffer = canvas.getBufferStrategy();
        if(buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }

        graphics = buffer.getDrawGraphics();
        //----------------------------------



        int anchoImagen = Assets.fondo.getWidth(null);
        int altoImagen = Assets.fondo.getHeight(null);
        for (int x = 0; x<Constantes.WIDTH;x+= anchoImagen){
            for(int y = 0; y<Constantes.HEIGHT;y+= altoImagen){
                graphics.drawImage(Assets.fondo,x,y,null);
            }
        }

        State.getEstadoActual().dibujar(graphics);
        //----------------------------------
        graphics.dispose();
        buffer.show();
    }

    private void init(){
        Assets.init();
        State.cambiarEstado(new EstadoMenu());
    }

    @Override
    public void run() {

        long ahora = 0;
        long lastTime = System.nanoTime();
        int fps = 0;
        long time = 0;

        init();

        while(running){
            ahora = System.nanoTime();
            delta += (ahora - lastTime)/targettime;
            time+=(ahora - lastTime);
            lastTime = ahora;
            if(delta >=1){
                actualizar((float) (delta * targettime *0.000001f));
                dibujar();
                delta--;
                fps++;
            }
            if (time>= 1000000000){
                AVGFPS = fps;
                fps=0;
                time=0;
            }
        }
        stop();
    }

    private void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
