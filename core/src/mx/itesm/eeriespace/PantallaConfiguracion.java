package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    //Colores
    public static final Color RED = new Color(0xff0000ff);

    // Buttons
    private TextButton okButton;
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

        // Avisar que queremos atrapar la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
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
        okButton = new TextButton("OK", textButtonStyle);
        okButton.getLabel().setFontScale(2);

        okButton.setPosition(ANCHO / 2 - okButton.getWidth() / 2, ALTO * 0.1f);
        settingsScene.addActor(okButton);

        creditsButton = new TextButton("Credits", textButtonStyle);
        creditsButton.getLabel().setFontScale(1.5f);
        creditsButton.setPosition(ANCHO / 2 - creditsButton.getWidth() / 2, ALTO * 0.3f);
        settingsScene.addActor(creditsButton);

        resetLeaderboardButton = new TextButton("Reset Leaderboard", textButtonStyle);
        resetLeaderboardButton.getLabel().setFontScale(1.5f);
        resetLeaderboardButton.setPosition(ANCHO / 2 - resetLeaderboardButton.getWidth() / 2, ALTO * 0.4f);
        settingsScene.addActor(resetLeaderboardButton);

        TextButton.TextButtonStyle textButtonStyleSettings = new TextButton.TextButtonStyle();
        textButtonStyleSettings.font = font;

        toggleMusicButton = new TextButton("Toggle Music", textButtonStyleSettings);
        toggleMusicButton.getLabel().setFontScale(1.5f);
        if (gameLauncher.music) toggleMusicButton.getLabel().setColor(Color.WHITE);
        else toggleMusicButton.getLabel().setColor(Color.RED);
        toggleMusicButton.setPosition(ANCHO / 2 - toggleMusicButton.getWidth() / 2, ALTO * 0.5f);
        System.out.println("music = " + toggleMusicButton.isChecked());
        settingsScene.addActor(toggleMusicButton);

        toggleSoundEffects = new TextButton("Toggle SFX", textButtonStyleSettings);
        toggleSoundEffects.getLabel().setFontScale(1.5f);
        if (gameLauncher.sfx) toggleSoundEffects.getLabel().setColor(Color.WHITE);
        else toggleSoundEffects.getLabel().setColor(Color.RED);
        toggleSoundEffects.setPosition(ANCHO / 2 - toggleSoundEffects.getWidth() / 2, ALTO * 0.6f);
        System.out.println("sfx = " + toggleSoundEffects.isChecked());
        settingsScene.addActor(toggleSoundEffects);

        toggleMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
                System.out.println("Music Button Pressed");
                if (gameLauncher.music) {
                    gameLauncher.music = false;
                    toggleMusicButton.getLabel().setColor(Color.RED);
                } else {
                    gameLauncher.music = true;
                    toggleMusicButton.getLabel().setColor(Color.WHITE);
                }
            }
        });

        toggleSoundEffects.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("SFX Button Pressed");
                if (gameLauncher.sfx) {
                    gameLauncher.sfx = false;
                    toggleSoundEffects.getLabel().setColor(Color.RED);
                } else {
                    gameLauncher.sfx = true;
                    toggleSoundEffects.getLabel().setColor(Color.WHITE);
                    if (gameLauncher.sfx) {
                        efectoClick.play(0.1f);
                    }
                }
            }
        });

        resetLeaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
                System.out.println("Reset Button Pressed");
                AdministradorPuntuacion administrador = new AdministradorPuntuacion();
                administrador.reiniciarPuntuacion();
            }
        });

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
                System.out.println("Return Button Pressed");
                gameLauncher.setScreen(new PantallaMenu(gameLauncher));
            }
        });

        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
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

        // Tecla de BACK
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
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
