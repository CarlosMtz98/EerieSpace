package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PantallaInstrucciones extends Pantalla {
    private final GameLauncher gameLauncher;

    // Opciones de configuración
    private Stage helpScene;

    // Buttons
    private TextButton okButton;

    public PantallaInstrucciones(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        createHelp();
        backgroundTexture = new Texture("HelpScreen.png");
    }

    private void createHelp() {
        helpScene = new Stage(vista);
        helpButtons();
        Gdx.input.setInputProcessor(helpScene);
    }

    private void helpButtons() {
        okButton = new TextButton("OK", textButtonStyle);
        okButton.getLabel().setFontScale(2);
        okButton.setPosition(ANCHO/2 - okButton.getWidth()/2, ALTO*0.1f);
        helpScene.addActor(okButton);

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("OK Button Pressed");
                gameLauncher.setScreen(new PantallaMenu(gameLauncher));
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

        helpScene.draw();
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