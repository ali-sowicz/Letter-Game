package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Klasa okna koncowego z wynikami
 */
public class ResultsWindow implements ActionListener {
    Frame resultWindow;
    JLabel mainLabel;
    String name;
    String score;
    String time;
    FinalButton lvls;
    FinalButton end;
    JPanel box;

    /**
     * Tworzy interfejs okna końcowego
     * @throws IOException dla readFiles()
     */
    public void createResWindow() throws IOException {

        //----------------dodanie zdjecia jako głowny label, który robi za tło ------//
        BackgroundImage temp = new BackgroundImage();
        mainLabel = new JLabel(temp.createBackgroundImg());
        mainLabel.setLayout(new GridLayout(8, 0));

        readFiles(); // odczytanie danych z plików

        JPanel pan1 = new JPanel(); //dodanie pustego panelu dla wypełnienia pierwszego wiersza
        pan1.setOpaque(false);
        mainLabel.add(pan1);

        JLabel usName = new JLabel( name, SwingConstants.CENTER);
        usName.setFont(new Font("Rubik",Font.BOLD,55));
        usName.setForeground(new Color(0x602A2A));
        mainLabel.add(usName); //dodanie napisu z imienie użytkownika

        JLabel scoreTxt = new JLabel("Twój wynik to: " + score + " pkt", SwingConstants.CENTER);
        scoreTxt.setFont(new Font("Rubik",Font.BOLD,55));
        scoreTxt.setForeground(new Color(0x602A2A));
        mainLabel.add(scoreTxt); // dodanie napisu z wynikiem

        JLabel timeTxt = new JLabel("Twój czas: " + time, SwingConstants.CENTER);
        timeTxt.setFont(new Font("Rubik",Font.BOLD,55));
        timeTxt.setForeground(new Color(0x602A2A));
        mainLabel.add(timeTxt); //dodanie napisu z czasem

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        mainLabel.add(pan); //dodanie pustego panelu dla wypełnienia kolejnego wiersza

        lineButtons(); //powstanie przycisków

        resultWindow = new Frame();
        resultWindow.add(mainLabel);
        resultWindow.createFrame();
        resultWindow.pack();
    }

    /**
     * Tworzy i umiejscawia przyciski
     */
    public void lineButtons(){
        //------------stworzenie i umiejscowienie przycisków --------
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        lvls = new FinalButton();
        lvls.setButtonFeatures();
        lvls.addActionListener(this);
        lvls.setText("Wybierz poziom");

        end = new FinalButton();
        end.setButtonFeatures();
        end.addActionListener(this);
        end.setText("Koniec gry");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 20;
        c.ipady = 40;
        c.gridwidth = 10;
        c.gridheight = 10;

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipadx = 60;
        c1.ipady = 40;
        c1.gridheight = 10;
        c1.gridwidth = 10;

        JLabel l = new JLabel();
        l.setOpaque(false);

        panel.add(lvls,c);
        panel.add(l,c1);
        panel.add(end,c1);

        box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(panel);
        mainLabel.add(box);

    }

    /**
     * Zczytuje wyniki i dane uzytkownika z plikow txt i je wyswietla
     * @throws IOException dla Scanner()
     */
    public void readFiles() throws IOException{  //odczytanie danych z plików zawierających imie, czas i punkty użytkownika
        File username = new File("username.txt");
        Scanner n = new Scanner(username).useDelimiter("\\n");
        name = n.next();

        File res = new File("wyniki.txt");
        Scanner results = new Scanner(res).useDelimiter(";");
        time = results.next();
        score = results.next();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == lvls) {  //powrót do okna wyboru poziomu gry
            new LevelWindow().createLvLWindow();
             resultWindow.dispose();
        }

        if (e.getSource() == end) { //zakończenie gry
            resultWindow.dispose();
        }
    }
}
