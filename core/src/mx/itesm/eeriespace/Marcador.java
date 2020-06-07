package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Marcador {
    private int puntos;
    private int nivel;
    private float xPuntos;
    private float yPuntos;
    private float xNivel;
    private float yNivel;

    private Text text;

    public Marcador(float xPuntos, float yPuntos, float xNivel, float yNivel) {
        this.xPuntos = xPuntos;
        this.yPuntos = yPuntos;
        this.xNivel = xNivel;
        this.yNivel = yNivel;
        puntos = 0;
        nivel = 1;
        text = new Text();
    }

    public void resetearPuntos() {
        puntos = 0;
    }

    public void incrementarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void incrementarNivel(){ nivel ++; }

    public int getPoints() {
        return puntos;
    }


    public void render(SpriteBatch batch) {
        String scoreLetrero = "Score: " + puntos;
        String nivelLetrero = "Level: " + nivel;
        text.render(batch, scoreLetrero, xPuntos, yPuntos);
        text.render(batch,nivelLetrero, xNivel, yNivel);
    }
}
