/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datas.ContendersManager;
import datas.MembersManager;
import datas.TournamentManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.util.Pair;
import models.Game;
import models.GameComparator;
import models.Member;
import models.Tournament;
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
    private TableColumn<Person, String> pseudoTableColumn;
    @FXML
    private TableColumn<Person, String> firstnameTableColumn;
    @FXML
    private TableColumn<Person, String> lastnameTableColumn;
    @FXML
    private TableColumn<Person, Integer> wonGamesTableColumn;
    @FXML
    private TableColumn<Person, Integer> lostGamesTableColumn;

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
        pseudoTableColumn.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        firstnameTableColumn = new TableColumn("First Name");
        firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        lastnameTableColumn = new TableColumn("Last Name");
        lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        wonGamesTableColumn = new TableColumn("Won Games");
        wonGamesTableColumn.setCellValueFactory(new PropertyValueFactory<>("wonGames"));

        lostGamesTableColumn = new TableColumn("Lost Games");
        lostGamesTableColumn.setCellValueFactory(new PropertyValueFactory<>("lostGames"));

        CurrentParticipantsView.setItems(data);
        CurrentParticipantsView.getColumns().addAll(pseudoTableColumn, firstnameTableColumn, lastnameTableColumn, wonGamesTableColumn, lostGamesTableColumn);
        
        generateTournamentMenuItem.setDisable(true);
    }

    //Check La Classe utils.FasterFXMLLoader si tu as un doute, c'est juste une manière de ne pas réécrire 100x le meme code :p
    @FXML
    private void handleNewMember(ActionEvent event) {
        FasterFXMLLoader.load("/views/FXMLInscription.fxml", this, "New Member");
    }

    @FXML
    // Méthode faisant le lien entre le click sur le menu et le lancement de la fenetre
    private void handleDeleteMember(ActionEvent event) {
        FasterFXMLLoader.load("/views/FXMLDeleteMember.fxml", this, "Delete Member");
    }

    @FXML
    private void handleModifyMember(ActionEvent event) {
        FasterFXMLLoader.load("/views/FXMLModifyMember.fxml", this, "Modify Member");
    }

    @FXML
    private void handleCurrentTournament(ActionEvent event) {

    }

    @FXML
    private void handleCloseTournament(ActionEvent event) {
        if (data.size() < 2 || data.size() > 16) {
            MyDialog.warningDialog("Error", "Number of member must be between 2 and 16 participants.");
            return;
        }
        closeTournamentMenuItem.setDisable(true);
        generateTournamentMenuItem.setDisable(false);
        btn_add_participant.setVisible(false);
        btn_delete_one.setVisible(false);
        btn_delete_all.setVisible(false);
    }

    @FXML
    private void handleSwitchTournament(ActionEvent event) {
        TournamentManager provider = new TournamentManager();
        List<Pair<String,String>> result = provider.selectAllcontenders();
        String id = "", pseudo = "";
        for(Pair<String,String> p : result){
            id += p.getKey() + " ";
            pseudo += p.getValue() + " ";
        }
        MyDialog.dialogWithoutHeader(id, pseudo);
        
        try{
            List<Game> result1 = provider.selectAllGames();
            String gameId = "", gameJ1 = "", gameJ2 = ""; 
            for(Game g : result1){ 
                gameId+= g.getId() + " ";
                gameJ1+= g.getJ1().getPseudo() + " ";
                gameJ2+= g.getJ2().getPseudo() + " ";
            }
            MyDialog.dialog(id, gameJ1, id);
        }catch(NullPointerException e) {
            MyDialog.warningDialog("Warning", "list de Games actuellement vide!");
        }
    }

    @FXML
    private void handleGenerateTournament(ActionEvent event) {       
       
        List<String> pseudos = new ArrayList<>();
        for(Person p : data)
            pseudos.add(p.getPseudo());
        Tournament t = new Tournament(pseudos.size(), pseudos, CastPersonsList(data));
        try{
            t.bindDataToQueue(1);
            insertGamesToDb(t);
        }catch(InterruptedException e) { 
            MyDialog.warningDialog("Internal Problem", "Error while generating tournament. Please close all your current prog and tru again!");
        }
            
        try {
            FXMLLoader loaderFXML = new FXMLLoader(getClass().getResource("/views/FXMLTestTournament.fxml"));
            Parent root = (Parent) loaderFXML.load();
            FXMLTestTournamentController controller = loaderFXML.getController();
            controller.setController(this);
            controller.setQueue(t.getQueue());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre.");
        }
    }

    //Never using it while the second app is not created and bit completed yet!
    @FXML
    private void handleLaunchGame(ActionEvent event) {
        String cmd = "c:\\windows\\Othello_Game.exe";
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);
            p.waitFor();//si l'application doit attendre a ce que ce process fini
        } catch (IOException | InterruptedException e) {
            MyDialog.warningDialog("Erreur", "Le programme 'Othello - Game' est-il bien installé? (Veuillez ne jamais le changer de place)");
        }
    }

    @FXML
    private void handleRules(ActionEvent event) {
        AppInfo.showRules();
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        AppInfo.showLicence();
    }

    @FXML
    // Impossible de passer par FasterFXMLLoader car besoin d'une référence vers le controller principale (celui-ci)
    private void handleBtnAddParticipant(ActionEvent event) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader(getClass().getResource("/views/FXMLAddContender.fxml"));
            Parent root = (Parent) loaderFXML.load();
            FXMLAddContenderController controller = loaderFXML.getController();
            controller.setMainController(this);
            controller.initDataFromDb();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            MyDialog.warningDialog("Erreur", "Erreur lors du chargement de fenêtre.");
        }/* catch (Exception e) {
            MyDialog.warningDialog("Connection Problem", "Please check your internet connection and try again");
        }*/
    }

    @FXML
    private void handleBtnDeleteParticipant(ActionEvent event) {

        if (!CurrentParticipantsView.getSelectionModel().isEmpty()) {
            if (MyDialog.confirmationDialog("Delete", "Delete Participant have an impact to the contenders", "Are you sure you want to delete all the participants in the list?")) {

                ContendersManager provider = new ContendersManager();
                int index = CurrentParticipantsView.getSelectionModel().getSelectedIndex();

                if (provider.deleteContenders(CurrentParticipantsView.getItems().get(index).getPseudo()) > 0) {
                    MyDialog.dialogWithoutHeader("Delete", "The participant has been successfully deleted ");
                    CurrentParticipantsView.getItems().remove(CurrentParticipantsView.getSelectionModel().getSelectedIndex());
                }
            } else {
                MyDialog.warningDialog("Warning", "no participants has been selected.\nPlease choose one before deleting.");
            }
        }
    }

    @FXML
    private void handleBtnDeleteAllParticipants(ActionEvent event) {
        if (MyDialog.confirmationDialog("All Delete", "Delete Participants have an impact on the Database", "Are you sure you want to delete all the participants in the list?")) {
            datas.ContendersManager provider = new ContendersManager();
            provider.deleteAllContenders();
            CurrentParticipantsView.getItems().clear();
        }
    }

    public void addDataToTableView(String pseudo) {
        datas.MembersManager provider = new MembersManager();
        //datas.ContendersManager pro = new ContendersManager();
        Member p = provider.selectMember(pseudo);
        
        data.add(new Person(p.getPseudo(), p.getFirstname(), p.getLastname(), 0, 0));

        //if (pro.insertContenders(pseudo) > 0) {
            MyDialog.dialogWithoutHeader("Add", "The member has been successful added");
        //}

    }
       
    public List<Person> getData() { return data; }
    
    public boolean isTableViewEmpty() {
        return CurrentParticipantsView.getItems().isEmpty();
    }

    public TableView getTableView() {
        return CurrentParticipantsView;
    }

    public void setTableView(TableView t) {
        CurrentParticipantsView = t;
    }

    public void insertGamesToDb(Tournament t) {
        List<Game> games = new ArrayList<>();
        t.getQueue().stream().forEach((g) -> {
                games.add(g);
        });
        games.sort(new GameComparator());
        datas.TournamentManager provider = new TournamentManager();
        provider.insertGames(games);
    }
    
    public List<Member> CastPersonsList(List<Person> list) {
        List<Member> result = new ArrayList<>();
        for(Person p : list){
            result.add(new Member(p.getPseudo(), p.getFirstname(), p.getLastname()));
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "FXMLController";
    }
}
