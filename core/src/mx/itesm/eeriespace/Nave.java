package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Nave extends Objeto {
    private EstadoMovimiento estadoMovimiento;
    private boolean tieneEscudo = false;
    public boolean triggerDash = false;
    private int vida;
    private int daño;
    private float time = 0;

    //Coordenadas
    float x;
    float y;

    // Pad
    float padX = 0;
    float padY = 0;

    // Disparo
    public boolean puedeDisparar;
    private float recargaDisparo;
    private float tiempoDeRecargaDisparo;

    // Dash
    private final float factorDeCargaDash = 0.1f;   // 0 = sin dash, 1 = delta (muy rápido)
    public static final float velocidad = 300;
    public boolean dashRecargado;
    private float recargaDash;
    private float tiempoDeRecargaDash;
    public boolean efectoDashHabilitado = false;

    //TextureRegion
    private TextureRegion[][] texturaNave;


    public Nave(Texture textura, float x, float y) {
        super(textura, x, y);
        TextureRegion region = new TextureRegion(textura);
        texturaNave = region.split(82,82);
        estadoMovimiento = EstadoMovimiento.QUIETO;
        vida = 100;
        daño = 15;
        puedeDisparar = true;
        recargaDisparo = 1f;
        dashRecargado = true;
        recargaDash = 1f;

        sprite = new Sprite(texturaNave[0][0]);
        sprite.setPosition(x, y);
        this.x = x;
        this.y = y;
    }

    public void mover(Touchpad pad, float delta){
        if(estadoMovimiento == EstadoMovimiento.MOVIMIENTO) {

            padX = pad.getKnobPercentX();
            padY = pad.getKnobPercentY();

            float dx = velocidad * padX * delta;
            float dy = velocidad * padY * delta;

            float anguloJoyStick = (float)Math.toDegrees(Math.atan((double) padY / (double) padX));
            anguloJoyStick += (padX > 0)? -90: 90; //cambiar a los grados en formato de libgdx
            float anguloNave = sprite.getRotation();

            sprite.setRotation(anguloJoyStick);


            sprite.setX(sprite.getX() + dx);
            sprite.setY(sprite.getY() + dy);

            this.x = sprite.getX();
            this.y = sprite.getY();
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
        if(!tieneEscudo){
            this.sprite.setColor(1f, (float)this.vida / 100, (float)this.vida / 100,1);
        }
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
            efectoDashHabilitado = true;
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

    public void hacerDash(float delta) {
        time += delta;
        sprite.setX(sprite.getX() + (padX * (velocidad * 1.5f)*delta));
        sprite.setY(sprite.getY() + (padY * (velocidad * 1.5f)*delta));
        if (padX == 0 && padY == 0) {
            sprite.setY(sprite.getY() + (padY * (velocidad * 1.5f)*delta));
        }
        if (time > .33f) {
            time = 0;
            dashRecargado = false;
            triggerDash = false;
            setTiempoDeRecargaDash(0);
        }
    }
    @Override
    public void render(SpriteBatch batch) {
        if (puedeDisparar) {
            TextureRegion region = texturaNave[0][1];
            sprite.setRegion(region);
        }
        else {
            TextureRegion region = texturaNave[0][0];
            sprite.setRegion(region);
        }
        sprite.draw(batch);
    }
}
