package IO;

import gameObjects.Constantes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONParser {

    public static ArrayList<DatoPuntuacion> leerArchivo() throws FileNotFoundException {
        ArrayList<DatoPuntuacion> listaDatos = new ArrayList<DatoPuntuacion>();

        File file = new File(Constantes.SCORE_PATH);
        if(!file.exists() || file.length()==0){
            return listaDatos;
        }
        JSONTokener parser = new JSONTokener(new FileInputStream(file));
        JSONArray jsonList = new JSONArray(parser);

        for(int i = 0;i<jsonList.length();i++){
            JSONObject obj = (JSONObject) jsonList.get(i);
            DatoPuntuacion datos = new DatoPuntuacion();
            if(obj.has("score")){
                datos.setScore(obj.getInt("score"));
            }
            if(obj.has("date")){
                datos.setDate(obj.getString("date"));
            }
            if(obj.has("coins")){
                datos.setCoins(obj.getInt("coins"));
            }

            listaDatos.add(datos);
        }
        return listaDatos;
    }

    public static boolean leerConfiguracion(String clave) throws FileNotFoundException {
        File file = new File(Constantes.CONFIG_PATH);
        if(!file.exists() || file.length()==0){
            return false; // El archivo de configuración no existe o está vacío
        }
        JSONTokener parser = new JSONTokener(new FileInputStream(file));
        JSONObject json = new JSONObject(parser);
        if(!json.has(clave)){
            return false;
        }
        return json.getBoolean(clave);
    }
    public static void escribirConfiguracion(JSONObject obj) throws IOException {
        File outputFile = new File(Constantes.CONFIG_PATH);
        outputFile.getParentFile().mkdir();
        outputFile.createNewFile();


        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile.toURI()));
        obj.write(writer);
        writer.close();
    }
    public static void escribirArchivo(ArrayList<DatoPuntuacion> listaDatos, int coins) throws IOException {
        File outputFile = new File(Constantes.SCORE_PATH);

        outputFile.getParentFile().mkdir();
        outputFile.createNewFile();

        JSONArray jList = new JSONArray();

        for(DatoPuntuacion dato : listaDatos){
            JSONObject obj = new JSONObject();
            obj.put("score",dato.getScore());
            obj.put("date",dato.getDate());
            obj.put("coins",dato.getCoins());
            jList.put(obj);
        }
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile.toURI()));
        jList.write(writer);
        writer.close();
    }
    public static void restarCoinsTotal(int monedas) throws IOException {
        ArrayList<DatoPuntuacion>listaDatos = leerArchivo();
        int coinsTotal = 0;
        int monedasRestantes = monedas;
        for(DatoPuntuacion dato : listaDatos){
            int monedasDato = dato.getCoins();
            if(monedasDato >= monedasRestantes){
                dato.setCoins(monedasDato-monedasRestantes);
                monedasRestantes =0;
                break;
            }else{
                dato.setCoins(0);
                monedasRestantes -= monedasDato;
            }
        }
        escribirArchivo(listaDatos,getCoinsTotal()-monedas+monedasRestantes);
    }

    public static int getCoinsTotal() throws FileNotFoundException{
        int coinsTotal = 0;
        ArrayList<DatoPuntuacion> listaDatos = leerArchivo();
        for(DatoPuntuacion dato:listaDatos){
            coinsTotal+=dato.getCoins();
        }
        return coinsTotal;
    }

}
