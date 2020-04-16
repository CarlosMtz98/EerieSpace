package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Pantalla implements Screen
{
    // Atributos disponibles en todas las clases del proyecto
    public static final float ANCHO = 1280;
    public static final float ALTO = 720;
    OrthographicCamera camara;
    Viewport vista;
    SpriteBatch batch;
    BitmapFont font;
    AssetManager manager = new AssetManager();
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    Label.LabelStyle label1Style = new Label.LabelStyle();

    // Textures file locations
    Texture backgroundTexture = new Texture("fondo.png");

    // Constructor, inicializa los objetos camara, vista, batch y font
    public Pantalla() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("BasierSquare.fnt"),
                Gdx.files.internal("BasierSquare.png"), false);
        textButtonStyle.font = font;
        label1Style.font = font;
    }

    // Borra la pantalla con fondo negro
    protected void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Borra la pantalla con el color RGB (r,g,b)
    protected void borrarPantalla(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Notifica a la vista, que el tamaño de la pantalla física ha cambiado
    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void hide() {
        // Libera los recursos asignados por cada pantalla
        // Las subclases están obligadas a sobrescribir el método dispose()
        dispose();
    }
}