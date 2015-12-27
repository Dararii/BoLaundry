package veloundry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**@author Darari
 */
public class VeLoundry extends Application {
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        //root.setId("pane");
        //root.getStylesheets().add(this.getClass().getResource("main.css").toExt‌​ernalForm());
        
        Scene scene = new Scene(root);
        stage.setTitle("VeLoundry");
        stage.setWidth(672);
        stage.setHeight(510);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
