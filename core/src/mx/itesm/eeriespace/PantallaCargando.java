package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaCargando implements Screen {

    private GameLauncher juego;

    //Cámara y vista
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Texturas
    private Texture texturaCargando;
    private Sprite spriteCargando; // Para animarlo

    private AssetManager assetManager;

    public PantallaCargando(GameLauncher juego){
        this.juego = juego;
        //todo Implementar Asset manager global
        //this.assetManager = juego.getAssetManager();
    }

    @Override
    public void show() {
        // Crea cámara/vista
        camara = new OrthographicCamera(1280, 800);
        camara.position.set(1280/2, 800/2, 0);
        camara.update();
        vista = new StretchViewport(1280, 800, camara);
        batch = new SpriteBatch();

        // Cargar recursos de esta pantalla
        //todo Subir archivo cargando.png (va a ROTAR)
        assetManager.load("cargando.png", Texture.class);
        assetManager.finishLoading();
        texturaCargando = assetManager.get("cargando.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(1280/2 - spriteCargando.getWidth()/2,
                800/2 - spriteCargando.getHeight()/2);
        // Ahora INICIA la carga de los recursos de la siguiente pantalla
        cargarRecursos();

    }

    // Estos son los recursos de la pantalla siguiente (PantallaEerieSpace)
    private void cargarRecursos() {
        //todo Si implementamos el AssetManager, aquí cargará lo necesario
    }

    @Override
    public void render(float delta) {
        // Revisar cómo va la carga de los recursos siguientes
        actualizarCarga();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteCargando.setRotation(spriteCargando.getRotation() + 10);  // Animación

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        spriteCargando.draw(batch);
        batch.end();
    }

    // Revisa cómo va la carga de assets
    private void actualizarCarga() {
        if(assetManager.update()){  // regresa true si ya terminó la carga
            juego.setScreen(new PantallaEerieSpace(juego));
        }else{
            // Aún no termina, preguntar cómo va
            float avance = assetManager.getProgress();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
        assetManager.unload("cargando.png");
    }
}
