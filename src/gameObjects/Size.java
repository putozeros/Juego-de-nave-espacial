package gameObjects;

import graficos.Assets;

import java.awt.image.BufferedImage;

public enum Size {

    BIG(2, Assets.medianos), MED(2,Assets.peques), SMALL(2, Assets.enanos), TINY(0,null);
    public  int cantidad;
    public BufferedImage[] texturas;
    private Size(int cantidad, BufferedImage[] texturas){
        this.cantidad = cantidad;
        this.texturas = texturas;
    }
}
