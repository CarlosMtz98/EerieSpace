package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private float velocidad;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(Touchpad pad){
        sprite.setX(sprite.getX() + velocidad*pad.getKnobPercentX());
        sprite.setY(sprite.getY() + velocidad*pad.getKnobPercentX());
    }


}
