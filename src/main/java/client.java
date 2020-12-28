import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class client extends Thread{

	String IPADDRESS;
	Integer PORTINPUT;
	Socket socketClient;



	ObjectOutputStream out;
	ObjectInputStream in;

	private Consumer<Serializable> winnerCallback;
	private Consumer<Serializable> imageCallback;
	private Consumer<Serializable> bankerImageCallback;
	private Consumer<Serializable> totalEarningsCallback;
	private Consumer<Serializable> playerValueCallBack;
	private Consumer<Serializable> bankerValueCallBack;
	private Consumer<Serializable> natCallBack;


	BaccaratInfo gameInfo = new BaccaratInfo();

	client(Consumer<Serializable> winnerCall,Consumer<Serializable> imageCall ,Consumer<Serializable> bankerImageCall,Consumer<Serializable> totalEarningsCall,Consumer<Serializable> playerValueCall, Consumer<Serializable> bankerValueCall, Consumer<Serializable> natValueCall, String ipInput, Integer portinputted){
		IPADDRESS = ipInput;
		PORTINPUT = portinputted;
		winnerCallback = winnerCall;
		imageCallback = imageCall;
		bankerImageCallback = bankerImageCall;
		totalEarningsCallback = totalEarningsCall;
		playerValueCallBack = playerValueCall;
		bankerValueCallBack = bankerValueCall;
		natCallBack = natValueCall;

	}

	public void run() {

		try {
			socketClient= new Socket(IPADDRESS,PORTINPUT);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
			gameInfo = (BaccaratInfo)in.readObject();

		}
		catch(Exception e) {}

		while(true) {

			try {
				gameInfo = (BaccaratInfo) in.readObject();
				gameInfo.playAgain = true;



			EventHandler<ActionEvent> displayPFirst = (e -> {
				imageCallback.accept(gameInfo.playerHandTrueValues.get(0));
				playerValueCallBack.accept(gameInfo.playerValues.get(0));

            });
			EventHandler<ActionEvent> displayPSecond = (e -> {
				imageCallback.accept(gameInfo.playerHandTrueValues.get(1));
				playerValueCallBack.accept(gameInfo.playerValues.get(1));


			});
			EventHandler<ActionEvent> displayPThird = (e -> {
				if(gameInfo.playerValues.size() == 3) {
					imageCallback.accept(gameInfo.playerHandTrueValues.get(2));
					playerValueCallBack.accept(gameInfo.playerValues.get(2));
				}
				else{

				}

			});
				EventHandler<ActionEvent> displayBFirst = (e -> {
					bankerImageCallback.accept(gameInfo.bankerHandTrueValues.get(0));
					bankerValueCallBack.accept(gameInfo.bankerValues.get(0));

				});

				EventHandler<ActionEvent> displayBSecond = (e -> {
					bankerImageCallback.accept(gameInfo.bankerHandTrueValues.get(1));
					bankerValueCallBack.accept(gameInfo.bankerValues.get(1));

				});
				EventHandler<ActionEvent> displayBThird = (e -> {
					if(gameInfo.bankerValues.size() == 3) {
						bankerImageCallback.accept(gameInfo.bankerHandTrueValues.get(2));
						bankerValueCallBack.accept(gameInfo.bankerValues.get(2));
					}
					else{

					}
				});
				EventHandler<ActionEvent> endResults = (e -> {

					winnerCallback.accept(gameInfo.whoWon);
				});
			EventHandler<ActionEvent> checkNat = (e -> {

				if (gameInfo.playerValues.size() == 2 || gameInfo.bankerValues.size()== 2) {
					int bankerNat = 0;
					int playerNat = 0;
//					for(Integer x : gameInfo.playerValues){
					for(int i = 0; i <= 1; i++){
						playerNat +=  gameInfo.playerValues.get(i);

					}
					for(int i = 0; i <= 1; i++){
						bankerNat +=  gameInfo.bankerValues.get(i);
					}



					if ( bankerNat != playerNat && (bankerNat == 9 || playerNat == 9 || bankerNat == 8 || playerNat == 8)){
						natCallBack.accept("lol");
						if(gameInfo.bankerValues.size() == 3){
							gameInfo.bankerValues.remove(gameInfo.bankerValues.get(2));
							gameInfo.bankerHandTrueValues.remove(gameInfo.bankerHandTrueValues.get(2));
						}
						if(gameInfo.playerValues.size() == 3){
							gameInfo.playerValues.remove(gameInfo.playerValues.get(2));
							gameInfo.playerHandTrueValues.remove(gameInfo.playerHandTrueValues.get(2));
						}


					}


				}
			});


            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), displayPFirst),
				new KeyFrame(Duration.seconds(2), displayPSecond),
				new KeyFrame(Duration.seconds(1), displayBFirst),
				new KeyFrame(Duration.seconds(2), displayBSecond),
				new KeyFrame(Duration.seconds(3), checkNat)



			);


            timeline.setCycleCount(1);
            timeline.play();

			if (gameInfo.bankerWon && gameInfo.pickedBet.equals("Banker")) {
				gameInfo.currentBet = gameInfo.currentBet * .95;
			}
			if (gameInfo.draw && gameInfo.pickedBet.equals("Draw")) {
				gameInfo.currentBet = gameInfo.currentBet * 8;
			}
			totalEarningsCallback.accept(gameInfo.currentBet);



			Timeline timeline2 = new Timeline(
					new KeyFrame(Duration.seconds(4), displayPThird),
					new KeyFrame(Duration.seconds(4), displayBThird),
					new KeyFrame(Duration.seconds(4.5), endResults));

				timeline2.setCycleCount(1);
				timeline2.play();
				gameInfo.playerWon = false;
				gameInfo.bankerWon = false;
				gameInfo.draw = false;
				gameInfo.currentBet = 0;





			}
			catch(Exception e) {}
		}

	}

	public void end()
	{
		try {
			in.close();
			out.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	public void send(BaccaratInfo data) {

		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
