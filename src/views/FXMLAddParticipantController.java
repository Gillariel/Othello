/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import models.Member;
import utils.MyDialog;
import views.models.Person;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAddParticipantController implements Initializable {

    private FXMLDocumentController mainController;

    @FXML
    private MenuBar mainMenuMenuBar;
    @FXML
    private ComboBox<String> participantComboBox;
    @FXML
    private Label partcipantLabel;
    @FXML 
    private Button btn_confirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if (!participantComboBox.getSelectionModel().isEmpty()) {
                    
                    mainController.addDataToTableView(participantComboBox.getItems().get(participantComboBox.getSelectionModel().getSelectedIndex()));
                    participantComboBox.getItems().remove(participantComboBox.getSelectionModel().getSelectedIndex());
                     
                } else {
                    MyDialog.warningDialog("Erreur", "Please select participant to add in the list below or cancel.");
                }
            }
        });
    }

    public void setMainController(FXMLDocumentController c) {
        this.mainController = c;
    }
    
    public void initDataFromDb() {
        datas.MembersManager provider = new datas.MembersManager();
        ObservableList<String> pseudoList = FXCollections.observableArrayList();

        for(Member p : provider.selectAllMembers()) pseudoList.add(p.getPseudo());
        for(Person p : mainController.getData()) pseudoList.remove(p.getPseudo());
        
        participantComboBox.getItems().addAll(pseudoList);
        participantComboBox.getItems().remove("?");
    }
}
