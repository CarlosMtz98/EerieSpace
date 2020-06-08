package mx.itesm.eeriespace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaSplash extends Pantalla {
    private final GameLauncher gameLauncher;

    private Stage splashScene;

    private float timeElapsed;

    private Texture texturaSplash;

    public PantallaSplash(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        crearSplash();
    }

    private void crearSplash() {
        splashScene = new Stage(vista);
        texturaSplash = new Texture("menu/Splash Screen.png");
    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaSplash, 0, 0);
        batch.end();
        splashScene.draw();

        if (timeElapsed > 2.0) {
            gameLauncher.setScreen(new PantallaMenu(gameLauncher));
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
}
