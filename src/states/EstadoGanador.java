package states;

import UI.Buttones;
import graficos.Animacion;
import graficos.Assets;
import graficos.Sonido;
import graficos.Text;
import math.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class EstadoGanador extends State{
    private Animacion putoBaila;
    private ArrayList<Buttones> boton;

    public EstadoGanador(){
        Sonido musicaending = new Sonido(Assets.musicaEnding);
        musicaending.cambiarVolumen(-10);
        musicaending.loop();
        boton = new ArrayList<>();
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100,
                550,
                "VOLVER",
                () ->{
                    musicaending.stop();
                    State.cambiarEstado(new EstadoMenu());
                },
                60));

        putoBaila = new Animacion(Assets.endingPuto,60,new Vector2D(100,225));
    }


    @Override
    public void updatear(float dt) {
        for(Buttones b: boton){
            b.actualizar();
        }
        putoBaila.actualizar(dt);
    }

    @Override
    public void dibujar(Graphics g) {
        for(Buttones b: boton){
            b.dibujar(g);
        }
        g.drawImage(Assets.bocadillo,10,50,null);
        g.drawImage(putoBaila.getCurrentFrame(),(int) putoBaila.getPosicion().getX(),(int)putoBaila.getPosicion().getY(),null);
        Text.drawText(g,"GRACIAS POR",new Vector2D(70,120),false,Color.white,Assets.fuentepeque);
        Text.drawText(g,"jugar a mi juego.",new Vector2D(55,150),false,Color.white,Assets.fuentepeque);
        Text.drawText(g,"¡ESPERO VERTE",new Vector2D(55,180),false,Color.white,Assets.fuentepeque);
        Text.drawText(g,"PRONTO!",new Vector2D(100,210),false,Color.white,Assets.fuentepeque);

        Text.drawText(g,"PROGRAMADO POR:",new Vector2D(600,200),false,Color.red,Assets.fuente);
        Text.drawText(g,"Víctor Medina Orihuela",new Vector2D(650,250),false,Color.yellow,Assets.fuentepeque);
        Text.drawText(g,"ASSETS GRÁFICOS:",new Vector2D(600,300),false,Color.red,Assets.fuente);
        Text.drawText(g,"Space Shooter (Redux, plus fonts and sounds)",new Vector2D(600,330),false,Color.yellow,Assets.fuentetiny);
        Text.drawText(g,"by Kenney Vleugels (www.kenney.nl)",new Vector2D(600,360),false,Color.yellow,Assets.fuentetiny);
        Text.drawText(g,"Música:",new Vector2D(600,425),false,Color.red,Assets.fuente);
        Text.drawText(g,"You say run (simpsonill Remix) - no copyright ;)",new Vector2D(600,460),false,Color.yellow,Assets.fuentetiny);
    }
}
