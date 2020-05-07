package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {

    private EstadoMovimiento estadoMovimiento;
    private boolean tieneEscudo = false;
    private int vida;
    private int daño;

    // Pad
    float padX = 0;
    float padY = 0;

    // Disparo
    public boolean puedeDisparar;
    private float recargaDisparo;
    private float tiempoDeRecargaDisparo;

    // Dash
    private final float factorDeCargaDash = 0.1f;   // 0 = sin dash, 1 = delta (muy rápido)
    public static final float desplazamientoDash = 125;
    public boolean dashRecargado;
    private float recargaDash;
    private float tiempoDeRecargaDash;

    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        estadoMovimiento = EstadoMovimiento.QUIETO;
        vida = 100;
        daño = 15;
        puedeDisparar = true;
        recargaDisparo = 1f;
        dashRecargado = true;
        recargaDash = 1f;
    }

    public void mover(Touchpad pad, float delta){
        if(estadoMovimiento == EstadoMovimiento.MOVIMIENTO) {

            padX = pad.getKnobPercentX();
            padY = pad.getKnobPercentY();

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

            // Límites de la pantalla
            if(anguloNave < 0 && sprite.getX() + sprite.getWidth()/2 + dx > PantallaEerieSpace.ANCHO ){   // sale por derecha
                sprite.setX(0);
            }else if(anguloNave > 0 && sprite.getX() + sprite.getWidth()/2 + dx < 0){ //sale por la izquierda
                sprite.setX(PantallaEerieSpace.ANCHO);
            }else if(anguloNave > -90 && anguloNave < 90 && (sprite.getY() + sprite.getHeight()/2 + dy > PantallaEerieSpace.ALTO)){ // sale por arriba
                sprite.setY(0);
            }else if(sprite.getY() + sprite.getHeight()/2 + dy < 0){ // rebota abajo
                sprite.setY(0);
            }
        }
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
        this.sprite.setColor(1f, (float)this.vida / 100, (float)this.vida / 100,1);
    }

    public void disminuirVida(int daño) {
        setVida(vida-=daño);
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
        tiempoDeRecargaDisparo += delta;
        if(tiempoDeRecargaDisparo >= recargaDisparo){
            tiempoDeRecargaDisparo = 0;
            puedeDisparar = true;
        }
    }

    public void setTiempoDeRecargaDisparo(float tiempo){
        tiempoDeRecargaDisparo = tiempo;
    }

    public void setRecargaDisparo(float recargaDisparo){
        this.recargaDisparo = recargaDisparo;
    }

    public float getRecargaDisparo(){
        return recargaDisparo;
    }

    ///

    public void recargarDash(float delta){
        tiempoDeRecargaDash += delta*factorDeCargaDash;
        if(tiempoDeRecargaDash >= recargaDash){
            tiempoDeRecargaDash = 0;
            dashRecargado = true;
        }
    }

    public void setTiempoDeRecargaDash(float tiempo){
        tiempoDeRecargaDash = tiempo;
    }

    public void setRecargaDash(float recargaDash){
        this.recargaDash = recargaDash;
    }

    public float getRecargaDash(){
        return recargaDash;
    }

    public void hacerDash() {
        sprite.setX(sprite.getX() + padX*desplazamientoDash);
        sprite.setY(sprite.getY() + padY*desplazamientoDash);
        if(padX == 0 && padY == 0){
            sprite.setY(sprite.getY() + desplazamientoDash);
        }
        dashRecargado = false;
    }
}
