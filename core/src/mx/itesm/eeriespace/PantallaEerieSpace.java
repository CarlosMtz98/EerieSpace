package mx.itesm.eeriespace;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    //Arreglos Texturas meteoros
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

    public PantallaEerieSpace(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    private void crearHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);

        Skin skin = new Skin();
        skin.add("fondo", new Texture("JoystickBackground.png"));
        skin.add("boton", new Texture("JoystickFront.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");

        //Crear pad joystick
        pad = new Touchpad(64, estilo);
        pad.setBounds(16, 16, 256, 256);

        pad.setColor(1,1,1,0.7f);
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);

        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            Touchpad pad = (Touchpad)actor;
            if (pad.getKnobPercentX() != 0)
            {
                nave.setEstado(EstadoMovimiento.MOVIMIENTO);
            }
            else
            {
                nave.setEstado(EstadoMovimiento.QUIETO);
            }
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
        InputProcessor inputProcessorOne = escenaHUD;
        InputProcessor inputProcessorTwo = new ProcesadorEntrada();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void crearMeteoro() {
        double probabilidad = Math.random();
        if (probabilidad < .7) {
            meteoros.add(new Meteoro(meteoroC.get((int) Math.floor(Math.random() * meteoroC.size())),
                    (float) (Math.random() * ANCHO), 15));
            System.out.println("Creado pequeño");
        } else if (probabilidad < .9) {
            meteoros.add(new Meteoro(meteoroM.get((int) Math.floor(Math.random() * meteoroM.size())),
                    (float) (Math.random() * ANCHO), 30));
            System.out.println("Creado mediano");
        } else {
            meteoros.add(new Meteoro(meteoroG.get((int) Math.floor(Math.random() * meteoroG.size())),
                    (float) (Math.random() * ANCHO), 45));
            System.out.println("Creado grande");
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
        actualizar(delta);

        gameTime += delta;
        if (gameTime > 3f) {
            crearMeteoro();
            gameTime = 0;
        }

        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        dibujarSprites();
        marcador.render(batch);
        batch.end();

        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
    }

    private void actualizar(float delta) {
        nave.mover(pad, delta);
        for (Bala bala : balas) bala.mover(delta);
        for (int i = meteoros.size() - 1; i > -1; i--) {
            Meteoro meteoro = meteoros.get(i);
            meteoro.mover(delta);
            if (meteoro.sprite.getX() - meteoro.sprite.getWidth() < 0 ||
                    meteoro.sprite.getX() > Pantalla.ANCHO ||
                    meteoro.sprite.getY() < 0) {
                meteoros.remove(meteoro);
            }
            for (Bala bala : balas) {
                if (bala.sprite.getBoundingRectangle().overlaps(meteoro.sprite.getBoundingRectangle())) {
                    meteoros.remove(meteoro);
                }
            }
            if (nave.sprite.getBoundingRectangle().overlaps(meteoro.sprite.getBoundingRectangle())) {
                terminarJuego();
            }
        }
    }

    private void terminarJuego() {
        meteoros.clear();
        balas.clear();
        gameLauncher.setScreen(new PantallaMenu(gameLauncher));
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
            if(v.x > ANCHO/2)disparar();
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
        float x = (float) (nave.sprite.getX() + texturaNave.getWidth() / 2 - texturaBala.getWidth() / 2 -
                nave.sprite.getHeight() * Math.sin(nave.sprite.getRotation()));
        float y = (float) (nave.sprite.getY() + texturaNave.getHeight() -
                nave.sprite.getWidth() * Math.cos(nave.sprite.getRotation()));
        Bala bala = new Bala(texturaBala, x, y);
        bala.setDireccion(nave);
        balas.add(bala);
    }
}
