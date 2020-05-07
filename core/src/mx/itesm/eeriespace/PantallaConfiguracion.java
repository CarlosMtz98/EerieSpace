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
    private TextButton toggleMusicButton;
    private TextButton toggleSoundEffects;
    private TextButton resetLeaderboardButton;
    private TextButton creditsButton;

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
        label1Style.fontColor = Color.WHITE;

        settingsTitleLabel = new Label("Settings",label1Style);
        settingsTitleLabel.setFontScale(4f);
        settingsTitleLabel.setSize(ANCHO,ALTO/12);
        settingsTitleLabel.setPosition(ANCHO / 2 - settingsTitleLabel.getWidth() / 2, ALTO * .8f);
        settingsTitleLabel.setAlignment(Align.center);

        settingsScene.addActor(settingsTitleLabel);
    }

    private void settingsButtons() {
        returnButton = new TextButton("Return", textButtonStyle);
        returnButton.getLabel().setFontScale(1.5f);
        returnButton.setPosition(ANCHO / 2 - returnButton.getWidth() / 2, ALTO * 0.2f);
        settingsScene.addActor(returnButton);

        creditsButton = new TextButton("Credits", textButtonStyle);
        creditsButton.getLabel().setFontScale(1.5f);
        creditsButton.setPosition(ANCHO / 2 - creditsButton.getWidth() / 2, ALTO * 0.3f);
        settingsScene.addActor(creditsButton);

        resetLeaderboardButton = new TextButton("Reset Leaderboard", textButtonStyle);
        resetLeaderboardButton.getLabel().setFontScale(1.5f);
        resetLeaderboardButton.setPosition(ANCHO / 2 - resetLeaderboardButton.getWidth() / 2, ALTO * 0.4f);
        settingsScene.addActor(resetLeaderboardButton);

        toggleMusicButton = new TextButton("Toggle Music", textButtonStyle);
        toggleMusicButton.getLabel().setFontScale(1.5f);
        toggleMusicButton.setPosition(ANCHO / 2 - toggleMusicButton.getWidth() / 2, ALTO * 0.5f);
        settingsScene.addActor(toggleMusicButton);

        toggleSoundEffects = new TextButton("Toggle SFX", textButtonStyle);
        toggleSoundEffects.getLabel().setFontScale(1.5f);
        toggleSoundEffects.setPosition(ANCHO / 2 - toggleSoundEffects.getWidth() / 2, ALTO * 0.6f);
        settingsScene.addActor(toggleSoundEffects);

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Return Button Pressed");
                gameLauncher.setScreen(new PantallaMenu(gameLauncher));
            }
        });

        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Credits Button Pressed");
                gameLauncher.setScreen(new PantallaCreditos(gameLauncher));
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
