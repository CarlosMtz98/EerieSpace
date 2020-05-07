package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item extends Objeto{

    public Item(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    abstract void darBonus(Nave nave);

}
