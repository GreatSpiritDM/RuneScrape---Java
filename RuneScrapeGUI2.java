/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabAssignment6;

import static LabAssignment6.RuneScrape.getHTML;
import static LabAssignment6.RuneScrape.getPrice;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 1996n
 */

public class RuneScrapeGUI2 extends Application {
    public static TextArea outText = new TextArea("Your results here");
    public static TextField tf = new TextField();
    public static ComboBox greaterLesser = new ComboBox();
    public static TextField targetPrice = new TextField();
    public int priceTarget = 0;
    public static boolean isRunning = true;
    public static int count = 0;
    public static ArrayList<HBox> hboxList = new ArrayList();
    public static ArrayList<Threads> threadsList = new ArrayList();
    public static ArrayList<Boolean> booleanList = new ArrayList();
    public static VBox itemsVbox = new VBox();
    int exchangeRate = 0;
    //public ArrayList list = new ArrayList();
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        HBox hbox0 = new HBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        
        
        //ArrayList hboxList = new ArrayList();
        
        
        outText.setWrapText(true);
        greaterLesser.getItems().addAll(">", "<", "=");
        greaterLesser.setValue(">");
        Text info = new Text("Enter the item you would like to track, and the price point which you would like to be notified about.");
        
        tf.setPromptText("Your item name here");
        
        targetPrice.setPromptText("Your item price here");
        Button btn = new Button();
        btn.setText("Run");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("This will start our program for the given item.");
            
            try{
                exchangeRate = getPrice(getHTML(tf.getText()));
            } catch (Exception e){
                System.out.println("disambiguation page?");
                outText.setText(outText.getText()+"\nEnter item name again. Check for spelling errors. You may have entered an item with a disambiguation page.");
                Media m = new Media(Paths.get("konata_error.mp3").toUri().toString());
                new MediaPlayer(m).play();
                return;
            }
            outText.setText(outText.getText()+"\nYour item trades at "+exchangeRate+" gp");
            
            //itemObj item = new itemObj(tf.getText(), greaterLesser.getValue().toString(), Integer.parseInt(targetPrice.getText()));
            if(RuneScrape.isNumeric(targetPrice.getText())){
                try {
                //list.add(item);
                    System.out.println(" thread created");
                Threads itemThread = new Threads();
                itemThread.start();
                threadsList.add(itemThread);
                    //System.out.println(threadsList.size());
                
                // put item hbox into itemsvbox passing the name and such
                
                hboxList.add(itemHbox(tf.getText(), greaterLesser.getValue().toString(), Integer.parseInt(targetPrice.getText()), count, exchangeRate));
                itemsVbox.getChildren().add(hboxList.get(hboxList.size() - 1));
                
                // System.out.println(hboxList.size());
                count ++;
                } catch (IOException ex) {
                Logger.getLogger(RuneScrapeGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                outText.setText(outText.getText()+"\nEnter item price again. Check for non-integer characters.");
                Media m = new Media(Paths.get("konata_error.mp3").toUri().toString());
                new MediaPlayer(m).play();
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
        isRunning = false;
        });
        
        
        vbox.getChildren().add(hbox0);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(itemsVbox);
        
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
    
    
    public static HBox itemHbox(String itemName, String greaterLesser, int price, int count, int exchangeRate){
        int targetPrice = price;
        int currentPrice = exchangeRate;
        //Image img = new Image(RuneScrape.getImg(itemName)); //Images just aren't working for some reason. Maybe RSWiki threw a wrench in things?
        //Image img = new Image("https://runescape.wiki/images/Armadyl_godsword.png");
        System.out.println(RuneScrape.getImg(itemName));
        //ImageView imgV = new ImageView(img);
        Button close = new Button("Stop tracking item");
        
        HBox itemBox = new HBox();
        //itemBox.setId(String.valueOf(count));
        Text itemBoxText = new Text("Item search #"+(count+1)+": "+itemName+ " priced "+greaterLesser+" "+targetPrice+". Current price: ");
        Text priceText = new Text(currentPrice+"");
        Text notification = new Text("Notify when price reached?");
        CheckBox check = new CheckBox();
        booleanList.add(false);
        //itemBox.getChildren().add(imgV);
        itemBox.getChildren().add(itemBoxText);
        itemBox.getChildren().add(priceText);
        itemBox.getChildren().add(close);
        itemBox.getChildren().add(notification);
        itemBox.getChildren().add(check);
        
        close.setOnAction((ActionEvent event) -> {
            System.out.println("Stopping item tracking for "+itemName);
            itemBox.setVisible(false);
            itemsVbox.getChildren().remove(itemBox);
            update(hboxList, threadsList);
            
        });
        check.setOnAction((e)->{
            //System.out.println("notification box ticked/unticked");
            if(check.isSelected()){
                System.out.println("notification box ticked.");
                booleanList.set(count, true);
                itemBox.setId("notify");
            } else {
                System.out.println("notification box unticked.");
                itemBox.setId("don't");
                booleanList.set(count, false);
            }
        });
        return itemBox;
    }
    /*
    public static boolean isNotify(ArrayList<HBox> itemBoxes, int count){
        if()
    }
    */
    public static void priceUpdate(ArrayList<HBox> itemBoxes, int count, int price, String itemName, String greaterLesser, int exchangeRate){
        HBox newHbox = itemHbox(itemName, greaterLesser, price, count, exchangeRate);
        itemBoxes.set(count, newHbox);
    }
    public static void update(ArrayList<HBox> itemBoxes, ArrayList<Threads> threads){
        int count = 0;
        for (Threads thread : threads) {
            if (!itemBoxes.get(count).isVisible()){
                thread.stopRunning();
            }
            count ++;
        }
        /*
        for (HBox itemBox : itemBoxes) {
            //if (!itemBox.isVisible() && )
            System.out.println(itemBox.getId());
            if (itemBox.getId() == "0"){
                System.out.println("hi");
            }
        }
*/
    }
    //  tf.getText(), greaterLesser.getValue().toString(), Integer.parseInt(targetPrice.getText())
}
