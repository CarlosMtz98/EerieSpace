package mx.itesm.eeriespace;

import com.badlogic.gdx.Game;
public class Launcher extends Game {
    @Override
    public void create() {
        setScreen(new PantallaMenu(this));
    }
}
