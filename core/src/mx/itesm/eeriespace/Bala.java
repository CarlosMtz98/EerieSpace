package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Bala extends Objeto {
    private float velocidad = 360;

    public Bala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float dt) {
        float dx = velocidad * dt;
        float dy = velocidad * dt;
        sprite.setPosition(dx, dy);
    }
}
