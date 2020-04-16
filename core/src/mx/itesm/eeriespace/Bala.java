package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala extends Objeto {
    private float velocidadX = 400;
    private float velocidadY = 400;

    public Bala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float delta) {
        sprite.setPosition(sprite.getX() + delta * velocidadX, sprite.getY() + delta * velocidadY);
    }

    public void setDireccion(Nave nave){
        sprite.setRotation(nave.sprite.getRotation());
        double x = Math.cos(Math.toRadians(sprite.getRotation() + 90));
        double y = Math.sin(Math.toRadians(sprite.getRotation() + 90));
        velocidadX *= x;
        velocidadY *= y;
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
