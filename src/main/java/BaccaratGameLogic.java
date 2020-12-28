import java.util.ArrayList;

public class BaccaratGameLogic
{
    // evaluates who won the game based on the given hands
    // the hand closer to 9 wins
    // if the value is the same it is a draw
    public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2)
    {
        //hand1 is player
        //hand2 is banker
        int hand1Points = handTotal(hand1);
        int hand2Points = handTotal(hand2);

        //check for natural win
//check for natural win
        if ( (hand1Points == 9 || hand2Points == 9) ||
                (hand1Points == 8 || hand1Points == 8) &&
                        (hand1.size() == 2 || hand2.size() == 2))
        {
            if (hand1Points == 9 && hand2Points == 9)
            {
                return "Draw";
            }
            else if (hand1Points == 9)
            {
                return "Player";
            }
            else
            {
                return "Banker";
            }
        }

        if (hand1Points > 9 || hand2Points > 9)
        {
            if (hand1Points > 9)
            {
                hand1Points = hand1Points - 10;
            }
            if (hand2Points > 9)
            {
                hand2Points = hand2Points - 10;
            }
        }

        // find which hand is closer to 9
        hand1Points = hand1Points - 9;
        hand2Points = hand2Points - 9;

        // player had smaller distance
        if (hand1Points > hand2Points)
        {
            return "Player";
        }
        // banker had smaller distance
        else if (hand1Points < hand2Points)
        {
            return "Banker";
        }
        // points are equal
        else
        {
            return "Draw";
        }
    }

    // gives back the value of the handPoints entered
    public static int handTotal(ArrayList<Card> hand)
    {
        int handPoints = 0;
        //will go through the arrayList and grab the value of each card.
        for(int i = 0; i < hand.size(); i++)
        {
            handPoints += (hand.get(i)).value;
        }
        return handPoints;
    }

    // evaluates whether the banker draws another card
    public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)
    {
        // get points for banker hand
        int bankerPoints = handTotal(hand);

        // Conditionals based on game rules:
        //  7 or more, no more cards are dealt
        if ( bankerPoints >= 7 )
        {
            return false;
        }
        //  total 2 or less, Banker gets one additional card
        else if ( bankerPoints <= 2 )
        {
            return true;
        }
        // total 3, 4, 5, or 6, it depends on if The Player drew another card
        // and if so, the value of that card to determine if The Banker receives another card.
        else
        {
            // if bankerPoints = 3, then playerPoints need to be either 0 or 1 or none drawn
            if (bankerPoints == 3 && (playerCard.value == -1 ||playerCard.value == 0 || playerCard.value == 1))
            {
                return true;
            }
            // if bankerPoints = 4, then playerPoints need to be either none drawn, or 2 - 7
            else if (bankerPoints == 4 && (playerCard.value == -1 || playerCard.value == 2 || playerCard.value == 3 ||
                    playerCard.value == 4 || playerCard.value == 5 || playerCard.value == 6 ||
                    playerCard.value == 7))
            {
                return true;
            }
            // if bankerPoints = 5, then playerPoints need to be either none drawn, or 4 - 7
            else if (bankerPoints == 5 && (playerCard.value == -1 || playerCard.value == 4 || playerCard.value == 5 ||
                    playerCard.value == 6 || playerCard.value == 7))
            {
                return true;
            }
            // if bankerPoints = 6, then playerPoints need to be either 6 or 7
            else if (bankerPoints == 6 && (playerCard.value == 6 || playerCard.value == 7))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    // evaluates whether the player draws another card
    public static boolean evaluatePlayerDraw(ArrayList<Card> hand)
    {
        // get player's points
        int playerPoints = handTotal(hand);

        // Based on game rules:
        //  totals to 5 or less, The Player gets one more card
        if ( playerPoints <= 5 )
        {
            return true;
        }
        return false;
    }
}
