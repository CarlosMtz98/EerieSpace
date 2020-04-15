package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;

public abstract class Meteoro extends Objeto {
    private int daño;
    private float angulo;
    public int velocidad = 15;

    //Creación aleatoria de meteoros
    public Meteoro(Texture textura, float x, float y, int daño) {
        super(textura, x, y);
        this.daño = daño;
        //Un problema con esta estrategia es que si se crea en una esquina y la dirección del
        //meteoro lo lleva a salirse de la pantalla, nunca lo va a poder destruir el jugador y se
        //desperdicia el meteoro
        this.angulo = (float) Math.random() - .5f;
    }

    public int getDaño() {
        return daño;
    }

    public void update(float delta) {
        mover(delta);

        if (sprite.getX() - sprite.getWidth() < 0 || sprite.getX() > Pantalla.ANCHO ||
                sprite.getY() > Pantalla.ALTO) {
            //Código para destruirlo
        }
    }

    public void mover(float delta) {
        sprite.setPosition(sprite.getX() + angulo * delta * velocidad, velocidad * delta);
    }

}
