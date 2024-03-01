/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabAssignment6;

import static LabAssignment6.RuneScrape.getHTML;
import static LabAssignment6.RuneScrape.getPrice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 1996n
 */

public class RuneScrapeGUI extends Application {
    public static TextArea outText = new TextArea("Your results here");
    public static TextField tf = new TextField();
    public static ComboBox greaterLesser = new ComboBox();
    public static TextField targetPrice = new TextField();
    public int priceTarget = 0;
    public static boolean isRunning = true;
    //public ArrayList list = new ArrayList();
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        HBox hbox0 = new HBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        VBox itemsVbox = new VBox();
        
        
        greaterLesser.getItems().addAll(">", "<", "=");
        greaterLesser.setValue(">");
        Text info = new Text("Enter the item you would like to track, and the price point which you would like to be notified about.");
        
        tf.setPromptText("Your item name here");
        
        targetPrice.setPromptText("Your item price here");
        Button btn = new Button();
        btn.setText("Run");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("This will start our program for the given item.");
            try {
                outText.setText(outText.getText()+"\nYour item trades at "+getPrice(getHTML(tf.getText()))+" gp");
            } catch (IOException ex) {
                outText.setText(outText.getText()+"\nEnter item name again. Check for spelling errors.");
            }
            //itemObj item = new itemObj(tf.getText(), greaterLesser.getValue().toString(), Integer.parseInt(targetPrice.getText()));
            if(RuneScrape.isNumeric(targetPrice.getText())){
                try {
                //list.add(item);
                Threads itemThread = new Threads();
                
                itemThread.start();
            } catch (IOException ex) {
                Logger.getLogger(RuneScrapeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            } else {
                outText.setText(outText.getText()+"\nEnter item price again. Check for non-integer characters.");
            }
            
        });
        /*
        for (Object object : list) {
            HBox hbox = new HBox();
            Text outName = new Text();
            
        }
        */
        
        primaryStage.setOnCloseRequest(event -> {
        System.out.println("Stage is closing");
        // Save file
        isRunning = false;
        });
        
        
        vbox.getChildren().add(hbox0);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);
        //vbox.getChildren().add(itemsVbox);
        hbox0.getChildren().add(info);
        hbox1.getChildren().add(tf);
        hbox1.getChildren().add(greaterLesser);
        hbox1.getChildren().add(targetPrice);
        hbox1.getChildren().add(btn);
        hbox2.getChildren().add(outText);
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("RuneScrape");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
    public static HBox threadBox(){
        
    }
    */
}
