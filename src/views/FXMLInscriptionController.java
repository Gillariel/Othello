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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
    @FXML
    private Label passwordComplexityLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fieldPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$")){
                passwordComplexityLabel.setText("Strong ! :O"); passwordComplexityLabel.setTextFill(Color.web("#39ff00"));
            }else if(newValue.matches("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$")){
                passwordComplexityLabel.setText("Good :)"); passwordComplexityLabel.setTextFill(Color.web("#ff9500"));
            }else if(newValue.matches("(?=.{6,}).*")) {
                passwordComplexityLabel.setText("Weak :/"); passwordComplexityLabel.setTextFill(Color.web("#ff0000"));
            }else{
                passwordComplexityLabel.setText("Too small"); passwordComplexityLabel.setTextFill(Color.web("#ff0000"));
            }
        });
        
    }    

    @FXML
    private void handleAdd(ActionEvent event) {
        if(!fieldPseudo.getText().matches("^\\S(.){6,20}\\S$")){
            MyDialog.warningDialog("Erreur", "Le pseudo doit contenir entre 8 et 20 caractères (sans restrictions");
            return;
        }else if(passwordComplexityLabel.getText().equals("Too Small")){
            MyDialog.warningDialog("Erreur", "Le mot de passe doit contenir au minimum 6 caractère");
            return;
        }
        datas.ParticipantsManager provider = new datas.ParticipantsManager();
        //Better to use BuilderFactory to not import the package
        Participant p = new Participant(fieldPseudo.getText(), SHA256.encode(fieldPassword.getText()), fieldFirstname.getText(), fieldLastname.getText());
        if(provider.insertParticipant(p) > 0) {
            MyDialog.dialogWithoutHeader("Ajout", "Le membre a bien été ajouté");
            try{
                //finalize() est une méthode détruisant completement l'objet et libérant ses ressources (a utiliser prudemment!)
                finalize();
            }catch (Throwable ex) { Logger.getLogger(FXMLInscriptionController.class.getName()).log(Level.SEVERE, null, ex); }
        }
        else { 
            MyDialog.warningDialog("Ajout Nok", "Erreur lors de l'insertion du mnouveau membre!");
            btn_reset_form.fire();
        }
    }

    @FXML
    private void handleReset(ActionEvent event) {
        fieldPseudo.setText("");
        fieldPassword.setText("");
        fieldFirstname.setText("");
        fieldLastname.setText("");
    }
}
