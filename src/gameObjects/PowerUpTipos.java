package gameObjects;

import graficos.Assets;

import java.awt.image.BufferedImage;

public enum PowerUpTipos {
    ESCUDO("Escudo", Assets.escudito),
    VIDA ("+1UP", Assets.vidas),
    DAMAGE("PUTENSIA!", Assets.putensia);

    public final String texto;
    public final BufferedImage textura;
    PowerUpTipos(String texto, BufferedImage textura){
        this.texto = texto;
        this.textura = textura;
    }
}
