package mx.itesm.eeriespace;

import com.badlogic.gdx.Game;

public class EerieSpace extends Game {

		@Override
		public void create () {
			setScreen(new PantallaMenu(this));
		}
//
	}

