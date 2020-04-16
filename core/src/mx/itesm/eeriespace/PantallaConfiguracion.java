package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PantallaConfiguracion extends Pantalla {
    private final GameLauncher gameLauncher;
    private AssetManager manager = new AssetManager();
    private Texture backgroundTexture;

    // Opciones de configuración
    private Stage settingsScene;

    // Textures file locations
    // todo private final String bgMenuImage = "nombreImagen.png";

    //Title
    private Label settingsTitleLabel;

    // Buttons
    private TextButton returnButton;
    private TextButton.TextButtonStyle textButtonStyle;

    public PantallaConfiguracion(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        loadSettingsFiles();
    }

    @Override
    public void show() {
        //texturaFondo = new Texture("fondo.jpg");
        createOptions();
    }

    private void createOptions() {
        settingsScene = new Stage(vista);
        // backgroundTexture = manager.get(bgMenuImage, Texture.class);
        // todo backgroundTexture = manager.get(bgMenuImage, Texture.class);
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
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

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

    private void loadSettingsFiles() {
        Gdx.app.log("Loading settings files", "Started loading files for Settings Scene");
        // todo manager.load(bgMenuImage, Texture.class);
        manager.finishLoading();
        Gdx.app.log("Loading settings files", "Finished loading files for Settings Scene");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        //batch.begin();
        // todo batch.draw(backgroundTexture,0,0);
        //batch.end();

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
