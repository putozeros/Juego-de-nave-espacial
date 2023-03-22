package states;

import java.awt.*;

public abstract class State {
    public static Window ventana;
    private static State estadoActual = null;
    public static void cambiarEstado(State nuevoEstado){
        estadoActual = nuevoEstado;
    }
    public abstract void updatear(float dt);
    public abstract void dibujar(Graphics g);

    public static State getEstadoActual() {
        return estadoActual;
    }
}
