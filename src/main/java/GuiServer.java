import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;

public class GuiServer extends Application {

    private Stage stage;
    server serverConnection;
    ListView<String> listItems;
    String userEnteredPort;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    //feel free to remove the starter code from this method
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        stage = primaryStage;


        stage.setScene(startScreen());
        stage.show();
    }

    // Scene where user enters port number
    public Scene startScreen()
    {
        stage.setTitle("Server");

        // set up an image
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-image: url('https://image.freepik.com/free-vector/binary-code-background-blue-vector_88211-920.jpg')");

        // set up needed things
        TextField portField = new TextField("");
        Button acceptButton = new Button("Accept port");
        Button turnServerOnButton = new Button("Turn on server");
        turnServerOnButton.setDisable(true);
        listItems = new ListView<>();

        // start the server
        turnServerOnButton.setOnAction(e->{
            stage.setScene(statusScene());
            serverConnection = new server(data -> {
                Platform.runLater(()->{
                    listItems.getItems().add(data.toString());
                });
            }, Integer.parseInt(userEnteredPort));
        });

        // Event handler - user enters the port, and accept button adds it to userEnteredPort
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userEnteredPort = portField.getText();
                turnServerOnButton.setDisable(false);
            }
        });

        HBox root = new HBox(portField, acceptButton);
        VBox vbox = new VBox(20, root, turnServerOnButton);
        vbox.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        pane.setCenter(vbox);
        return new Scene(pane, 610,458);
    }

    // Scene where the server gets the information and displays it
    public Scene statusScene()
    {
        stage.setTitle("Server Status");
        BorderPane pane = new BorderPane();

        // set up an image
        pane.setStyle("-fx-background-image: url('https://image.freepik.com/free-vector/binary-code-background-blue-vector_88211-920.jpg')");
        pane.setPadding(new Insets(70));

        // Needed buttons and text
        Button turnOffServer = new Button("Turn off server");

        // Text to add a header to the listview
        Text gameState = new Text("State of the game:");
        gameState.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        gameState.setFill(Color.WHITE);

        VBox vbox = new VBox(20, turnOffServer, gameState, listItems);
        pane.setCenter(vbox);

        // turns off the server if the turnOffServer button is clicked
        turnOffServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
//                stage.setScene(startScreen());
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

        return new Scene(pane,610,458);
    }

}
