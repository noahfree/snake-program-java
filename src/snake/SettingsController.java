/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import static snake.Controller.model;

/**
 *
 * @author noahf
 */
public class SettingsController extends Controller implements Initializable{
    
    @FXML
    MenuButton difficultyMenu;
    @FXML
    MenuButton gridSizeMenu;
    @FXML
    MenuButton blockSizeMenu;
    @FXML
    MenuButton wallsMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void start(Stage stage) {
        Controller.stage = stage; 
        sceneForSettings = stage.getScene();
    }
   
    @FXML
    public void goToUI(ActionEvent event){
        super.goToUI(false);
    }
    
    
    @FXML
    public void selectSlow(ActionEvent event){
        difficultyMenu.setText("SLOW");
        model.setSpeed(model.getSlowSpeed());
    }
    
    @FXML
    public void selectMedium(ActionEvent event){
        difficultyMenu.setText("MEDIUM");
        model.setSpeed(model.getMediumSpeed());
    }
    
    @FXML
    public void selectFast(ActionEvent event){
        difficultyMenu.setText("FAST");
        model.setSpeed(model.getFastSpeed());
    }
    
    @FXML
    public void wallsTrue(ActionEvent event){
        wallsMenu.setText("ON");
        model.setWalls(true);
    }
    
    @FXML
    public void wallsFalse(ActionEvent event){
        wallsMenu.setText("OFF");
        model.setWalls(false);
    }
    
    @FXML
    public void smallGridOnClick(ActionEvent event){
        gridSizeMenu.setText("SMALL");
        Point point = model.getSmallGrid();
        model.setXAxis((int)point.getX());
        model.setYAxis((int)point.getY());
    }
    
    @FXML
    public void mediumGridOnClick(ActionEvent event){
        gridSizeMenu.setText("MEDIUM");
        Point point = model.getMediumGrid();
        model.setXAxis((int)point.getX());
        model.setYAxis((int)point.getY());
    }
    
    @FXML
    public void largeGridOnClick(ActionEvent event){
        gridSizeMenu.setText("LARGE");
        Point point = model.getLargeGrid();
        model.setXAxis((int)point.getX());
        model.setYAxis((int)point.getY());
    }
    
    @FXML
    public void smallBlockOnClick(ActionEvent event){
        blockSizeMenu.setText("SMALL");
        model.setBlockSize(model.getSmallBlock());
    }
    
    @FXML
    public void mediumBlockOnClick(ActionEvent event){
        blockSizeMenu.setText("MEDIUM");
        model.setBlockSize(model.getMediumBlock());
    }
    
    @FXML
    public void largeBlockOnClick(ActionEvent event){
        blockSizeMenu.setText("LARGE");
        model.setBlockSize(model.getLargeBlock());
    }
    
    @FXML
    public void goToAbout(ActionEvent event){
        super.goToAbout();
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
