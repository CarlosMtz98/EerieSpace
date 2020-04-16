package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class PantallaMenu extends Pantalla {
    private final GameLauncher gameLauncher;
    private AssetManager manager = new AssetManager();
    private Texture backgroundTexture;

    // Menu
    private Stage menuScene;

    // Textures file locations
    private final String bgMenuImage = "menu/MenuBackground.png";
    // Skins
    private final String defaultButtonSkin = "buttons/default.png";
    // Buttons
    private TextButton playButton;
    private TextButton helpButton;
    private TextButton settingsButton;
    private TextButton.TextButtonStyle textButtonStyle;

    public PantallaMenu(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        loadMenuFiles();
    }

    @Override
    public void show() {
        createMenu();
    }

    private void createMenu() {
        menuScene = new Stage(vista);
        //font = new Text("BasierSquare.fnt");
        backgroundTexture = manager.get(bgMenuImage, Texture.class);
        menuButtons();
        Gdx.input.setInputProcessor(menuScene);
    }

    private void menuButtons() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        playButton = new TextButton("Play", textButtonStyle);
        helpButton = new TextButton("Help", textButtonStyle);
        settingsButton = new TextButton("Settings", textButtonStyle);

        playButton.getLabel().setFontScale(2);
        helpButton.getLabel().setFontScale(2);
        settingsButton.getLabel().setFontScale(2);

        helpButton.setPosition(ANCHO * .2f - helpButton.getWidth() / 2, ALTO / 4);
        settingsButton.setPosition(ANCHO * .5f - settingsButton.getWidth() / 2, ALTO / 4);
        playButton.setPosition(ANCHO * .79f - playButton.getWidth() / 2, ALTO / 4);

        menuScene.addActor(playButton);
        menuScene.addActor(helpButton);
        menuScene.addActor(settingsButton);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Play Button Pressed");
                gameLauncher.setScreen(new PantallaEerieSpace(gameLauncher));
            }
        });

        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Help Button Pressed");
                gameLauncher.setScreen(new PantallaInstrucciones(gameLauncher));
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                super.clicked(event, x, y);
                System.out.println("Settings Button Pressed");
                gameLauncher.setScreen(new PantallaConfiguracion(gameLauncher));
            }
        });
    }

    private void loadMenuFiles() {
        Gdx.app.log("Loading menu files", "Started loading files for Menu Scene");
        manager.load(bgMenuImage, Texture.class);
        manager.finishLoading();
        Gdx.app.log("Loading menu files", "Finished loading files for Menu Scene");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0,0);
        batch.end();
        menuScene.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
