package mx.itesm.eeriespace;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

// Donde se desarrolla el juego. Es el equivalente e PantallaSpaceInvaders
class PantallaEerieSpace extends Pantalla {

    private final GameLauncher gameLauncher;

    // Balas
    private ArrayList<Bala> balas = new ArrayList<>();
    private Texture texturaBala;

    // Nave
    private Texture texturaNave;
    private Nave nave;

    public PantallaEerieSpace(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        cargarTexturas();
        crearNave();
    }

    private void crearNave() {
        float x = ANCHO/2 - texturaNave.getWidth()/2;
        float y = 0;
        nave = new Nave(texturaNave, x, y);
    }

    private void cargarTexturas() {
        texturaBala = new Texture("Bullet.png");
        texturaNave = new Texture("Player.png");
    }

    @Override
    public void render(float delta) {

        actualizar();

        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        dibujarSprites();
        batch.end();
    }

    private void actualizar() {
        //nave.mover(pad);
        for(Bala bala: balas)bala.mover();
    }

    private void dibujarSprites() {
        nave.draw(batch);
        for (Bala bala: balas){
            bala.draw(batch);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            if(v.x > ANCHO/2)disparar();
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    private void disparar(){
        float x = nave.sprite.getX() + texturaNave.getWidth()/2 - texturaBala.getWidth()/2;
        float y = nave.sprite.getY() + texturaNave.getHeight();
        Bala bala = new Bala(texturaBala, x, y);
        balas.add(bala);
    }
}
