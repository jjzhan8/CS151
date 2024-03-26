package application;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Category;

public class NewCategory extends VBox {
	private Category category;
	
	private HBox line1;
	private HBox line2;
	private HBox line3;

	private Button confirm;
	private Button clear;
	private TextField nameField;

	public NewCategory() {
		super(30); // spacing parameter 30
		super.setPadding(new Insets(40, 40, 40, 40));

		Text titleText = new Text("Create New Category");
		titleText.setFont(Font.font("Arial", 30));
		line1 = new HBox(titleText);
		line1.setAlignment(Pos.BASELINE_CENTER);

		Label redAsterisk = new Label("*");
		redAsterisk.setFont(Font.font("Arial", 20));
		redAsterisk.setTextFill(Color.RED);

		Label nameLabel = new Label("Category's name:  ");
		nameLabel.setFont(Font.font("Arial", 20));
		nameField = new TextField();
		line2 = new HBox(redAsterisk, nameLabel, nameField);
		line2.setAlignment(Pos.BASELINE_CENTER);

		confirm = createButton("Confirm");
		clear = createButton("Clear");
		line3 = new HBox(50, confirm, clear);
		line3.setAlignment(Pos.BASELINE_CENTER);

		initialize();
		buttonAction();
		
		
	}

	private void initialize() {
		this.setPrefSize(560, 300);
		this.setAlignment(Pos.TOP_LEFT);
		// Add contents to the VBox
		this.getChildren().addAll(line1, line2, line3);

		
	}
	private void buttonAction() {
		Consumer<String> processInput = input -> {
		    // Imagine this is a method that processes the input in some way
		    category = new Category(input);
		};

		nameField.setOnAction(event -> processInput.accept(nameField.getText()));
		
		confirm.setOnAction(e -> {
			
			String name = nameField.getText();
			if (name.isEmpty()) {
				// Show an error message if the name is empty
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Category name can not be empty!");
				alert.showAndWait();
			} else {
				// Save the category name to a .csv file
				saveCategoryToCsv(name);
			}
		});
        
		clear.setOnAction(e -> {
			
			// Clear the text field
			nameField.clear();
		});
    }

	public Button createButton(String arg) {
		Button button = new Button(arg);

		button.setStyle("-fx-text-fill: Black; " + "-fx-font-size: 16px; ");

		button.setPrefSize(120, 30);

		return button;
	}

	private void saveCategoryToCsv(String name) {
		try {
			// Check if the file exists
			if (Files.exists(Paths.get("categories.csv"))) {
				// If it exists, append the new category to the file
				try (FileWriter writer = new FileWriter("categories.csv", true)) {
					writer.append("\n");
					writer.append(name);
				}
			} else {
				// If the file doesn't exist, create it and write the header row
				try (FileWriter writer = new FileWriter("categories.csv")) {
					writer.append("Category Name");
				}

				// Then append the new category to the file
				saveCategoryToCsv(name);
			}

			// Show a success message
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Success");
			alert.setContentText("Category created successfully.");
			alert.showAndWait();
		} catch (IOException ex) {
			// Show an error message if there was a problem saving the category
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("There was a problem saving the category.");
			alert.showAndWait();
		}
	}

}