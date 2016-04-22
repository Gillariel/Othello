/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datas.ParticipantsManager;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Participant;
import utils.AppInfo;
import utils.FasterFXMLLoader;
import utils.MyDialog;
import views.models.Person;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {
    
    //Menu
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private Menu participantMenu;
    @FXML
    private MenuItem newMemberMenuItem;
    @FXML
    private MenuItem deleteMemberMenuItem;
    @FXML
    private MenuItem modifiyMemberMenuItem;
    
    @FXML
    private Menu tournamentMenu;
    @FXML
    private MenuItem currentTournamentMenuItm;
    @FXML
    private MenuItem closeTournamentMenuItem;
    @FXML
    private MenuItem switchTournamentMenuItem;
    @FXML
    private MenuItem generateTournamentMenuItem;
    
    @FXML
    private MenuItem launchGameMenuItem;
    
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem rulesMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    //End Menu
    
    //TableView
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
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    
    //Buttons
    @FXML
    private Button btn_add_participant;
    @FXML
    private Button btn_delete_one;
    @FXML
    private Button btn_delete_all;
    
    @FXML
    private ImageView imgOthelloGame;
    
    @FXML
    private Pane mainMenuPane;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CurrentParticipantsView.setEditable(true);
        CurrentParticipantsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
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
        
    }    

    //Check La Classe utils.FasterFXMLLoader si tu as un doute, c'est juste une manière de ne pas réécrire 100x le meme code :p
    @FXML
    private void handleNewMember(ActionEvent event) { FasterFXMLLoader.load("/views/FXMLInscription.fxml", this); }
    @FXML
    // Méthode faisant le lien entre le click sur le menu et le lancement de la fenetre
    private void handleDeleteMember(ActionEvent event) { FasterFXMLLoader.load("/views/FXMLDeleteMember.fxml", this,"Delete Member");  }
    @FXML
    private void handleModifyMember(ActionEvent event) { FasterFXMLLoader.load("/views/FXMLModifyMember.fxml", this, "Modify Member"); }
    
    @FXML
    private void handleCurrentTournament(ActionEvent event) {
    
    }
    @FXML
    private void handleCloseTournament(ActionEvent event) {
    
    }
    @FXML
    private void handleSwitchTournament(ActionEvent event) {
    
    }
    @FXML
    private void handleGenerateTournament(ActionEvent event) {
    
    }
    
    //Never using it while the second app is not created and bit completed yet!
    @FXML
    private void handleLaunchGame(ActionEvent event) {
        String cmd = "c:\\windows\\Othello_Game.exe";
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);
            p.waitFor();//si l'application doit attendre a ce que ce process fini
        }catch(Exception e) {
            MyDialog.warningDialog("Erreur", "Le programme 'Othello - Game' est-il bien installé? (Veuillez ne jamais le changer de place)");
        } 
    }

    @FXML
    private void handleRules(ActionEvent event) { AppInfo.showRules(); }
    @FXML
    private void handleAbout(ActionEvent event) { AppInfo.showLicence(); }
    
    @FXML
    // Impossible de passer par FasterFXMLLoader car besoin d'une référence vers le controller principale (celui-ci)
    private void handleBtnAddParticipant(ActionEvent event) { 
        try{
            FXMLLoader loaderFXML = new FXMLLoader(getClass().getResource("/views/FXMLAddParticipant.fxml"));
            Parent root = (Parent)loaderFXML.load();
            FXMLAddParticipantController controller = loaderFXML.getController();
            controller.setMainController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }catch(IOException e) { MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre."); }
    }
    
    @FXML
    private void handleBtnDeleteParticipant(ActionEvent event) {
        if(!CurrentParticipantsView.getSelectionModel().isEmpty())
            if(MyDialog.confirmationDialog("All Delete", "Delete Participants not impact the Database","Are you sure you want to delete all the participants in the list?"))
                CurrentParticipantsView.getItems().remove(CurrentParticipantsView.getSelectionModel().getSelectedIndex());
        else
            MyDialog.warningDialog("Warning", "no participants has been selected.\nPlease choose one before deleting.");
    }
    @FXML
    private void handleBtnDeleteAllParticipants(ActionEvent event) {
        if(MyDialog.confirmationDialog("All Delete", "Delete Participants not impact the Database","Are you sure you want to delete all the participants in the list?"))
            CurrentParticipantsView.getItems().clear();
    }
    
    
    public void addDataToTableView(String pseudo){
        datas.ParticipantsManager provider = new ParticipantsManager();
        Participant p = provider.selectParticipant(pseudo);
        data.add(new Person(p.getPseudo(),p.getFirstname(), p.getLastname(),0,0));
    }
    
    public boolean isTableViewEmpty() { return CurrentParticipantsView.getItems().isEmpty(); }
    
    public TableView getTableView() { return CurrentParticipantsView; }
    
    @Override
    public String toString() { return "FXMLController"; }
}
