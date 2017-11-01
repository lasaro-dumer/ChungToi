package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Movement;

public class TwoStepMovementTest extends TestCase{
    private int movement = 2;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TwoStepMovementTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TwoStepMovementTest.class);
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
     * Test two UP_LEFT movement from position 0 through 8
     */
    public void test_Two_UP_LEFT_from_0_to_8() {
        int direction = Movement.UP_LEFT.getCode();
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        Movement m8 = validDiagonal(8, direction, movement);
        assertTrue(m8.getNewPosition() == 0);
    }

    /**
     * Test two UP movement from position 0 through 8
     */
    public void test_Two_UP_from_0_to_8() {
        int direction = Movement.UP.getCode();
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        Movement m6 = validPerpendicular(6, direction, movement);
        assertTrue(m6.getNewPosition() == 0);
        invalidDiagonal(6, direction, movement);
        Movement m7 = validPerpendicular(7, direction, movement);
        assertTrue(m7.getNewPosition() == 1);
        invalidDiagonal(7, direction, movement);
        Movement m8 = validPerpendicular(8, direction, movement);
        assertTrue(m8.getNewPosition() == 2);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two UP_RIGHT movement from position 0 through 8
     */
    public void test_Two_UP_RIGHT_from_0_to_8() {
        int direction = Movement.UP_RIGHT.getCode();
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        Movement m6 = validDiagonal(6, direction, movement);
        assertTrue(m6.getNewPosition() == 2);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two LEFT movement from position 0 through 8
     */
    public void test_Two_LEFT_from_0_to_8() {
        int direction = Movement.LEFT.getCode();
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        Movement m2 = validPerpendicular(2, direction, movement);
        assertTrue(m2.getNewPosition() == 0);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        Movement m5 = validPerpendicular(5, direction, movement);
        assertTrue(m5.getNewPosition() == 3);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        Movement m8 = validPerpendicular(8, direction, movement);
        assertTrue(m8.getNewPosition() == 6);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two STILL movement from position 0 through 8
     */
    public void test_Two_STILL_from_0_to_8() {
        int direction = Movement.STILL.getCode();
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
     * Test two RIGHT movement from position 0 through 8
     */
    public void test_Two_RIGHT_from_0_to_8() {
        int direction = Movement.RIGHT.getCode();
        Movement m0 = validPerpendicular(0, direction, movement);
        assertTrue(m0.getNewPosition() == 2);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        Movement m3 = validPerpendicular(3, direction, movement);
        assertTrue(m3.getNewPosition() == 5);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        Movement m6 = validPerpendicular(6, direction, movement);
        assertTrue(m6.getNewPosition() == 8);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two DOWN_LEFT movement from position 0 through 8
     */
    public void test_Two_DOWN_LEFT_from_0_to_8() {
        int direction = Movement.DOWN_LEFT.getCode();
        invalidPerpendicular(0, direction, movement);
        invalidDiagonal(0, direction, movement);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        Movement m2 = validDiagonal(2, direction, movement);
        assertTrue(m2.getNewPosition() == 6);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two DOWN movement from position 0 through 8
     */
    public void test_Two_DOWN_from_0_to_8() {
        int direction = Movement.DOWN.getCode();
        Movement m0 = validPerpendicular(0, direction, movement);
        assertTrue(m0.getNewPosition() == 6);
        invalidDiagonal(0, direction, movement);
        Movement m1 = validPerpendicular(1, direction, movement);
        assertTrue(m1.getNewPosition() == 7);
        invalidDiagonal(1, direction, movement);
        Movement m2 = validPerpendicular(2, direction, movement);
        assertTrue(m2.getNewPosition() == 8);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
        invalidPerpendicular(5, direction, movement);
        invalidDiagonal(5, direction, movement);
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test two DOWN_RIGHT movement from position 0 through 8
     */
    public void test_Two_DOWN_RIGHT_from_0_to_8() {
        int direction = Movement.DOWN_RIGHT.getCode();
        invalidPerpendicular(0, direction, movement);
        Movement m0 = validDiagonal(0, direction, movement);
        assertTrue(m0.getNewPosition() == 8);
        invalidPerpendicular(1, direction, movement);
        invalidDiagonal(1, direction, movement);
        invalidPerpendicular(2, direction, movement);
        invalidDiagonal(2, direction, movement);
        invalidPerpendicular(3, direction, movement);
        invalidDiagonal(3, direction, movement);
        invalidPerpendicular(4, direction, movement);
        invalidDiagonal(4, direction, movement);
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
