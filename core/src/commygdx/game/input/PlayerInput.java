package commygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInput {




    public static boolean left(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            return true;
        }return false;
    }

    public static boolean right(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            return true;
        }return false;
    }

    public static boolean up(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            return true;
        }return false;
    }

    public static boolean down(){
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            return true;
        }return false;
    }

    public static boolean arrest(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            return true;
        }return false;
    }
}
