package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import javafx.stage.Stage;
import model.Asset;
import model.Category;
import model.Location;
import application.NewAsset;



public class ExpiredAsset extends VBox implements LayoutHelper {

    private final String csvFilePath = "asset.csv";
    private TextField searchField;
    private TableView<Asset> assetTable;

    private ArrayList<HBox> layout;
    private final String title = "Expired Assets";  // Set the title for the asset management 
    private final String line1 = "Here are your expired assets.";  // Welcome line
   
    public ExpiredAsset() {

        super(30);   //spacing 30
        super.setPadding(new Insets(40, 40, 40, 40));

        layout = new ArrayList<HBox>();
        
        layout.add(createTitle(title));
        layout.add(createTextFieldLine(line1, true));

        // UI components for search and buttons
        searchField = new TextField();
        searchField.setPromptText("Enter asset name to search");
        
        Button searchButton = new Button("Expired Assets");
        searchButton.setOnAction(e -> searchAssets());
        
        HBox buttonBox = new HBox(10, searchField, searchButton);
        
        // TableView to display search results
        assetTable = new TableView<>();
        initializeTable();
        
        this.getChildren().addAll(buttonBox, assetTable);
    }

    private void initializeTable() {
        TableColumn<Asset, String> nameCol = new TableColumn<>("Asset Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Asset, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        TableColumn<Asset, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        TableColumn<Asset, LocalDate> purchaseDateCol = new TableColumn<>("Purchase Date");
        purchaseDateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        
        TableColumn<Asset, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<Asset, Double> purchaseValueCol = new TableColumn<>("Purchase Value");
        purchaseValueCol.setCellValueFactory(new PropertyValueFactory<>("purchaseValue"));
        
        TableColumn<Asset, LocalDate> warrantyExpDateCol = new TableColumn<>("Warranty Exp Date");
        warrantyExpDateCol.setCellValueFactory(new PropertyValueFactory<>("warrantyExpDate"));
        
        assetTable.getColumns().addAll(nameCol, categoryCol, locationCol, purchaseDateCol, descriptionCol, purchaseValueCol, warrantyExpDateCol);
    }
    
    //Search the asset
    private void searchAssets() {
		String partiLocation = (String) (((ComboBox) layout.get(2).lookup("#choice")).getValue());
		
        List<Asset> assets = loadAssetsFromCsv();
        List<Asset> matchingAssets = assets.stream()
                .filter(asset -> asset.getLocation().toString().equals(partiLocation))
                .collect(Collectors.toList());
       
        assetTable.getItems().setAll(matchingAssets);
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
}
