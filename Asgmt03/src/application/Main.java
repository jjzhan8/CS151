package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	final private String appName = "SortYourLife";

	private StackPane rightSection = new StackPane();

	private WelcomePage welcomePage = new WelcomePage();
	private NewCategory newCategory = new NewCategory();
	private NewLocation newLocation = new NewLocation();

	private NavigationMenu navigationMenu = new NavigationMenu(choice -> {
		welcomePage.setVisible("Welcome Page".equals(choice));
		newCategory.setVisible("New Category Page".equals(choice));
		newLocation.setVisible("New Location Page".equals(choice));
	});

	public void start(Stage primaryStage) throws Exception {

		rightSection.getChildren().addAll(welcomePage, newCategory, newLocation);
		initialPage();
		
		HBox mainBkgd = new HBox();
		mainBkgd.getChildren().addAll(navigationMenu, rightSection);

		Scene scene = new Scene(mainBkgd, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle(appName);
		primaryStage.show();

	}

	public void initialPage() {
		// initialize with welcome page
		rightSection.getChildren().get(0).setVisible(true);
		// other page not visible
		rightSection.getChildren().get(1).setVisible(false);
		rightSection.getChildren().get(2).setVisible(false);
	}

	public static void main(String[] args) {

		launch(args);
	}
}
