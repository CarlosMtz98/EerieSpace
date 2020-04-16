package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private float velocidad;
    private boolean puedeDisparar;
    private EstadoMovimiento estadoMovimiento;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        estadoMovimiento = EstadoMovimiento.QUIETO;
    }

    public void mover(Touchpad pad){
        if (EstadoMovimiento.MOVIMIENTO == estadoMovimiento) {
            float dx = velocidad * pad.getKnobPercentX();
            float dy = velocidad * pad.getKnobPercentY();
            sprite.setX(sprite.getX() + dx);
            sprite.setY(sprite.getY() + dy);
            velocidad = 1;
            puedeDisparar = true;
        }
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setEstado(EstadoMovimiento movimiento) {
        this.estadoMovimiento = movimiento;
    }
}
