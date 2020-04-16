package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Marcador {
    private int puntos;
    private float x;
    private float y;

    private Text text;

    public Marcador(float x, float y) {
        this.x = x;
        this.y = y;
        puntos = 0;

        text = new Text("BasierSquare.fnt");
    }

    public void resetearPuntos() {
        puntos = 0;
    }

    public void incrementarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void render(SpriteBatch batch) {
        String scoreLetrero = "Score: " + puntos;
        text.render(batch, scoreLetrero, x, y);
    }
}
