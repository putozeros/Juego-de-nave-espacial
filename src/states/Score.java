package states;

import IO.DatoPuntuacion;
import IO.JSONParser;
import UI.Accion;
import UI.Buttones;
import gameObjects.Constantes;
import graficos.Assets;
import graficos.Text;
import math.Vector2D;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Score extends State{
    private Buttones retornar;

    private PriorityQueue<DatoPuntuacion> highScore;
    private Comparator<DatoPuntuacion> scoreComp;
    private DatoPuntuacion[] auxArray;

    public Score() {
        retornar = new Buttones(Assets.botonGris, Assets.botonRojo, Assets.botonGris.getHeight(),
                Constantes.HEIGHT - Assets.botonGris.getHeight() * 2, "VOLVER",
                new Accion() {
                    @Override
                    public void doAction() {
                        State.cambiarEstado(new EstadoMenu());
                    }
                });

        scoreComp = new Comparator<DatoPuntuacion>() {
            @Override
            public int compare(DatoPuntuacion e1, DatoPuntuacion e2) {
                return e1.getScore() < e2.getScore() ? -1: e1.getScore() > e2.getScore() ? 1:0;
            }
        };

        highScore = new PriorityQueue<DatoPuntuacion>(10,scoreComp);

        try {
            ArrayList<DatoPuntuacion> datoPuntuacions = JSONParser.leerArchivo();

            for(DatoPuntuacion d: datoPuntuacions){
                highScore.add(d);
            }

            while(highScore.size()>10){
                highScore.poll();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatear(float dt) {
        retornar.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        retornar.dibujar(g);

        auxArray = highScore.toArray(new DatoPuntuacion[highScore.size()]);

        Arrays.sort(auxArray,scoreComp);

        Vector2D scorePos = new Vector2D(
                Constantes.WIDTH/2 -200,
                100
        );
        Vector2D datePos = new Vector2D(
                Constantes.WIDTH/2 +200,
                100
        );

        Text.drawText(g,"PUNTOS",scorePos,true,Color.RED,Assets.fuente);
        Text.drawText(g,"FECHA",datePos,true,Color.RED,Assets.fuente);

        scorePos.setY(scorePos.getY()+40);
        datePos.setY(datePos.getY()+40);

        for(int i = auxArray.length -1;i> -1;i--){
            DatoPuntuacion d = auxArray[i];
            Text.drawText(g, Integer.toString(d.getScore()),scorePos,true,Color.WHITE,Assets.fuentepeque);
            Text.drawText(g, d.getDate(),datePos,true,Color.WHITE,Assets.fuentepeque);

            scorePos.setY(scorePos.getY()+40);
            datePos.setY(datePos.getY()+40);
        }
    }
}
