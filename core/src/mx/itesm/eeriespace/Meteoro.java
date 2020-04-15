package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public abstract class Meteoro extends Objeto {
    protected int daño;
    protected int tamaño;
    protected int velocidad;

    public Meteoro(Texture textura, float x, float y) {
        super(textura, x, y);
    }

}
