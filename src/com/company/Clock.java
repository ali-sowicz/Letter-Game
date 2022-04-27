package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Klasa timeru gry
 */
public class Clock implements ActionListener {

    JLabel timeLabel= new JLabel("",SwingConstants.CENTER);

    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;

    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);

    Timer timer = new Timer(1000, e -> {

        elapsedTime = elapsedTime + 1000;         //dodano 1000 ms ponieważ timer działa z opóźnieniem 1000ms
        minutes = (elapsedTime / 60000) % 60;
        seconds = (elapsedTime / 1000) % 60;      //gdy upłynie 60 sekund pole się zeruje i zlicza sekundy od zera
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        timeLabel.setText( " Time: "+minutes_string + ":" + seconds_string); //wyświetla aktualny czas w przeznaczonym do tego polu
    });

    /**
     * Definiuje wyglad timera
     */
    public void wygladZegar() {
        timeLabel.setText( minutes_string + ":" + seconds_string);
        timeLabel.setFont(new Font("Rubik", Font.BOLD, 25));
        timeLabel.setForeground(new Color(0x602A2A));
    }

    /**
     * rozpoczyna dzialanie timera
     */
    void start() {
        timer.start();
    }

    /**
     * zatrzymuje timer
     */
    void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
