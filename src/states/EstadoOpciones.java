package states;

import UI.Accion;
import UI.Buttones;
import graficos.Assets;

import java.awt.*;
import java.util.ArrayList;

import static graficos.Assets.*;

public class EstadoOpciones extends State{
    private ArrayList<Buttones> boton;
    private Buttones retornar;

    private Buttones tripsModeBoton;
    public EstadoOpciones(){
        boton = new ArrayList<>();

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                345,
                450,
                "TRIPSMODE",
                () ->  {
                    cambiarModoTrips();
                    State.cambiarEstado(new EstadoMenu());
                },
                5
        ));
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                745,
                450,
                "PUTOMODE",
                () -> {
                    cambiarPutoMode();
                    State.cambiarEstado(new EstadoMenu());
                  } ,
                6
        ));
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                545,
                450,
                "NORMAL",
                () -> {
                    cambiarNormal();
                    State.cambiarEstado(new EstadoMenu());
                } ,
                6
        ));

        retornar = new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100, 550,
                "VOLVER",
                new Accion() {
                    @Override
                    public void doAction() {
                        State.cambiarEstado(new EstadoMenu());
                    }
                },
                6
        );
        boton.add(retornar);
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
