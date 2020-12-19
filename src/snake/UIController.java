/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import static java.lang.Math.floor;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static snake.Controller.model;
import static snake.Controller.sceneForGame;
import static snake.Controller.sceneForUI;
import static snake.Controller.stage;
import static snake.Model.toggle;

/**
 *
 * @author noahf
 */
public class UIController extends Controller implements Initializable, PropertyChangeListener{
   
    @FXML
    BorderPane enterName;
    @FXML
    Label pressToPlay;
    @FXML
    TextField nameInput;
    @FXML
    Button play;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model.addPropertyChangeListener(this);
    }  
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "setUIScene":
                pressKey.setFill(Color.WHITESMOKE);
                container = new HBox();
                container.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0,0,0,0))));
                container.getChildren().add(pressKey);
                container.alignmentProperty().set(Pos.CENTER);
                stack.getChildren().remove(pressKey);
                stack.getChildren().add(container);
                pressKey.setText("Thank you for playing!\nScore: " + model.getScore() + "\n\nPress enter to return home\nHold space to view grid");
                sceneForGame.setOnKeyReleased(new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent event){
                        if (event.getCode().equals(KeyCode.SPACE)){
                            if (model.onEndOfGame()){
                                stack.getChildren().add(container);
                            }
                        }
                    }   
                });
                break;
            case "setGameScene":
                Integer sizeXAxis = Integer.parseInt(evt.getOldValue().toString());
                Integer sizeYAxis = Integer.parseInt(evt.getNewValue().toString());
                
                if (rootForGame == null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Snake.fxml"));
                        rootForGame = loader.load(); 
                        snakeController = loader.getController();
                
                    } catch (IOException ex) {
                        Logger.getLogger(SnakeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         
                snakeController.start(stage);
                stage.getScene().setRoot(rootForGame);
                stage.setWidth((sizeXAxis*model.getBlockSize()) + 18);
                stage.setHeight((sizeYAxis*model.getBlockSize()) + 47);
                stage.show();
                
                GridPane grid = new GridPane();
                stack = new StackPane();
                stack.getChildren().add(0, grid);
                
                Integer colorPalette = (int) floor(Math.random()*5);
                
                pressKey = new Text("Press any key to begin.");
                Integer fontSize = model.getBlockSize();
                if (fontSize.equals(model.getLargeBlock())){
                    fontSize = 35;
                }
                else if (fontSize.equals(model.getMediumBlock())){
                    fontSize = 16;
                }
                pressKey.setFont(Font.font("Courier New", FontWeight.BOLD, fontSize));
                pressKey.setFill(model.colors[colorPalette][1]);
                pressKey.textAlignmentProperty().set(TextAlignment.CENTER);
                stack.getChildren().add(1, pressKey);
                stack.setAlignment(Pos.CENTER);
                
                sceneForGame.setRoot(stack);
                spaces = new Rectangle[sizeXAxis][sizeYAxis];
                for (int i = 0; i < sizeXAxis; i++){
                    for (int j = 0; j < sizeYAxis; j++){
                        rectangle = new Rectangle(model.getBlockSize(), model.getBlockSize());
                        rectangle.setFill(model.colors[colorPalette][0]);
                        spaces[i][j] = rectangle;
                        grid.add(rectangle, i, j);
                    }
                }
                spaces[(sizeXAxis/2-1)][sizeYAxis/2 - 3].setFill(model.colors[colorPalette][1]);
                spaces[(sizeXAxis/2)][sizeYAxis/2 - 3].setFill(model.colors[colorPalette][1]);
                snakeController.spaces = spaces;
                model.setBackgroundColor(model.colors[colorPalette][0]);
                model.setSnakeColor(model.colors[colorPalette][1]);
                model.setBlockColor(model.colors[colorPalette][2]);
        
                setKeyActions();
                break;
            case "setTextContinue":
                setTextContinue();
                break;
            case "setTextPause":
                setTextPause();
                break;
            default:
                break;
        }
    }
    // how to use Key Press Events taken from -	https://www.programcreek.com/java-api-examples/index.php?class=javafx.scene.Scene&method=setOnKeyPressed 
    private void setKeyActions(){
        sceneForGame.setOnKeyPressed(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent event){
                 switch (event.getCode()) {
                     case UP:
                     case W:
                     case O:
                         if (!(toggle.equals("down")) && model.isRunning()){
                            toggle = "up"; 
                         }
                         break;
                     case DOWN: 
                     case S:
                     case L:
                         if (!(toggle.equals("up")) && model.isRunning()){
                            toggle = "down";
                         }
                         break;
                     case LEFT:
                     case A:
                     case K:
                         if (!(toggle.equals("right")) && model.isRunning()){
                            toggle = "left"; 
                         }
                         break;
                     case RIGHT:
                     case D:
                     case SEMICOLON:
                         if (!(toggle.equals("left")) && model.isRunning()){
                            toggle = "right"; 
                         }
                         break;
                     case SPACE:
                         if (!model.onEndOfGame()){
                            model.pause();
                         }
                         else {
                            stack.getChildren().remove(container);
                         }
                         break;
                     case ENTER:
                         if (!model.isRunning()){
                             goToUI(true);
                         }
                         if (model.onEndOfGame()){
                             goToUI(true);
                         }
                         break;
                 }
                 if (model.getClock() == 0 & !(model.isRunning())){
                     model.pause();
                 }
             }
         });
    }
    
    @FXML
    public void play(ActionEvent event){
        if (nameInput.getText().equals("")){
            enterName.visibleProperty().set(true);
        }
        else {
            enterName.visibleProperty().set(false);
            model.start(nameInput.getText());
        }    
    }
    
    public void displayPrompt(boolean value){
         enterName.visibleProperty().set(false);
        if (value){
            nameInput.clear();
        }
    }
    
    public void setTextContinue(){
        pressKey.setText("Press space bar to continue.\n(enter to quit)");
    }
    
    public void setTextPause(){
        pressKey.setText("Press space bar to pause.");
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() { // how to set a request on close: -	https://www.programcreek.com/java-api-examples/?class=javafx.stage.Stage&method=setOnCloseRequest 
            @Override
            public void handle(WindowEvent we) {
                model.saveData();
            }
        });
        
        Controller.stage = stage; 
        sceneForUI = stage.getScene();
        
        sceneForUI.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){
                    case ENTER:
                        if (stage.getScene().equals(sceneForUI) && !nameInput.getText().equals("")){
                             model.start(nameInput.getText());
                         }
                }
            }
        });
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
    public void goToRecords(ActionEvent event) throws IOException{
        super.goToRecords();
    }
    
    @FXML
    public void closeWindow(ActionEvent event){
        super.closeWindow();
    }
    
    
}
