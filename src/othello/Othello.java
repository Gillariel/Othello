/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.MyDialog;
import utils.Serializer;
import views.FXMLDocumentController;

/**
 *
 * @author User
 */
public class Othello extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FXMLDocument.fxml"));
        Parent root = loader.load();
        FXMLDocumentController controller = loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.getIcons().add(new Image("http://swap.sec.net/annex/icon.png"));
        
        if(controllerFileExists(controller.toString()))
            /*FXMLDocumentController con =*/ //Serializer.LoadData(controller);
            
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            MyDialog.warningDialog("Check", "CheckOk");
            if(controller.isTableViewEmpty()){
                if(MyDialog.confirmationDialog("Save", "Tournament is not finished and save in DB", "Do you want to save it in a specific file?"))
                    Serializer.save(controller);
            }
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private boolean controllerFileExists(String name){
        File file = new File(name + ".object");
        return file.exists();
    }
}
