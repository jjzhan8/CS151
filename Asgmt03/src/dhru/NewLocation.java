package dhru;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewLocation extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    	
		
    	
    	Text titleText = new Text("Create New Storage Location");
        titleText.setFont(new Font("Arial", 20));

        Label nameLabel = new Label("Location's name:  ");
        TextField nameField = new TextField();
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, nameField);

        Label descriptionLabel = new Label("Description:  ");
        TextField descriptionField = new TextField();
        HBox descriptionBox = new HBox();
        descriptionBox.getChildren().addAll(descriptionLabel, descriptionField);



        VBox root = new VBox(10);
        root.getChildren().addAll(titleText, nameBox, descriptionBox);

        StackPane.setAlignment(titleText, Pos.CENTER);
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(root);

        Scene scene = new Scene(rootPane, 400, 400);

        primaryStage.setTitle("Create New Storage Location");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}