/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 *
 * @author noahf
 */
public class DataEntry implements java.io.Serializable{
    private Integer place;
    private String name;
    private Integer score;
 
    public DataEntry(Integer place, String name, Integer score){
        this.place = place;
        this.name = name;
        this.score = score;
    }
    
    public Integer getPlace(){
        return this.place;
    }
    public String getName(){
        return this.name;
    }
    public Integer getScore(){
        return this.score;
    }
    public void setPlace(Integer place){
        this.place = place;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setScore(Integer score){
        this.score = score;
    }

}
