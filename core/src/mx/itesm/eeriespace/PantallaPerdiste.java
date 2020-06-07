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
    private Marcador marcador;

    private Label gameOverTitleLabel;
    private Label puntuacionAlta;

    private TextButton returnButton;
    private TextButton playAgainButton;

    private AdministradorPuntuacion administradorPuntuacion;

    public PantallaPerdiste(GameLauncher gameLauncher, int puntos) {
        this.gameLauncher = gameLauncher;
        this.marcador = new Marcador(ANCHO / 2, ALTO / 2, -1000f, -1000f);
        this.marcador.incrementarPuntos(puntos);
    }

    @Override
    public void show() {
        createOptions();
    }

    private void createOptions() {
        cargarPuntuaciones();
        gameOverScene = new Stage(vista);
        gameOverButtons();
        gameOverTitle();
        highScoreTitle();
        Gdx.input.setInputProcessor(gameOverScene);
    }

    private void cargarPuntuaciones() {
        administradorPuntuacion = new AdministradorPuntuacion();
        Gdx.app.log("High score", Integer.toString(administradorPuntuacion.getPuntuacionAlta()));
    }

    private void highScoreTitle() {
        label1Style.fontColor = Color.WHITE;
        administradorPuntuacion.añadirPuntuacion(marcador.getPoints());
        puntuacionAlta = new Label( "High score: " + Integer.toString(administradorPuntuacion.getPuntuacionAlta()), label1Style);
        puntuacionAlta.setFontScale(1.5f);
        puntuacionAlta.setSize(ANCHO, ALTO / 12);
        puntuacionAlta.setPosition(ANCHO / 2 - puntuacionAlta.getWidth() / 2, ALTO * .6f);
        puntuacionAlta.setAlignment(Align.center);

        gameOverScene.addActor(puntuacionAlta);
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
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
                System.out.println("Return Button Pressed");
                gameLauncher.setScreen(new PantallaMenu(gameLauncher));
            }
        });

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }
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
        marcador.render(batch);
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
