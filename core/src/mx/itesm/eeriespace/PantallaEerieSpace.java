package mx.itesm.eeriespace;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

// Donde se desarrolla el juego. Es el equivalente e PantallaSpaceInvaders
class PantallaEerieSpace extends Pantalla {
    private final GameLauncher gameLauncher;

    // Balas
    private ArrayList<Bala> balas = new ArrayList<>();
    private ArrayList<Meteoro> meteoros = new ArrayList<Meteoro>();
    private Texture texturaBala;

    // Meteoros

    // Arreglos Texturas meteoros
    private ArrayList<Texture> meteoroC = new ArrayList<>();
    private ArrayList<Texture> meteoroM = new ArrayList<>();
    private ArrayList<Texture> meteoroG = new ArrayList<>();

    // Nave
    private Texture texturaNave;
    private Nave nave;

    // Marcador
    private Marcador marcador;

    // HUD joystick virtual
    private Stage escenaHUD;
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    protected Touchpad pad;
    float gameTime = 0f;

    //Musica
    Music musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("audio/cancion.mp3"));

    //Efectos
    Sound efectoLaser = Gdx.audio.newSound(Gdx.files.internal("audio/laserSfx.wav"));
    Sound efectoDaño = Gdx.audio.newSound(Gdx.files.internal("audio/hpDownSfx.mp3"));

    //Estado
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    public PantallaEerieSpace(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    private void crearHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);


        Texture texturabtnPausa = new Texture("Pausa.png");
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(texturabtnPausa));
        ImageButton btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(ANCHO * .97f - btnPausa.getWidth(), ALTO * .97f - btnPausa.getHeight());


        Skin skin = new Skin();
        skin.add("fondo", new Texture("JoystickBackground.png"));
        skin.add("boton", new Texture("JoystickFront.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");

        // Crear pad joystick
        pad = new Touchpad(64, estilo);
        pad.setBounds(16, 16, 256, 256);

        pad.setColor(1,1,1,0.7f);
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnPausa);

        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if (pad.getKnobPercentX() != 0){
                    nave.setEstado(EstadoMovimiento.MOVIMIENTO);
                }
                else{
                    nave.setEstado(EstadoMovimiento.QUIETO);
                }
            }
        });

        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estadoJuego = EstadoJuego.PAUSADO;
            }
        });
    }

    @Override
    public void show() {
        cargarTexturas();
        crearMarcador();
        crearHUD();
        crearNave();
        crearMeteoro();
        cargarMusica();
        InputProcessor inputProcessorOne = escenaHUD;
        InputProcessor inputProcessorTwo = new ProcesadorEntrada();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    private void cargarMusica() {
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.5f);
        musicaFondo.play();
    }

    private void crearMeteoro() {
        double probabilidad = Math.random();
        if (probabilidad < .7) {
            meteoros.add(new Meteoro(meteoroC.get((int) Math.floor(Math.random() * meteoroC.size())),
                    (float) (Math.random() * ANCHO), 15));
        } else if (probabilidad < .9) {
            meteoros.add(new Meteoro(meteoroM.get((int) Math.floor(Math.random() * meteoroM.size())),
                    (float) (Math.random() * ANCHO), 30));
        } else {
            meteoros.add(new Meteoro(meteoroG.get((int) Math.floor(Math.random() * meteoroG.size())),
                    (float) (Math.random() * ANCHO), 45));
        }
    }

    private void crearMarcador() {
        marcador = new Marcador(ANCHO * 0.1f, ALTO * 0.95f);
    }

    private void crearNave() {
        float x = ANCHO/2 - texturaNave.getWidth()/2;
        float y = 0;
        nave = new Nave(texturaNave, x, y);
    }

    private void cargarTexturas() {
        texturaBala = new Texture("Bullet.png");
        texturaNave = new Texture("Player.png");
        meteoroC.add(new Texture("asteroides/pequeno-0.png"));
        meteoroC.add(new Texture("asteroides/pequeno-1.png"));
        meteoroC.add(new Texture("asteroides/pequeno-2.png"));
        meteoroC.add(new Texture("asteroides/pequeno-3.png"));
        meteoroM.add(new Texture("asteroides/medio-0.png"));
        meteoroM.add(new Texture("asteroides/medio-1.png"));
        meteoroM.add(new Texture("asteroides/medio-2.png"));
        meteoroM.add(new Texture("asteroides/medio-3.png"));
        meteoroG.add(new Texture("asteroides/Grande-0.png"));
        meteoroG.add(new Texture("asteroides/Grande-1.png"));
        meteoroG.add(new Texture("asteroides/Grande-2.png"));
        meteoroG.add(new Texture("asteroides/Grande-3.png"));
    }

    @Override
    public void render(float delta) {
        if (estadoJuego == EstadoJuego.JUGANDO) {
            actualizar(delta);
        }

        gameTime += delta;
        if (gameTime > 1f) {
            crearMeteoro();
            gameTime = 0;
        }

        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        dibujarSprites();
        marcador.render(batch);
        batch.end();

        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
    }

    private void actualizar(float delta) {
        if (nave.getVida() >  0) {
            nave.mover(pad, delta);
            for (Bala bala : balas) bala.mover(delta);
            for (int i = meteoros.size() - 1; i > -1; i--) {
                Meteoro meteoro = meteoros.get(i);
                meteoro.mover(delta);
                if (meteoro.sprite.getX() - meteoro.sprite.getWidth() < 0 ||
                        meteoro.sprite.getX() > Pantalla.ANCHO ||
                        meteoro.sprite.getY() + meteoro.sprite.getHeight() < 0) {
                    meteoros.remove(meteoro);
                }
                for (int n = balas.size() - 1; n > -1; n--) {
                    Bala bala = balas.get(n);
                    if (bala.sprite.getBoundingRectangle().overlaps(meteoro.sprite.getBoundingRectangle())) {
                        meteoros.remove(meteoro);
                        balas.remove(bala);
                        marcador.incrementarPuntos(meteoro.getDaño());
                    }
                }
                if (nave.sprite.getBoundingRectangle().overlaps(meteoro.sprite.getBoundingRectangle())) {
                    nave.disminuirVida(meteoro.getDaño());
                    efectoDaño.play(0.1f);
                    meteoros.remove(meteoro);
                    Gdx.app.log("Life RGB", Integer.toString(Math.round(255 * (float)nave.getVida() / 100)));
                }
            }
        }
        else {
            terminarJuego();
        }
    }

    private void terminarJuego() {
        meteoros.clear();
        balas.clear();
        musicaFondo.stop();
        gameLauncher.setScreen(new PantallaPerdiste(gameLauncher));
    }

    private void dibujarSprites() {
        nave.draw(batch);
        for (Bala bala: balas){
            bala.draw(batch);
        }
        for (Meteoro meteoro : meteoros) {
            meteoro.render(batch);
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

    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            if (v.x > ANCHO / 2) {
                disparar();
                efectoLaser.play(0.1f);
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    private void disparar(){
        float x = obtenerPuntaX();
        float y = obtenerPuntaY();
        Bala bala = new Bala(texturaBala, x, y);
        bala.setDireccion(nave);
        balas.add(bala);
    }

    private float obtenerPuntaX(){
        float grados = nave.sprite.getRotation();
        float correccionAngulo = 90f;
        grados += correccionAngulo;

        float origenX = nave.sprite.getX() + nave.sprite.getWidth()/2;
        float dx = (float)Math.cos(Math.toRadians(grados))*nave.sprite.getWidth()/2;

        return origenX + dx;
    }

    private float obtenerPuntaY(){
        float grados = nave.sprite.getRotation();
        float correccionAngulo = 90f;
        float direccion = grados < 0? -1: 1;
        grados += correccionAngulo;

        float origenY = nave.sprite.getY() + nave.sprite.getHeight()/2;
        float dy = (float)Math.sin(Math.toRadians(grados))*nave.sprite.getHeight()/2 +
                direccion*((float)Math.cos(Math.toRadians(grados))*texturaBala.getHeight()/2);

        return origenY + dy;
    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        PERDIO,
    }

}
