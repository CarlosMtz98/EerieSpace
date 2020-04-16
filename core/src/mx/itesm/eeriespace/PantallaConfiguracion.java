package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PantallaConfiguracion extends Pantalla {
    private final GameLauncher gameLauncher;

    // Opciones de configuración
    private Stage settingsScene;

    //Title
    private Label settingsTitleLabel;

    // Buttons
    private TextButton returnButton;

    public PantallaConfiguracion(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        createOptions();
    }

    private void createOptions() {
        settingsScene = new Stage(vista);
        settingsButtons();
        settingsTitle();
        Gdx.input.setInputProcessor(settingsScene);
    }

    private void settingsTitle() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font;
        label1Style.fontColor = Color.WHITE;

        settingsTitleLabel = new Label("Settings",label1Style);
        settingsTitleLabel.setFontScale(4f);
        settingsTitleLabel.setSize(ANCHO,ALTO/12);
        settingsTitleLabel.setPosition(ANCHO/2 - settingsTitleLabel.getWidth()/2, ALTO/4 * 3);
        settingsTitleLabel.setAlignment(Align.center);

        settingsScene.addActor(settingsTitleLabel);
    }

    private void settingsButtons() {
        returnButton = new TextButton("RETURN", textButtonStyle);
        returnButton.getLabel().setFontScale(2);
        returnButton.setPosition(ANCHO/2 - returnButton.getWidth()/2, ALTO*0.1f);
        settingsScene.addActor(returnButton);

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Return Button Pressed");
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

        settingsScene.draw();
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
