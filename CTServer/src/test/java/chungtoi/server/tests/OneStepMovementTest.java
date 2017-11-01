package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Movement;

public class OneStepMovementTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OneStepMovementTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(OneStepMovementTest.class);
    }

    private void invalidPerpendicular(int currentPosition, int direction, int movement){
        assertTrue(Movement.processMovement(currentPosition, Movement.PERPENDICULAR_VALUE, direction, movement) == Movement.INVALID);
    }

    private void invalidDiagonal(int currentPosition, int direction, int movement){
        assertTrue(Movement.processMovement(currentPosition, Movement.DIAGONAL_VALUE, direction, movement) == Movement.INVALID);
    }

    private Movement validPerpendicular(int currentPosition, int direction, int movement){
        Movement move = Movement.processMovement(currentPosition, Movement.PERPENDICULAR_VALUE, direction, movement);
        assertTrue(move != Movement.INVALID);
        return move;
    }

    private Movement validDiagonal(int currentPosition, int direction, int movement){
        Movement move = Movement.processMovement(currentPosition, Movement.DIAGONAL_VALUE, direction, movement);
        assertTrue(move != Movement.INVALID);
        return move;
    }

    /**
     * Test one UP_LEFT movement from position 0 through 8
     */
    public void test_One_UP_LEFT_from_0_to_8() {
        int direction = Movement.UP_LEFT.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        Movement m4 = validDiagonal(4, direction, movement);
        assertTrue(m4.getNewPosition() == 0);
        invalidPerpendicular(5, direction, movement);
        Movement m5 = validDiagonal(5, direction, movement);
        assertTrue(m5.getNewPosition() == 1);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        Movement m7 = validDiagonal(7, direction, movement);
        assertTrue(m7.getNewPosition() == 3);
        invalidPerpendicular(8, direction, movement);
        Movement m8 = validDiagonal(8, direction, movement);
        assertTrue(m8.getNewPosition() == 4);
    }

    /**
     * Test one UP movement from position 0 through 8
     */
    public void test_One_UP_from_0_to_8() {
        int direction = Movement.UP.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        Movement m3 = validPerpendicular(3, direction, movement);
        assertTrue(m3.getNewPosition() == 0);
        invalidDiagonal(3, direction, movement);
        Movement m4 = validPerpendicular(4, direction, movement);
        assertTrue(m4.getNewPosition() == 1);
        invalidDiagonal(4, direction, movement);
        Movement m5 = validPerpendicular(5, direction, movement);
        assertTrue(m5.getNewPosition() == 2);
        invalidDiagonal(5, direction, movement);
        Movement m6 = validPerpendicular(6, direction, movement);
        assertTrue(m6.getNewPosition() == 3);
        invalidDiagonal(6, direction, movement);
        Movement m7 = validPerpendicular(7, direction, movement);
        assertTrue(m7.getNewPosition() == 4);
        invalidDiagonal(7, direction, movement);
        Movement m8 = validPerpendicular(8, direction, movement);
        assertTrue(m8.getNewPosition() == 5);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one UP_RIGHT movement from position 0 through 8
     */
    public void test_One_UP_RIGHT_from_0_to_8() {
        int direction = Movement.UP_RIGHT.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        Movement m3 = validDiagonal(3, direction, movement);
        assertTrue(m3.getNewPosition() == 1);
        invalidPerpendicular(4, direction, movement);
        Movement m4 = validDiagonal(4, direction, movement);
        assertTrue(m4.getNewPosition() == 2);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        Movement m6 = validDiagonal(6, direction, movement);
        assertTrue(m6.getNewPosition() == 4);
        invalidPerpendicular(7, direction, movement);
        Movement m7 = validDiagonal(7, direction, movement);
        assertTrue(m7.getNewPosition() == 5);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one LEFT movement from position 0 through 8
     */
    public void test_One_LEFT_from_0_to_8() {
        int direction = Movement.LEFT.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        Movement m1 = validPerpendicular(1, direction, movement);
        assertTrue(m1.getNewPosition() == 0);
        invalidDiagonal(1, direction, movement);
        Movement m2 = validPerpendicular(2, direction, movement);
        assertTrue(m2.getNewPosition() == 1);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        Movement m4 = validPerpendicular(4, direction, movement);
        assertTrue(m4.getNewPosition() == 3);
        invalidDiagonal(4, direction, movement);
        Movement m5 = validPerpendicular(5, direction, movement);
        assertTrue(m5.getNewPosition() == 4);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        Movement m7 = validPerpendicular(7, direction, movement);
        assertTrue(m7.getNewPosition() == 6);
        invalidDiagonal(7, direction, movement);
        Movement m8 = validPerpendicular(8, direction, movement);
        assertTrue(m8.getNewPosition() == 7);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one STILL movement from position 0 through 8
     */
    public void test_One_STILL_from_0_to_8() {
        int direction = Movement.STILL.getCode();
        int movement = 1;
        Movement m;
        m = validPerpendicular(0, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 0);
        m = validDiagonal(0, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 0);
        m = validPerpendicular(1, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 1);
        m = validDiagonal(1, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 1);
        m = validPerpendicular(2, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 2);
        m = validDiagonal(2, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 2);
        m = validPerpendicular(3, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 3);
        m = validDiagonal(3, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 3);
        m = validPerpendicular(4, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 4);
        m = validDiagonal(4, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 4);
        m = validPerpendicular(5, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 5);
        m = validDiagonal(5, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 5);
        m = validPerpendicular(6, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 6);
        m = validDiagonal(6, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 6);
        m = validPerpendicular(7, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 7);
        m = validDiagonal(7, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 7);
        m = validPerpendicular(8, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 8);
        m = validDiagonal(8, direction, movement);
        assertTrue(m == Movement.STILL);
        assertTrue(m.getNewPosition() == 8);
    }

    /**
     * Test one RIGHT movement from position 0 through 8
     */
    public void test_One_RIGHT_from_0_to_8() {
        int direction = Movement.RIGHT.getCode();
        int movement = 1;
        Movement m0 = validPerpendicular(0, direction, movement);
        assertTrue(m0.getNewPosition() == 1);
        invalidDiagonal(0, direction, movement);
        Movement m1 = validPerpendicular(1, direction, movement);
        assertTrue(m1.getNewPosition() == 2);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        Movement m3 = validPerpendicular(3, direction, movement);
        assertTrue(m3.getNewPosition() == 4);
        invalidDiagonal(3, direction, movement);
        Movement m4 = validPerpendicular(4, direction, movement);
        assertTrue(m4.getNewPosition() == 5);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        Movement m6 = validPerpendicular(6, direction, movement);
        assertTrue(m6.getNewPosition() == 7);
        invalidDiagonal(6, direction, movement);
        Movement m7 = validPerpendicular(7, direction, movement);
        assertTrue(m7.getNewPosition() == 8);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one DOWN_LEFT movement from position 0 through 8
     */
    public void test_One_DOWN_LEFT_from_0_to_8() {
        int direction = Movement.DOWN_LEFT.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        Movement m1 = validDiagonal(1, direction, movement);
        assertTrue(m1.getNewPosition() == 3);
        invalidPerpendicular(2, direction, movement);
        Movement m2 = validDiagonal(2, direction, movement);
        assertTrue(m2.getNewPosition() == 4);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        Movement m4 = validDiagonal(4, direction, movement);
        assertTrue(m4.getNewPosition() == 6);
        invalidPerpendicular(5, direction, movement);
        Movement m5 = validDiagonal(5, direction, movement);
        assertTrue(m5.getNewPosition() == 7);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one DOWN movement from position 0 through 8
     */
    public void test_One_DOWN_from_0_to_8() {
        int direction = Movement.DOWN.getCode();
        int movement = 1;
        Movement m0 = validPerpendicular(0, direction, movement);
        assertTrue(m0.getNewPosition() == 3);
        invalidDiagonal(0, direction, movement);
        Movement m1 = validPerpendicular(1, direction, movement);
        assertTrue(m1.getNewPosition() == 4);
        invalidDiagonal(1, direction, movement);
        Movement m2 = validPerpendicular(2, direction, movement);
        assertTrue(m2.getNewPosition() == 5);
        invalidDiagonal(2, direction, movement);
        Movement m3 = validPerpendicular(3, direction, movement);
        assertTrue(m3.getNewPosition() == 6);
        invalidDiagonal(3, direction, movement);
        Movement m4 = validPerpendicular(4, direction, movement);
        assertTrue(m4.getNewPosition() == 7);
        invalidDiagonal(4, direction, movement);
        Movement m5 = validPerpendicular(5, direction, movement);
        assertTrue(m5.getNewPosition() == 8);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test one DOWN_RIGHT movement from position 0 through 8
     */
    public void test_One_DOWN_RIGHT_from_0_to_8() {
        int direction = Movement.DOWN_RIGHT.getCode();
        int movement = 1;
        invalidPerpendicular(0, direction, movement);
        Movement m0 = validDiagonal(0, direction, movement);
        assertTrue(m0.getNewPosition() == 4);
        invalidPerpendicular(1, direction, movement);
        Movement m1 = validDiagonal(1, direction, movement);
        assertTrue(m1.getNewPosition() == 5);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        Movement m3 = validDiagonal(3, direction, movement);
        assertTrue(m3.getNewPosition() == 7);
        invalidPerpendicular(4, direction, movement);
        Movement m4 = validDiagonal(4, direction, movement);
        assertTrue(m4.getNewPosition() == 8);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }
}
