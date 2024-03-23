package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NewLocation extends VBox {
	private HBox line1;
	private HBox line2;
	private HBox line3;
	private HBox line4;
	
	public NewLocation() {
		super(30); // spacing parameter 30
		super.setPadding(new Insets(40, 40, 40, 40));
		
		Text titleText = new Text("Create New Location");
        titleText.setFont(Font.font("Arial", 30));
        line1 = new HBox(titleText);
        line1.setAlignment(Pos.BASELINE_CENTER);
        
        Label redAsterisk = new Label("*");
        redAsterisk.setFont(Font.font("Arial", 20));
        redAsterisk.setTextFill(Color.RED);
        
        Label nameLabel = new Label("Location's name:  ");
        nameLabel.setFont(Font.font("Arial", 20));
        TextField nameField = new TextField();
        line2 = new HBox(redAsterisk, nameLabel, nameField);
        line2.setAlignment(Pos.BASELINE_CENTER);
        
        Label descriptionLabel = new Label("Location's Description:");
        descriptionLabel.setFont(Font.font("Arial", 20));
        TextField descriptionField = new TextField();
        line3 = new HBox(descriptionLabel, descriptionField);
        line3.setAlignment(Pos.BASELINE_CENTER);
        
        Button confirm = createButton("Confirm");
        Button clear = createButton("Clear");
        line4 = new HBox(50, confirm, clear);
        line4.setAlignment(Pos.BASELINE_CENTER);
        
        
		initialize();
	}
	
	
	private void initialize() {
		this.setPrefSize(560, 300);
		this.setAlignment(Pos.TOP_LEFT);
		// Add contents to the VBox
		this.getChildren().addAll(line1, line2, line3, line4);
	}
	
	public Button createButton(String arg) {
		Button button = new Button(arg);

		button.setStyle("-fx-text-fill: Black; " + 
					 	"-fx-font-size: 16px; ");

		button.setPrefSize(120, 30);

		return button;
	}
	
}
