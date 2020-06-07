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

    // Items
    private ArrayList<Item> items = new ArrayList<>();

    //Escudo
    private Texture texturaEscudo;
    private Escudo escudo;

    //Vida
    private Texture texturaVida;
    private Reparacion reparacion;

    //Daño
    private Texture texturaDaño;
    private VelocidadDeAtaque velocidadDeAtaque;
    private PoderDeAtaque poderDeAtaque;

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
    Sound efectoItem = Gdx.audio.newSound(Gdx.files.internal("audio/item.wav"));

    //Estado
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    //Nivel
    float dificultad = 0f;
    float tiempoCambiarNivel = 0f;
    int nivel = 1;

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
                if (gameLauncher.sfx) {
                    efectoClick.play(0.1f);
                }

                if (estadoJuego == EstadoJuego.JUGANDO) {
                    estadoJuego = EstadoJuego.PAUSADO;
                    musicaFondo.pause();
                } else {
                    estadoJuego = EstadoJuego.JUGANDO;
                    if (gameLauncher.music) {
                        cargarMusica();
                    }
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
        if (gameLauncher.music) {
            cargarMusica();
        }
        InputProcessor inputProcessorOne = escenaHUD;
        InputProcessor inputProcessorTwo = new ProcesadorEntrada();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    private void cargarMusica() {
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.2f);
        musicaFondo.play();
    }

    private void crearMeteoro() {
        double probabilidad = Math.random();
        if (probabilidad < .7 - 0.7*dificultad) {
            meteoros.add(new Meteoro(meteoroC.get((int) Math.floor(Math.random() * meteoroC.size())),
                    (float) (Math.random() * ANCHO), 15 + Math.round(45*dificultad),
                    150 + 150*dificultad));
        } else if (probabilidad < .9- .9*dificultad) {
            meteoros.add(new Meteoro(meteoroM.get((int) Math.floor(Math.random() * meteoroM.size())),
                    (float) (Math.random() * ANCHO), 30 + Math.round(70*dificultad),
                    150 + 150*dificultad));
        } else {
            meteoros.add(new Meteoro(meteoroG.get((int) Math.floor(Math.random() * meteoroG.size())),
                    (float) (Math.random() * ANCHO), 45 + Math.round(100*dificultad),
                    150 + 150*dificultad));
        }
    }

    private void crearMarcador() {
        marcador = new Marcador(ANCHO * 0.1f, ALTO * 0.95f, ANCHO*0.8f, ALTO*0.95f);
    }

    private void crearNave() {
        float x = ANCHO/2 - texturaNave.getWidth()/2;
        float y = 360;
        nave = new Nave(texturaNave, x, y);
    }

    private void cargarTexturas() {
        texturaBala = new Texture("Bullet.png");
        texturaNave = new Texture("Player.png");
        meteoroC.add(new Texture("asteroides/pequeno-0.png"));
        meteoroC.add(new Texture("asteroides/pequeno-1.png"));
        meteoroC.add(new Texture("asteroides/pequeno-2.png"));
        meteoroC.add(new Texture("asteroides/pequeno-3.png"));
        meteoroC.add(new Texture("asteroides/pequeno-4.png"));
        meteoroM.add(new Texture("asteroides/medio-0.png"));
        meteoroM.add(new Texture("asteroides/medio-1.png"));
        meteoroM.add(new Texture("asteroides/medio-2.png"));
        meteoroM.add(new Texture("asteroides/medio-3.png"));
        meteoroM.add(new Texture("asteroides/medio-4.png"));
        meteoroG.add(new Texture("asteroides/Grande-0.png"));
        meteoroG.add(new Texture("asteroides/Grande-1.png"));
        meteoroG.add(new Texture("asteroides/Grande-2.png"));
        meteoroG.add(new Texture("asteroides/Grande-3.png"));
        meteoroG.add(new Texture("asteroides/Grande-4.png"));
        texturaEscudo = new Texture("items/Shield.png");
        texturaDaño = new Texture("items/Damage.png");
        texturaVida = new Texture("items/Health.png");
    }

    @Override
    public void render(float delta) {
        if (estadoJuego == EstadoJuego.JUGANDO) {
            actualizar(delta);
        }

        gameTime += delta;
        if (gameTime > 1f && estadoJuego == EstadoJuego.JUGANDO) {
            crearMeteoro();
            gameTime = 0;
        }

        tiempoCambiarNivel += delta;
        if(tiempoCambiarNivel >= 20f){
            dificultad += 0.05;
            tiempoCambiarNivel = 0f;
            marcador.incrementarNivel();
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
        // recargar disparo y dash
        nave.recargarDisparo(delta);
        nave.recargarDash(delta);

        // colisiones y movimiento
        if (nave.getVida() >  0) {
            nave.mover(pad, delta);

            // colisiones items
            for(int i = items.size()-1 ; i >= 0; i--){
                Item item = items.get(i);
                if(nave.sprite.getBoundingRectangle().overlaps(item.sprite.getBoundingRectangle())){
                    item.darBonus(nave);
                    if (gameLauncher.sfx) {
                        efectoItem.play(0.1f);
                    }
                    items.remove(i);
                }
            }

            // mover balas
            for (Bala bala : balas) bala.mover(delta);

            // colisiones meteoros con balas y nave y salir de la pantalla.
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
                        meteoro.disminuirVida(nave.getDaño());
                        marcador.incrementarPuntos(meteoro.getDaño());
                        balas.remove(bala);
                        Gdx.app.log("Vida Meteoro", Integer.toString(meteoro.getVida()));
                        if (meteoro.getVida() <= 0)
                        {
                            crearItem(meteoro.sprite.getX(), meteoro.sprite.getY());
                            Gdx.app.log("Vida Meteoro", Integer.toString(meteoro.getVida()));
                            meteoros.remove(meteoro);
                        }

                    }
                }
                if (nave.sprite.getBoundingRectangle().overlaps(meteoro.sprite.getBoundingRectangle())) {
                    if (!nave.getEscudo()) {
                        nave.disminuirVida(meteoro.getDaño());
                        Gdx.app.log("Life RGB", Integer.toString(Math.round(255 * (float)nave.getVida() / 100)));
                    } else {
                        nave.setEscudo(false);
                        nave.disminuirVida(0);
                    }
                    meteoros.remove(meteoro);
                    if (gameLauncher.sfx) {
                        efectoDaño.play(0.1f);
                    }
                    meteoros.remove(meteoro);
                }
            }
        }
        else {
            terminarJuego();
        }
    }

    private void crearItem(float x, float y){
        Item item;
        if (Math.random()<= 1){
            double r = Math.random();
            if(r < 0.1){
                item = new Reparacion(texturaVida, x, y);
            } else if(r < 0.3){
                item = new Escudo(texturaEscudo, x, y);
            } else if(r < 0.65){
                item = new PoderDeAtaque(texturaDaño, x, y);
            } else{
                item = new VelocidadDeAtaque(texturaDaño, x, y);
            }
            items.add(item);
        }
    }

    private void terminarJuego() {
        meteoros.clear();
        balas.clear();
        musicaFondo.stop();
        gameLauncher.setScreen(new PantallaPerdiste(gameLauncher, marcador.getPoints()));
    }

    private void dibujarSprites() {
        nave.render(batch);
        for (Bala bala: balas){
            bala.render(batch);
        }
        for (Meteoro meteoro : meteoros) {
            meteoro.render(batch);
        }
        for(Item item : items){
            item.render(batch);
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
            if (v.x > ANCHO / 2 && estadoJuego == EstadoJuego.JUGANDO) {
                if(v.y < ALTO/2){
                    if(nave.puedeDisparar) {
                        disparar();
                        if (gameLauncher.sfx) { efectoLaser.play(0.1f); }
                        nave.puedeDisparar = false;
                        nave.setTiempoDeRecargaDisparo(0);
                    }
                } else if(nave.dashRecargado){
                    nave.hacerDash();
                }
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
