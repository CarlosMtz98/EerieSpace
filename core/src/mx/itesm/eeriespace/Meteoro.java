package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public class Meteoro extends Objeto {
    private int daño;
    private float angulo;
    private int vida;
    public float velocidad;

    //Creación aleatoria de meteoros
    public Meteoro(Texture textura, float x, int daño, float velocidad) {
        super(textura, x, Pantalla.ALTO + 150);
        this.daño = daño;
        this.vida = daño;
        this.velocidad = velocidad;
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
