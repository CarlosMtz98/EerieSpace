package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private float velocidad;
    private boolean puedeDisparar;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        velocidad = 1;
        puedeDisparar = true;
    }

    public void mover(Touchpad pad){
        float padX = pad.getKnobPercentX();
        float padY = pad.getKnobPercentY();
        float dx = velocidad*padX;
        float dy = velocidad*padY;

        double angle = Math.atan((double)padY/(double)padX);
        sprite.rotate((float)angle);
        sprite.setX(sprite.getX() + dx);
        sprite.setY(sprite.getY() + dy);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }


}
