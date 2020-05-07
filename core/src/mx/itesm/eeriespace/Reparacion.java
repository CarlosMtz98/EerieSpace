package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Reparacion extends Item {

    public Reparacion(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    void darBonus(Nave nave) {
        int vida = nave.getVida();
        nave.setVida(vida+20);
    }
}
