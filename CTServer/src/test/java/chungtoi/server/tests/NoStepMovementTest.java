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

    public void shouldntMoveForGivenDirection(int direction){
        Movement m;
        for (int p=0; p<=8; p++) {
            m = validPerpendicular(p, direction, movement);
            String msg = String.format("Validating %s in position %s failed. Reason: ", direction, p);
            assertTrue(msg+" m!=STILL", m == Movement.STILL);
            assertTrue(msg+" p!=newPosition", m.getNewPosition() == p);
            m = validDiagonal(p, direction, movement);
            assertTrue(msg+" m!=STILL", m == Movement.STILL);
            assertTrue(msg+" p!=newPosition", m.getNewPosition() == p);
        }
    }

    /**
     * Test no move length UP_LEFT movement from position 0 through 8
     */
    public void test_No_UP_LEFT_from_0_to_8() {
        int direction = Movement.UP_LEFT.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length UP movement from position 0 through 8
     */
    public void test_No_UP_from_0_to_8() {
        int direction = Movement.UP.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length UP_RIGHT movement from position 0 through 8
     */
    public void test_No_UP_RIGHT_from_0_to_8() {
        int direction = Movement.UP_RIGHT.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length LEFT movement from position 0 through 8
     */
    public void test_No_LEFT_from_0_to_8() {
        int direction = Movement.LEFT.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length STILL movement from position 0 through 8
     */
    public void test_No_STILL_from_0_to_8() {
        int direction = Movement.STILL.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length RIGHT movement from position 0 through 8
     */
    public void test_No_RIGHT_from_0_to_8() {
        int direction = Movement.RIGHT.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length DOWN_LEFT movement from position 0 through 8
     */
    public void test_No_DOWN_LEFT_from_0_to_8() {
        int direction = Movement.DOWN_LEFT.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length DOWN movement from position 0 through 8
     */
    public void test_No_DOWN_from_0_to_8() {
        int direction = Movement.DOWN.getCode();
        shouldntMoveForGivenDirection(direction);
    }

    /**
     * Test no move length DOWN_RIGHT movement from position 0 through 8
     */
    public void test_No_DOWN_RIGHT_from_0_to_8() {
        int direction = Movement.DOWN_RIGHT.getCode();
        shouldntMoveForGivenDirection(direction);
    }
}
