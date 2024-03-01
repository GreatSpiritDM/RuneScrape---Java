/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabAssignment6;

import static LabAssignment6.RuneScrape.getHTML;
import static LabAssignment6.RuneScrape.getPrice;
import static LabAssignment6.RuneScrapeGUI2.booleanList;
import static LabAssignment6.RuneScrapeGUI2.isRunning;
import static LabAssignment6.RuneScrapeGUI2.count;
import static LabAssignment6.RuneScrapeGUI2.hboxList;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author 1996n
 */
public class Threads extends Thread {
    boolean privateRun = isRunning;
    public Threads() throws IOException{
        
        
        //run();
        
        /*
        for (int i = 0; i < 10; i++) {
            System.out.println(item.name + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
    }
    public void stopRunning(){
            privateRun = false;
        }
    
    @Override
    public void run(){
        boolean thisRunning = isRunning;
        int thisCount = count; 
        while (isRunning && thisRunning){
           thisRunning = privateRun;
        
        itemObj item = new itemObj(RuneScrapeGUI2.tf.getText(), RuneScrapeGUI2.greaterLesser.getValue().toString(), Integer.parseInt(RuneScrapeGUI2.targetPrice.getText()));
        
        System.out.println("Made it to the thread.");
        int currentPrice = 1;
        boolean notify = false;
            try {
                currentPrice = getPrice(getHTML(item.name));
            } catch (IOException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
                if(item.greaterLesser == ">" && currentPrice > item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is above the target price of "+item.price);
                    notify = true;
                } else if (item.greaterLesser =="<" && currentPrice < item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is below the target price of "+item.price);
                    notify = true;
                } else if (item.greaterLesser =="=" && currentPrice == item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is equal to the target price of "+item.price);
                    notify = true;
                } else if(item.greaterLesser == ">" && currentPrice <= item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is not above the target price of "+item.price);
                } else if (item.greaterLesser =="<" && currentPrice >= item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is not below the target price of "+item.price);
                } else if (item.greaterLesser =="=" && currentPrice != item.price){
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\n"+
                            item.name + " reached "+currentPrice+" gp, which is not equal to the target price of "+item.price);
                } else {
                    RuneScrapeGUI2.outText.setText(RuneScrapeGUI2.outText.getText()+"\nSomething went wrong.");
                }
                
                if (notify && booleanList.get(count-1)){ //&& hboxList.get(count-1).getChildren().get(4) < Didn't work
                    System.out.println("Sound will play");
                    Media m = new Media(Paths.get("konata_notification.mp3").toUri().toString());
                    new MediaPlayer(m).play();
                } else {
                    System.out.println("sound won't play");
                }
                
                
            /*
            for (int i = 0; i < 10; i++) { //for testing purposes
            System.out.println(item.name + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            */
            //break;
            
            
                try {
                    //Thread.sleep(24*60*60*1000); // sleeps the thred for a day.

                    System.out.println("Sleeping...");
                    // Thread.sleep(1000*10); // purely for testing purposes--re-checks the item prices every ten seconds.
                    Thread.sleep(1000*60*60*24); // This puts the thread to sleep for 24 hours--RuneScape prices are updated daily.
                } catch (InterruptedException ex) {
                    Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                }
        RuneScrapeGUI2.priceUpdate(hboxList, thisCount, item.price, item.name, item.greaterLesser, currentPrice);
        if (isRunning){
            System.out.println("Back through the loop again "+thisCount);
        }    
        }
        

    }
    
}
