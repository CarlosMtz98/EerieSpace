package mx.itesm.eeriespace;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

// Donde se desarrolla el juego. Es el equivalente e PantallaSpaceInvaders
class PantallaEerieSpace extends Pantalla {
    private final GameLauncher gameLauncher;


    // Balas
    private ArrayList<Bala> balas;
    private Texture texturaBala;

    // Nave
    private Texture texturaNave;

    public PantallaEerieSpace(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        cargarTexturas();
    }

    private void cargarTexturas() {
        texturaBala = new Texture("Bullet.png");
        texturaNave = new Texture("Player.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);
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
            return false;
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
}
