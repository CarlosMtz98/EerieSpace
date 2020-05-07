package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private EstadoMovimiento estadoMovimiento;
    private boolean tieneEscudo = false;
    private int vida;
    private int daño;
    public boolean puedeDisparar;
    private float recargaDisparo;
    private float tiempoDeRecarga;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        estadoMovimiento = EstadoMovimiento.QUIETO;
        vida = 100;
        daño = 15;
        puedeDisparar = true;
        recargaDisparo = 1f;
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

            System.out.println(anguloNave);

            // Límites de la pantalla
            if(anguloNave < 0 && sprite.getX() + sprite.getWidth()/2 + dx > PantallaEerieSpace.ANCHO ){   // sale por derecha
                sprite.setX(0);
            }else if(anguloNave > 0 && sprite.getX() + sprite.getWidth()/2 + dx < 0){ //sale por la izquierda
                sprite.setX(PantallaEerieSpace.ANCHO);
            }else if(anguloNave > -90 && anguloNave < 90 && (sprite.getY() + sprite.getHeight()/2 + dy > PantallaEerieSpace.ALTO)){ // sale por arriba
                sprite.setY(0);
            }else if(sprite.getY() + sprite.getHeight()/2 + dy < 0){ // sale por abajo
                sprite.setY(0);
            }

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
        if(this.vida > 100) this.vida = 100;
    }

    public void disminuirVida(int daño) {
        vida -= daño;
        this.sprite.setColor(1f, (float)this.vida / 100, (float)this.vida / 100,1);
    }

    public void setEscudo(boolean tieneEscudo){
        this.tieneEscudo = tieneEscudo;
    }

    public boolean getEscudo(){
        return tieneEscudo;
    }

    public void setDaño(int daño){
        this.daño = daño;
    }

    public int getDaño(){
        return daño;
    }

    public void recargarDisparo(float delta){
        tiempoDeRecarga += delta;
        if(tiempoDeRecarga >= recargaDisparo){
            tiempoDeRecarga = 0;
            puedeDisparar = true;
        }
    }

    public void setTiempoDeRecarga(float tiempo){
        tiempoDeRecarga = tiempo;
    }

    public void setRecargaDisparo(float recargaDisparo){
        this.recargaDisparo = recargaDisparo;
    }

    public float getRecargaDisparo(){
        return recargaDisparo;
    }
}
