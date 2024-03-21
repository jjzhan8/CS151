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
		
		StackPane button_NewCategory = navigationButton("New Category");
		StackPane button_NewLocation = navigationButton("New Location");
		StackPane button_NewAsset = navigationButton("New Asset");
		StackPane button_Search = navigationButton("Search");
		StackPane button_Reports = navigationButton("Reports");
		
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

		
		
		// 
		HBox mainBkgd = new HBox(menuNavigation, content1, content2);

		Scene scene = new Scene(mainBkgd, 800, 600);
		

		
		
		homeStage.setTitle("SortYourLife");
		homeStage.setScene(scene);
		homeStage.show();
		
		
		
		
		
	}
	
	public StackPane navigationButton(String arg) {
		StackPane res;
		//create button 
		Button button = new Button();
		button.setStyle("-fx-background-color: #0068B8;" + 
				"-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
				"-fx-text-fill: Black; " + 
				"-fx-font-size: 20px; " + 
				"-fx-padding: 12 120 12 40");
		//create text
		Text label = new Text(arg);
		label.setFont(Font.font("Arial", 18));
		//
		res = new StackPane(button, label);
		
		return res;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
