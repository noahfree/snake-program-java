/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author noahf
 */
public class Controller implements Navigation{
    
    static Model model;
    
    static Parent rootForGame;
    static Parent rootForUI;
    static Parent rootForAbout;
    static Parent rootForSettings;
    static Parent rootForRecords;
    
    static Text pressKey;
    static HBox container;
    static Scene sceneForGame;
    static SnakeController snakeController;
    static StackPane stack;
    static Scene sceneForUI;
    static UIController userInterfaceController;
    static Scene sceneForAbout;
    static AboutController aboutController;
    static Scene sceneForSettings;
    static SettingsController settingsController;
    static Scene sceneForRecords;
    static RecordsController recordsController;
    static Stage stage;
    
    Rectangle rectangle;
    Rectangle[][] spaces;
    
    Color backgroundColor;
    Color snakeColor;
    Color blockColor;
    
    // NOTE: Code related to switching scenes was learned from Professor Wergeles' Lecture 31 on Switching Scenes
    @Override
    public void goToUI(boolean value){
        userInterfaceController.start(stage);
        userInterfaceController.displayPrompt(value);
        stage.getScene().setRoot(rootForUI);
        stage.setWidth(491);
        stage.setHeight(420);
        stage.show();
    }
    
    @Override
    public void goToAbout(){
        if (rootForAbout == null || aboutController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
                rootForAbout = loader.load(); 
                aboutController = loader.getController();
                
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        aboutController.start(stage);
        stage.getScene().setRoot(rootForAbout);
        stage.setWidth(500);
        stage.setHeight(475);
        stage.show();
    }
    
    @Override
    public void goToSettings(){
        
        if (rootForSettings == null || settingsController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
                rootForSettings = loader.load(); 
                settingsController = loader.getController();
                
            } catch (IOException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        settingsController.start(stage);
        stage.getScene().setRoot(rootForSettings);
        stage.setWidth(500);
        stage.setHeight(550);
        stage.show();
    }

    @Override
    public void goToRecords() throws IOException{
        
        if (rootForRecords == null || recordsController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Records.fxml"));
                rootForRecords = loader.load(); 
                recordsController = loader.getController();
                
            } catch (IOException ex) {
                Logger.getLogger(RecordsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        recordsController.addTable();
         
        recordsController.start(stage);
        stage.getScene().setRoot(rootForRecords);
        stage.setWidth(508);
        stage.setHeight(475);
        stage.show();
    }
    
    @Override
    public void closeWindow(){
        stage.close();
    }
    
}
