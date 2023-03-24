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
    public static int getCoinsTotal() throws FileNotFoundException{
        int coinsTotal = 0;
        ArrayList<DatoPuntuacion> listaDatos = leerArchivo();
        for(DatoPuntuacion dato:listaDatos){
            coinsTotal+=dato.getCoins();
        }
        return coinsTotal;
    }
    public void mostrarMonedasTotales() throws FileNotFoundException{
        int coinsTotal = JSONParser.getCoinsTotal();
        System.out.println("Monedas totales = "+coinsTotal);
    }
}
