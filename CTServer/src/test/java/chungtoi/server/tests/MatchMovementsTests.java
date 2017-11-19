package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Match;
import chungtoi.server.MatchState;
import java.util.LinkedList;
import static org.mockito.Mockito.*;

public class MatchMovementsTests extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MatchMovementsTests(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MatchMovementsTests.class);
    }

    public void test_NoMovement_UP_RIGHT_But_Changes_Orientation(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getMatchState()).thenReturn(MatchState.BLACK_TURN);
        when(matchSpy.canMovePieces(2)).thenReturn(true);
        when(matchSpy.getBoard()).thenReturn("C.cE..ECE");
        int moveResult = matchSpy.movePiece(2,6,2,0,1);
        assertTrue(moveResult == 1);
        reset(matchSpy);
        assertTrue(matchSpy.getBoard().equals("C.cE..eCE"));
    }

    public void test_NoMovement_And_NoOrientation_Change(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getMatchState()).thenReturn(MatchState.BLACK_TURN);
        when(matchSpy.canMovePieces(2)).thenReturn(true);
        when(matchSpy.getBoard()).thenReturn("C.cE..ECE");
        int moveResult = matchSpy.movePiece(2,6,2,0,0);
        assertTrue(moveResult == 0);
        reset(matchSpy);
        assertTrue(!matchSpy.getBoard().equals("C.cE..ECE"));
    }

    public void test_MoveTwoSteps_STILL_And_NoOrientation_Change(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getMatchState()).thenReturn(MatchState.WHITE_TURN);
        when(matchSpy.canMovePieces(1)).thenReturn(true);
        when(matchSpy.getBoard()).thenReturn("C.cE..eCE");
        // 100:7:4:2:0
        int moveResult = matchSpy.movePiece(1,7,4,2,0);
        assertTrue(moveResult == 0);
        reset(matchSpy);
        assertTrue(!matchSpy.getBoard().equals("C.cE..eCE"));
    }
}
