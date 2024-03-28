
//Source code recreated from a .class file by IntelliJ IDEA
//(powered by FernFlower decompiler)
//

package jeffery;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
 public Main() {
 }

 public void start(Stage primaryStage) {
     try {
         primaryStage.setTitle("Home");
         Button newCategory = new Button("New category");
         Label title = new Label("Welcome to SortYourLife");
         HBox root = new HBox(15.0);
         root.getChildren().add(newCategory);
         root.getChildren().add(title);
         Scene scene = new Scene(root, 400.0, 400.0);
         primaryStage.setScene(scene);
         primaryStage.show();
     } catch (Exception var6) {
         var6.printStackTrace();
     }

 }

 public static void main(String[] args) {
     launch(args);
 }
}