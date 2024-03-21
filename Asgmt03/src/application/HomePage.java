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
		
		//Navigation Buttons
		Button button_NewCategory = new Button("New Category");
		Button button_NewLocation = new Button("New Location");
		Button button_NewAsset = new Button("   New Asset   ");
		Button button_Search = new Button("     Search      ");
		Button button_Reports = new Button(/*"     Reports     " */ "");
		
		//modify button
		button_NewCategory.setStyle("-fx-background-color: #0068B8; " + 
									"-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
									"-fx-text-fill: Black; " + 
									"-fx-font-size: 20px; " + 
									"-fx-padding: 12 18 12 18");
		button_NewLocation.setStyle("-fx-background-color: #0068B8; " + 
									"-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
									"-fx-text-fill: Black; " + 
									"-fx-font-size: 20px; " + 
									"-fx-padding: 12 18 12 18");
		button_NewAsset.setStyle("-fx-background-color: #0068B8; " + 
								 "-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
								 "-fx-text-fill: Black; " + 
								 "-fx-font-size: 20px; " + 
								 "-fx-padding: 12 18 12 18");
		button_Search.setStyle("-fx-background-color: #0068B8; " + 
						   	   "-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
							   "-fx-text-fill: Black; " + 
							   "-fx-font-size: 20px; " + 
							"-fx-padding: 12 18 12 18");
		
		button_Reports.setStyle("-fx-background-color: #0068B8;" + 
								"-fx-background-radius: 20; " + "-fx-background-insets: 2;" +
								"-fx-text-fill: Black; " + 
								"-fx-font-size: 20px; " + 
								"-fx-padding: 12 120 12 40");
		Text label_Reports = new Text("Reports Info");
		label_Reports.setFont(Font.font("Arial", 18));
		
		StackPane button5 = new StackPane(button_Reports, label_Reports);
		
		
		menuNavigation.getChildren().addAll(button_NewCategory, button_NewLocation, button_NewAsset, button_Search, /*button_Reports*/ button5);
		
		
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
