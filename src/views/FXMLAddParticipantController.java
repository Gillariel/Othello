/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datas.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javax.swing.text.DefaultEditorKit;
import models.Participant;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAddParticipantController implements Initializable {

    @FXML
    private MenuBar mainMenuMenuBar;
    @FXML
    private ComboBox<String> partcipantComboBox;
    @FXML
    private Label partcipantLabel;
    @FXML
    private Button btn_confirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datas.ParticipantsManager provider = new datas.ParticipantsManager();
        ObservableList<String> pseudoList = FXCollections.observableArrayList();
        for(Participant p : provider.selectAllParticipants())
            pseudoList.add(p.getPseudo());
            
        partcipantComboBox.getItems().addAll(pseudoList);
    }    
    
}
