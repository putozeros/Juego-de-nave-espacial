package states;

import UI.Buttones;
import gameObjects.Constantes;
import graficos.Assets;
import graficos.Sonido;
import graficos.Text;
import math.Vector2D;

import java.awt.*;
import java.util.ArrayList;

import static graficos.Assets.*;

public class EstadoMenu extends State{
    private static ArrayList<Buttones> boton;;
    private static boolean musicaisOn = false;
    private Sonido musicamenu = new Sonido(musicaMenu);
    public EstadoMenu(){
        if(!musicaisOn){
            musicamenu.cambiarVolumen(-10);
            musicamenu.loop();
            musicaisOn = true;
        }

        boton = new ArrayList<>();
        if(tripsMode){
            boton.add (new Buttones(
                    Assets.botonGris,
                    Assets.botonRojo,
                    345,
                    360,
                    "MATATRIPS",
                    () ->{
                        musicamenu.stop();
                        musicaisOn = false;
                        State.cambiarEstado(new GameState());
                    },
                    1
            ));
        }else if(putoMode){
            boton.add(new Buttones(
                    Assets.botonGris,
                    botonRojo,
                    345,
                    360,
                    "MATAPUTOS",
                    () -> {
                        musicamenu.stop();
                        musicaisOn = false;
                        State.cambiarEstado(new GameState());
                    },
                    1
            ));
        }
        else{
            boton.add (new Buttones(
                    Assets.botonGris,
                    Assets.botonRojo,
                    345,
                    360 ,
                    "JUGAR",
                    () -> {
                        musicamenu.stop();
                        musicaisOn = false;
                        State.cambiarEstado(new GameState());
                    },
                    1
            ));
        }
        boton.add(new Buttones(
                botonGris,
                botonRojo,
                545,300,
                "Como Jugar",
                () -> State.cambiarEstado(new EstadoHowTo()),
                82
        ));

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                545,
                420,
                "TIENDA",
                () -> State.cambiarEstado(new EstadoTienda()),
                2
        ));

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                Constantes.WIDTH / 2 - Assets.botonGris.getWidth() / 2,
                360,
                "PUNTOS",() -> State.cambiarEstado(new Score()),
                3
        ));
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                745, 360,
                "OPCIONES",() -> State.cambiarEstado(new EstadoOpciones()),
                4
        ));
        boton.add(new Buttones(
                botonGris,
                botonRojo,
                1030,
                600,
                "salir",
                () -> System.exit(0),
                9
        ));

    }

    @Override
    public void updatear(float dt) {
        for(Buttones b: boton){
            b.actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
        for(Buttones b: boton){
            b.dibujar(g);
        }
        Text.drawText(g,"PONME TITULO",new Vector2D(270,150),false,Color.WHITE, fuenteTitulo);
    }
}
