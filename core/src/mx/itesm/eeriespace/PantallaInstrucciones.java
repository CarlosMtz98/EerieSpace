package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PantallaInstrucciones extends Pantalla {
    private final GameLauncher gameLauncher;
    private AssetManager manager = new AssetManager();
    private Texture backgroundTexture;

    // Opciones de configuración
    private Stage helpScene;

    // Textures file locations
    // todo private final String bgMenuImage = "nombreImagen.png";

    //Title
    private Label helpTitleLabel;

    // Buttons
    private TextButton okButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    public PantallaInstrucciones(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        loadSettingsFiles();
    }

    @Override
    public void show() {
        //texturaFondo = new Texture("fondo.jpg");
        createHelp();
    }

    private void createHelp() {
        helpScene = new Stage(vista);
        font = new BitmapFont();
        // todo backgroundTexture = manager.get(bgMenuImage, Texture.class);
        helpButtons();
        helpTitle();
        Gdx.input.setInputProcessor(helpScene);
    }

    private void helpTitle() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font;
        label1Style.fontColor = Color.WHITE;

        helpTitleLabel = new Label("Help",label1Style);
        helpTitleLabel.setFontScale(4f);
        helpTitleLabel.setSize(ANCHO,ALTO/12);
        helpTitleLabel.setPosition(ANCHO/2 - helpTitleLabel.getWidth()/2, ALTO/4 * 3);
        helpTitleLabel.setAlignment(Align.center);

        helpScene.addActor(helpTitleLabel);
    }

    private void helpButtons() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

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

    private void loadSettingsFiles() {
        Gdx.app.log("Loading help files", "Started loading files for Help Scene");
        // todo manager.load(bgMenuImage, Texture.class);
        manager.finishLoading();
        Gdx.app.log("Loading help files", "Finished loading files for Help Scene");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        //batch.begin();
        // todo batch.draw(backgroundTexture,0,0);
        //batch.end();

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