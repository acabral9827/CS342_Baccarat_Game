import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class BaccaratDealerTest {

    static BaccaratDealer d1, d2, d3, d4, d5, d6, d7, d8;

    @BeforeAll
    static void setUp()
    {
        d1 = new BaccaratDealer();
        d1.generateDeck();

        d2 = new BaccaratDealer();
        d2.generateDeck();

        d3 = new BaccaratDealer();
        d3.generateDeck();

        d4 = new BaccaratDealer();
        d4.generateDeck();

        d5 = new BaccaratDealer();
        d5.generateDeck();

        d6 = new BaccaratDealer();
        d6.generateDeck();

        d7 = new BaccaratDealer();
        d7.generateDeck();

        d8 = new BaccaratDealer();
        d8.generateDeck();
    }

    // Tests the constructor generateDeck()
    @Test
    void testGenerateDeck()
    {
        assertEquals("Hearts",d1.deck.get(0).suite, "wrong value");
    }

    // Tests the dealHand() method
    @Test
    void testDealHand()
    {
        d2.shuffleDeck();
        ArrayList<Card> handGiven = d2.dealHand();
        assertEquals(2, handGiven.size());
    }

    @Test
    void testDealHand2()
    {
        d3.shuffleDeck();
        ArrayList<Card> handGiven = d3.dealHand();
        assertEquals(2, handGiven.size());
    }

    // Tests the drawOne() method
    @Test
    void testDrawOne()
    {
        Card cardDrawn = d4.drawOne();
        assertEquals(51, d4.deckSize());
        assertEquals("Clubs", cardDrawn.suite);
    }

    @Test
    void testDrawOne2()
    {
        Card cardDrawn = d7.drawOne();
        assertEquals(51, d7.deckSize());
        assertEquals("Clubs", cardDrawn.suite);
    }

    // Tests shuffleDeck() method
    @Test
    void testShuffleDeck()
    {
        d6.shuffleDeck();
        assertNotSame(d6, d5);
    }

    @Test
    void testShuffleDeck2()
    {
        d8.shuffleDeck();
        assertNotSame(d8, d6);
    }

    // Tests the deckSize method
    @Test
    void testDeckSize()
    {
        assertEquals(52, d1.deckSize());
    }

    @Test
    void testDeckSize2()
    {
        assertEquals(52, d8.deckSize());
    }

}
