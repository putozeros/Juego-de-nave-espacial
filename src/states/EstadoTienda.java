package states;

import IO.JSONParser;
import UI.Buttones;
import graficos.Assets;
import graficos.Text;
import math.Vector2D;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EstadoTienda extends State{

    private ArrayList<Buttones> boton;

    public EstadoTienda(){
        boton = new ArrayList<>();

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100,
                550,
                "VOLVER",
                () -> State.cambiarEstado(new EstadoMenu()),
                10));
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
        try {
            Text.drawText(g,"= "+ JSONParser.getCoinsTotal(),new Vector2D(535,95),false,Color.yellow,Assets.fuente);
            g.drawImage(Assets.pilamonedas,450,50,null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
