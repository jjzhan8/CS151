package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Asset;
import model.Category;
import model.Location;

public class AssetManagement extends Application {
    private List<Asset> assets = new ArrayList<>();
    private final String file = "asset.csv";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Asset Management");

        // Load assets from the CSV file
        loadAssets();

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(40));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter asset name");

        ListView<String> assetListView = new ListView<>();
        
        // Populate list view with asset names
        assetListView.getItems().addAll(assets.stream().map(Asset::getName).collect(Collectors.toList()));

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchAssets(searchField.getText(), assetListView));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteAsset(assetListView.getSelectionModel().getSelectedItem(), assetListView));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            String selectedAsset = assetListView.getSelectionModel().getSelectedItem();
            if (selectedAsset != null) {
                editAsset(selectedAsset);
            } else {
                showAlert(Alert.AlertType.ERROR, "No asset selected", "Please select an asset to edit.");
            }
        });

        mainLayout.getChildren().addAll(searchField, searchButton, assetListView, new HBox(10, deleteButton, editButton));

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadAssets() {
        assets.clear(); // Clear any previous data
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Asset asset = new Asset(
                    parts[0], // Asset name
                    parts[1], // Category
                    parts[2], // Location
                    parts[3], // Purchase date
                    parts[4], // Description
                    parts[5], // Purchase value
                    parts[6]  // Warranty date
                );
                assets.add(asset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchAssets(String searchTerm, ListView<String> listView) {
        listView.getItems().clear(); // Clear previous results
        listView.getItems().addAll(
            assets.stream()
                  .filter(asset -> asset.getName().contains(searchTerm))
                  .map(Asset::getName)
                  .collect(Collectors.toList())
        );
    }

    private void deleteAsset(String assetName, ListView<String> listView) {
        if (assetName == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an asset to delete.");
            return;
        }

        assets.removeIf(asset -> asset.getName().equals(assetName)); // Remove from list

        // Rewrite the CSV file with updated assets
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("temp.csv"))) {
            for (Asset asset : assets) {
                bw.write(asset.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStacktrace();
        }

        File tempFile = new File("temp.csv");
        if (tempFile.renameTo(new File(file))) {
            listView.getItems().remove(assetName); // Update the ListView
            showAlert(Alert.AlertType.INFORMATION, "Success", "Asset deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete asset.");
        }
    }

    private void editAsset(String assetName) {
        // Find the asset to edit
        Asset assetToEdit = assets.stream()
                                  .filter(asset -> asset.getName().equals(assetName))
                                  .findFirst()
                                  .orElse(null);

        if (assetToEdit == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Asset not found.");
            return;
        }

        // Open a new window with editable fields to update the asset
        VBox editLayout = new VBox(20);
        editLayout.setPadding(new Insets(40));

        TextField assetNameField = new TextField(assetToEdit.getName());
        TextField categoryField = new TextField(assetToEdit.getCategory());
        TextField locationField = new TextField(assetToEdit.getLocation());
        DatePicker purchaseDateField = new DatePicker(LocalDate.parse(assetToEdit.getPurchaseDate()));
        TextField descriptionField = new TextField(assetToEdit.getDescription());
        TextField purchaseValueField = new TextField(assetToEdit.getPurchaseValue());
        purchaseValueField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        DatePicker warrantyDateField = new DatePicker(LocalDate.parse(assetToEdit.getWarrantyExpDate()));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            assetToEdit.setName(assetNameField.getText());
            assetToEdit.setCategory(categoryField.getText());
            assetToEdit.setLocation(locationField.getText());
            assetToEdit.setPurchaseDate(purchaseDateField.getValue().toString());
            assetToEdit.setDescription(descriptionField.getText());
            assetToEdit.setPurchaseValue(purchaseValueField.getText());
            assetToEdit.setWarrantyExpDate(warrantyDateField.getValue().toString());

            // Rewrite the CSV file with updated assets
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("temp.csv"))) {
                for (Asset asset : assets) {
                    bw.write(asset.toCsv());
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            File tempFile = new File("temp.csv");
            if (tempFile.renameTo(new File(file))) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Asset updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update asset.");
            }

            // Close the editing window
            ((Stage)saveButton.getScene().getWindow()).close();
        });

        editLayout.getChildren().addAll(assetNameField, categoryField, locationField, purchaseDateField, descriptionField, purchaseValueField, warrantyDateField, saveButton);

        Stage editStage = new Stage();
        editStage.setTitle("Edit Asset");
        editStage.setScene(new Scene(editLayout, 400, 300));
        editStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

