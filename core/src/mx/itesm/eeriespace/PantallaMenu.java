package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

class PantallaMenu extends Pantalla {
    private final Launcher launcher;
    private AssetManager manager = new AssetManager();
    private Texture backgroudTexture;
    private Stage menuScene;

    // Textures file locations
    private final String bgMenuImage = "menu/background.jpg";
    // Skins
    private final String defaultButtonSkin = "buttons/default.png";
    //Title
    private Label menuTitleLabel;
    // Buttons
    private TextButton playButton;
    private TextButton helpButton;
    private TextButton settingsButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    public PantallaMenu(Launcher launcher) {
        this.launcher = launcher;
        loadMenuFiles();
    }

    @Override
    public void show() {
        createMenu();
    }

    private void createMenu() {
        menuScene = new Stage(vista);
        font = new BitmapFont();
        backgroudTexture = manager.get(bgMenuImage, Texture.class);
        menuButtons();
        menuTitle();
        Gdx.input.setInputProcessor(menuScene);
    }

    private void menuTitle() {
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font;
        label1Style.fontColor = Color.WHITE;

        menuTitleLabel = new Label("Eerie Space",label1Style);
        menuTitleLabel.setFontScale(4f);
        menuTitleLabel.setSize(ANCHO,ALTO/12);
        menuTitleLabel.setPosition(ANCHO/2 - menuTitleLabel.getWidth()/2, ALTO/4 * 3);
        menuTitleLabel.setAlignment(Align.center);

        menuScene.addActor(menuTitleLabel);
    }

    private void menuButtons() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        playButton = new TextButton("PLAY", textButtonStyle);
        helpButton = new TextButton("HELP", textButtonStyle);
        settingsButton = new TextButton("SETTINGS", textButtonStyle);

        playButton.getLabel().setFontScale(2);
        helpButton.getLabel().setFontScale(2);
        settingsButton.getLabel().setFontScale(2);

        playButton.setPosition(ANCHO/2 - playButton.getWidth()/2, 2*ALTO/3);
        helpButton.setPosition(ANCHO/2 - playButton.getWidth() * 5.5f, 2*ALTO/3);
        settingsButton.setPosition(ANCHO/2 + playButton.getWidth() * 5, 2*ALTO/3);

        menuScene.addActor(playButton);
        menuScene.addActor(helpButton);
        menuScene.addActor(settingsButton);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Play Button Pressed");
            }
        });

        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Help Button Pressed");
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Settings Button Pressed");
            }
        });
    }

    private void loadMenuFiles() {
        Gdx.app.log("Loding menu files", "Start loading files for menu Scene");
        manager.load(bgMenuImage, Texture.class);
        manager.finishLoading();
        Gdx.app.log("Loding menu files", "Finish loading files for menu Scene");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(backgroudTexture, 0,0);
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
