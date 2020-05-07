package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Escudo extends Item {

    public Escudo(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    void darBonus(Nave nave) {
        nave.setEscudo(true);
    }
}
