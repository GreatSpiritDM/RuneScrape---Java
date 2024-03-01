/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabAssignment6;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 *
 * @author 1996n
 */
public class RuneScrape {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Threads first = new Threads();
        //Threads second = new Threads();
        //first.start();
        //second.start();
        int price = -1;
        Document doc = Jsoup.connect("https://runescape.wiki/w/Tin_ore").get();
        Elements span = doc.select("span.infobox-quantity > span");
        //System.out.println(span.text());
        price = Integer.parseInt(span.text().replaceAll(",", ""));
        System.out.println("Price is "+price);
        
        getHTML("bronze med helm");
        
    }
    public static String getHTML(String item){
        String link = "https://runescape.wiki/w/";
        link += item.replaceAll(" ", "_");
        //System.out.println(link);
        return link;
    }
    public static String getImg(String item){
        String link = "https://runescape.wiki/w/File:";
        link += item.replaceAll(" ", "_");
        link += ".png";
        //System.out.println(link);
        return link;
    }
    public static int getPrice(String link) throws IOException{
        Document doc = Jsoup.connect(link).get();
        Elements span = doc.select("span.infobox-quantity > span");
        int price = Integer.parseInt(span.text().replaceAll(",", ""));
        System.out.println("Price is "+ price);
        return price;
    }
    public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double i = Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
}
