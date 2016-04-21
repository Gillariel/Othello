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
import utils.SHA256;
import views.models.Person;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLModifyMemberController implements Initializable {

    Participant tempParticipant = new Participant();
    
    @FXML
    private Label labelSearch;
    @FXML
    private Label labelPseudo;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField fieldSearch;
    @FXML
    private TextField fieldPseudo;
    @FXML
    private TextField fieldPassword;
    @FXML
    private Button btn_search;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_reset_form;
    @FXML
    private TableView<Person> searchTableView;
    @FXML
    private TableColumn<Person, String> pseudoTableColumn;
    @FXML
    private TableColumn<Person, String> firstNameTableColumn;
    @FXML
    private TableColumn<Person, String> lastNameTableColumn;
    @FXML
    private TableColumn<Person, Integer> wonGamesTableColumn;
    @FXML
    private TableColumn<Person, Integer> lostGamesTableColumn;
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTableView.setEditable(true);
        
        pseudoTableColumn = new TableColumn("Pseudo");
        pseudoTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("pseudo"));
        
        firstNameTableColumn = new TableColumn("First Name");
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstname"));
        
        lastNameTableColumn = new TableColumn("Last Name");
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("lastname"));
        
        wonGamesTableColumn = new TableColumn("Won Games");
        wonGamesTableColumn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("wonGames"));
        
        lostGamesTableColumn = new TableColumn("Lost Games");
        lostGamesTableColumn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("lostGames"));
    
        searchTableView.setItems(data);
        searchTableView.getColumns().addAll(pseudoTableColumn,firstNameTableColumn, lastNameTableColumn, wonGamesTableColumn, lostGamesTableColumn);
        
    }    

    @FXML
    private void handleSearch(ActionEvent event) {
        datas.ParticipantsManager provider = new datas.ParticipantsManager();
        for(Participant p : provider.selectAllParticipants())
            if(p.getPseudo().contains(fieldSearch.getText()))
                data.add(new Person(p.getPseudo(), p.getFirstname(), p.getLastname(),0,0));
        fieldSearch.setText("");
    }

    @FXML
    private void handleConfirm(ActionEvent event) {
        if(!searchTableView.getSelectionModel().isEmpty()){
            int selectedIndex = searchTableView.getSelectionModel().getSelectedIndex();
            tempParticipant.setPseudo(fieldPseudo.getText());
            tempParticipant.setPassword(SHA256.encode(fieldPassword.getText()));
            tempParticipant.setFirstname(searchTableView.getItems().get(selectedIndex).getFirstname());
            tempParticipant.setLastname(searchTableView.getItems().get(selectedIndex).getLastname());
            datas.ParticipantsManager provider = new datas.ParticipantsManager();
            provider.updateParticipant(tempParticipant);
        }else
            MyDialog.warningDialog("Warning", "Please select a member from the list to update it");
    }

    @FXML
    private void handleReset(ActionEvent event) {
        fieldPseudo.setText("");
        fieldPassword.setText("");
    }
    
}
