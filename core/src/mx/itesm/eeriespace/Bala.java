package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala extends Objeto {
    private float velocidad = 10;

    public Bala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover() {
        sprite.setY(sprite.getY() + velocidad);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
