package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NewAsset extends VBox {
	/**
	 * This page is VBox object. All page like this will put in StackPane.
	 * Navigation page on the left could change visibility of page on the right.
	 */
	
	private HBox last;
	private ArrayList<HBox> eachLine;
	private final String title = "Create New Asset";
	private final String line1 = "Asset's Name: ";
	private final String line2 = "Category: ";
	private final String line3 = "Location: ";
	

	public NewAsset() {
		super(30); // spacing parameter 30
		super.setPadding(new Insets(40, 40, 40, 40));
		
		eachLine = new ArrayList<HBox>();
	
        eachLine.add(createTitle(title));

        eachLine.add(createTextLine(line1, true));
        eachLine.add(createDropdownList(line2, true));
        eachLine.add(createDropdownList(line3, true));
        
        Button confirm = createButton("Confirm");
        Button clear = createButton("Clear");
        last = new HBox(50, confirm, clear);
        last.setAlignment(Pos.BASELINE_CENTER);
        
        eachLine.add(last);
        
		initialize(eachLine);
	}
	
	private void initialize(ArrayList<HBox> arg) {
		this.setPrefSize(560, 300);
		this.setAlignment(Pos.TOP_LEFT);
		// Add contents to the VBox
		for(HBox itr: arg) {
			this.getChildren().add(itr);
		}
	}

	public Button createButton(String arg) {
		Button button = new Button(arg);

		button.setStyle("-fx-text-fill: Black; " + "-fx-font-size: 16px; ");

		button.setPrefSize(120, 30);

		return button;
	}
	
	/**
	 * Create HBox that contain the following: redAsterisk(optional) label
	 * text(must) text field(must)
	 */
	private HBox createTitle(String arg) {
		HBox title = new HBox();
		Text titleText = new Text(arg);
        titleText.setFont(Font.font("Arial", 30));
        
        title.getChildren().add(titleText);
        title.setAlignment(Pos.BASELINE_CENTER);
        
        return title;
	}
	
	private HBox createTextLine(String arg, boolean must) {
		// redAsterisk
		HBox res = new HBox();
		if (must) {
			Label redAsterisk = new Label("*");
			redAsterisk.setFont(Font.font("Arial", 20));
			redAsterisk.setTextFill(Color.RED);

			res.getChildren().add(redAsterisk);
		}
		// label
		Label label = new Label(arg);
		label.setFont(Font.font("Arial", 20));
		// text field
		TextField textField = new TextField();

		res.getChildren().addAll(label, textField);
		res.setAlignment(Pos.BASELINE_CENTER);
		return res;
	}

	private HBox createDropdownList(String arg, boolean must) {
		// redAsterisk
		HBox res = new HBox();
		if (must) {
			Label redAsterisk = new Label("*");
			redAsterisk.setFont(Font.font("Arial", 20));
			redAsterisk.setTextFill(Color.RED);

			res.getChildren().add(redAsterisk);
		}
		// label
		Label label = new Label(arg);
		label.setFont(Font.font("Arial", 20));

		// temporary comboBox
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Example 1", "Example 2");
		comboBox.setPromptText("Select a " + arg);
		// temporary action
		comboBox.setOnAction(e -> {
			String selected = comboBox.getValue();
			System.out.println("Selected item: " + selected);
		});

		res.getChildren().addAll(label, comboBox);
		res.setAlignment(Pos.BASELINE_CENTER);
		return res;
	}

	

}
