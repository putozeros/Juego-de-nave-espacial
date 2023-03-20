package graficos;
import javax.sound.sampled.*;
import java.io.File;

public class Sonido {
    private Clip clip;
    private FloatControl volumen;

    public Sonido(Clip clip){
        this.clip = clip;
        volumen = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public void play(){
        if(clip != null){
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void stop(){
        if(clip != null){
            clip.stop();
        }
    }
    public void loop(){
        if(clip != null){
            clip.stop();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void cambiarVolumen (float value){
        volumen.setValue(value);
    }
}
