/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.floor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import javafx.util.Duration;

/**
 *
 * @author noahf
 */
public class Model extends AbstractModel{
    
    public static String toggle;
    private ArrayList<Integer> xAxis;
    private ArrayList<Integer> yAxis;
    
    private TableView<Record> table;
    private ObservableList<Record> data;
    
    private static Integer counter;
    private Color nextColor;
    private Integer nextX;
    private Integer nextY;
    private Integer newX;
    private Integer newY;
    private Integer clock;
    private String name;
    private Integer score;
    private Boolean queue;
    private Boolean endGame;
    
    private final static String FILENAME = "save.ser";
    
    private Timeline timeline = null;
    
    private DataEntry[] dataCollection;
    
    private File[] fileList;
    private String[] songList;
    private final Integer NUMOFSONGS = 10;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean currentStatus = false;
    
    
    
    
    public Model() throws URISyntaxException{
        colors = new Color[5][3];
        colors[0][0] = Color.BLACK;
        colors[0][1] = Color.WHITE;
        colors[0][2] = Color.LIGHTGREEN;
        colors[1][0] = Color.WHITESMOKE;
        colors[1][1] = Color.BURLYWOOD;
        colors[1][2] = Color.CHOCOLATE;
        colors[2][0] = Color.DARKCYAN;
        colors[2][1] = Color.DARKORANGE;
        colors[2][2] = Color.AQUAMARINE;
        colors[3][0] = Color.DARKRED;
        colors[3][1] = Color.GOLDENROD;
        colors[3][2] = Color.BISQUE;
        colors[4][0] = Color.GREY;
        colors[4][1] = Color.FIREBRICK;
        colors[4][2] = Color.DARKOLIVEGREEN;
        
        songList = new String[NUMOFSONGS];
         for (int i = 0; i < NUMOFSONGS; i++){
             songList[i] = "song" + i + ".mp3";
         }
    }
    
    protected int getClock(){
        return this.clock;
    }
    protected Integer getScore(){
        return this.score;
    }
    
    @Override
    public ObservableList<Record> getData() throws IOException{
        data = FXCollections.observableArrayList();
        
        Record record;
        for (int i = 0; i < 100; i++){
            record = outputRecord(dataCollection[i]);
            data.add(record);
        }
     
        return data;
    }
    
    // serialization taken from the lecture code in addition to https://www.tutorialspoint.com/java/java_serialization.htm
    @Override
    public void saveData(){
        
        try {
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(FILENAME);
            try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                for (int i = 0; i < 100; i++){
                    out.writeObject(dataCollection[i]);
                }
            }
            fileOut.close(); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    // serialization taken from the lecture code in addition to https://www.tutorialspoint.com/java/java_serialization.htm
    @Override
    public void loadData(){
        dataCollection = new DataEntry[100];
        FileInputStream fileIn; 
        try {
            fileIn = new FileInputStream(FILENAME);
            try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
                for (int i = 0; i < 100; i++){
                    dataCollection[i] = (DataEntry) in.readObject();
                }
            }
            fileIn.close(); 
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                
        }
            
    }
    
    @Override
    public Record outputRecord(DataEntry dataEntry){
        String place = dataEntry.getPlace().toString();
        String name = dataEntry.getName();
        String score = dataEntry.getScore().toString();
        return new Record(place, name, score);
    }
    
    private void newSong() throws URISyntaxException{
        Integer random = (int) floor(Math.random()*songList.length);
        String mp3 = "musicFiles/" + songList[random];
        URL resource = getClass().getResource(mp3);
        media = new Media(resource.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.25);
    }
    @Override
    public void start(String name){
        try {
            newSong();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.name = name;
        initTimeline(super.getSpeed());
        toggle = "left";
        xAxis = new ArrayList<>();
        yAxis = new ArrayList<>();
        counter = 0;
        clock = 0;
        score = 0;
        queue = false;
        endGame = false;
       
        firePropertyChange("setGameScene", super.getXAxis(), super.getYAxis());
        
        xAxis.add(0, super.getXAxis()/2-1);
        xAxis.add(1, (super.getXAxis()/2));
        yAxis.add(0, super.getYAxis()/2 - 3);
        yAxis.add(1, super.getYAxis()/2 - 3);
        counter += 2;
        
        firePropertyChange("placeBlock", null, null);
        timeline.setCycleCount(Animation.INDEFINITE);
 
   }
   
    
    private void initTimeline(int time){ 
        timeline = new Timeline(new KeyFrame(Duration.millis(time), (ActionEvent event) -> {
            if (media.getDuration().toMillis() - mediaPlayer.getCurrentTime().toMillis() == 0){
                try {
                    newSong();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            clock++;
            if (currentStatus && !(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)){
                try {
                    newSong();
                    mediaPlayer.play();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            switch (toggle) {
                case "left":
                    if ((xAxis.get(0)-1) >= 0){
                        nextX = xAxis.get(0)-1;
                    }
                    else{
                        nextX = (super.getXAxis()-1);
                        if (super.getWalls()){
                            endGame = true;
                        }
                    }   nextY = yAxis.get(0);
                    if (nextX.equals(xAxis.get(1)) && nextY.equals(yAxis.get(1))){
                        if ((xAxis.get(0)+1) <= (super.getXAxis()-1)){
                            nextX = xAxis.get(0)+1;
                        }
                        else{
                            nextX = 0;
                            if (super.getWalls()){
                                endGame = true;
                            }
                        }   nextY = yAxis.get(0);
                    }
                    break;
                case "right":
                    if ((xAxis.get(0)+1) <= (super.getXAxis()-1)){
                        nextX = xAxis.get(0)+1;
                    }
                    else{
                        nextX = 0;
                        if (super.getWalls()){
                            endGame = true;
                        }
                    }   nextY = yAxis.get(0);
                    if (nextX.equals(xAxis.get(1)) && nextY.equals(yAxis.get(1))){
                        if ((xAxis.get(0)-1) >= 0){
                            nextX = xAxis.get(0)-1;
                        }
                        else{
                            nextX = (super.getXAxis()-1);
                            if (super.getWalls()){
                                endGame = true;
                            }
                        }   nextY = yAxis.get(0);
                    }
                    break;
                case "up":
                    if ((yAxis.get(0)-1) >= 0){
                        nextY = yAxis.get(0)-1;
                    }
                    else{
                        nextY = (super.getYAxis()-1);
                        if (super.getWalls()){
                            endGame = true;
                        }
                    }   nextX = xAxis.get(0);
                    if (nextX.equals(xAxis.get(1)) && nextY.equals(yAxis.get(1))){
                        if ((yAxis.get(0)+1) <= (super.getYAxis()-1)){
                            nextY = yAxis.get(0)+1;
                        }
                        else{
                            nextY = 0;
                            if (super.getWalls()){
                                endGame = true;
                            }
                        }   nextX = xAxis.get(0);
                    }
                    break;
                case "down":
                    if ((yAxis.get(0)+1) <= (super.getYAxis()-1)){
                        nextY = yAxis.get(0)+1;
                    }
                    else{
                        nextY = 0;
                        if (super.getWalls()){
                            endGame = true;
                        }
                    }   nextX = xAxis.get(0);
                    if (nextX.equals(xAxis.get(1)) && nextY.equals(yAxis.get(1))){
                        if ((yAxis.get(0)-1) >= 0){
                            nextY = yAxis.get(0)-1;
                        }
                        else{
                            nextY = (super.getYAxis()-1);
                            if (super.getWalls()){
                                endGame = true;
                            }
                        }   nextX = xAxis.get(0);
                    }
                    break;
                default:
                    break;
            }
            if (nextX.equals(nextY)) firePropertyChange("getNextColor", nextX, null);
            else firePropertyChange("getNextColor", nextX, nextY);
            if (nextColor.equals(super.getBlockColor())){
                this.queue = true;
                if (Objects.equals(super.getSpeed(), this.getSlowSpeed())) this.score++;
                else if (Objects.equals(super.getSpeed(), this.getMediumSpeed())) this.score += 3;
                else if (Objects.equals(super.getSpeed(), this.getFastSpeed())) this.score += 5;
                if (Objects.equals(super.getBlockSize(), this.getSmallBlock())) this.score += 2;
                else if (Objects.equals(super.getBlockSize(), this.getMediumBlock())) this.score ++;
                firePropertyChange("placeBlock", null, null);
            }
        
            if (nextColor.equals(super.getSnakeColor()) || endGame == true){
                stop();
            }
            else {
                if (nextX.equals(nextY)) firePropertyChange("setNextBlock", nextX, null);
                else firePropertyChange("setNextBlock", nextX, nextY);
                
                if (this.queue == true){
                    counter++;
                    this.queue = false;
                }
                else{
                    if (xAxis.get(counter-1).equals(yAxis.get(counter-1))) firePropertyChange("setLastBlock", xAxis.get(counter-1), null);
                    else firePropertyChange("setLastBlock", xAxis.get(counter-1), yAxis.get(counter-1));
                    xAxis.remove(counter-1);
                    yAxis.remove(counter-1);
                }
                xAxis.add(0, nextX);
                yAxis.add(0, nextY);
            }
        }));
    }
    
    @Override
    public boolean onEndOfGame(){
        return counter == 0;
    }
    
    private void stop(){
        timeline.pause();
        mediaPlayer.pause();
        currentStatus = false;
 
        if (super.getWalls()){
            score += (int)floor(score/10);
        }
        Integer gridSize = super.getXAxis() * super.getYAxis();
        float multiplier = 1 + counter/(gridSize/10);
        score = (int) floor(score*multiplier);
        
        if (score > dataCollection[99].getScore()){
            int query = dataCollection[0].getScore();
            int i;
            for (i = 0; score < query && i < 100; i++){
                query = dataCollection[i].getScore();
            }
            if (i == 0) i++;
            if (i != 100){
                int k = 0;
                for (int j = 99; k < (100 - i); j--){
                    dataCollection[j] = dataCollection[j-1];
                    dataCollection[j].setPlace(100-k);
                    k++;
                }
                dataCollection[i-1] = new DataEntry(i, name, score);
            }
        }
        
        counter = 0;
        firePropertyChange("setUIScene", null, null);
    } 
    
    @Override
    public void pause(){
        if (isRunning()){
            timeline.pause();
            mediaPlayer.pause();
            firePropertyChange("setTextContinue", null, null);
            currentStatus = false;
        }
        else {
            timeline.play();
            mediaPlayer.play();
            firePropertyChange("setTextPause", null, null);
            currentStatus = true;
        }
    }
    
    // this function was taken from Professor Wergeles' lecture, returns true if stopwatch is running or false if else
    @Override
    public boolean isRunning(){
        if (timeline != null){
            if (timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }
    
    public void setNextColor(Color color){
        this.nextColor = color;
    }
}
