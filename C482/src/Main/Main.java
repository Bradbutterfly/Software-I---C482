package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.util.Objects;

/**
 * JavaDoc located in  C482/JavaDocs
 */

    public class Main extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
        }

        public static void main(String[] args) {

            launch(args);
        }
    }
