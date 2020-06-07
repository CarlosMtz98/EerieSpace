package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PantallaCreditos extends Pantalla {
    private final GameLauncher gameLauncher;
    private Texture backgroundTexture;

    // Opciones de configuración
    private Stage escenaCreditos;

    // Buttons
    private TextButton okButton;
    private BitmapFont font;

    public PantallaCreditos(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        crearCreditos();
        backgroundTexture = new Texture("CreditsScreen.png");

        // Avisar que queremos atrapar la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearCreditos() {
        escenaCreditos = new Stage(vista);
        botonesCreditos();
        Gdx.input.setInputProcessor(escenaCreditos);
    }

    private void botonesCreditos() {
        okButton = new TextButton("OK", textButtonStyle);
        okButton.getLabel().setFontScale(2);

        okButton.setPosition(ANCHO / 2 - okButton.getWidth() / 2, ALTO * 0.1f);

        escenaCreditos.addActor(okButton);

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
                System.out.println("OK Button Pressed");
                gameLauncher.setScreen(new PantallaConfiguracion(gameLauncher));
            }
        });
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        escenaCreditos.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gameLauncher.setScreen(new PantallaConfiguracion(gameLauncher));
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
