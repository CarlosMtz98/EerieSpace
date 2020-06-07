package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class VelocidadDeAtaque extends Item {

    public VelocidadDeAtaque(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    void darBonus(Nave nave) {
        float velocidad = nave.getRecargaDisparo();
        velocidad *= 0.9;
        nave.setRecargaDisparo(velocidad);
    }
}
