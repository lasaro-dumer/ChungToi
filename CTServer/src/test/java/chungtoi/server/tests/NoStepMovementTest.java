package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Movement;

public class NoStepMovementTest extends TestCase{
    private int movement = 0;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NoStepMovementTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(NoStepMovementTest.class);
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
     * Test no move length UP_LEFT movement from position 0 through 8
     */
    public void test_No_UP_LEFT_from_0_to_8() {
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
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length UP movement from position 0 through 8
     */
    public void test_No_UP_from_0_to_8() {
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
        invalidPerpendicular(6, direction, movement);
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length UP_RIGHT movement from position 0 through 8
     */
    public void test_No_UP_RIGHT_from_0_to_8() {
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
        invalidDiagonal(6, direction, movement);
        invalidPerpendicular(7, direction, movement);
        invalidDiagonal(7, direction, movement);
        invalidPerpendicular(8, direction, movement);
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length LEFT movement from position 0 through 8
     */
    public void test_No_LEFT_from_0_to_8() {
        int direction = Movement.LEFT.getCode();
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
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length STILL movement from position 0 through 8
     */
    public void test_No_STILL_from_0_to_8() {
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
     * Test no move length RIGHT movement from position 0 through 8
     */
    public void test_No_RIGHT_from_0_to_8() {
        int direction = Movement.RIGHT.getCode();
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
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length DOWN_LEFT movement from position 0 through 8
     */
    public void test_No_DOWN_LEFT_from_0_to_8() {
        int direction = Movement.DOWN_LEFT.getCode();
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
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length DOWN movement from position 0 through 8
     */
    public void test_No_DOWN_from_0_to_8() {
        int direction = Movement.DOWN.getCode();
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
        invalidDiagonal(8, direction, movement);
    }

    /**
     * Test no move length DOWN_RIGHT movement from position 0 through 8
     */
    public void test_No_DOWN_RIGHT_from_0_to_8() {
        int direction = Movement.DOWN_RIGHT.getCode();
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
        invalidDiagonal(8, direction, movement);
    }
}
