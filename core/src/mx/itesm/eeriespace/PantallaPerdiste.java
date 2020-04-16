package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PantallaPerdiste extends Pantalla {

    private final GameLauncher gameLauncher;

    private Stage gameOverScene;

    private Label gameOverTitleLabel;

    private TextButton returnButton;
    private TextButton playAgainButton;

    public PantallaPerdiste(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void show() {
        createOptions();
    }

    private void createOptions() {
        gameOverScene = new Stage(vista);
        gameOverButtons();
        gameOverTitle();
        Gdx.input.setInputProcessor(gameOverScene);
    }

    private void gameOverTitle() {
        label1Style.fontColor = Color.WHITE;

        gameOverTitleLabel = new Label("Game Over", label1Style);
        gameOverTitleLabel.setFontScale(4f);
        gameOverTitleLabel.setSize(ANCHO, ALTO / 12);
        gameOverTitleLabel.setPosition(ANCHO / 2 - gameOverTitleLabel.getWidth() / 2, ALTO * .8f);
        gameOverTitleLabel.setAlignment(Align.center);

        gameOverScene.addActor(gameOverTitleLabel);
    }

    private void gameOverButtons() {
        returnButton = new TextButton("Return", textButtonStyle);
        returnButton.getLabel().setFontScale(1.5f);
        returnButton.setPosition(ANCHO / 2 - returnButton.getWidth() / 2, ALTO * 0.2f);
        gameOverScene.addActor(returnButton);

        playAgainButton = new TextButton("Play again?", textButtonStyle);
        playAgainButton.getLabel().setFontScale(1.5f);
        playAgainButton.setPosition(ANCHO / 2 - playAgainButton.getWidth() / 2, ALTO * 0.3f);
        gameOverScene.addActor(playAgainButton);

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Return Button Pressed");
                gameLauncher.setScreen(new PantallaMenu(gameLauncher));
            }
        });

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Play again Button Pressed");
                gameLauncher.setScreen(new PantallaEerieSpace(gameLauncher));
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

        gameOverScene.draw();
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