package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasa okna logowania
 */
public class LoginWindow extends JTextField implements ActionListener {
    Frame loginWindow;
    JLabel label; // pytanie o imie
    JLabel mainLabel;
    JTextField textField;
    FinalButton button;
    String name;

    /**
     * Tworzy interfejs okna logowania
     */
    public void createLogWindow() {

        //----------------dodanie zdjecia jako głowny label, który robi za tło ------//
        BackgroundImage temp = new BackgroundImage();
        mainLabel = new JLabel(temp.createBackgroundImg());
        mainLabel.setLayout(new GridLayout(5, 3));

        line0(); //-budowanie layoutu
        line1();//dodanie napisu
        line2();//--dodanie textu
        line3();// -guziczek

        loginWindow = new Frame();
        loginWindow.add(mainLabel);
        loginWindow.createFrame();
        loginWindow.pack();
    }

    /**
     * Rysuje pierwszy panel
     */
    private void line0() {  //przezroczysty panel wypełniający pierwszy wiersz
        JPanel panel0 = new JPanel();
        panel0.add(new JLabel());
        panel0.setOpaque(false);
        mainLabel.add(panel0);
    }

    /**
     * Rysuje panel zawierajacy napis
     */
    private void line1() { //dodanie napisu
        label = new JLabel("Jak masz na imię?", SwingConstants.CENTER);
        label.setFont(new Font("Rubik", Font.BOLD, 35));
        label.setForeground(new Color(0x602A2A));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setOpaque(false);
        panel1.add(label, BorderLayout.SOUTH);
        mainLabel.add(panel1);

    }

    /**
     * Rysuje TextField
     */
    private void line2() { //rysowanie textfieldu
        textField = new JTextField();
        textField.setFont(new Font("Rubik", Font.BOLD, 35));
        textField.setForeground(new Color(0x602A2A));
        textField.setBackground(new Color(0xe39586));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0x602A2A), 3, true));
        textField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setOpaque(false);

        for(int i = 0; i <= 3; i++) { //----------rysowanie przezroczystych paneli wypełniających części panel3
            JPanel panel1 = new JPanel();
            JLabel l = new JLabel();
            panel1.setOpaque(false);
            panel1.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL; //----ustalanie szerokości i wysokości paneli
            c.ipadx = 120;
            c.ipady = 45;
            panel1.add(l, c);

            if(i == 0){
                panel3.add(panel1, BorderLayout.LINE_START);  //dodawanie do panelu z layoutem BorderLayout puste panele
            }
            if(i == 1){
                panel3.add(panel1, BorderLayout.LINE_END);
            }
            if(i == 2){
                panel3.add(panel1, BorderLayout.PAGE_START);
            }
            if(i == 3){
                panel3.add(panel1, BorderLayout.PAGE_END);
            }
        }
        panel3.add(textField, BorderLayout.CENTER); //dodanie textfieldu w pozycji centralnej panelu z layotem BorderLayout
        mainLabel.add(panel3);


    }

    /**
     * Rysuje przycisk "OK"
     */
    private void line3() { //dodanie przycisku "OK"

        button = new FinalButton();
        button.setButtonFeatures();
        button.setText("OK");
        button.addActionListener(this);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(3, 3)); //stworzenie panelu 3x3

        for (int i = 0; i < 7; i++) { //dodanie 7 pustych labeli w celu ustalenia wielkości i pozycji przycisku "OK"
            JLabel empty = new JLabel();
            panel4.add(empty);
        }
        panel4.add(button); // dodanie przycisku do panel4

        panel4.setOpaque(false);
        mainLabel.add(panel4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            if(textField.getText().equals("")){ //jeśli użytkownik nie podał imienia, wyskakuje okienko z wiadomością
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "Wpisz imię!");
            }
            else {
                name = textField.getText(); //podane imie zostaje wpisane do pliku txt
                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new FileWriter("username.txt"));
                    writer.write(name);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                new LevelWindow().createLvLWindow(); //przekierowanie do okna z wyborem poziomów
                loginWindow.dispose(); //zamknięcie bieżącego okna
            }
        }

    }
}







