/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author noahf
 */
public class Record {
    private SimpleStringProperty place;
    private SimpleStringProperty name;
    private SimpleStringProperty score;
 
    public Record(String place, String name, String score){
        this.place = new SimpleStringProperty(place);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(score);
    }
    
    public String getPlace(){
        return place.get();
    }
    
    public String getName(){
        return name.get();
    }
    
    public String getScore(){
        return score.get();
    }
}
