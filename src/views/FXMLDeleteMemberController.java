/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datas.ParticipantsManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Participant;
import utils.MyDialog;
import views.models.Person;

/**
 * FXML Controller class
 *
 * @author Sev
 */
public class FXMLDeleteMemberController implements Initializable {

    private final ObservableList<Person> data = FXCollections.observableArrayList();
    
    @FXML
    private TextField fieldPseudo;
    @FXML
    private Label pseudoLabel;
    @FXML
    private TableView<Person> memberTableView;
    @FXML
    private TableColumn<Person, String> peudoColomn;
    @FXML
    private TableColumn<Person, String> firstNameColomn;
    @FXML
    private TableColumn<Person, String> lastNameColomn;
    @FXML
    private TableColumn<Person, Integer> lostGamesColomn;
    @FXML
    private TableColumn<Person, Integer> wonGamesColomn;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberTableView.setEditable(true);
        memberTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        peudoColomn.setCellValueFactory(new PropertyValueFactory<Person,String>("pseudo"));
        firstNameColomn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstname"));
        lastNameColomn.setCellValueFactory(new PropertyValueFactory<Person,String>("lastname"));
        wonGamesColomn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("wonGames"));
        lostGamesColomn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("lostGames"));
        
        memberTableView.setItems(data);
    }    

    @FXML
    private void handle_btn_delete(ActionEvent event) {
        if(!memberTableView.getSelectionModel().isEmpty()) {
            datas.ParticipantsManager provider = new ParticipantsManager();
            int index = memberTableView.getSelectionModel().getSelectedIndex();
           if(provider.deleteParticipant((memberTableView.getItems().get(index)).getPseudo()) > 0) {
            MyDialog.dialogWithoutHeader("Delete", "The member has been successfully deleted ");
            memberTableView.getItems().remove(memberTableView.getSelectionModel().getSelectedIndex());
           }else{
               MyDialog.warningDialog("Warning", "Error in Database modification, please check you connection and try again.");
           }
        }else{
            MyDialog.dialogWithoutHeader("Warning", "Please select a member from the list below");
        }
         
    }

    @FXML
    private void handle_btn_search(ActionEvent event) {
        data.clear();
        datas.ParticipantsManager provider = new ParticipantsManager();
        List<Participant> result = provider.selectAllParticipants();
        if(result.isEmpty()) 
            MyDialog.dialogWithoutHeader("Info", "Database is empty right now.");
        else{
            for(Participant p : result)
                if(p.getPseudo().contains(fieldPseudo.getText()))
                    data.add(new Person(p.getPseudo(), p.getFirstname(), p.getLastname(), 0, 0));
        }
        fieldPseudo.setText("");
    }
    
}
