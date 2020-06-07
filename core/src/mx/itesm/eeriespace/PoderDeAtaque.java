package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class PoderDeAtaque extends Item {

    public PoderDeAtaque(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    void darBonus(Nave nave) {
        nave.setDaño(nave.getDaño() + 10);
    }
}
