package commygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import commygdx.game.Screens.GameOverScreen;
import commygdx.game.Screens.IntroScreen;
import commygdx.game.Screens.PlayScreen;

public class AuberGame extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static final int V_WIDTH=2560;
	public static final int V_HEIGHT=1440;
	public String onTeleport;
	private Screen screen;
	//game state -1= intro screen 0=exitintroscreen 1=playing 2=win 3=lost
	public int gameState;
	private Screen introScreen;
	private GameOverScreen gameOverScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		introScreen=new IntroScreen(this);
		screen=new PlayScreen(this);
		gameOverScreen=new GameOverScreen(this);
		setScreen(introScreen);
		onTeleport="false";
		gameState=-1;
	}

	@Override
	public void render () {
		super.render();
		//exit intro screen and start game
		if (gameState==0){

			setScreen(screen);
			gameState=1;
		}

		//when game over go to gameoverscreen
		if (gameState==2 || gameState==3){
			setScreen(gameOverScreen);

		}

		if (onTeleport!="true" && onTeleport!="false"){
			//exit teleport screen
			setScreen(screen);

		}

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void setGameState(int gameState){
		this.gameState=gameState;
	}
}
