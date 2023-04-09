package states;

import UI.Buttones;
import graficos.Assets;
import graficos.Text;
import math.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class EstadoHowTo extends State{

    private ArrayList<Buttones> boton;
    public EstadoHowTo(){
        boton = new ArrayList<>();
        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100,550,
                "VOLVER",
                () -> State.cambiarEstado(new EstadoMenu()),
                80
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
        Text.drawText(g,"POWER UPS",new Vector2D(100,75),false,Color.white,Assets.fuente);
        Text.drawText(g,"ENEMIGOS",new Vector2D(700,75),false,Color.white,Assets.fuente);
        Text.drawText(g,"CONTROLES",new Vector2D(100,300),false,Color.white,Assets.fuente);

        g.drawImage(Assets.vidas,100,100,null);
        Text.drawText(g,"Curación: cura un 10% de vitalidad",new Vector2D(145,120),false,Color.green,Assets.fuentetiny);
        g.drawImage(Assets.putensia,100,150,null);
        Text.drawText(g,"Potencia x2: Multiplica el daño x2",new Vector2D(145,175),false,Color.red,Assets.fuentetiny);
        g.drawImage(Assets.escudito,100,210,null);
        Text.drawText(g,"Escudo: Repele los asteroides",new Vector2D(145,230),false,Color.cyan,Assets.fuentetiny);
        g.drawImage(Assets.ufoexample,700,100,null);
        Text.drawText(g,"UFO: 50HP, es rápido, pero no muy dañino",new Vector2D(750,125),false,Color.cyan,Assets.fuentetiny);
        g.drawImage(Assets.bigexample,700,165,null);
        Text.drawText(g,"UFO-mayor: 500HP, Hace mucho daño al contacto",new Vector2D(760,195),false,Color.red,Assets.fuentetiny);
        g.drawImage(Assets.limpexample,700,250,null);
        Text.drawText(g,"Limpiador: 5HP, Tiene una puntería excelente",new Vector2D(750,270),false,Color.MAGENTA,Assets.fuentetiny);
        g.drawImage(Assets.bossexample,700,315,null);
        Text.drawText(g,"UFO-Mother: 15000HP, Hace un daño descomunal",new Vector2D(765,350),false,Color.green,Assets.fuentetiny);
        g.drawImage(Assets.teclaW,160,320,null);
        g.drawImage(Assets.teclaA,110,370,null);
        g.drawImage(Assets.teclaD,210,370,null);
        g.drawImage(Assets.space,110,430,null);
        Text.drawText(g,"MOVIMIENTO",new Vector2D(300,400),false,Color.white,Assets.fuentepeque);
        Text.drawText(g,"DISPARO",new Vector2D(300,460),false,Color.red,Assets.fuentepeque);
        Text.drawText(g,"¡Acaba con todas las oleadas de Asteroides",new Vector2D(550,460),false,Color.yellow,Assets.fuentepeque);
        Text.drawText(g,"que la UFO-Mother ha enviado para poder",new Vector2D(550,490),false,Color.yellow,Assets.fuentepeque);
        Text.drawText(g,"enfrentarte finalmente a ella y librarnos",new Vector2D(550,520),false,Color.yellow,Assets.fuentepeque);
        Text.drawText(g,"de su amenaza!",new Vector2D(550,550),false,Color.yellow,Assets.fuentepeque);


    }
}
