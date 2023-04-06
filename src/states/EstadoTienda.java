package states;

import IO.JSONParser;
import UI.Buttones;
import UI.ButtonesPeques;
import gameObjects.Laser;
import gameObjects.Player;
import graficos.Assets;
import graficos.Text;
import math.Vector2D;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EstadoTienda extends State {

    private boolean potencia1Presionado = false;
    private boolean potencia2Presionado = false;
    private boolean potencia3Presionado = false;
    private boolean potencia4Presionado = false;
    private ArrayList<Buttones> boton;
    private ArrayList<ButtonesPeques> botoncilloPotencia;
    private ArrayList<ButtonesPeques> botoncilloRatio;
    private ArrayList<ButtonesPeques> botoncilloRecarga;
    private boolean potencia1Activada = false;
    private boolean potencia2Activada = false;
    private boolean potencia3Activada = false;
    private boolean potencia4Activada = false;
    private boolean ratio1Activado = false;
    private boolean ratio2Activado = false;
    private boolean ratio3Activado = false;
    private boolean ratio4Activado = false;
    private boolean recarga1Activada = false;
    private boolean recarga2Activada = false;
    private boolean recarga3Activada = false;
    private boolean recarga4Activada = false;
    private boolean recarga1Presionada = false;
    private boolean recarga2Presionada = false;
    private boolean recarga3Presionada = false;
    private boolean recarga4Presionada = false;
    private boolean ratio1Presionado = false;
    private boolean ratio2Presionado = false;
    private boolean ratio3Presionado = false;
    private boolean ratio4Presionado = false;
    private HashMap<String, Boolean> potenciaActivada;
    private HashMap<String, Boolean> ratioActivado;
    private HashMap<String, Boolean> recargaActivada;

    public EstadoTienda() {
        boton = new ArrayList<>();
        botoncilloPotencia = new ArrayList<>();
        botoncilloRatio = new ArrayList<>();
        botoncilloRecarga = new ArrayList<>();
        potenciaActivada = new HashMap<>();
        ratioActivado = new HashMap<>();
        recargaActivada = new HashMap<>();
        potenciaActivada.put("Potencia 1", false);
        potenciaActivada.put("Potencia 2", false);
        ratioActivado.put("Ratio 1", false);
        ratioActivado.put("Ratio 2", false);
        recargaActivada.put("Recarga 1", false);
        recargaActivada.put("Recarga 2", false);

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100,
                550,
                "VOLVER",
                () -> State.cambiarEstado(new EstadoMenu()),
                10));

        botoncilloPotencia.add(new ButtonesPeques(
                Assets.botonPequeGris,
                Assets.botonPequeRojo,
                200, 200,
                "20",
                () -> {
                    if (JSONParser.getCoinsTotal() >= 20) {
                        actualizarBonus("Potencia 1");
                        JSONParser.restarCoinsTotal(20);
                        potencia1Presionado = true;
                    }
                },
                25));
        try {
            if (JSONParser.leerConfiguracion("Potencia 1")) {
                botoncilloPotencia.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        200, 250,
                        "200",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 200) {
                                actualizarBonus("Potencia 2");
                                JSONParser.restarCoinsTotal(200);
                                potencia2Presionado = true;
                            }
                        },
                        26));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (JSONParser.leerConfiguracion("Potencia 1") && JSONParser.leerConfiguracion("Potencia 2")) {
                botoncilloPotencia.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        200, 300,
                        "2000",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 2000) {
                                actualizarBonus("Potencia 3");
                                JSONParser.restarCoinsTotal(2000);
                                potencia3Presionado = true;
                            }
                        },
                        27));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (JSONParser.leerConfiguracion("Potencia 1") && JSONParser.leerConfiguracion("Potencia 2")
                    && JSONParser.leerConfiguracion("Potencia 3")) {
                botoncilloPotencia.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        200, 350,
                        "20000",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 20000) {
                                actualizarBonus("Potencia 4");
                                JSONParser.restarCoinsTotal(20000);
                                potencia4Presionado = true;
                            }
                        },
                        28));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        botoncilloRatio.add(new ButtonesPeques(
                Assets.botonPequeGris,
                Assets.botonPequeRojo,
                400,200,
                "20",
                () ->{
                    if(JSONParser.getCoinsTotal()>=20){
                        actualizarBonus("Ratio 1");
                        JSONParser.restarCoinsTotal(20);
                        ratio1Presionado = true;
                    }
                },
                30
        ));
        try {
            if (JSONParser.leerConfiguracion("Ratio 1")) {
                botoncilloRatio.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        400, 250,
                        "200",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 200) {
                                actualizarBonus("Ratio 2");
                                JSONParser.restarCoinsTotal(200);
                                ratio2Presionado = true;
                            }
                        },
                        31));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (JSONParser.leerConfiguracion("Ratio 1") && JSONParser.leerConfiguracion("Ratio 2")) {
                botoncilloRatio.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        400, 300,
                        "2000",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 2000) {
                                actualizarBonus("Ratio 3");
                                JSONParser.restarCoinsTotal(2000);
                                ratio3Presionado = true;
                            }
                        },
                        32));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (JSONParser.leerConfiguracion("Ratio 1") && JSONParser.leerConfiguracion("Ratio 2") &&
            JSONParser.leerConfiguracion("Ratio 3")) {
                botoncilloRatio.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        400, 350,
                        "20000",
                        () -> {
                            if (JSONParser.getCoinsTotal() >= 20000) {
                                actualizarBonus("Ratio 4");
                                JSONParser.restarCoinsTotal(20000);
                                ratio4Presionado = true;
                            }
                        },
                        32));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        botoncilloRecarga.add(new ButtonesPeques(
                Assets.botonPequeGris,
                Assets.botonPequeRojo,
                600,200,
                "20",
                () ->{
                    if(JSONParser.getCoinsTotal() >= 20){
                        actualizarBonus("Recarga 1");
                        JSONParser.restarCoinsTotal(20);
                        recarga1Presionada = true;
                    }
                },
                40
        ));
        try{
            if(JSONParser.leerConfiguracion("Recarga 1")){
                botoncilloRecarga.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        600,250,
                        "200",
                        () ->{
                            if(JSONParser.getCoinsTotal() >= 200){
                                actualizarBonus("Recarga 2");
                                JSONParser.restarCoinsTotal(200);
                                recarga2Presionada = true;
                            }
                        },
                        41
                ));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        try{
            if(JSONParser.leerConfiguracion("Recarga 1") && JSONParser.leerConfiguracion("Recarga 2")){
                botoncilloRecarga.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        600,300,
                        "2000",
                        () ->{
                            if(JSONParser.getCoinsTotal() >= 2000){
                                actualizarBonus("Recarga 3");
                                JSONParser.restarCoinsTotal(2000);
                                recarga3Presionada = true;
                            }
                        },
                        42
                ));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        try{
            if(JSONParser.leerConfiguracion("Recarga 1") && JSONParser.leerConfiguracion("Recarga 2") &&
            JSONParser.leerConfiguracion("Recarga 3")){
                botoncilloRecarga.add(new ButtonesPeques(
                        Assets.botonPequeGris,
                        Assets.botonPequeRojo,
                        600,350,
                        "20000",
                        () ->{
                            if(JSONParser.getCoinsTotal() >= 20000){
                                actualizarBonus("Recarga 4");
                                JSONParser.restarCoinsTotal(20000);
                                recarga4Presionada = true;
                            }
                        },
                        42
                ));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        try {
            potencia1Activada = JSONParser.leerConfiguracion("Potencia 1");
            potencia2Activada = JSONParser.leerConfiguracion("Potencia 2");
            potencia3Activada = JSONParser.leerConfiguracion("Potencia 3");
            potencia4Activada = JSONParser.leerConfiguracion("Potencia 4");
            ratio1Activado = JSONParser.leerConfiguracion("Ratio 1");
            ratio2Activado = JSONParser.leerConfiguracion("Ratio 2");
            ratio3Activado = JSONParser.leerConfiguracion("Ratio 3");
            ratio4Activado = JSONParser.leerConfiguracion("Ratio 4");
            recarga1Activada = JSONParser.leerConfiguracion("Recarga 1");
            recarga2Activada = JSONParser.leerConfiguracion("Recarga 2");
            recarga3Activada = JSONParser.leerConfiguracion("Recarga 3");
            recarga4Activada = JSONParser.leerConfiguracion("Recarga 4");

            potenciaActivada.put("Potencia 1", potencia1Activada);
            potenciaActivada.put("Potencia 2", potencia2Activada);
            potenciaActivada.put("Potencia 3", potencia3Activada);
            potenciaActivada.put("Potencia 4", potencia4Activada);
            ratioActivado.put("Ratio 1", ratio1Activado);
            ratioActivado.put("Ratio 2", ratio2Activado);
            ratioActivado.put("Ratio 3", ratio3Activado);
            ratioActivado.put("Ratio 4", ratio4Activado);
            recargaActivada.put("Recarga 1",recarga1Activada);
            recargaActivada.put("Recarga 2",recarga2Activada);
            recargaActivada.put("Recarga 3",recarga3Activada);
            recargaActivada.put("Recarga 4",recarga4Activada);

            if (potencia1Activada) {
                botoncilloPotencia.removeIf(b -> b.getTexto().equals("20"));
            }
            if (potencia2Activada) {
                botoncilloPotencia.removeIf(b -> b.getTexto().equals("200"));
            }
            if (potencia3Activada) {
                botoncilloPotencia.removeIf(b -> b.getTexto().equals("2000"));
            }
            if (potencia4Activada) {
                botoncilloPotencia.removeIf(b -> b.getTexto().equals("20000"));
            }
            if(ratio1Activado){
                botoncilloRatio.removeIf(b -> b.getTexto().equals("20"));
            }
            if(ratio2Activado){
                botoncilloRatio.removeIf(b -> b.getTexto().equals("200"));
            }
            if(ratio3Activado){
                botoncilloRatio.removeIf(b -> b.getTexto().equals("2000"));
            }
            if(ratio4Activado){
                botoncilloRatio.removeIf(b -> b.getTexto().equals("20000"));
            }
            if(recarga1Activada){
                botoncilloRecarga.removeIf(b -> b.getTexto().equals("20"));
            }
            if(recarga2Activada){
                botoncilloRecarga.removeIf(b -> b.getTexto().equals("200"));
            }
            if(recarga3Activada){
                botoncilloRecarga.removeIf(b -> b.getTexto().equals("2000"));
            }
            if(recarga4Activada){
                botoncilloRecarga.removeIf(b -> b.getTexto().equals("20000"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void actualizarBonus(String boton) {
        switch (boton) {
            case "Potencia 1":
                if (!potencia1Presionado && !potencia1Activada) {
                    Laser.setPotencia1Bonus(10);
                    potenciaActivada.put(boton, true);
                    potencia1Activada = true;
                    guardarConfiguracion();
                    botoncilloPotencia.removeIf(b -> b.getTexto().equals("20"));
                }
                break;
            case "Potencia 2":
                if (!potencia2Presionado && !potencia2Activada) {
                    Laser.setPotencia2Bonus(10);
                    potenciaActivada.put(boton, true);
                    potencia2Activada = true;
                    guardarConfiguracion();
                    botoncilloPotencia.removeIf(b -> b.getTexto().equals("200"));
                }
                break;
            case "Potencia 3":
                if (!potencia3Presionado && !potencia3Activada) {
                    Laser.setPotencia3Bonus(20);
                    potenciaActivada.put(boton, true);
                    potencia3Activada = true;
                    guardarConfiguracion();
                    botoncilloPotencia.removeIf(b -> b.getTexto().equals("2000"));
                }
                break;
            case "Potencia 4":
                if (!potencia4Presionado && !potencia4Activada) {
                    Laser.setPotencia4Bonus(45);
                    potenciaActivada.put(boton, true);
                    potencia4Activada = true;
                    guardarConfiguracion();
                    botoncilloPotencia.removeIf(b -> b.getTexto().equals("20000"));
                }
                break;
            case "Ratio 1":
                if(!ratio1Presionado && !ratio1Activado){
                    Player.setRatio1Bonus(500);
                    ratioActivado.put(boton, true);
                    ratio1Activado = true;
                    guardarConfiguracion();
                    botoncilloRatio.removeIf(b -> b.getTexto().equals("20"));
                }
                break;
            case "Ratio 2":
                if(!ratio2Presionado && !ratio2Activado){
                    Player.setRatio2Bonus(250);
                    ratioActivado.put(boton, true);
                    ratio2Activado = true;
                    guardarConfiguracion();
                    botoncilloRatio.removeIf(b -> b.getTexto().equals("200"));
                }
                break;
            case "Ratio 3":
                if(!ratio3Presionado && !ratio3Activado){
                    Player.setRatio2Bonus(150);
                    ratioActivado.put(boton, true);
                    ratio3Activado = true;
                    guardarConfiguracion();
                    botoncilloRatio.removeIf(b -> b.getTexto().equals("2000"));
                }
                break;
            case "Ratio 4":
                if(!ratio4Presionado && !ratio4Activado){
                    Player.setRatio2Bonus(50);
                    ratioActivado.put(boton, true);
                    ratio4Activado = true;
                    guardarConfiguracion();
                    botoncilloRatio.removeIf(b -> b.getTexto().equals("20000"));
                }
                break;
            case "Recarga 1":
                if(!recarga1Presionada && !recarga1Activada){
                    Player.setDelay1bonus(450);
                    recargaActivada.put(boton, true);
                    recarga1Activada = true;
                    guardarConfiguracion();
                    botoncilloRecarga.removeIf(b -> b.getTexto().equals("20"));
                }
                break;
            case "Recarga 2":
                if(!recarga2Presionada && !recarga2Activada){
                    Player.setDelay2bonus(250);
                    recargaActivada.put(boton, true);
                    recarga2Activada = true;
                    guardarConfiguracion();
                    botoncilloRecarga.removeIf(b -> b.getTexto().equals("200"));
                }
                break;
            case "Recarga 3":
                if(!recarga3Presionada && !recarga3Activada){
                    Player.setDelay3bonus(200);
                    recargaActivada.put(boton, true);
                    recarga3Activada = true;
                    guardarConfiguracion();
                    botoncilloRecarga.removeIf(b -> b.getTexto().equals("2000"));
                }
                break;
            case "Recarga 4":
                if(!recarga4Presionada && !recarga4Activada){
                    Player.setDelay4bonus(95);
                    recargaActivada.put(boton, true);
                    recarga4Activada = true;
                    guardarConfiguracion();
                    botoncilloRecarga.removeIf(b -> b.getTexto().equals("20000"));
                }
                break;
            default:
                break;
        }
    }

    private void guardarConfiguracion() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Potencia 1", potenciaActivada.get("Potencia 1"));
            obj.put("Potencia 2", potenciaActivada.get("Potencia 2"));
            obj.put("Potencia 3", potenciaActivada.get("Potencia 3"));
            obj.put("Potencia 4", potenciaActivada.get("Potencia 4"));
            obj.put("Ratio 1", ratioActivado.get("Ratio 1"));
            obj.put("Ratio 2", ratioActivado.get("Ratio 2"));
            obj.put("Ratio 3", ratioActivado.get("Ratio 3"));
            obj.put("Ratio 4", ratioActivado.get("Ratio 4"));
            obj.put("Recarga 1", recargaActivada.get("Recarga 1"));
            obj.put("Recarga 2", recargaActivada.get("Recarga 2"));
            obj.put("Recarga 3", recargaActivada.get("Recarga 3"));
            obj.put("Recarga 4", recargaActivada.get("Recarga 4"));
            JSONParser.escribirConfiguracion(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updatear(float dt) {
        ArrayList<ButtonesPeques>botoncilloCopy = (ArrayList<ButtonesPeques>) botoncilloPotencia.clone();
        ArrayList<ButtonesPeques>botoncilloRatioCopy = (ArrayList<ButtonesPeques>) botoncilloRatio.clone();
        ArrayList<ButtonesPeques>botoncilloRecargaCopy= (ArrayList<ButtonesPeques>) botoncilloRecarga.clone();
        for (Buttones b : boton) {
            b.actualizar();
        }
        for (ButtonesPeques b : botoncilloCopy){
            try {
                b.actualizar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(ButtonesPeques b: botoncilloRatioCopy){
            try{
                b.actualizar();
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        for(ButtonesPeques b: botoncilloRecargaCopy){
            try{
                b.actualizar();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

    }


    @Override
    public void dibujar(Graphics g) {
        for(Buttones b: boton){
            b.dibujar(g);
        }
        for(ButtonesPeques b: botoncilloPotencia){
            b.dibujar(g);
        }
        for(ButtonesPeques b: botoncilloRatio){
            b.dibujar(g);
        }
        for(ButtonesPeques b: botoncilloRecarga){
            b.dibujar(g);
        }
        try {
            Text.drawText(g,"= "+ JSONParser.getCoinsTotal(),new Vector2D(535,95),false,Color.yellow,Assets.fuente);
            Text.drawText(g,"POWER 1",new Vector2D(220,195),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"POWER 2",new Vector2D(220,245),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"POWER 3",new Vector2D(220,295),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"POWER 4",new Vector2D(220,345),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RATIO 1",new Vector2D(420,195),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RATIO 2",new Vector2D(420,245),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RATIO 3",new Vector2D(420,295),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RATIO 4",new Vector2D(420,345),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RELOAD 1",new Vector2D(615,195),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RELOAD 2",new Vector2D(615,245),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RELOAD 3",new Vector2D(615,295),false,Color.yellow,Assets.fuentetiny);
            Text.drawText(g,"RELOAD 4",new Vector2D(615,345),false,Color.yellow,Assets.fuentetiny);
            g.drawImage(Assets.pilamonedas,450,50,null);
            if(potencia1Activada){
                Text.drawText(g,"SOLD",new Vector2D(230,220),false,Color.red,Assets.fuentetiny);
            }
            if(potencia2Activada){
                Text.drawText(g,"SOLD",new Vector2D(230,270),false,Color.red,Assets.fuentetiny);
            }
            if(potencia3Activada){
                Text.drawText(g,"SOLD",new Vector2D(230,320),false,Color.red,Assets.fuentetiny);
            }
            if(potencia4Activada){
                Text.drawText(g,"SOLD",new Vector2D(230,370),false,Color.red,Assets.fuentetiny);
            }
            if(recarga1Activada){
                Text.drawText(g,"SOLD",new Vector2D(630,220),false,Color.red,Assets.fuentetiny);
            }
            if(recarga2Activada){
                Text.drawText(g,"SOLD",new Vector2D(630,270),false,Color.red,Assets.fuentetiny);
            }
            if(recarga3Activada){
                Text.drawText(g,"SOLD",new Vector2D(630,320),false,Color.red,Assets.fuentetiny);
            }
            if(recarga4Activada){
                Text.drawText(g,"SOLD",new Vector2D(630,370),false,Color.red,Assets.fuentetiny);
            }
            if(ratio1Activado){
                Text.drawText(g,"SOLD",new Vector2D(430,220),false,Color.red,Assets.fuentetiny);
            }
            if(ratio2Activado){
                Text.drawText(g,"SOLD",new Vector2D(430,270),false,Color.red,Assets.fuentetiny);
            }
            if(ratio3Activado){
                Text.drawText(g,"SOLD",new Vector2D(430,320),false,Color.red,Assets.fuentetiny);
            }
            if(ratio4Activado){
                Text.drawText(g,"SOLD",new Vector2D(430,370),false,Color.red,Assets.fuentetiny);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
