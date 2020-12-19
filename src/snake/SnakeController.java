/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static snake.Controller.model;
import static snake.Controller.sceneForGame;

/**
 *
 * @author noahf
 */
public class SnakeController extends Controller implements Initializable, PropertyChangeListener{
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model.addPropertyChangeListener(this);
    }  
    
    private void placeBlockRandom(){
        Integer newX = ThreadLocalRandom.current().nextInt(0, model.getXAxis());
        Integer newY = ThreadLocalRandom.current().nextInt(0, model.getYAxis());
            
        if (spaces[newX][newY].getFill().equals(model.getSnakeColor()) || spaces[newX][newY].getFill().equals(model.getBlockColor())){
            placeBlockRandom();
        }
        else {
            spaces[newX][newY].setFill(model.getBlockColor());
        }
   }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "getNextColor":
                int nextX = Integer.parseInt(evt.getOldValue().toString());
                int nextY;
                if (evt.getNewValue() == null) nextY = nextX;
                else nextY = Integer.parseInt(evt.getNewValue().toString());
                model.setNextColor((Color) spaces[nextX][nextY].getFill());
                break;
            case "setNextBlock":
                int x = Integer.parseInt(evt.getOldValue().toString());
                int y;
                if (evt.getNewValue() == null) y = x;
                else y = Integer.parseInt(evt.getNewValue().toString());
                spaces[x][y].setFill(model.getSnakeColor());
                break;
            case "setLastBlock":
                int X = Integer.parseInt(evt.getOldValue().toString());
                int Y;
                if (evt.getNewValue() == null) Y = X;
                else Y = Integer.parseInt(evt.getNewValue().toString());
                spaces[X][Y].setFill(model.getBackgroundColor());
                break;
            case "placeBlock":
                placeBlockRandom();
                break;
            default:
                break;
        }
    }
    
    public void start(Stage stage) {
        Controller.stage = stage; 
        sceneForGame = stage.getScene();
    }
}
