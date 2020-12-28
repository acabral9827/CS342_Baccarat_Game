
import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import java.lang.Math;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GuiClient extends Application{
    private Stage stage;
    String IPString;
    Integer port;
    String portInputted;
    TextField s1,s2,s3,s4, c1;
    Button serverChoice,clientChoice, sendButton;
    HashMap<String, Scene> sceneMap;
    GridPane grid;
    HBox buttonBox;
    VBox clientBox;
    Scene startScene;
    Integer totalPlayerValues = 0;
    Integer totalBankerValues = 0;
    BorderPane startPane;
    client clientConnection;
    boolean betButtonPressed = false;
    Label netvalue = new Label();
    ArrayList<ImageView> cards = new ArrayList<>();
    HBox cardBox = new HBox(10);
    ListView<String> listItems, listItems2;
    ArrayList<ImageView> playerCards = new ArrayList<>();
    ArrayList<ImageView> bankerCards = new ArrayList<>();
    HBox playerCardLayout = new HBox(5);
    HBox bankerCardLayout = new HBox(5);
    Text totalText = new Text("Total:");
    ArrayList<Image> playerCardPics = new ArrayList<>(3);
    ArrayList<Integer> playerHand = new ArrayList<>();
    Label winnerLabel = new Label("");
    Text naturalWin = new Text("NATURAL WIN");
    ArrayList<Integer> bankerHand = new ArrayList<>();
    ArrayList<Image> bankerCardPics = new ArrayList<>(3);
    double startingBalance = 500;
    Label balanceValue = new Label(String.valueOf(startingBalance));
    Label totalValue2 = new Label("");
    Label totalValue = new Label("");
    Button playAgainButton = new Button("Play again");
    Integer doesThisEqual2 = 0;
    Boolean wasIPPressed = false;
    Boolean wasPortPressed = false;
    Integer currentPlayerTrueCounter = 0;
    Integer currentRunCount = 0;
    Image playerImageCard;
    Image bankerImageCard;
    Integer pausetiming = 2;
    PauseTransition pause = new PauseTransition(Duration.seconds(2));



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    public Runnable methodForPlayerHand(Integer playerTrueData){
        currentRunCount++;

        return new Runnable() {
            @Override
            public void run() {


                currentPlayerTrueCounter++;
                playerImageCard = displayCards(playerTrueData);
                ImageView imageViewPlayerCard = new ImageView(playerImageCard);
                imageViewPlayerCard.setFitHeight(125);
                imageViewPlayerCard.setFitWidth(75);

                playerCardLayout.getChildren().add(imageViewPlayerCard);




            }
        };

    }

    public Runnable methodForBankerHand(Integer bankerTrueData){
        return new Runnable() {
            @Override
            public void run() {


                bankerImageCard = displayCards(bankerTrueData);
                ImageView imageViewBankerCard = new ImageView(bankerImageCard);
                imageViewBankerCard.setFitHeight(125);
                imageViewBankerCard.setFitWidth(75);
                bankerCardLayout.getChildren().add(imageViewBankerCard);


            }
        };
    }
    public Runnable methodForPlayerValues(Integer playerValueData){
        return new Runnable() {
            @Override
            public void run() {

                totalPlayerValues += playerValueData;
                if(totalPlayerValues >= 10){
                    totalPlayerValues = totalPlayerValues - 10;
                }
                totalValue.setText(String.valueOf(totalPlayerValues));


            }
        };
    }
    public Runnable methodForBankValues(Integer bankerValueData){
        return new Runnable() {
            @Override
            public void run() {

                totalBankerValues += bankerValueData;
                if(totalBankerValues >= 10){
                    totalBankerValues = totalBankerValues - 10;
                }
                totalValue2.setText(String.valueOf(totalBankerValues));


            }
        };
    }
    public Runnable methodForTotalEarnings(Double totalEarningsData){
        return new Runnable() {
            @Override
            public void run() {
                double finalAnswer;
                if (totalEarningsData < 0) {
                     startingBalance = startingBalance + totalEarningsData;
                }
                else{
                    startingBalance = startingBalance + totalEarningsData;

                }
                balanceValue.setText(String.valueOf(Math.round(startingBalance)));
                netvalue.setText(String.valueOf(Math.round(totalEarningsData)));



            }
        };
    }
    public Runnable methodForNatCalls(String natValueData){
        return new Runnable() {
            @Override
            public void run() {

                naturalWin.setFill(Color.GOLD);


            }
        };
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        stage = primaryStage;


        clientChoice = new Button("Client");
        clientChoice.setPrefWidth(229);
        clientChoice.setPrefHeight(30);
        Button ipButton = new Button("ip enter");
        ipButton.setPrefWidth(70);
        ipButton.setPrefHeight(25);
        Button portButton = new Button("port enter");
        portButton.setPrefWidth(70);
        portButton.setPrefHeight(25);

        TextField ipText = new TextField();
        TextField portText = new TextField();
        VBox allStuff = new VBox();
        HBox portStuff = new HBox();
        HBox ipStuff = new HBox();
        clientChoice.setDisable(false);


        clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
            primaryStage.setTitle("This is a client");
            clientConnection = new client(
                    winnerData->{
                //edit this later
                Platform.runLater(()-> {  winnerLabel.setText(winnerData.toString());
                    playAgainButton.setDisable(false);
                });},
                    playerImageData->{
                    Platform.runLater(methodForPlayerHand(Integer.parseInt(playerImageData.toString())));
                    },
                    bankerImageData->{
                        Platform.runLater(methodForBankerHand(Integer.parseInt(bankerImageData.toString())));
                    },
                    totalEarningsData->{
                        Platform.runLater(methodForTotalEarnings(Double.parseDouble(totalEarningsData.toString())));
                    },
                    playerValueData->{
                        Platform.runLater(methodForPlayerValues(Integer.parseInt(playerValueData.toString())));
                    },
                    bankerValueData->{
                        Platform.runLater(methodForBankValues(Integer.parseInt(bankerValueData.toString())));
                    },
                    natValueData->{
                        Platform.runLater(methodForNatCalls(natValueData.toString()));

                    },
                    IPString,Integer.parseInt(portInputted));

            clientConnection.start();
        });

        ipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IPString = ipText.getText();
                wasIPPressed = true;
                if(wasPortPressed){
                    clientChoice.setDisable(false);
                }

            }
        });
        portButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                portInputted = portText.getText();
                wasPortPressed = true;
                if(wasIPPressed){
                    clientChoice.setDisable(false);
                }
            }
        });




        ipStuff = new HBox(10,ipText,ipButton);
        ipStuff.setAlignment(Pos.CENTER);
        portStuff = new HBox(10,portText,portButton);
        portStuff.setAlignment(Pos.CENTER);
        allStuff = new VBox(5, ipStuff,portStuff,clientChoice);
        allStuff.setAlignment(Pos.CENTER);


        startPane = new BorderPane();
        startPane.setPadding(new Insets(70));
        startPane.setCenter(allStuff);

        startScene = new Scene(startPane, 400,400);

        listItems = new ListView<String>();
        listItems2 = new ListView<String>();

        c1 = new TextField();

        sceneMap = new HashMap<String, Scene>();


        sceneMap.put("client",  createClientGui());

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });



        stage.setScene(startScene);
        stage.show();

    }
    public Scene createClientGui() {

        // create a grid pane
        playAgainButton.setDisable(true);
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-image: url('https://images.creativemarket.com/0.1.0/ps/356802/910/607/m1/fpnw/wm1/poker_pattern-.jpg?1424100509&s=0a4734c16d558ff76624155ea92ad20b')");
//		gridPane.setGridLinesVisible(true);

        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);

        // Column 0
        //------------------------------------------------------------------------------
        // title for player bets
        Label titleForPlayerBet = new Label("Place your bet here:");
        titleForPlayerBet.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleForPlayerBet.setTextFill(Color.WHITE);
        GridPane.setHalignment(titleForPlayerBet, HPos.CENTER);
        // Put on cell (0,0), span 2 column, 1 row.
        gridPane.add(titleForPlayerBet, 0, 0);

        // Betting buttons //
        // Bet on player:
        Button betOnPlayerButton = new Button("Bet on player");
        betOnPlayerButton.setPrefWidth(100);
        betOnPlayerButton.setPrefHeight(50);
        betOnPlayerButton.setFont(Font.font("Arial"));
        gridPane.add(betOnPlayerButton, 0, 1, 1, 1);
        GridPane.setHalignment(betOnPlayerButton, HPos.CENTER);
//		TextField fieldUserName = new TextField();

        // Bet on banker:
        Button betOnBankerButton = new Button("Bet on banker");
        betOnBankerButton.setPrefWidth(100);
        betOnBankerButton.setPrefHeight(50);
        betOnBankerButton.setFont(Font.font("Arial"));
        gridPane.add(betOnBankerButton, 0, 2);
        GridPane.setHalignment(betOnBankerButton, HPos.CENTER);

        // Bet on draw:
        Button betOnDrawButton = new Button("Bet on draw");
        betOnDrawButton.setPrefWidth(100);
        betOnDrawButton.setPrefHeight(50);
        gridPane.add(betOnDrawButton, 0, 3);
        betOnDrawButton.setFont(Font.font("Arial"));
        GridPane.setHalignment(betOnDrawButton, HPos.CENTER);

        // Balance and Net gained/lost //
        Label titleForBalance = new Label("Balance");
        titleForBalance.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleForBalance.setTextFill(Color.WHITE);
        gridPane.add(titleForBalance, 0, 4);
        GridPane.setHalignment(titleForBalance, HPos.CENTER);

        Text balance = new Text("$");
        balance.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        balance.setFill(Color.WHITE);

        balanceValue.setPrefSize(100, 50);
        balanceValue.setFont(Font.font("Arial" ,FontWeight.BOLD, 14));
        balanceValue.setStyle("-fx-background-color: gray;");

        HBox balanceTotal = new HBox(balance, balanceValue);
        gridPane.add(balanceTotal, 0, 5);
        balanceTotal.setAlignment(Pos.CENTER);
        GridPane.setHalignment(balanceTotal, HPos.CENTER);

        Text titleForNet = new Text("Net gained/lost");
        titleForNet.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleForNet.setFill(Color.WHITE);
        gridPane.add(titleForNet, 0, 6);
        GridPane.setHalignment(titleForNet, HPos.CENTER);

        Text net = new Text("$");
        net.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        net.setFill(Color.WHITE);

        netvalue.setPrefSize(100, 50);
        netvalue.setFont(Font.font("Arial" ,FontWeight.BOLD, 14));
        netvalue.setStyle("-fx-background-color: gray;");

        HBox netGainedLost = new HBox(5, net, netvalue);
        gridPane.add(netGainedLost, 0, 7);
        netGainedLost.setAlignment(Pos.CENTER);
        GridPane.setHalignment(netGainedLost, HPos.CENTER);


        // Column 2
        //------------------------------------------------------------------------------
        Text playerHeader = new Text("Player");
        playerHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        playerHeader.setFill(Color.WHITE);


        // populate cardPics with the image file names



        // put the images in a horizontal layout
        for (ImageView x : playerCards)
        {
            // picture dimensions
            x.setFitHeight(125);
            x.setFitWidth(75);
            playerCardLayout.getChildren().add(x);
        }

        totalText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalText.setFill(Color.WHITE);
        totalValue.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalValue.setPrefSize(100, 50);
        totalValue.setStyle("-fx-background-color: gray;");
        HBox totalPts = new HBox(totalText, totalValue);
        totalPts.setAlignment(Pos.CENTER);

        Button drawButton = new Button("DRAW");
        drawButton.setPrefWidth(100);
        drawButton.setPrefHeight(50);
        drawButton.setFont(Font.font("Arial"));

        gridPane.add(playerHeader, 2, 1, 1, 1);
        GridPane.setHalignment(playerHeader, HPos.CENTER);
        gridPane.add(playerCardLayout, 2, 2);
        playerCardLayout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(playerCardLayout, HPos.CENTER);
        gridPane.add(totalPts, 2, 4);
        GridPane.setHalignment(totalPts, HPos.CENTER);
        gridPane.add(drawButton, 2, 6);
        GridPane.setHalignment(drawButton, HPos.CENTER);
        //------------------------------------------------------------------------------

        // Column 3
        //------------------------------------------------------------------------------
        naturalWin.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        naturalWin.setFill(Color.TRANSPARENT);

        Text winnerHeader = new Text("Winner");
        winnerHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        winnerHeader.setFill(Color.WHITE);

        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        winnerLabel.setPrefSize(100, 25);
        winnerLabel.setAlignment(Pos.CENTER);
        winnerLabel.setStyle("-fx-background-color: gray;");
        VBox winnerBox = new VBox(naturalWin, winnerHeader, winnerLabel);
        winnerBox.setAlignment(Pos.CENTER);

        playAgainButton.setPrefWidth(75);
        playAgainButton.setPrefHeight(50);
        playAgainButton.setFont(Font.font("Arial"));

        Text amountToBetText = new Text("Place bet:");
        amountToBetText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        amountToBetText.setFill(Color.WHITE);
        TextField enterBetField = new TextField();
        VBox placeCashBet = new VBox(amountToBetText, enterBetField);
        placeCashBet.setAlignment(Pos.CENTER);

        gridPane.add(winnerBox, 3, 2);
        gridPane.add(playAgainButton, 3, 5);
        GridPane.setHalignment(playAgainButton, HPos.CENTER);
        gridPane.add(placeCashBet, 3, 6);
        GridPane.setHalignment(placeCashBet, HPos.CENTER);
        //------------------------------------------------------------------------------

        // Column 4
        //------------------------------------------------------------------------------
        Text bankerHeader = new Text("Banker");
        bankerHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        bankerHeader.setFill(Color.WHITE);

        // populate cardPics with the image file names


        // populate the list of images
        for ( Image x : bankerCardPics )
        {
            bankerCards.add(new ImageView(x));
        }

        // put the images in a horizontal layout
        for (ImageView x : bankerCards)
        {
            // picture dimensions
            x.setFitHeight(125);
            x.setFitWidth(75);
            bankerCardLayout.getChildren().add(x);
        }

        Text totalText2 = new Text("Total:");
        totalText2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalText2.setFill(Color.WHITE);
        totalValue2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalValue2.setPrefSize(100, 50);
        totalValue2.setStyle("-fx-background-color: gray;");
        HBox totalPts2 = new HBox(totalText2, totalValue2);
        totalPts2.setAlignment(Pos.CENTER);

        Button placeBetButton = new Button("BET");
        placeBetButton.setPrefWidth(100);
        placeBetButton.setPrefHeight(50);

        gridPane.add(bankerHeader, 4, 1, 1, 1);
        GridPane.setHalignment(bankerHeader, HPos.CENTER);
        gridPane.add(bankerCardLayout, 4, 2);
        bankerCardLayout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(bankerCardLayout, HPos.CENTER);
        gridPane.add(totalPts2, 4, 4);
        GridPane.setHalignment(totalPts2, HPos.CENTER);
        gridPane.add(placeBetButton, 4, 6);
        GridPane.setHalignment(placeBetButton, HPos.CENTER);

        //------------------------------------------------------------------------------
        // Column 5
        Button turnOffClientButton = new Button("Turn off");
        turnOffClientButton.setStyle("-fx-background-color: red;-fx-text-fill: white;");
        gridPane.add(turnOffClientButton, 5, 0);

        //------------------------------------------------------------------------------
        // Event Handlers
        drawButton.setDisable(true);
        betOnPlayerButton.setOnAction(e->{
            clientConnection.gameInfo.pickedBet = "Player";
            betOnPlayerButton.setDisable(true);
            betOnBankerButton.setDisable(false);
            betOnDrawButton.setDisable(false);
            betButtonPressed = true;

        });
        betOnBankerButton.setOnAction(e->{
            clientConnection.gameInfo.pickedBet = "Banker";
            betOnPlayerButton.setDisable(false);
            betOnBankerButton.setDisable(true);
            betOnDrawButton.setDisable(false);
            betButtonPressed = true;

        });
        betOnDrawButton.setOnAction(e->{
            clientConnection.gameInfo.pickedBet = "Draw";
            betOnPlayerButton.setDisable(false);
            betOnBankerButton.setDisable(false);
            betOnDrawButton.setDisable(true);
            betButtonPressed = true;

        });

        placeBetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!enterBetField.getText().trim().isEmpty())
                {
                    clientConnection.gameInfo.currentBet = Integer.parseInt(enterBetField.getText());
                }

                if (betButtonPressed == false)
                {
                    drawButton.setDisable(true);
                }
                else
                {
                    drawButton.setDisable(false);
                }
            }
        });
        playAgainButton.setOnAction(e-> {
            betOnPlayerButton.setDisable(false);
            betOnBankerButton.setDisable(false);
            betOnDrawButton.setDisable(false);
            placeBetButton.setDisable(false);
            drawButton.setDisable(true);
            playAgainButton.setDisable(true);
            betButtonPressed = false;
            naturalWin.setFill(Color.TRANSPARENT);





        });
        drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if (!enterBetField.getText().trim().isEmpty())
            {
                playerCards.clear();
                bankerCards.clear();

                playerCardLayout.getChildren().clear();
                bankerCardLayout.getChildren().clear();
                totalPlayerValues = 0;
                totalBankerValues = 0;

                playerHand.clear();
                bankerHand.clear();
                playerCardPics.clear();
                bankerCardPics.clear();
                clientConnection.send(clientConnection.gameInfo);
                betOnPlayerButton.setDisable(true);
                betOnBankerButton.setDisable(true);
                betOnDrawButton.setDisable(true);
                placeBetButton.setDisable(true);
                drawButton.setDisable(true);
                playAgainButton.setDisable(true);
                enterBetField.clear();
                winnerLabel.setText("");
                currentPlayerTrueCounter = 0;
                totalValue2.setText("0");
                totalValue.setText("0");

              }

            }
        });

        // turns off the server if the turnOffServer button is clicked
        turnOffClientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientConnection.end();
                stage.setScene(startScene);

            }
        });

        // turns off the server if the user presses the close window button
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        //------------------------------------------------------------------------------

        // Row constraints
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(10);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(20);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(20);
        RowConstraints r4 = new RowConstraints();
        r4.setPercentHeight(20);
        RowConstraints r5 = new RowConstraints();
        r5.setPercentHeight(5);


// Column constraints
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(5);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(25);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(20);
        ColumnConstraints c5 = new ColumnConstraints();
        c5.setPercentWidth(25);


        gridPane.getRowConstraints().addAll(r1, r2, r3, r4, r5);
        gridPane.getColumnConstraints().addAll(c1, c2, c3, c4);

        return new Scene(gridPane, 1000, 500);

    }

    public Scene old() {

        // Interactive GUI
        Button betPlayerButton = new Button("Player");
        Button betBankerButton = new Button("Banker");
        Button betDrawButton = new Button("Draw");
        Button placeBetButton = new Button("Place bet");
        Button drawButton = new Button("Draw");
        Button drawOneCard = new Button("Draw a card");
        TextField enterBetText = new TextField();
        Text betHeader = new Text("Enter bet:");
        HBox placeBet = new HBox(enterBetText, placeBetButton);


        ArrayList<Image> cardPics = new ArrayList<>(3);
        ArrayList<Integer> playerHand = new ArrayList<>();


        ArrayList<Integer> bankerHand = new ArrayList<>();




        // populate cardPics with the image file names



        for ( Image x : cardPics )
        {
            cards.add(new ImageView(x));
        }

//		ImageView card1 = new ImageView(cardPics.get(0));

//		ImageView test = new ImageView("cardImages/" + 13 + ".png");

        drawButton.setDisable(true);
        drawOneCard.setDisable(true);

        // Betting buttons event handlers
        betPlayerButton.setOnAction(e->{clientConnection.gameInfo.pickedBet = "Player";});
        betBankerButton.setOnAction(e->{clientConnection.gameInfo.pickedBet = "Banker";});
        betDrawButton.setOnAction(e->{clientConnection.gameInfo.pickedBet = "Draw";});






        clientBox = new VBox(20, betPlayerButton, betBankerButton, betDrawButton, placeBet, drawButton);

        for (ImageView x : cards )
        {
            x.setFitHeight(200);
            x.setFitWidth(100);
            cardBox.getChildren().add(x);
        }
        clientBox.getChildren().add(cardBox);
        clientBox.setStyle("-fx-background-color: blue");
        return new Scene(clientBox, 900, 500);

    }


    static public Image displayCards(Integer hand)
    {

        Image card = new Image(("cardImages/" + hand + ".png"));



        return card;
    }




}
