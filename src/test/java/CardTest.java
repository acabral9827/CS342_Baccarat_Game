import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CardTest
{
    static Card c1, c2;

    @BeforeAll
    static void setup()
    {
        c1 = new Card("Hearts", 9);
        c2 = new Card("Clubs", 2);
    }

    @Test
    void testConstructor()
    {
        assertEquals("Hearts", c1.suite);
        assertEquals(9, c1.value);
        assertEquals("Clubs", c2.suite);
        assertEquals(2, c2.value);
    }
}
