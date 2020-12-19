/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 *
 * @author noahf
 */

public abstract class AbstractModel {
    private final Point SMALLGRID = new Point(17,12);
    private final Point MEDIUMGRID = new Point(24,16);
    private final Point LARGEGRID = new Point(32,20);
    private Integer sizeXAxis = 20;
    private Integer sizeYAxis = 15;
    private final Integer SMALLBLOCK = 12;
    private final Integer MEDIUMBLOCK = 24;
    private final Integer LARGEBLOCK = 48;
    private Integer blockSize = 25;
    private Boolean walls = true;
    private final Integer SLOWSPEED = 175;
    private final Integer MEDIUMSPEED = 125;
    private final Integer FASTSPEED = 75;
    private Integer speed = 125;
    
    public Color[][] colors;
    private Color backgroundColor;
    private Color snakeColor;
    private Color blockColor;
    
    abstract ObservableList<Record> getData() throws IOException;
    abstract void saveData();
    abstract void loadData();
    abstract Record outputRecord(DataEntry dataEntry);
    abstract void start(String name);
    abstract boolean onEndOfGame();
    abstract void pause();
    abstract boolean isRunning();
    
    protected void setXAxis(int x){
        this.sizeXAxis = x;
    }
    protected Integer getXAxis(){
        return this.sizeXAxis;
    }
    protected void setYAxis(int y){
        this.sizeYAxis = y;
    }
    protected Integer getYAxis(){
        return this.sizeYAxis;
    }
    protected Point getSmallGrid(){
        return this.SMALLGRID;
    }
    protected Point getMediumGrid(){
        return this.MEDIUMGRID;
    }
    protected Point getLargeGrid(){
        return this.LARGEGRID;
    }
    protected Integer getSmallBlock(){
        return this.SMALLBLOCK;
    }
    protected Integer getMediumBlock(){
        return this.MEDIUMBLOCK;
    }
    protected Integer getLargeBlock(){
        return this.LARGEBLOCK;
    }
    protected void setBlockSize(int size){
        this.blockSize = size;
    }
    protected Integer getBlockSize(){
        return this.blockSize;
    }
    protected Integer getSlowSpeed(){
        return this.SLOWSPEED;
    }
    protected Integer getMediumSpeed(){
        return this.MEDIUMSPEED;
    }
    protected Integer getFastSpeed(){
        return this.FASTSPEED;
    }
    protected void setSpeed(int speed){
        this.speed = speed;
    }
    protected Integer getSpeed(){
        return this.speed;
    }
    protected void setWalls(boolean toggle){
        this.walls = toggle;
    }
    protected boolean getWalls(){
        return this.walls;
    }
    protected void setBackgroundColor(Color color){
        this.backgroundColor = color;
    }
    protected void setSnakeColor(Color color){
        this.snakeColor = color;
    }
    protected void setBlockColor(Color color){
        this.blockColor = color;
    }
    protected Color getBackgroundColor(){
        return backgroundColor;
    }
    protected Color getSnakeColor(){
        return snakeColor;
    }
    protected Color getBlockColor(){
        return blockColor;
    }

    // ***Property Change Methods taken from: https://www.oracle.com/technical-resources/articles/java/java-se-app-design-with-mvc.html***
    protected PropertyChangeSupport propertyChangeSupport;
    public AbstractModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
