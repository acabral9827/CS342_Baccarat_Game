import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaccaratInfo implements Serializable
{
    Socket serverSocket;
    int port;
    int clientNum = 0;


    double currentBet = 0;
    String pickedBet = "default";
    boolean playAgain = false;
    boolean bankerWon = false;
    boolean playerWon = false;
    String whoWon = "";
    boolean draw = false;



    ArrayList<Integer> playerValues = new ArrayList<>();
    ArrayList<Integer> bankerValues = new ArrayList<>();


    ArrayList<Integer> playerHandTrueValues = new ArrayList<>();
    ArrayList<Integer> bankerHandTrueValues = new ArrayList<>();

    void newGame(){
        playerHandTrueValues.clear();
        bankerHandTrueValues.clear();
        currentBet = 0;
        playerValues.clear();
        bankerValues.clear();
        pickedBet = "default";
        playAgain = false;
        bankerWon = false;
        playerWon = false;
        draw = false;
        whoWon = "";
    }


}
