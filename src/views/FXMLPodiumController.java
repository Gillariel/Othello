/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datas.MembersManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.Gamer;

/**
 * FXML Controller class
 *
 * @author Sev
 */
public class FXMLPodiumController implements Initializable {

    @FXML
    private AnchorPane thirdLabel;
    @FXML
    private TableView<Gamer> podiumTableView;
    @FXML
    private TableColumn<Gamer, String> pseudoTableColumn;
    @FXML
    private TableColumn<Gamer, Integer> scoreTableColumn;
    @FXML
    private Label winnerLabel;
    @FXML
    private Label secondLabel;

    private ObservableList<Gamer> data = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        podiumTableView.setEditable(true);
        podiumTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        pseudoTableColumn.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
       
        addDataToTableView();
        
        podiumTableView.setItems(data);
    }
    
    public void addDataToTableView() {
        datas.MembersManager provider = new MembersManager();
        List<Gamer> gamers = provider.selectParticipantsScore();
        gamers.stream().forEach((g) -> {
            data.add(g); 
        });
    }
       
    public List<Gamer> getData() { return data; }   
}
