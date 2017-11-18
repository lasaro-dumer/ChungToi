package chungtoi.server.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import chungtoi.server.Match;
import java.util.LinkedList;
import static org.mockito.Mockito.*;

public class MatchTests extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MatchTests(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MatchTests.class);
    }

    public void test_FirstLineVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("CCC......");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_FirstLineVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("EEE......");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_SecondLineVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("...CCC...");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_SecondLineVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("...EEE...");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_ThirdLineVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("......CCC");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_ThirdLineVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("......EEE");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_FirstColumnVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("C..C..C..");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_FirstColumnVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("E..E..E..");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_SecondColumnVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn(".C..C..C.");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_SecondColumnVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn(".E..E..E.");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_ThirdColumnVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("..C..C..C");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_ThirdColumnVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("..E..E..E");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_MainDiagonalVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("C...C...C");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_MainDiagonalVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("E...E...E");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }

    public void test_SecondaryDiagonalVictory_ForWhite(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("..C.C.C..");
        assertTrue(matchSpy.isMyTurn(1) == 2);
    }

    public void test_SecondaryDiagonalVictory_ForBlack(){
        Match match = new Match(1,"1C",2,"2E");
        Match matchSpy = spy(match);
        when(matchSpy.getBoard()).thenReturn("..E.E.E..");
        assertTrue(matchSpy.isMyTurn(2) == 2);
    }
}
