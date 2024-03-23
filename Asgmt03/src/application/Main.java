//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	final private String appName = "SortYourLife";
	
	private NavigationMenu navigationMenu = new NavigationMenu();
	private WelcomePage welcomePage = new WelcomePage();
	private NewCategory newCategory = new NewCategory();
	private NewLocation newLocation = new NewLocation();
	
	
    public void start(Stage primaryStage) throws Exception {
        
    	StackPane rightSection = new StackPane();
    	rightSection.getChildren().addAll(welcomePage, newCategory, newLocation);
    	rightSection.getChildren().get(0).setVisible(false);
    	rightSection.getChildren().get(1).setVisible(false);
    	rightSection.getChildren().get(2).setVisible(true);

    	
    	HBox mainBkgd = new HBox();
    	mainBkgd.getChildren().addAll(navigationMenu, rightSection);
    	
        Scene scene = new Scene(mainBkgd, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(appName);
        primaryStage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
