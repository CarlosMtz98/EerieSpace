package mx.itesm.eeriespace;

import com.badlogic.gdx.Game;
public class GameLauncher extends Game {
    @Override
    public void create() {
        setScreen(new PantallaMenu(this));
    }
}
