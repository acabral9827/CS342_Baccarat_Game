import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class server{
    int altCount = 0;
    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    BaccaratInfo gameInfo = new BaccaratInfo();
    BaccaratInfo initGameInfo = new BaccaratInfo();
    BaccaratGame game = new BaccaratGame();
    HashMap<Integer, BaccaratGame> hashMapOfGames = new HashMap<Integer, BaccaratGame>();
    int playerHandSize = 0;
    int bankerHandSize = 0;


    server(Consumer<Serializable> call, int portNum){


        callback = call;
        server = new TheServer(portNum);
        server.start();
    }


    public class TheServer extends Thread{
        int port;
        TheServer(int portVal)
        {
            this.port = portVal;
        }
    // need to let the user set the port

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(port);){
                System.out.println("Server is waiting for a client!");


                while(true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);

                    callback.accept("client has connected to server: " + "client #" + count);
                    altCount++;
                    //we havent sent anything back to the client at this point

                    clients.add(c);
                    gameInfo.clientNum = count;

                    c.start();



                    count++;



                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{


        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        public void updateClient(String message) {

                ClientThread t = clients.get(gameInfo.clientNum);
                try {
                    t.out.writeObject(message);
                }
                catch(Exception e) {}

        }

        public void run(){
            int exchanges = 0;

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }



            while(true)
            {
                try
                {
                    if (exchanges == 0)
                    {
                        System.out.println("first initial exchange for the client");
                        hashMapOfGames.put(count,game);
                        send(gameInfo);
                        exchanges++;

                    }
                    else
                    {

                        gameInfo = (BaccaratInfo) in.readObject();
                        if(gameInfo.playAgain){
                            callback.accept("client# " + count + " chose to play again");
                            gameInfo.playAgain = false;
                        }


                        game = hashMapOfGames.get(gameInfo.clientNum);
//                        System.out.println(gameInfo.pickedBet);

                        game.pickedBet = gameInfo.pickedBet;
                        game.currentBet = gameInfo.currentBet;

                        double result = game.evaluateWinnings();
                        String whoWon = BaccaratGameLogic.whoWon(game.playerHand, game.bankerHand);
                        if (whoWon.equals("Banker")) {
                            gameInfo.bankerWon = true;
                        }
                        if (whoWon.equals("Player")) {
                            gameInfo.playerWon = true;

                        }
                        if (whoWon.equals("Draw")) {
                            gameInfo.draw = true;

                        }


                        if (result < 0)
                        {
                            // results of game
//                            String whoWon = BaccaratGameLogic.whoWon(game.playerHand, game.bankerHand);
                            callback.accept("client# " + count + " results: " + whoWon + " hand has won");
                            callback.accept("client# " + count + " lost: " + result + " with bet of: " + gameInfo.currentBet);
                            gameInfo.currentBet = (0-gameInfo.currentBet);
                        }
                        // player won bet
                        else
                        {
                            // results of game
                            callback.accept("client# " + count + " results: " + whoWon + " hand has won");

                            callback.accept("client# " + count + " won: " + result + " with bet of: " + gameInfo.currentBet);
                            gameInfo.currentBet = (0+gameInfo.currentBet);

                        }

                        for (int i = 0; i < game.bankerHand.size(); i++){
                            gameInfo.bankerHandTrueValues.add(game.bankerHand.get(i).cardTrueVal);
                        }

                        gameInfo.whoWon = whoWon;
                        gameInfo.playerHandTrueValues = game.returnTheTrueValuesOfPlayer();
                        gameInfo.bankerHandTrueValues = game.returnTheTrueValuesOfBanker();
                        gameInfo.playerValues = game.returnTheValuesOfPlayer();
                        gameInfo.bankerValues = game.returnTheValuesOfBanker();

                        send(gameInfo);

                    }
                }
                catch(Exception e) {
                    callback.accept("client: " + count + " has left the game.");
                    System.out.println(e);
                    clients.remove(this);
                    break;
                }
            }
        }//end of run

        public void send(BaccaratInfo data)
        {
            try {
                out.writeObject(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//end of client thread
}






