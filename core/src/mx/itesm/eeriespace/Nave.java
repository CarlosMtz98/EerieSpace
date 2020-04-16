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
        velocidad = 150;
        puedeDisparar = true;
    }

    public void mover(Touchpad pad, float delta){
        if(estadoMovimiento == EstadoMovimiento.MOVIMIENTO) {

            float padX = pad.getKnobPercentX();
            float padY = pad.getKnobPercentY();

            float dx = velocidad * padX * delta;
            float dy = velocidad * padY * delta;

            double angulo = Math.toDegrees(Math.atan((double) padY / (double) padX));
            angulo += (padX > 0)? -90: 90;

            sprite.setRotation((float)angulo);

            sprite.setX(sprite.getX() + dx);
            sprite.setY(sprite.getY() + dy);
        }
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setEstado(EstadoMovimiento movimiento) {
        this.estadoMovimiento = movimiento;
    }
}
