import java.util.ArrayList;
import java.util.*;

public class BaccaratDealer
{
    ArrayList<Card> deck = new ArrayList<Card>();

    // Generates a new standard 52 card deck
    public void generateDeck()
    {
        // Array of all suites
        int count = 0;

        ArrayList<String> suitesArray = new ArrayList<String>();
        suitesArray.add("Hearts");
        suitesArray.add("Spades");
        suitesArray.add("Diamonds");
        suitesArray.add("Clubs");

        // Loop through adding a set of each suite
        for(int i = 0; i < 4; i++)
        {
            // get current suite
            String currentSuite = suitesArray.get(i);

            // cards A-K in suite
            for(int j = 0; j < 13; j++)
            {
                int theValue = j;



                // add card to the deck
                Card newCard = new Card(currentSuite,theValue+1);
                if (newCard.value > 9)
                {
                    newCard.value = 0;
                }
                newCard.cardTrueVal = count;
                count++;

//                System.out.println(newCard.cardTrueVal);
                deck.add(newCard);
            }
        }
    }

    // Deals 2 cards and return them in an ArrayList<Card>
    public ArrayList<Card> dealHand()
    {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(drawOne());
        hand.add(drawOne());

        return hand;
    }

    // Deals a single card and return it
    public Card drawOne()
    {
        // Check deck is generated
        if (deck.isEmpty())
        {
            generateDeck();
            shuffleDeck();
        }

        Card drawnCard;
        int x = deck.size()-1;
        drawnCard = deck.get(x);
        deck.remove(x);
        return drawnCard;
    }

    // Randomizes the cards in the deck
    public void shuffleDeck()
    {
        Collections.shuffle(deck, new Random());
    }

    // Returns how many cards are in the deck
    public int deckSize()
    {
        return deck.size();
    }
}
