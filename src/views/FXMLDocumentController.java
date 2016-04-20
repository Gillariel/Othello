/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import views.models.Person;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {
    
    private final ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("A", "Z", "a@example.com", 0, 0),
            new Person("B", "X", "b@example.com", 0, 0),
            new Person("C", "W", "c@example.com", 0, 0),
            new Person("D", "Y", "d@example.com", 0, 0),
            new Person("E", "V", "e@example.com", 0, 0)
        );
    
    private Label label;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu editMenu;
    @FXML
    private MenuItem newMemberMenuItem;
    @FXML
    private MenuItem currentTournamentMenuItm;
    @FXML
    private MenuItem closeTournamentMenuItem;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private TableView<Person> CurrentParticipantsView;
    @FXML
    private TableColumn<Person,String> pseudoTableColumn;
    @FXML
    private TableColumn<Person,String> firstnameTableColumn;
    @FXML
    private TableColumn<Person,String> lastnameTableColumn;
    @FXML
    private TableColumn<Person,Integer> wonGamesTableColumn;
    @FXML
    private TableColumn<Person,Integer> lostGamesTableColumn;
    @FXML
    private Button btn_add_participant;
    @FXML
    private Button btn_delete_one;
    @FXML
    private Button btn_delete_all;
    @FXML
    private ImageView imgOthelloGame;
    
    /*private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }*/
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CurrentParticipantsView.setEditable(true);
        
        pseudoTableColumn = new TableColumn("Pseudo");
        pseudoTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("pseudo"));
        
        firstnameTableColumn = new TableColumn("First Name");
        firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstname"));
        
        lastnameTableColumn = new TableColumn("Last Name");
        lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("lastname"));
        
        wonGamesTableColumn = new TableColumn("Won Games");
        wonGamesTableColumn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("wonGames"));
        
        lostGamesTableColumn = new TableColumn("Lost Games");
        lostGamesTableColumn.setCellValueFactory(new PropertyValueFactory<Person,Integer>("lostGames"));
    
        CurrentParticipantsView.setItems(data);
        CurrentParticipantsView.getColumns().addAll(pseudoTableColumn,firstnameTableColumn, lastnameTableColumn, wonGamesTableColumn, lostGamesTableColumn);
        
        btn_add_participant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(new Person("Add","Je marche?", "Bah Oui!",99999,-1));
            }
        });
    }    

    @FXML
    private void handleNewMember(ActionEvent event) {
        try{
           Parent root = FXMLLoader.load(getClass().getResource("/views/FXMLInscription.fxml"));
           Scene scene = new Scene(root);
 
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.setTitle("Inscription");
           stage.centerOnScreen();
           stage.setResizable(false);
           stage.show();
        }catch(IOException e){
            
        }
    }

    @FXML
    private void handleCurrentTournament(ActionEvent event) {
    }

    @FXML
    private void handleCloseTournament(ActionEvent event) {
    }

    @FXML
    private void handleBtnAddParticipant(ActionEvent event) {
    }

    @FXML
    private void handleBtnDeleteParticipant(ActionEvent event) {
    }

    @FXML
    private void handleBtnDeleteAllParticipants(ActionEvent event) {
    }
    
}
