package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomePage extends Application{
	
	public void start(Stage homeStage) {
		//VBox for the menu on the left
		VBox menuNavigation = new VBox(30);
		menuNavigation.setStyle("-fx-background-color: rgba(72, 155, 105, 0.7); -fx-padding: 30;");
		
		Button button_NewCategory = navigationButton("New Category");
		Button button_NewLocation = navigationButton("New Location");
		Button button_NewAsset = navigationButton("New Asset");
		Button button_Search = navigationButton("Search");
		Button button_Reports = navigationButton("Reports");
		

		
		menuNavigation.getChildren().addAll(button_NewCategory, button_NewLocation, button_NewAsset, button_Search, button_Reports);
		
		
		
		// Welcome message + app name
		Text welcomeMessage = new Text("Welcome to");
		welcomeMessage.setFont(Font.font("Arial", 40));
		
		HBox content1 = new HBox(10);
		content1.setAlignment(Pos.TOP_CENTER);
		content1.getChildren().addAll(welcomeMessage);
		
		Text appName = new Text("SortYourLife");
		appName.setFont(Font.font("Arial", 48));
		
		HBox content2 = new HBox(10);
		content2.setAlignment(Pos.CENTER);
		content2.getChildren().addAll(appName);
		
		//image
		/*
		ImageView box = new ImageView(new Image("art_box.jpg"));
		
		HBox content3 = new HBox(10);
		content3.getChildren().addAll(box);
		*/
		
		
		// 
		HBox mainBkgd = new HBox(menuNavigation, content1, content2);
		//mainBkgd.setSpacing(10);
		//mainBkgd.setAlignment(Pos.CENTER);
		//
		Scene scene = new Scene(mainBkgd, 800, 600);
		
		//
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//String css = this.getClass().getResource("application.css").toExternalForm();
		//scene.getStylesheets().add(css);
		
		
		homeStage.setTitle("SortYourLife");
		homeStage.setScene(scene);
		homeStage.show();
		
		
		
		
		
	}
	

	
	public Button navigationButton(String arg) {
		Button res = new Button(arg);
		
		res.setStyle("-fx-background-color: #0068B8;" + 
					"-fx-background-radius: 25; " + "-fx-background-insets: 2;" +
					"-fx-text-fill: Black; " + 
					"-fx-font-size: 20px; ");
		
		res.setMinSize(180, 50);
		
		return res;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}


















