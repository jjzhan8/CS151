package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Asset;
import model.Category;
import model.Location;

public class WelcomePage extends VBox {
	private Text welcomeMessage;
	private Text appName;
	private ImageView logo;
    private final String csvFilePath = "asset.csv";
	private StackPane rightSection = new StackPane();

	public WelcomePage() {
		super(30); // spacing parameter 30

		// Welcome message + app name
		welcomeMessage = textMessage("Welcome to", 40);
		appName = textMessage("QuickSort", 48);

		// App Logo
		logo = new ImageView(new Image(getClass().getResourceAsStream("/application/resources/logo.png")));

		initialize();
		WarrantyAlarm();
	}

	private void initialize() {
		this.setPrefSize(560, 300);
		this.setAlignment(Pos.CENTER);
		// Add contents to the VBox
		this.getChildren().addAll(welcomeMessage, appName, logo);
	}

	public Text textMessage(String arg, int size) {
		Text text = new Text(arg);
		text.setFont(Font.font("Helvetica", size));

		return text;
	}
	
	public void WarrantyAlarm() {
		List<Asset> assets = loadAssetsFromCsv();
	    List<Asset> matchingAssets = assets.stream()
	    		.filter(asset -> asset.getWarrantyExpDate().isBefore(LocalDate.now()))
	    		.collect(Collectors.toList());
		if (matchingAssets.isEmpty()) {
			return;
		} else {
            showAlert(AlertType.NONE, "You have assets with expired warranties.");
		}
	}
	
	  private List<Asset> loadAssetsFromCsv() {
	        List<Asset> assets = new ArrayList<>();
	        
	        try {
	            if (Files.exists(Paths.get(csvFilePath))) {
	                List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
	                for (String line : lines.subList(1, lines.size())) {
	                    String[] parts = line.split(","); // Ensure parsing is robust
	                    if (parts.length >= 6) { // Ensure required data fields
	                        Asset asset = new Asset();
	                        asset.setName(parts[0]);
	                        asset.setCategory(new Category(parts[1]));
	                        asset.setLocation(new Location(parts[2], ""));
	                        asset.setPurchaseDate(LocalDate.parse(parts[3]));
	                        asset.setDescription(parts[4]);
	                        asset.setPurchaseValue(Double.parseDouble(parts[5]));
	                        asset.setWarrantyExpDate(LocalDate.parse(parts[6]));
	                        assets.add(asset);
	                    }
	                }
	            }
	        } catch (IOException ex) {

	            showAlert(AlertType.ERROR, "Error", "File Read Error", "Unable to read assets from CSV.");

	        }
	        
	        return assets;
	    }
	  
	    private void showAlert(AlertType type, String title, String header, String content) {
	        Alert alert = new Alert(type);
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
	    
	    private void showAlert(AlertType type, String content) {
	        Alert alert = new Alert(type);
	        alert.setContentText(content);
	        ButtonType showMe = new ButtonType("Show Me");
	        alert.getButtonTypes().addAll(ButtonType.OK, showMe);
	        ButtonType result = alert.showAndWait().orElse(showMe);
	    }

}
