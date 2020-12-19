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
import javafx.stage.Stage;


/**
 *
 * @author noahf
 */
public class AboutController extends Controller implements Initializable{
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    
    
    
    
    
    
    
    
    public void start(Stage stage) {
        Controller.stage = stage; 
        sceneForAbout = stage.getScene();
    }
    
    @FXML
    public void goToUI(ActionEvent event){
        super.goToUI(false);
    }
    
    @FXML
    public void goToSettings(ActionEvent event){
        super.goToSettings();
    }
    
    @FXML
    public void goToRecords(ActionEvent event) throws IOException{
        super.goToRecords();
    }
    
    @FXML
    public void closeWindow(ActionEvent event){
        super.closeWindow();
    }
}
