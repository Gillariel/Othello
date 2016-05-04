/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Game;
import models.GameComparator;
import models.Gamer;
import views.models.Person;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLTestTournamentController implements Initializable {

    private FXMLDocumentController controller;
    private PriorityQueue<Game> queue;
    
    private ObservableList<Game> data = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Game> tableView;

    private TableColumn<Game, String> idColumns;
    private TableColumn<Game, Integer> priorityColumns;
    private TableColumn<Game, Gamer> J1Columns;
    private TableColumn<Game, Gamer> J2Columns;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        idColumns = new TableColumn("Id");
        idColumns.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        priorityColumns = new TableColumn("Priority");
        priorityColumns.setCellValueFactory(new PropertyValueFactory<>("priority"));
    
        J1Columns = new TableColumn("J1");
        J1Columns.setCellValueFactory(new PropertyValueFactory<>("J1"));
        
        J2Columns = new TableColumn("J2");
        J2Columns.setCellValueFactory(new PropertyValueFactory<>("J2"));
        
        tableView.getColumns().clear();
        tableView.getColumns().addAll(idColumns, priorityColumns, J1Columns, J2Columns);
        tableView.setItems(data);
    }    
    
    public void setQueue(PriorityQueue<Game> q) { 
        this.queue = q;
        queue.stream().forEach((g) -> {
            data.add(g);
        });
        data.sort(new GameComparator());
        
    }
    
    public void setController(FXMLDocumentController c){
        this.controller = c;
    }
}
