package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;


public class AdministradorPuntuacion {
    private ArrayList<Integer> puntuaciones;
    Preferences prefs = Gdx.app.getPreferences("My Preferences");

    public AdministradorPuntuacion() {
        this.puntuaciones = new ArrayList<>();
        readFile();
    }

    public void readFile() {
        prefs.getInteger("highscore", 0);
    }

    public int getPuntuacionAlta() {
        return prefs.getInteger("highscore", 0);
    }

    public void añadirPuntuacion(int puntuacion)
    {
        if (puntuacion > prefs.getInteger("highscore", 0)) {
            prefs.putInteger("highscore", puntuacion);
        }
    }

    public void reiniciarPuntuacion()
    {
        prefs.putInteger("highscore", 0);
    }
}
