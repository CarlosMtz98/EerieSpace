package mx.itesm.eeriespace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {
    private BitmapFont font;
    public Text(String file){
        font = new BitmapFont(Gdx.files.internal(file)); // Archivo .fnt
    }

    public void render(SpriteBatch batch, String label, float x, float y)
    {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, label);
    }
}
