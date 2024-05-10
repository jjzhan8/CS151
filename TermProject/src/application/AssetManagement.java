package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.*;
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



public class AssetManagement extends VBox implements LayoutHelper {

    private final String csvFilePath = "asset.csv";
	private final String catFile = "category.csv";
	private final String locFile = "location.csv";
    private TextField searchField;
    private TableView<Asset> assetTable;

    private ArrayList<HBox> layout;
    private final String title = "Asset Management";  // Set the title for the asset management 
    private final String line1 = "Welcome to Asset Management System";  // Welcome line
   
    public AssetManagement() {

        super(30);   //spacing 30
        super.setPadding(new Insets(40, 40, 40, 40));
        
		getCategories(Category.category);
		getLocations(Location.location);

        layout = new ArrayList<HBox>();
        
        layout.add(createTitle(title));
        layout.add(createTextFieldLine(line1, true));

        // UI components for search and buttons
        searchField = new TextField();
        searchField.setPromptText("Enter asset name to search");
        
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchAssets());
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editAsset());
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteAsset());
    	
        ComboBox cateCombo_box = new ComboBox(FXCollections.observableArrayList(Category.category.keySet()));
        cateCombo_box.getSelectionModel().select(0);
    	cateCombo_box.setPromptText("Select a Location");

        ComboBox locCombo_box = new ComboBox(FXCollections.observableArrayList(Location.location.keySet()));
        locCombo_box.getSelectionModel().select(0);
        locCombo_box.setPromptText("Select a Category");
        
        HBox buttonBox = new HBox(10, searchField, searchButton, editButton, deleteButton, cateCombo_box, locCombo_box);
        
        // TableView to display search results
        assetTable = new TableView<>();
        initializeTable();
        
        this.getChildren().addAll(buttonBox, assetTable);
        /*EventHandler<ActionEvent> locSelect = new EventHandler<ActionEvent>() {
        	public void handle (ActionEvent e) {
                locationFindAsset(locCombo_box);
        	}
        };
        locCombo_box.setOnAction(locSelect);
        
        EventHandler<ActionEvent> catSelect = new EventHandler<ActionEvent>() {
        	public void handle (ActionEvent e) {
                categoryFindAsset(cateCombo_box);
        	}
        };
        cateCombo_box.setOnAction(catSelect);*/
        
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
        String query = searchField.getText();
        if (query.isEmpty()) {
            showAlert(AlertType.WARNING, "Error", "Required field", "Enter the asset name ");
            return;
        }
        
        List<Asset> assets = loadAssetsFromCsv();
        List<Asset> matchingAssets = assets.stream()
                .filter(asset -> asset.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        
        if (matchingAssets.isEmpty()) {

            showAlert(AlertType.INFORMATION, "Error", "No Results", "No assets found matching your search.");

        } else {
            assetTable.getItems().setAll(matchingAssets);
        }
    }
    
private void editAsset() {
    Asset selectedAsset = assetTable.getSelectionModel().getSelectedItem();
    if (selectedAsset == null) {
        showAlert(Alert.AlertType.WARNING, "Warning", "No Asset Selected", "Please select an asset to edit.");
        return;
    }

    // Open a new window to edit the asset details
    Stage editStage = new Stage();
    VBox editLayout = new VBox(20);
    editLayout.setPadding(new Insets(20, 20, 20, 20));

    // Text fields for asset details
    TextField nameField = new TextField(selectedAsset.getName());
    ComboBox<String> categoryField = new ComboBox<>(FXCollections.observableArrayList(Category.category.keySet()));
    categoryField.setValue(selectedAsset.getCategory().toString());

    ComboBox<String> locationField = new ComboBox<>(FXCollections.observableArrayList(Location.location.keySet()));
    locationField.setValue(selectedAsset.getLocation().toString());

    DatePicker purchaseDateField = new DatePicker(selectedAsset.getPurchaseDate());

    TextArea descriptionArea = new TextArea(selectedAsset.getDescription());
    descriptionArea.setWrapText(true);

    TextField valueField = new TextField();
    TextFormatter<Double> formatter = new TextFormatter<>(new DoubleStringConverter());
    valueField.setTextFormatter(formatter);
    valueField.setText(String.valueOf(selectedAsset.getPurchaseValue()));

    DatePicker warrantyExpDateField = new DatePicker(selectedAsset.getWarrantyExpDate());

    // Save button with action to update the asset and save to CSV
    Button saveButton = new Button("Save");
    saveButton.setOnAction(e -> {
        // Validation
        if (nameField.getText().isEmpty() || valueField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Invalid Data", "Asset name and value must not be empty.");
            return;
        }

        try {
            // Update asset details
            selectedAsset.setName(nameField.getText());
            selectedAsset.setCategory(new Category(categoryField.getValue()));
            selectedAsset.setLocation(new Location(locationField.getValue(), ""));
            selectedAsset.setPurchaseDate(purchaseDateField.getValue());
            selectedAsset.setDescription(descriptionArea.getText());
            selectedAsset.setPurchaseValue(Double.parseDouble(valueField.getText()));
            selectedAsset.setWarrantyExpDate(warrantyExpDateField.getValue());

            // Load current assets, find and replace the updated one
            List<Asset> assets = loadAssetsFromCsv();
            int index = assets.indexOf(selectedAsset);
            if (index >= 0) {
                assets.set(index, selectedAsset); // Efficient update
            }

            saveAssetsToCsv(assets); // Save to CSV
            assetTable.refresh(); // Refresh the table with updated data

            editStage.close(); // Close the edit window
        } catch (NumberFormatException ex) {
            showAlert(AlertType.ERROR, "Error", "Invalid Data", "Purchase value must be a valid number.");
        }
    });

    editLayout.getChildren().addAll(
        new Label("Edit Asset"),
        new HBox(10, new Label("Asset Name:"), nameField),
        new HBox(10, new Label("Category:"), categoryField),
        new HBox(10, new Label("Location:"), locationField),
        new HBox(10, new Label("Purchase Date:"), purchaseDateField),
        new HBox(10, new Label("Description:"), descriptionArea),
        new HBox(10, new Label("Purchase Value:"), valueField),
        new HBox(10, new Label("Warranty Expiration Date:"), warrantyExpDateField),
        saveButton
    );

    Scene editScene = new Scene(editLayout, 500, 400);
    editStage.setScene(editScene);
    editStage.showAndWait();
}

    
    private void deleteAsset() {
        Asset selectedAsset = assetTable.getSelectionModel().getSelectedItem();
        if (selectedAsset == null) {
            showAlert(AlertType.WARNING, "Error", "No Asset Selected", "Please select an asset to delete.");
            return;
        }
        
        List<Asset> assets = loadAssetsFromCsv();
        assets.removeIf(asset -> asset.getName().equals(selectedAsset.getName())); // Remove the selected asset
        
        saveAssetsToCsv(assets); // Save the updated list to CSV
        
        assetTable.getItems().remove(selectedAsset);
        showAlert(AlertType.INFORMATION, "Completed", "Asset Deleted", "The selected asset has been deleted.");

        
    }
    
    private void locationFindAsset(ComboBox locCombo_box) {
		 String partiLoc = (String) (((ComboBox) (locCombo_box.lookup("#choice"))).getValue());

    	 List<Asset> assets = loadAssetsFromCsv();
         List<Asset> matchingAssets = assets.stream()
                 .filter(asset -> asset.getLocation().equals(partiLoc))
                 .collect(Collectors.toList());
        
         if (matchingAssets.isEmpty()) {
         	showAlert(AlertType.ERROR, "Error", "No Expired Assets", "You have no expired assets");
         } else {
             assetTable.getItems().setAll(matchingAssets);
         }
    }
    
    private void categoryFindAsset(ComboBox cateCombo_box) {
		 String partiCat = (String) (((ComboBox) (cateCombo_box.lookup("#choice"))).getValue());

    	 List<Asset> assets = loadAssetsFromCsv();
         List<Asset> matchingAssets = assets.stream()
                 .filter(asset -> asset.getLocation().equals(partiCat))
                 .collect(Collectors.toList());
        
         if (matchingAssets.isEmpty()) {
         	showAlert(AlertType.ERROR, "Error", "No Expired Assets", "You have no expired assets");
         } else {
             assetTable.getItems().setAll(matchingAssets);
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

    private void saveAssetsToCsv(List<Asset> assets) {
        try {
            FileWriter writer = new FileWriter(csvFilePath);
            writer.append("Asset Name,Category,Location,Purchase Date,Description,Purchase Value,Warranty Exp Date\n");
            for (Asset asset : assets) {
                writer.append(asset.getName() + "," + 
                              asset.getCategory().getName() + "," + 
                              asset.getLocation().getName() + "," +
                              asset.getPurchaseDate().toString() + "," + 
                              asset.getDescription() + "," + 
                              String.valueOf(asset.getPurchaseValue()) + "," + 
                              asset.getWarrantyExpDate().toString());
                writer.append("\n");
            }
            writer.close();
            
            // Show a success message
            
           // Alert alert = new Alert(AlertType.INFORMATION);
			//alert.setHeaderText("Success");
			//alert.setContentText("Asset saved successfully.");
			//alert.showAndWait();

        } catch (IOException ex) {
            showAlert(AlertType.ERROR, "Error", "File Write Error", "Unable to save assets to CSV");
        }
    }

   
    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private HashMap<String, Category> getCategories(HashMap<String, Category> catMap) {
        try {
            if (Files.exists(Paths.get(catFile))) {
                List<String> lines = Files.readAllLines(Paths.get(catFile));
                for (String line : lines.subList(1, lines.size())) {
                    String[] parts = line.split(","); // Ensure parsing is robust
                    if (parts.length == 1) { // Ensure required data fields
                        Category cat = new Category();
                        cat.setName(parts[0]);
                        catMap.put(cat.toString(), cat);
                    }
                }
            }
        } catch (IOException ex) {

            showAlert(AlertType.ERROR, "Error", "File Read Error", "Unable to read assets from CSV.");

        }
        
        return catMap;
}

    private HashMap<String, Location> getLocations(HashMap<String, Location> catMap) { //change location and string
        try {
            if (Files.exists(Paths.get(locFile))) {
                List<String> lines = Files.readAllLines(Paths.get(locFile));
                for (String line : lines.subList(1, lines.size())) {
                    String[] parts = line.split(","); // Ensure parsing is robust
                    if (parts.length == 2) { // Ensure required data fields
                        Location loc = new Location();
                        loc.setName(parts[0]);
                        loc.setDescription(parts[1]);
                        catMap.put(loc.toString(), loc);
                    }
                }
            }
        } catch (IOException ex) {

            showAlert(AlertType.ERROR, "Error", "File Read Error", "Unable to read assets from CSV.");

        }
        
        return catMap;
    }
}
