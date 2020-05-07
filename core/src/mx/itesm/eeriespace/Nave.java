package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private EstadoMovimiento estadoMovimiento;
    private int vida;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        estadoMovimiento = EstadoMovimiento.QUIETO;
        vida = 100;
    }

    public void mover(Touchpad pad, float delta){
        if(estadoMovimiento == EstadoMovimiento.MOVIMIENTO) {

            float padX = pad.getKnobPercentX();
            float padY = pad.getKnobPercentY();

            float velocidad = 300;
            float dx = velocidad * padX * delta;
            float dy = velocidad * padY * delta;

            float anguloJoyStick = (float)Math.toDegrees(Math.atan((double) padY / (double) padX));
            anguloJoyStick += (padX > 0)? -90: 90; //cambiar a los grados en formato de libgdx
            float anguloNave = sprite.getRotation();
            float diffAngulo = anguloNave - anguloJoyStick;
            if(Math.abs(diffAngulo) > 5){
                diffAngulo = 5;
            }

            // para que siempre rote la distancia más corta
            if((anguloNave > anguloJoyStick && !(anguloJoyStick < -90 && anguloNave > 90))
                    || (anguloNave < -90 && anguloJoyStick > 90)){
                diffAngulo *= -1;
            }

            // que no se pase del angulo y no se quede dando vueltas eternamente
            if(anguloNave > 180) anguloNave -= 360f;
            if(anguloNave < -180) anguloNave+= 360f;

            sprite.setRotation(anguloNave + diffAngulo);

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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void disminuirVida(int daño) {
        vida -= daño;
        this.sprite.setColor(1f, (float)this.vida / 100, (float)this.vida / 100,1);
    }

    public int getDaño() {
        return 15;
    }
}
