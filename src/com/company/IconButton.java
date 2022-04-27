package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa ikon przyciskow
 */
public class IconButton {
    /**
     * Skaluje zdjecie do rozmiarow 50x50, ktore bedzie wyswietlane na przycisku
     * @param file zdjecie, ktore bedzie ikona
     * @return wyskalowana ikone
     */
    public ImageIcon iconDefault(String file){
        ImageIcon background=new ImageIcon("resources/"+file.toString());
        Image img=background.getImage();
        Image temp=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        background =new ImageIcon(temp);
        return background;}
}
