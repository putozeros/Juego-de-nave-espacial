package IO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatoPuntuacion {
    private String date;
    private int score;

    public DatoPuntuacion(int score){
        this.score = score;

        Date hoy = new Date(System.currentTimeMillis());
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        date = formato.format(hoy);

    }

    public DatoPuntuacion(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
