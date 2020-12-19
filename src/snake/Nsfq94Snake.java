/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author noahf
 */
public class Nsfq94Snake extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        Controller.model = new Model();
        
//     COMMENTED OUT CODE IS USED TO RESET THE DATA (LEADERBOARD)
//        Controller.model.loadData();
//        DataEntry record;
//        try {
//            FileOutputStream fileOut;
//            fileOut = new FileOutputStream("save.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            for (int i = 1; i <= 100; i++){
//                record = new DataEntry(i, "default", 0);
//                out.writeObject(record);
//            }
//            out.close();
//            fileOut.close(); 
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        Controller.model.loadData();
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInterface.fxml"));
        UIController.rootForUI = loader.load();
        UIController controller = loader.getController();
        Scene scene = new Scene(UIController.rootForUI);
        
        UIController.userInterfaceController = controller;
        UIController.sceneForUI = scene;
        
        stage.setScene(scene);
        stage.setTitle("Noah Free - CS3330 Final Project");
        stage.setResizable(false);
        stage.show();
        controller.start(stage);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
