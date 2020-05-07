package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.Collections;


public class AdministradorPuntuacion {
    private ArrayList<Integer> puntuaciones;
    private FileHandle archivoPuntuaciones = Gdx.files.local("scores.txt");

    public AdministradorPuntuacion() {
        this.puntuaciones = new ArrayList<>();
        readFile();
    }

    public void readFile() {
        String texto = archivoPuntuaciones.readString();
        String arregloPalabras[] = texto.split("\\r?\\n");
        for(String palabra : arregloPalabras) {
            puntuaciones.add(Integer.parseInt(palabra));
            Gdx.app.log("Score", palabra);
        }
    }

    public int getPuntuacionAlta() {
        readFile();
        if (puntuaciones.size() > 0) {
            return Collections.max(puntuaciones);
        }
        else
            return -1;
    }

    public void añadirPuntuacion(int puntuacion)
    {
        archivoPuntuaciones.writeString("\n"+ Integer.toString(puntuacion), true);
    }

    public void reiniciarPuntuacion()
    {
        archivoPuntuaciones.writeString("", false);
    }
}
