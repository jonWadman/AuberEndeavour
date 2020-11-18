package commygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AuberGame extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static final int V_WIDTH=2560;
	public static final int V_HEIGHT=1440;
	public String onTeleport;
	private Screen screen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		screen=new PlayScreen(this);
		setScreen(screen);
		onTeleport="false";
	}

	@Override
	public void render () {

		super.render();

		if (onTeleport!="true" && onTeleport!="false"){
			setScreen(screen);

		}

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
