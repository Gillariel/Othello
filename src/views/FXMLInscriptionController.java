/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Participant;
import utils.MyDialog;
import utils.SHA256;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLInscriptionController implements Initializable {

    @FXML
    private Label labelPseudo;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelFirstname;
    @FXML
    private Label labelLastname;
    @FXML
    private TextField fieldPseudo;
    @FXML
    private TextField fieldPassword;
    @FXML
    private TextField fieldFirstname;
    @FXML
    private TextField fieldLastname;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_reset_form;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btn_reset_form.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fieldPseudo.setText("");
                fieldPassword.setText("");
                fieldFirstname.setText("");
                fieldLastname.setText("");
            }
        });
        
        this.btn_confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                datas.ParticipantsManager provider = new datas.ParticipantsManager();
                //Better to use BuilderFactory to not import the package
                Participant p = new Participant(fieldPseudo.getText(), SHA256.encode(fieldPassword.getText()), fieldFirstname.getText(), fieldLastname.getText());
                if(provider.insertParticipant(p) > 0) {
                    MyDialog.dialogWithoutHeader("Ajout", "Le membre a bien été ajouté");
                    try{
                        finalize();
                    }catch (Throwable ex) { Logger.getLogger(FXMLInscriptionController.class.getName()).log(Level.SEVERE, null, ex); }
                }
                else { 
                    MyDialog.warningDialog("Ajout Nok", "Erreur lors de l'insertion du mnouveau membre!");
                    btn_reset_form.fire();
                }
            }
        });
    }    

    @FXML
    private void handleAdd(ActionEvent event) {
    }

    @FXML
    private void handleReset(ActionEvent event) {
    }
    
}
