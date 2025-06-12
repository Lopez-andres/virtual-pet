import javax.sound.sampled.*;
import java.io.File;

//El objetivo de esta clase es reproducir la musica

public class ReproductorMusica {
    private Clip clip; //libreria de audio

    //constructor de la clase que carga la ruta del archivo musical
    public ReproductorMusica(String ruta) {
        try {

            //lee el archivo de audio
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(ruta));

            clip = AudioSystem.getClip(); //obtenemos una instancia de la clase Clip
            clip.open(audioInput); //abre el audio
            clip.loop(Clip.LOOP_CONTINUOUSLY); // empieza a sonar en bucle continuo
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //usado para el boton de pausar y renaudar musica
    public void toggleMusica() {
        if (clip != null) {
            if (clip.isRunning()) { //si la musica esta sonando
                clip.stop(); // pausa
            } else {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // reanuda y sigue en bucle continuo
                clip.start();
            }
        }
    }

    //usado para el boton de regresar al menu principal, para pausar la musica al salir del juego
    public void pausarMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

