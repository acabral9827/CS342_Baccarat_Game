import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaccaratGameLogicTest
{
    ArrayList<Card> ph1, ph2, ph3, ph4, ph5, ph6, ph7, ph8, ph9, ph10, ph11, bh1, bh2, bh3, bh4, bh5, bh6, bh7, bh8, bh9;

    @BeforeAll
    void setup()
    {
        ph1 = new ArrayList<>();
        ph2 = new ArrayList<>();
        ph3 = new ArrayList<>();
        ph4 = new ArrayList<>();
        ph5 = new ArrayList<>();
        ph6 = new ArrayList<>();
        ph7 = new ArrayList<>();
        ph8 = new ArrayList<>();
        ph9 = new ArrayList<>();
        ph10 = new ArrayList<>();
        ph11 = new ArrayList<>();

        bh1 = new ArrayList<>();
        bh2 = new ArrayList<>();
        bh3 = new ArrayList<>();
        bh4 = new ArrayList<>();
        bh5 = new ArrayList<>();
        bh6 = new ArrayList<>();
        bh7 = new ArrayList<>();
        bh8 = new ArrayList<>();
        bh9 = new ArrayList<>();
    }

    @Test
    void testWhoWon()
    {
        Card c1 = new Card("Hearts", 7);
        Card c2 = new Card("Hearts", 2);
        Card c3 = new Card("Hearts", 3);
        Card c4 = new Card("Hearts", 4);

        ph9.add(c1);
        ph9.add(c2);

        bh7.add(c3);
        bh7.add(c4);

        assertEquals("Player", BaccaratGameLogic.whoWon(ph9, bh7));

        ph9.clear();
        bh7.clear();
        Card c5 = new Card("Hearts", 3);
        Card c6 = new Card("Hearts", 3);
        Card c7 = new Card("Hearts", 3);
        Card c8 = new Card("Hearts", 2);

        ph9.add(c5);
        ph9.add(c6);

        bh7.add(c7);
        bh7.add(c8);
        assertEquals("Player", BaccaratGameLogic.whoWon(ph9, bh7));
    }

    @Test
    void testWhoWon2()
    {
        Card c1 = new Card("Hearts", 7);
        Card c2 = new Card("Hearts", 2);
        Card c3 = new Card("Hearts", 7);
        Card c4 = new Card("Hearts", 2);

        ph10.add(c1);
        ph10.add(c2);

        bh8.add(c3);
        bh8.add(c4);
        assertEquals("Draw", BaccaratGameLogic.whoWon(ph10, bh8));

        ph10.clear();
        bh8.clear();
        Card c5 = new Card("Hearts", 6);
        Card c6 = new Card("Hearts", 1);
        Card c7 = new Card("Hearts", 3);
        Card c8 = new Card("Hearts", 4);

        ph10.add(c5);
        ph10.add(c6);

        bh8.add(c7);
        bh8.add(c8);
        assertEquals("Draw", BaccaratGameLogic.whoWon(ph10, bh8));
    }

    @Test
    void testWhoWon3()
    {
        Card c1 = new Card("Hearts", 3);
        Card c2 = new Card("Hearts", 4);
        Card c3 = new Card("Hearts", 7);
        Card c4 = new Card("Hearts", 2);

        ph11.add(c1);
        ph11.add(c2);

        bh9.add(c3);
        bh9.add(c4);
        assertEquals("Banker", BaccaratGameLogic.whoWon(ph11, bh9));

        ph11.clear();
        bh9.clear();
        Card c5 = new Card("Hearts", 4);
        Card c6 = new Card("Hearts", 2);
        Card c7 = new Card("Hearts", 5);
        Card c8 = new Card("Hearts", 3);

        ph11.add(c1);
        ph11.add(c2);

        bh9.add(c3);
        bh9.add(c4);
        assertEquals("Banker", BaccaratGameLogic.whoWon(ph11, bh9));
    }

    @Test
    void testHandTotal()
    {
        Card c1 = new Card("Hearts", 7);
        Card c2 = new Card("Hearts", 2);
        Card c3 = new Card("Hearts", 3);
        Card c4 = new Card("Hearts", 4);

        ph1.add(c1);
        ph1.add(c2);

        bh1.add(c3);
        bh1.add(c4);

        assertEquals(9, BaccaratGameLogic.handTotal(ph1));
        assertEquals(7, BaccaratGameLogic.handTotal(bh1));
    }

    @Test
    void testHandTotal2()
    {
        Card c1 = new Card("Hearts", 0);
        Card c2 = new Card("Hearts", 5);
        Card c3 = new Card("Hearts", 4);
        Card c4 = new Card("Hearts", 4);

        ph2.add(c1);
        ph2.add(c2);

        bh2.add(c3);
        bh2.add(c4);

        assertEquals(5, BaccaratGameLogic.handTotal(ph2));
        assertEquals(8, BaccaratGameLogic.handTotal(bh2));
    }

    @Test
    void testEvaluateBankerDraw()
    {
        Card c1 = new Card("Hearts", 3);
        Card c2 = new Card("Hearts", 4);
        Card c3 = new Card("Hearts", 2);
        Card c4 = new Card("Hearts", 0);

        ph5.add(c1);
        ph5.add(c2);

        bh3.add(c3);
        bh3.add(c4);

        assertTrue(BaccaratGameLogic.evaluateBankerDraw(bh3, ph5.get(ph5.size()-1)));
    }

    @Test
    void testEvaluateBankerDraw2()
    {
        Card c1 = new Card("Hearts", 7);
        Card c2 = new Card("Hearts", 2);
        Card c3 = new Card("Hearts", 3);
        Card c4 = new Card("Hearts", 4);

        ph6.add(c1);
        ph6.add(c2);

        bh4.add(c3);
        bh4.add(c4);

        assertFalse(BaccaratGameLogic.evaluateBankerDraw(bh4, ph6.get(ph6.size()-1)));
    }

    @Test
    void testEvaluateBankerDraw3()
    {
        Card c1 = new Card("Hearts", 0);
        Card c2 = new Card("Hearts", 1);
        Card c3 = new Card("Hearts", 3);
        Card c4 = new Card("Hearts", 0);

        ph8.add(c1);
        ph8.add(c2);

        bh6.add(c3);
        bh6.add(c4);

        assertTrue(BaccaratGameLogic.evaluateBankerDraw(bh6, ph8.get(ph8.size()-1)));

        ph8.clear();
        bh6.clear();
        Card c5 = new Card("Hearts", 3);
        Card c6 = new Card("Hearts", 2);
        Card c7 = new Card("Hearts", 2);
        Card c8 = new Card("Hearts", 2);

        ph8.add(c5);
        ph8.add(c6);

        bh6.add(c7);
        bh6.add(c8);
        assertTrue(BaccaratGameLogic.evaluateBankerDraw(bh6, ph8.get(ph8.size()-1)));

        ph8.clear();
        bh6.clear();
        Card c9 = new Card("Hearts", 2);
        Card c10 = new Card("Hearts", 6);
        Card c11 = new Card("Hearts", 2);
        Card c12 = new Card("Hearts", 3);

        ph8.add(c9);
        ph8.add(c10);

        bh6.add(c11);
        bh6.add(c12);
        assertTrue(BaccaratGameLogic.evaluateBankerDraw(bh6, ph8.get(ph8.size()-1)));

        ph8.clear();
        bh6.clear();
        Card c13 = new Card("Hearts", 2);
        Card c14 = new Card("Hearts", 6);
        Card c15 = new Card("Hearts", 3);
        Card c16 = new Card("Hearts", 3);

        ph8.add(c13);
        ph8.add(c14);

        bh6.add(c15);
        bh6.add(c16);
        assertTrue(BaccaratGameLogic.evaluateBankerDraw(bh6, ph8.get(ph8.size()-1)));

        ph8.clear();
        bh6.clear();
        Card c17 = new Card("Hearts", 2);
        Card c18 = new Card("Hearts", -1);
        Card c19 = new Card("Hearts", 3);
        Card c20 = new Card("Hearts", 3);

        ph8.add(c17);
        ph8.add(c18);

        bh6.add(c19);
        bh6.add(c20);
        assertFalse(BaccaratGameLogic.evaluateBankerDraw(bh6, ph8.get(ph8.size()-1)));
    }

    @Test
    void testEvaluatePlayerDraw()
    {
        Card c1 = new Card("Hearts", 7);
        Card c2 = new Card("Hearts", 2);

        ph3.add(c1);
        ph3.add(c2);

        assertFalse(BaccaratGameLogic.evaluatePlayerDraw(ph3));
    }

    @Test
    void testEvaluatePlayerDraw2()
    {
        Card c1 = new Card("Hearts", 2);
        Card c2 = new Card("Hearts", 2);

        ph4.add(c1);
        ph4.add(c2);

        assertTrue(BaccaratGameLogic.evaluatePlayerDraw(ph4));
    }
}
