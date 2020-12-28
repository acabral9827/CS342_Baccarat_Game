import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BaccaratGame {
    ArrayList<Card> playerHand = new ArrayList<>();
    ArrayList<Card> bankerHand = new ArrayList<>();
    BaccaratDealer theDealer;
    double currentBet = 0;
    double totalWinnings = 0;


    // our own variable to get who the player bet on //
    String pickedBet;

    public void startGame()
    {
        // Initialize the deck
        theDealer = new BaccaratDealer();



        theDealer.generateDeck();
        theDealer.shuffleDeck();
        playerHand.clear();
        bankerHand.clear();



        // Get the player's hand
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
        boolean didPlayerDraw = false;
        Card playerDrawnCard = new Card("",-1);
        Card bankerDrawnCard = new Card("",-1);

        // check if either player or banker need to draw another card
        if ( BaccaratGameLogic.evaluatePlayerDraw(playerHand) == true )
        {
            playerDrawnCard = theDealer.drawOne();
            playerHand.add(playerDrawnCard);
        }

        if ( BaccaratGameLogic.evaluateBankerDraw(bankerHand, playerDrawnCard) == true )
        {
            bankerDrawnCard = theDealer.drawOne();
            bankerHand.add(bankerDrawnCard);
        }
    }

    // determines who won the bet: the player or the banker
    // returns amount won or lost based on currentBet
    public double evaluateWinnings()
    {
        // Get the player's bet
//        BaccaratInfo gameInfo = new BaccaratInfo();
//        String pickedBet = gameInfo.betPicked;
        startGame();

        String winner = BaccaratGameLogic.whoWon(playerHand, bankerHand);


        //check who the player bet on and do results based off that
        // "Player won"
        if (winner.equals(pickedBet))
        {
            totalWinnings += currentBet;
        }
        // else player bet incorrectly
        else
        {
            totalWinnings = -1 * currentBet;
            currentBet = -1 * currentBet;
        }

        return currentBet;
    }
    public ArrayList<Integer> returnTheTrueValuesOfPlayer()
    {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (Card x:playerHand){

            returnList.add(x.cardTrueVal);
        }
        return returnList;
    }
    public ArrayList<Integer> returnTheTrueValuesOfBanker()
    {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (Card x:bankerHand){
            returnList.add(x.cardTrueVal);
        }
        return returnList;
    }
    public ArrayList<Integer> returnTheValuesOfPlayer()
    {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (Card x:playerHand){

            returnList.add(x.value);
        }
        return returnList;
    }
    public ArrayList<Integer> returnTheValuesOfBanker()
    {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (Card x:bankerHand){
            returnList.add(x.value);
        }
        return returnList;
    }

}
