/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static snake.Controller.model;

/**
 *
 * @author noahf
 */
public class RecordsController extends Controller implements Initializable{
    
    @FXML
    TableView<Record> table;
    @FXML
    TableColumn<Record, String> column1;
    @FXML
    TableColumn<Record, String> column2;
    @FXML
    TableColumn<Record, String> column3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    
    
    
    
    
    
    
    
    
    // how to make and use a table taken from https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
    public void addTable() throws IOException{
        
        column1.setCellValueFactory(new PropertyValueFactory<>("place"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        table.setItems(model.getData());
    }
    
    public void start(Stage stage) {
        Controller.stage = stage; 
        sceneForRecords = stage.getScene();
    }
    
    @FXML
    public void goToUI(ActionEvent event){
        super.goToUI(false);
    }
    
    @FXML
    public void goToAbout(ActionEvent event){
        super.goToAbout();
    }
    
    @FXML
    public void goToSettings(ActionEvent event){
        super.goToSettings();
    }
    
    @FXML
    public void closeWindow(ActionEvent event){
        super.closeWindow();
    }
    
}
