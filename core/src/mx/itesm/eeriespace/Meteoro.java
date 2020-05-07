package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Meteoro extends Objeto {
    private int daño;
    private float angulo;
    public int velocidad = 150;

    //Creación aleatoria de meteoros
    public Meteoro(Texture textura, float x, int daño) {
        super(textura, x, Pantalla.ALTO + 150);
        this.daño = daño;
        this.angulo = x > Pantalla.ANCHO/2? (float)(Math.random()*(-0.8f)) : (float)Math.random()*0.8f;
    }

    public int getDaño() {
        return daño;
    }

    public void update(float delta) {
        mover(delta);
    }

    public void mover(float delta) {
        sprite.setPosition(sprite.getX() + delta * angulo * velocidad, sprite.getY() - velocidad * delta);
    }

}
