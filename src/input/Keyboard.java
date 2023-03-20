package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private boolean[] keys = new boolean[256];
    public static boolean ARRIBA;
    public static boolean IZQUIERDA;
    public static boolean DERECHA;
    public static boolean DISPARO;
    public Keyboard(){
        ARRIBA=false;
        IZQUIERDA=false;
        DERECHA=false;
        DISPARO = false;
    }

    public void actualizar(){
        ARRIBA = keys[KeyEvent.VK_W];
        IZQUIERDA = keys[KeyEvent.VK_A];
        DERECHA = keys[KeyEvent.VK_D];
        DISPARO = keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
