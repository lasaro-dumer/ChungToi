/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lásaro
 */
public enum Movement {
    UP_LEFT(0, false, -4), UP(1, true, -3), UP_RIGHT(2, false, -2), LEFT(3, true, -1),
    STILL(4, true, 0),
    RIGHT(5, true, 1), DOWN_LEFT(6, false, 2), DOWN(7, true, 3), DOWN_RIGHT(8, false, 4), INVALID(-1, false, 0);

    private static Map map = null;
    private int newPosition;
    private int code;
    private boolean perpendicular;
    private int factor;

    private Movement(int code, boolean perpendicular, int factor) {
        this.code = code;
        this.perpendicular = perpendicular;
        this.factor = factor;
    }

    public int getNewPosition() {
        return this.newPosition;
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

    private static void debugMovement(Movement ret,int currentPosition, int movement){
        System.out.println("Invalid: " + ret + "("+currentPosition+","+movement+")");
    }

    public static Movement processMovement(int currentPosition, int currOrientation, int direction, int movement) {
        Movement ret = Movement.valueOfCode(direction);
        if (ret != INVALID) {
            boolean moveIsValid = (movement >= 0 && movement <= 2) && !(ret == STILL && movement != 0) && !(ret != STILL && movement == 0);
            System.out.println("ret.perpendicular: " + ret.perpendicular + " currOrientation: " + currOrientation + " moveIsValid:"+moveIsValid);
            if (ret.perpendicular && (currOrientation == 0) && moveIsValid) {
                switch (ret) {
                    case UP:
                        if (!((movement == 2) && (currentPosition == 6 || currentPosition == 7 || currentPosition == 8))
                                && !(movement == 1 && (currentPosition == 3 || currentPosition == 4 || currentPosition == 5
                                || currentPosition == 6 || currentPosition == 7 || currentPosition == 8))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case LEFT:
                        if (!((movement == 2) && (currentPosition == 2 || currentPosition == 5 || currentPosition == 8))
                                && !(movement == 1 && (currentPosition == 1 || currentPosition == 2 || currentPosition == 4
                                || currentPosition == 5 || currentPosition == 7 || currentPosition == 8))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case STILL:
                        //already validated?
                        break;
                    case RIGHT:
                        if (!((movement == 2) && (currentPosition == 0 || currentPosition == 3 || currentPosition == 6))
                                && !(movement == 1 && (currentPosition == 0 || currentPosition == 1 || currentPosition == 3
                                || currentPosition == 4 || currentPosition == 6 || currentPosition == 7))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case DOWN:
                        if (!((movement == 2) && (currentPosition == 0 || currentPosition == 1 || currentPosition == 2))
                                && !(movement == 1 && (currentPosition == 0 || currentPosition == 1 || currentPosition == 2
                                || currentPosition == 3 || currentPosition == 4 || currentPosition == 5))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    default:
                        ret = INVALID;
                        break;
                }
            } else if (!ret.perpendicular && (currOrientation == 1) && moveIsValid) {
                switch (ret) {
                    case UP_LEFT:
                        if (!((movement == 2) && (currentPosition == 8))
                                && !(movement == 1 && (currentPosition == 4 || currentPosition == 5
                                || currentPosition == 7 || currentPosition == 8))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case UP_RIGHT:
                        if (!((movement == 2) && (currentPosition == 6))
                                && !(movement == 1 && (currentPosition == 3 || currentPosition == 4
                                || currentPosition == 6 || currentPosition == 7))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case STILL:
                        break;
                    case DOWN_LEFT:
                        if (!((movement == 2) && (currentPosition == 2))
                                && !(movement == 1 && (currentPosition == 1 || currentPosition == 2
                                || currentPosition == 4 || currentPosition == 5))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    case DOWN_RIGHT:
                        if (!((movement == 2) && (currentPosition == 0))
                                && !(movement == 1 && (currentPosition == 0 || currentPosition == 1
                                || currentPosition == 3 || currentPosition == 4))) {
                            debugMovement(ret, currentPosition, movement);
                            ret = INVALID;
                        }
                        break;
                    default:
                        break;
                }
            } else if(ret != STILL){
                ret = INVALID;
            }

            if (ret != INVALID) {
                ret.newPosition = evaluateMovement(currentPosition, movement, ret.factor);
            }
        }
        return ret;
    }

    private static int evaluateMovement(int currentPosition, int movement, int factor) {
        return currentPosition + (factor * movement);
    }
}
