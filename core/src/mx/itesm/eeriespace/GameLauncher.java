package mx.itesm.eeriespace;

import com.badlogic.gdx.Game;
public class GameLauncher extends Game {

    protected boolean music;
    protected boolean sfx;

    @Override
    public void create() {
        setScreen(new PantallaMenu(this));
    }
}
