package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public interface LayoutHelper {
	/**
	 * Program interface are VBox objects. Each line on page are HBox object.
	 * 
	 * @param page: VBox object
	 * @param line: HBox object
	 */
	public default void initialize(VBox page, ArrayList<HBox> line) {
		page.setPrefSize(560, 300);
		page.setAlignment(Pos.TOP_CENTER);
		// Add contents to the VBox
		for (HBox itr : line) {
			page.getChildren().add(itr);
		}
	}
	/**
	 * Create HBox as line on the page. This method is for Title on the page.
	 * @param arg
	 * @return
	 */
	public default HBox createTitle(String arg) {
		HBox title = new HBox();
		Text titleText = new Text(arg);
		titleText.setFont(Font.font("Arial", 30));

		title.getChildren().add(titleText);
		title.setAlignment(Pos.BASELINE_CENTER);

		return title;
	}
	/**
	 * Create line with TextField with redAsterisk.
	 * @param arg
	 * @param must
	 * @return
	 */
	public default HBox createTextLine(String arg, boolean must) {
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
		textField.setId("text");//set id for textField lookup

		res.getChildren().addAll(label, textField);
		res.setAlignment(Pos.BASELINE_CENTER);
		return res;
	}
	/**
	 * Create line with TextField without redAsterisk. 
	 * @param arg
	 * @return
	 */
	public default HBox createTextLine(String arg) {
		return createTextLine(arg, false);
	}
	/**
	 * Create line with drop down list
	 * @param <T>
	 * @param arg
	 * @param list
	 * @return
	 */
	public default <T> HBox createDropdownList(String arg, List<T> list) {
		// redAsterisk
		HBox res = new HBox();

		res.getChildren().add(redAsterisk());

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
	/**
	 * Create line with date picker.
	 * @param arg
	 * @return
	 */
	public default HBox createDatePicker(String arg) {
		HBox res = new HBox();
		// label
		Label label = new Label(arg);
		label.setFont(Font.font("Arial", 20));
		// date picker
		DatePicker date = new DatePicker();

		res.getChildren().addAll(label, date);
		res.setAlignment(Pos.BASELINE_CENTER);

		return res;
	}
	/**
	 * Create Button (Confirm & Clear)
	 * @param arg: text on Button
	 * @return
	 */
	public default Button createButton(String arg) {
		Button button = new Button(arg);

		button.setStyle("-fx-text-fill: Black; " + "-fx-font-size: 16px; ");
		button.setPrefSize(120, 30);

		return button;
	}
	/**
	 * Create last line on the page which is Confirm & Clear Button.
	 * @return
	 */
	public default HBox lastLine() {
		HBox res = new HBox(50, createButton("Confirm"), createButton("Clear"));
		res.setAlignment(Pos.BASELINE_CENTER);
		return res;
	}
	/**
	 * This method help clear Button clear TextField
	 * @param arg: multiple TextField
	 */
	public default void clearTextField(TextField... arg) {
		for (TextField itr : arg) {
			itr.clear();
		}
	}
	/**
	 * Set action for clear Button
	 * @param arg
	 * @param itr: represent which line is TextField
	 */
	public default void clearButtonAction(ArrayList<HBox> arg, int...itr) {
		((Button)arg.get(arg.size() - 1).getChildren().get(1)).setOnAction(e -> {
			for(int i : itr) {
				clearTextField((TextField)arg.get(i).lookup("#text"));
			}
		});
	}
	/**
	 * This is Label to represent that line can not be empty.
	 * @return
	 */
	public default Label redAsterisk() {
		Label redAsterisk = new Label("*");
		redAsterisk.setFont(Font.font("Arial", 20));
		redAsterisk.setTextFill(Color.RED);

		return redAsterisk;
	}
}
