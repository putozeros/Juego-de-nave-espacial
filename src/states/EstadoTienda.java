package states;

import IO.JSONParser;
import UI.Buttones;
import UI.ButtonesPeques;
import gameObjects.Laser;
import graficos.Assets;
import graficos.Text;
import math.Vector2D;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EstadoTienda extends State{
    private boolean objetoComprado = false;
    private boolean potencia1Presionado = false;
    private boolean potencia2Presionado = false;
    private boolean potencia3Presionado = false;
    private boolean potencia4Presionado = false;
    private ArrayList<Buttones> boton;
    private ArrayList<ButtonesPeques>botoncillo;
    private boolean potencia1Activada = false;
    private boolean potencia2Activada = false;
    private boolean potencia3Activada = false;
    private boolean potencia4Activada = false;
    private HashMap<String, Boolean>potenciaActivada;

    public EstadoTienda(){
        boton = new ArrayList<>();
        botoncillo = new ArrayList<>();
        potenciaActivada = new HashMap<>();
        potenciaActivada.put("Potencia 1", false);
        potenciaActivada.put("Potencia 2", false);

        boton.add(new Buttones(
                Assets.botonGris,
                Assets.botonRojo,
                100,
                550,
                "VOLVER",
                () -> State.cambiarEstado(new EstadoMenu()),
                10));

        botoncillo.add(new ButtonesPeques(
                Assets.botonPequeGris,
                Assets.botonPequeRojo,
                200,200,
                "Potencia 1",
                    () -> {
                    if(JSONParser.getCoinsTotal()>=20) {
                        actualizarBonus("Potencia 1");
                        JSONParser.restarCoinsTotal(20);
                        potencia1Presionado = true;
                    }
                    },
                25));
        try {
            if(JSONParser.leerConfiguracion("Potencia 1")){
                botoncillo.add(new ButtonesPeques(
                    Assets.botonPequeGris,
                    Assets.botonPequeRojo,
                    200,250,
                    "Potencia 2",
                    () -> {
                        if (JSONParser.getCoinsTotal() >= 200) {
                            actualizarBonus("Potencia 2");
                            JSONParser.restarCoinsTotal(200);
                            potencia2Presionado = true;
                        }
                        },
                    26));}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if(JSONParser.leerConfiguracion("Potencia 1") && JSONParser.leerConfiguracion("Potencia 2")){
            botoncillo.add(new ButtonesPeques(
                    Assets.botonPequeGris,
                    Assets.botonPequeRojo,
                    200,300,
                    "Potencia 3",
                    () ->{
                        if(JSONParser.getCoinsTotal()>=2000) {
                            actualizarBonus("Potencia 3");
                            JSONParser.restarCoinsTotal(2000);
                            potencia3Presionado = true;
                        }
                    },
                    27));}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if(JSONParser.leerConfiguracion("Potencia 1") && JSONParser.leerConfiguracion("Potencia 2")
                    && JSONParser.leerConfiguracion("Potencia 3")){
            botoncillo.add(new ButtonesPeques(
                    Assets.botonPequeGris,
                    Assets.botonPequeRojo,
                    200,350,
                    "Potencia 4",
                    () ->{
                        if(JSONParser.getCoinsTotal()>=20000) {
                            actualizarBonus("Potencia 4");
                            JSONParser.restarCoinsTotal(20000);
                            potencia4Presionado = true;
                        }
                    },
                    28));}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            potencia1Activada = JSONParser.leerConfiguracion("Potencia 1");
            potencia2Activada = JSONParser.leerConfiguracion("Potencia 2");
            potencia3Activada = JSONParser.leerConfiguracion("Potencia 3");
            potencia4Activada = JSONParser.leerConfiguracion("Potencia 4");

            potenciaActivada.put("Potencia 1",potencia1Activada);
            potenciaActivada.put("Potencia 2",potencia2Activada);
            potenciaActivada.put("Potencia 3",potencia3Activada);
            potenciaActivada.put("Potencia 4",potencia4Activada);

            if(potencia1Activada){
                botoncillo.removeIf(b -> b.getTexto().equals("Potencia 1"));
            }
            if(potencia2Activada){
                botoncillo.removeIf(b -> b.getTexto().equals("Potencia 2"));
            }
            if(potencia3Activada){
                botoncillo.removeIf(b -> b.getTexto().equals("Potencia 3"));
            }
            if(potencia4Activada){
                botoncillo.removeIf(b -> b.getTexto().equals("Potencia 4"));
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void actualizarBonus(String boton) {
        switch (boton) {
            case "Potencia 1":
                if (!potencia1Presionado && !potencia1Activada) {
                    Laser.setPotencia1Bonus(10);
                    potenciaActivada.put(boton,true);
                    potencia1Activada=true;
                    guardarConfiguracion();
                    botoncillo.removeIf(b -> b.getTexto().equals(boton));
                }
                break;
            case "Potencia 2":
                if (!potencia2Presionado && !potencia2Activada) {
                    Laser.setPotencia2Bonus(10);
                    potenciaActivada.put(boton,true);
                    potencia2Activada=true;
                    guardarConfiguracion();
                    botoncillo.removeIf(b -> b.getTexto().equals(boton));
                }
                break;
            case "Potencia 3":
                if (!potencia3Presionado && !potencia3Activada) {
                    Laser.setPotencia3Bonus(20);
                    potenciaActivada.put(boton,true);
                    potencia3Activada=true;
                    guardarConfiguracion();
                    botoncillo.removeIf(b -> b.getTexto().equals(boton));
                }
                break;
            case "Potencia 4":
                if (!potencia4Presionado && !potencia4Activada) {
                    Laser.setPotencia4Bonus(45);
                    potenciaActivada.put(boton,true);
                    potencia4Activada=true;
                    guardarConfiguracion();
                    botoncillo.removeIf(b -> b.getTexto().equals(boton));
                }
                break;
            default:
                break;
        }
    }

    private void guardarConfiguracion() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Potencia 1",potenciaActivada.get("Potencia 1"));
            obj.put("Potencia 2",potenciaActivada.get("Potencia 2"));
            obj.put("Potencia 3",potenciaActivada.get("Potencia 3"));
            obj.put("Potencia 4",potenciaActivada.get("Potencia 4"));
            JSONParser.escribirConfiguracion(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatear(float dt) {
        ArrayList<ButtonesPeques>botoncilloCopy = (ArrayList<ButtonesPeques>) botoncillo.clone();
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
    }


    @Override
    public void dibujar(Graphics g) {
        for(Buttones b: boton){
            b.dibujar(g);
        }
        for(ButtonesPeques b: botoncillo){
            b.dibujar(g);
        }
        try {
            Text.drawText(g,"= "+ JSONParser.getCoinsTotal(),new Vector2D(535,95),false,Color.yellow,Assets.fuente);
            g.drawImage(Assets.pilamonedas,450,50,null);
            if(potencia1Activada){
                Text.drawText(g,"SOLD",new Vector2D(200,200),false,Color.red,Assets.fuentetiny);
            }
            if(potencia2Activada){
                Text.drawText(g,"SOLD",new Vector2D(200,250),false,Color.red,Assets.fuentetiny);
            }
            if(potencia3Activada){
                Text.drawText(g,"SOLD",new Vector2D(200,300),false,Color.red,Assets.fuentetiny);
            }
            if(potencia4Activada){
                Text.drawText(g,"SOLD",new Vector2D(200,350),false,Color.red,Assets.fuentetiny);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
