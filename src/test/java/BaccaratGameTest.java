import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaccaratGameTest
{
    static BaccaratGame g1 = new BaccaratGame();
    static BaccaratGame g2 = new BaccaratGame();
    static BaccaratGame g3 = new BaccaratGame();
    static BaccaratGame g4 = new BaccaratGame();
//    static BaccaratGameLogic gl1 = new BaccaratGameLogic();

    @BeforeAll
    void setup()
    {
        g1.currentBet = 20;
        g1.totalWinnings = 0;

        g2.currentBet = 50;
        g2.totalWinnings = 0;

        g3.currentBet = 70;
        g3.totalWinnings = 0;

        g4.currentBet = 100;
        g4.totalWinnings = 0;
    }

    @Test
    void testStartGame()
    {
        g2.startGame();

        assertTrue(g2.theDealer.deckSize() < 52);
        assertTrue(g2.playerHand.size() >= 2 || g2.playerHand.size() < 4);
        assertTrue(g2.bankerHand.size() >= 2 || g2.bankerHand.size() < 4);
    }

    @Test
    void testStartGame2()
    {
        g3.startGame();

        assertTrue(g3.theDealer.deckSize() < 52);
        assertTrue(g3.playerHand.size() >= 2 || g3.playerHand.size() < 4);
        assertTrue(g3.bankerHand.size() >= 2 || g3.bankerHand.size() < 4);
    }

    @Test
    void testEvaluateWinnings()
    {
        double temp = g1.evaluateWinnings();
        assertTrue((temp > 0 || temp < 0));
    }

    @Test
    void testEvaluateWinnings2()
    {
        double temp2 = g4.evaluateWinnings();
        assertTrue((temp2 > 0 || temp2 < 0));
    }
}
