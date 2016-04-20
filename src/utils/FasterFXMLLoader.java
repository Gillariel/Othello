/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class FasterFXMLLoader {
    
    
    /** <summary>
    * load the fxmlName views by using his constructors automaticaly
    * </summary>
    * @param fxmlName The fxml's name + .fxml
    * @param classController  Object who launch the method
    */
    public static void load(String fxmlName, Object classController) {
        try{
            Parent root = FXMLLoader.load(classController.getClass().getResource(fxmlName));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre.");
        }
    }
    
    /** <summary>
    * !(Call not the show method of the stage)! load the fxmlName views by using his constructors automaticaly
    * </summary>
     * @param fxmlName The fxml's name + .fxml
     * @param classController  Object who launch the method
     * @param returnStage true -> Stage Object, false -> null
     * @return A Stage Object to customize the window
     * 
    */
    public static Stage load(String fxmlName, Object classController, boolean returnStage) {
        try{
            Parent root = FXMLLoader.load(classController.getClass().getResource(fxmlName));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            return stage;
        }catch(IOException e){
            MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre.");
            return null;
        }
    }
    
    /** <summary>
     * load the fxmlName views by using his constructors automaticaly
     * </summary>
     * @param fxmlName The fxml's name + .fxml
     * @param classController  Object who launch the method
     * @param title Title of the window
    */
    public static void load(String fxmlName, Object classController, String title){
        try{
            Parent root = FXMLLoader.load(classController.getClass().getResource(fxmlName));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre.");
        }
    }
}
