/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Alert;

/**
 *
 * @author nathan
 */
public class MyDialog {

    public static void dialogWithoutHeader(String title, String text) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setContentText(text);
        info.setHeaderText(null);
        info.showAndWait();
    }
    
    public static void dialog(String title, String header, String text) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setContentText(text);
        info.setHeaderText(header);
        info.showAndWait();
    }
    
    public static void warningDialog(String title, String text) {
        Alert info = new Alert(Alert.AlertType.WARNING);
        info.setTitle(title);
        info.setContentText(text);
        info.setHeaderText(null);
        info.showAndWait();
    }
}
