package com.company;
import javax.swing.*;
import java.awt.*;

/**
 * Klasa ramki okna
 */
public class Frame extends JFrame {
    /**
     *  Tworzy ramke okna, definiuje jego stale wymiary, ikonę i tytul
     */
    public void createFrame() {

        this.setTitle("Słowne Puzzle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 750);
        this.setVisible(true);
        this.setResizable(false);
        ImageIcon image = new ImageIcon("resources/logo.png");
        this.setIconImage(image.getImage());
    }

}

/**
 * Klasa przyciskow gry
 */
class FinalButton extends JButton {
    /**
     * Definiuje staly wyglad i wlasciwosci przycisku
     */
    public void setButtonFeatures(){
        this.setFocusable(false);
        this.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 20));
        this.setBackground(new Color(0xe39586));
        this.setForeground(new Color(0x602A2A));
        this.setBorder(BorderFactory.createLineBorder(new Color(0x602A2A), 3, true));
    }
}







