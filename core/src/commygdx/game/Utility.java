package commygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Utility {
    private static final float DISTANCE_BUFFER = 10;

    public static final String[] rooms = {"brig","crew","infirmary","laboratory","command","engine"};

    public static int randomIntBelow(int n){
        return (int)(Math.floor(Math.random()*n));
    }

    public static boolean closeEnough(Vector2 pos1,Vector2 pos2){
        if(pos1.dst(pos2)< DISTANCE_BUFFER){
            return true;
        }
        return false;
    }
}
