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
            datos.setScore(obj.getInt("score"));
            datos.setDate(obj.getString("date"));
            listaDatos.add(datos);
        }
        return listaDatos;
    }

    public static void escribirArchivo(ArrayList<DatoPuntuacion> listaDatos) throws IOException {
        File outputFile = new File(Constantes.SCORE_PATH);

        outputFile.getParentFile().mkdir();
        outputFile.createNewFile();

        JSONArray jList = new JSONArray();

        for(DatoPuntuacion dato : listaDatos){
            JSONObject obj = new JSONObject();
            obj.put("score",dato.getScore());
            obj.put("date",dato.getDate());
            jList.put(obj);
        }
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile.toURI()));
        jList.write(writer);
        writer.close();
    }
}
