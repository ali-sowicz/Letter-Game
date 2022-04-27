package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa zdjecie w tle
 */
public class BackgroundImage {
    /**
     * Tworzy zdjecie, ktore bedzie tlem
     * @return zdjecie w rozmiarze 800x800
     */
    public ImageIcon createBackgroundImg(){
    ImageIcon background=new ImageIcon("resources/tlo.png");
    Image img=background.getImage();
    Image temp=img.getScaledInstance(800,780,Image.SCALE_SMOOTH);
    background =new ImageIcon(temp);
    return background;}


}


