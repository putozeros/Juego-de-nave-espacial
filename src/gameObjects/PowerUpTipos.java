package gameObjects;

import graficos.Assets;

import java.awt.image.BufferedImage;

public enum PowerUpTipos {
    ESCUDO("Escudo", Assets.escudito),
    VIDA ("+1UP", Assets.vidas);

    public String texto;
    public BufferedImage textura;
    private PowerUpTipos(String texto, BufferedImage textura){
        this.texto = texto;
        this.textura = textura;
    }

}
