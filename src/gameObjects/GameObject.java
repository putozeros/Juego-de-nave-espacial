package gameObjects;

import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected BufferedImage texture;
    protected Vector2D posicion;

    public GameObject(Vector2D posicion, BufferedImage texture ) {
        this.posicion = posicion;
        this.texture = texture;
    }
    public abstract void actualizar(float dt);
    public abstract void dibujar(Graphics graphics);

    public Vector2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2D posicion) {
        this.posicion = posicion;
    }
}
