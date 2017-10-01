/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lásaro
 */
public enum Movement {
    UP_LEFT(0, false), UP(1, true), UP_RIGHT(2, false), LEFT(3, true),
    STILL(4, true),
    RIGHT(5, true), DOWN_RIGHT(6, false), DOWN(7, true), DOWN_LEFT(8, false), INVALID(-1, false);

    private static Map map = null;
    private int code;
    private boolean perpendicular;

    private Movement(int code, boolean perpendicular) {
        this.code = code;
        this.perpendicular = perpendicular;
    }

    public static Movement valueOfCode(int code) {
        synchronized (Movement.class) {
            if (map == null) {
                map = new HashMap();
                for (Movement v : values()) {
                    map.put(v.code, v);
                }
            }
        }
        Movement result = (Movement) map.get(code);
        if (result == null) {
            throw new IllegalArgumentException("No enum const " + Movement.class + "@code." + code);
        }

        return result;
    }

    public static Movement calculate(int currentPosition, int currOrientation, int direction, int movement) {
        Movement ret = Movement.valueOfCode(direction);
        if (ret != INVALID) {
            if ((ret.perpendicular && (currOrientation == 0))
                    || (!ret.perpendicular && (currOrientation == 1))) {
                switch(ret){
                    case UP_LEFT:
                        
                        break;
                    case UP:
                        break;
                    case UP_RIGHT:
                        break;
                    case LEFT:
                        break;
                    case RIGHT:
                        break;
                    case DOWN_RIGHT:
                        break;
                    case DOWN:
                        break;
                    case DOWN_LEFT:
                        break;
                }
            } else {
                ret = INVALID;
            }
        }
        return ret;
    }
}
