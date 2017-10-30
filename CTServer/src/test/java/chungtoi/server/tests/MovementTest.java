package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Movement;

public class MovementTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MovementTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MovementTest.class);
    }

    /**
     * Test basic
     */
    public void test_Basic() {
        assertTrue(true);
    }
}
