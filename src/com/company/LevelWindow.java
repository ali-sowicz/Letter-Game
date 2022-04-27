package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa okna poziomow
 */
public class LevelWindow implements ActionListener {
   
    FinalButton button1;
    FinalButton button2;
    FinalButton button3;
    FinalButton buttonBack; //powrot
    FinalButton endGame;
    JLabel mainLabel;
    Frame frameLvlWindow;

    /**
     * Tworzy interfejs okna poziomow
     */
    public void createLvLWindow() {
        //----------------dodanie zdjecia jako głowny label, który jest tłem ------//
        BackgroundImage temp = new BackgroundImage();
        mainLabel = new JLabel(temp.createBackgroundImg());
        mainLabel.setLayout(new GridLayout(6, 3));

        line0();
        line1();
        line2();
        line3();
        line4();
        line5();

        frameLvlWindow = new Frame();
        frameLvlWindow.add(mainLabel);
        frameLvlWindow.createFrame();
        frameLvlWindow.pack();

    }

    /**
     * Rysuje pierwszy panel
     */
    private void line0(){
        JPanel empty = new JPanel();
        empty.add(new JLabel());
        empty.setOpaque(false);
        mainLabel.add(empty);
    }

    /**
     * rysuje napis
     */
    private void line1(){ //rysowanie napisu
        JLabel label = new JLabel("Wybierz poziom", SwingConstants.CENTER);
        label.setFont(new Font("Bahnschrift", Font.BOLD, 35));
        label.setForeground(new Color(0x602A2A));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setOpaque(false);
        panel2.add(label, BorderLayout.CENTER);
        mainLabel.add(panel2);
    }

    /**
     * Rysuje przycisk poziomu latwego
     */
    private void line2(){ //dodanie przycisku poziomu
        button1 = new FinalButton();
        button1.setButtonFeatures();
        button1.setText("Latwy");
        button1.addActionListener(this);

        // stworzenie panelu 2x3 i odpowiednie wypełnienie go pustymi
        // labelami i przyciskiem w celu ustalenia jego rozmiaru i położenia

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(2, 3));
        panel3.setOpaque(false);

        for (int i = 0; i < 1; i++) {
            JLabel pusty = new JLabel();
            panel3.add(pusty);
        }
        panel3.add(button1);
        for (int i = 0; i < 3; i++) {
            JLabel pusty = new JLabel();
            panel3.add(pusty);
        }
        mainLabel.add(panel3);
    }
    /**
     * Rysuje przycisk poziomu sredniego
     */
    private void line3(){
        button2 = new FinalButton();
        button2.setButtonFeatures();
        button2.setText("Sredni");
        button2.addActionListener(this);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(2, 3));

        for (int i = 0; i < 1; i++) {
            JLabel pusty = new JLabel();
            panel4.add(pusty);
        }
        panel4.add(button2);
        for (int i = 0; i < 3; i++) {
            JLabel pusty = new JLabel();
            panel4.add(pusty);
        }
        panel4.setOpaque(false);
        mainLabel.add(panel4);

    }
    /**
     * Rysuje przycisk poziomu trudnego
     */
    private void line4(){

        button3 = new FinalButton();
        button3.setButtonFeatures();
        button3.setText("Trudny");
        button3.addActionListener(this);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(2, 3));

        for (int i = 0; i < 1; i++) {
            JLabel pusty = new JLabel();
            panel5.add(pusty);
        }
        panel5.add(button3);
        for (int i = 0; i < 3; i++) {
            JLabel pusty = new JLabel();
            panel5.add(pusty);
        }
        panel5.setOpaque(false);
        mainLabel.add(panel5);


    }

    /**
     * Rysuje przycisk powrotu i zakonczenia gry
     */
    private void line5(){
        IconButton goBack = new IconButton();  //Klasa ikony
        buttonBack = new FinalButton(); //stworzenie przycisku korzystając z klasy zawierającej jego domyślne parametry
        buttonBack.setButtonFeatures(); //ustawienie wyglądu przycisku
        buttonBack.addActionListener(this);
        buttonBack.setIcon(goBack.iconDefault("B3.png")); //ustawienie zdjęcia w przycisku

        GridBagConstraints c = new GridBagConstraints(); //ustawienie wymiarów przycisku
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 70;
        c.ipady = 10;

        JPanel panelGB = new JPanel(); //stworzenie panelu, w którym będzie znajdował się przycisk
        panelGB.setLayout(new GridBagLayout());
        panelGB.setOpaque(false);
        panelGB.add(buttonBack,c); //nadanie nowych wymiarów

        //----przycisk końca gry----//
        IconButton endG = new IconButton();
        endGame = new FinalButton();
        endGame.addActionListener(this);
        endGame.setButtonFeatures();
        endGame.setFocusable(false);
        endGame.setIcon(endG.iconDefault("Y3.png"));

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipadx = 70;
        c1.ipady = 10;

        JPanel panelEG = new JPanel();
        panelEG.setLayout(new GridBagLayout());
        panelEG.setOpaque(false);
        panelEG.add(endGame,c1);

        JPanel panel6 = new JPanel(); //stworzenie panelu 2x4, w którym będą panele zawierające przyciski
        panel6.setLayout(new GridLayout(2, 4));


       for (int i = 0; i < 5; i++) {  //dodanie 5 pustych labeli w celu ustalenia wielkości i pozycji przycisków
            JLabel pusty = new JLabel();
            panel6.add(pusty);
        }

        panel6.add(panelGB); //dodanie przycisku do panel6
        panel6.add(panelEG);
        panel6.setOpaque(false);
        mainLabel.add(panel6);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) { //kliknięcie przycisku odpowiadającemu łatwemu poziomowi gry
            GameWindow Easy;
            try {
                Easy = new GameWindow(); //stworzenie obiektu klasy GameWindow
                Easy.game("resources/EA.txt","resources/EASY/e%d.png"); // wywołanie metody odpowiadającej za rozpoczęcie gry, podanie
                //plików, w których znajdują się obrazy i słowa
                Easy.setAmount(10); //podanie ilosci punktów zdobytych za każdą poprawną odpowiedź

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frameLvlWindow.dispose(); //zamknięcie okna wyboru poziomów
        }
        if (e.getSource() == button2) {
            GameWindow Medium;
            try {
                Medium = new GameWindow();
                Medium.game("resources/ME.txt","resources/MEDIUM/m%d.png");
                Medium.setAmount(20);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frameLvlWindow.dispose();
        }
        if (e.getSource() == button3) {
            GameWindow Hard;
            try {
                Hard = new GameWindow();
                Hard.game("resources/HA.txt","resources/HARD/h%d.png");
                Hard.setAmount(30);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frameLvlWindow.dispose();

            }
        if(e.getSource() == buttonBack){
            new LoginWindow().createLogWindow(); // powrót do okna wpisywania loginu
            frameLvlWindow.dispose();
        }
        if(e.getSource() == endGame){
            frameLvlWindow.dispose();
        }
        }

    }


