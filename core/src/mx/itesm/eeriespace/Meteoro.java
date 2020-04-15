package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public abstract class Meteoro extends Objeto {
    private int daño;
    private float angulo;
    public int velocidad = 15;

    //Creación aleatoria de meteoros
    public Meteoro(Texture textura, float x, float y, int daño) {
        super(textura, x, y);
        this.daño = daño;
        this.angulo = (float) Math.random() - .5f;
    }

    public int getDaño() {
        return daño;
    }

    public void mover(float delta) {
        sprite.setPosition(sprite.getX() + angulo * delta * velocidad, velocidad * delta);
    }
}
