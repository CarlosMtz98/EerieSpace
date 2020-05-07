package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Meteoro extends Objeto {
    private int daño;
    private float angulo;
    public int velocidad = 150;
    private int vida;

    //Creación aleatoria de meteoros
    public Meteoro(Texture textura, float x, int daño) {
        super(textura, x, Pantalla.ALTO + 150);
        this.daño = daño;
        //Un problema con esta estrategia es que si se crea en una esquina y la dirección del
        //meteoro lo lleva a salirse de la pantalla, nunca lo va a poder destruir el jugador y se
        //desperdicia el meteoro
        this.angulo = (float) Math.random() - .5f;
        this.vida = daño;
    }

    public int getDaño() {
        return daño;
    }

    public void update(float delta) {
        mover(delta);
    }

    public int getVida() {
        return vida;
    }

    public void disminuirVida(int daño) {
        vida -= daño;
        this.sprite.setColor(1f, (float)this.vida / this.daño, (float)this.vida / this.daño,1);
    }

    public void mover(float delta) {
        sprite.setPosition(sprite.getX() + delta * angulo * velocidad, sprite.getY() - velocidad * delta);
    }

}
