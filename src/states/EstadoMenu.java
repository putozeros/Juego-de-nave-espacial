package states;

import IO.JSONParser;
import UI.Accion;
import UI.Buttones;
import gameObjects.Constantes;
import graficos.Assets;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static graficos.Assets.*;

public class EstadoMenu extends State{
    private static ArrayList<Buttones> boton;;
    public EstadoMenu(){
        boton = new ArrayList<>();

        if(tripsMode){
            boton.add (new Buttones(
                    Assets.botonGris,
                    Assets.botonRojo,
                    345,
                    360 ,
                    "MATATRIPS",
                    () -> State.cambiarEstado(new GameState()),
                    1
            ));
        }else if(putoMode){
            boton.add(new Buttones(
                    Assets.botonGris,
                    botonRojo,
                    345,
                    360,
                    "MATAPUTOS",
                    () -> State.cambiarEstado(new GameState()),
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
                    () -> State.cambiarEstado(new GameState()),
                    1
            ));
        }

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                545,
                540,
                "TIENDA",
                () -> State.cambiarEstado(new EstadoTienda()),
                2
        ));

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                Constantes.WIDTH / 2 - Assets.botonGris.getWidth() / 2,
                360,
                "PUNTOS",
                new Accion() {
                    @Override
                    public void doAction(){
                        State.cambiarEstado(new Score());
                    }
                },
                3
        ));
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                745, 360,
                "OPCIONES",
                new Accion() {
                    @Override
                    public void doAction() {
                        State.cambiarEstado(new EstadoOpciones());
                    }
                },
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
    }
}
