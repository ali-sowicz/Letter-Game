package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * Klasa okna gry
 */
public class GameWindow implements ActionListener {
    Frame window;
    int points;
    int amountConstant;
    JLabel score;
    JTextField field;
    JLabel mainLabel;
    JPanel panelTnS;
    JPanel line5;
    JPanel line6;
    JPanel panelBUTTONS;
    String word;
    String trudneLitery;
    List<String>wordsList;
    FinalButton buttonDone;
    FinalButton buttonBackspace;
    JButton[] buttonsArray;
    String buffer ="";
    FinalButton buttonBack;
    FinalButton endGame;
    String file ;
    String fileDirectory;
    JPanel panelTXT;
    int gameloop;
    int count;
    Clock Timer1 = new Clock();
    int amount;

    /**
     * Tworzy interfejs gry
     * @param file plik zawierajacy słowa
     * @param fileDirectory plik zawierajacy zdjecia przedmiotow dopasowanych do słow
     * @throws IOException dla funkcji loadImage() i userInterface()
     */
    public void game(String file, String fileDirectory) throws IOException {

        this.file = file;  //przyjęcie podanej nazwy pliku txt ze słowami
        this.fileDirectory = fileDirectory;// przyjęcie podanej nazwy pliku ze zdjęciami
        setAmount(this.amount); //przyjęcie podanych punktów dla danego poziomu trudności

        //----------------dodanie zdjecia jako głowny label, który jest tłem ------//
        BackgroundImage temp = new BackgroundImage();
        mainLabel = new JLabel(temp.createBackgroundImg());
        mainLabel.setLayout(new GridLayout(3,0));       //podział rozkładu interfejsu na 3 rzędy

        loadImage(); //załadowanie zdjęć z pliku do tablicy
        userInterface(); //załadowanie wszystkich elementów na ekranie

        window = new Frame(); //stworzenie ramki korzystając z klasy Frame
        window.add(mainLabel); //dodanie tła do ramki
        window.createFrame(); //nadanie cech ramce
        window.pack();
    }

    /**
     * Rysuje zdjecie przedstawiajace przedmiot przypisany danemu slowu
     * @throws IOException dla funkcji loadImage()
     */
    private void part1() throws IOException {
        JLabel image;
        image = new JLabel();
        ArrayList<Image> imgArray = this.loadImage(); //załadowuje zdjęcia dla danego poziomu do tablicy
        ImageIcon icon = new ImageIcon(imgArray.get(gameloop)); //wybiera zdjęcie o indeksie takim samym jak numer słowa w danej kolejności
        Image newimg =  icon.getImage().getScaledInstance(230,220, java.awt.Image.SCALE_SMOOTH); //skaluje zdjęcie
        icon =new ImageIcon(newimg); //zwraca wyskalowane zdjęcie
        image.setIcon(icon); //rysuje zdjęcie na labelu


        image.setBorder(BorderFactory.createLineBorder(new Color(0x602A2A),8,true)); //obramowanie labelu
        JPanel panel = new JPanel(); //stworzenie panelu, w którym będzie znajdował się label ze zdjęciem
        panel.setLayout(new FlowLayout(FlowLayout.CENTER)); //ustawienie layoutu i pozycji
        panel.setOpaque(false);
        panel.add(image);
        mainLabel.add(panel);

    }

    /**
     * Rysuje TextField i umiejscawia przyciski
     */
    private void part2(){
        //--------------rysowanie textfieldu
        field = new JTextField();
        field.addActionListener(this);
        field.setFont(new Font("Rubik",Font.BOLD,35));
        field.setForeground(new Color(0x602A2A));
        field.setBackground(new Color(0xe39586));
        field.setBorder(BorderFactory.createLineBorder(new Color(0x602A2A),3,true));
        field.setHorizontalAlignment(SwingConstants.CENTER); //wpisywany tekst będzie wyśrodkowany
        //-------------stworzenie panelu, w którym będzie znajdował się textfield
        panelTXT = new JPanel();
        panelTXT.setLayout(new BorderLayout());
        panelTXT.setOpaque(false);

        for(int i = 0; i <= 3; i++) { //----------rysowanie przezroczystych paneli wypełniających części panel3
            JPanel panel1 = new JPanel();
            JLabel l = new JLabel();
            panel1.setLayout(new GridBagLayout());
            panel1.setOpaque(false);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL; //----ustalanie szerokości i wysokości paneli
            c.ipadx = 90;
            c.ipady = 30;
            panel1.add(l, c);

            if(i == 0){
                panelTXT.add(panel1, BorderLayout.LINE_START); //dodawanie do panelu z layoutem BorderLayout puste panele
            }
            if(i == 1){
                panelTXT.add(panel1, BorderLayout.LINE_END);
            }
            if(i == 2){
                panelTXT.add(panel1, BorderLayout.PAGE_START);
            }
            if(i == 3){
                panelTXT.add(panel1, BorderLayout.PAGE_END);
            }
        }

        panelTXT.add(field, BorderLayout.CENTER); //dodanie textfieldu w pozycji centralnej panelu z layotem BorderLayout

        JPanel panel = new JPanel(); //stworzenie panelu, w którym będzie znajdował się panel z textfieldem i panel z przyciskami
        panel.setLayout(new GridLayout(2,0));
        panel.setOpaque(false);
        panel.add(panelTXT);
        buttons(); //przywołanie metody tworzącej przyciski
        panel.add(panelBUTTONS);
        mainLabel.add(panel);


    }

    /**
     * Rysuje przyciski zawierajace litery
     */
    private void buttons(){
        panelBUTTONS = new JPanel(); //panel, w którym będa przechwywane przyciski
        panelBUTTONS.setOpaque(false);
        JPanel panelB = new JPanel();
        panelB.setLayout(new GridBagLayout());
        panelB.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        List<String> strList = this.loadWords(this.file); //załadowanie do tablicy słów z podanego pliku
        word = strList.get(gameloop); //słowo z listy z pliku odpowiadajace danemu indeksowi
        trudneLitery = "żźóuch"; //litery, które zostaną dodane jako utrudnienie do możliwych przycisków

        String[] litery0 = word.split(""); //rozdzielenie slowa na litery
        for(String ignored :litery0){                   //usuwa powtarzające się litery, dzięki temu litery na przyciskach nie będa się powtarzały
            checkChar("ż");
            checkChar("ź");
            checkChar("ó");
            checkChar("u");
            checkChar("c");
            checkChar("h");
        }
        String word1 = word + trudneLitery; //łączy słowo z tablicy z dopasowanymi "trudniejszymi literami"
        String[] litery = word1.split("");  //rodzielenie słowa z trudnymi literami na litery
        List<String> characterArray = Arrays.asList(litery); //stworzenie tablicy liter

        buttonsArray = new JButton[characterArray.size()]; //stworzenie tablicy przycisków o długości takiej samej co ilość liter w słowie
        int count=0;
        Collections.shuffle((characterArray)); //pomieszanie kolejności liter

       for(int i=0; i <= characterArray.size()-1; i++){  //operacja powtarza się aż wszystkie litery zostaną wyczerpane

            FinalButton button = new FinalButton(); //tworzenie przycisku
            button.setButtonFeatures();        //zmiana wyglądu przycisków
            button.addActionListener(this);
            c.fill = GridBagConstraints.HORIZONTAL; //zmiana wielkości przycisku
            c.ipadx = 40;
            c.ipady = 40;
            buttonsArray[count++] = button; //dodanie do tablicy przycisku i zwiększenie indeksu dla kolejnego przycisku
            String x = litery[i]; //litera odpowiadająca danemu idneksowi w tablicy litery[]
            button.setText(""+x+""); //umieszczenie danej litery na przycisku
            panelB.add(button,c);       //zmiana rozmiaru przycisku
            panelBUTTONS.add(panelB); //dodanie orzycisku do panelu
        }
    }

    /**
     * Sprawdza czy slowo zawiera daną litere. W przypadku gdy litera jest obecna w slowie jej duplikat zostanie usuniety
     * @param c litera, ktorej obecnosc zostanie sprawdzona
     */
    private void checkChar(String c){
        if(word.contains(c)){
            trudneLitery = trudneLitery.replace(c,""); //jeśli string zawiera wskazana literę to zostaje ona usunięta
        }
    }

    /**
     * Rysuje timer, wynik punktowy, przycisk poworotu i zakonczenia gry
     */
    private void timerAndScore(){  //panel
        //---------timer-------//
        panelTnS = new JPanel();
        panelTnS.setLayout(new GridLayout(1,2));
        panelTnS.setOpaque(false);
        Timer1.wygladZegar(); //nadanie wygladu timera
        Timer1.start();  //rozpoczęcie pracy timera
        //--------score-------//
        score = new JLabel();
        score = new JLabel("Score:"+points, SwingConstants.CENTER);
        score.setFont(new Font("Rubik",Font.BOLD,25));
        score.setForeground(new Color(0x602A2A));
        //------przycisk powrotu do poprzedniej strony---//
        IconButton goBack = new IconButton();
        buttonBack = new FinalButton();
        buttonBack.addActionListener(this);
        buttonBack.setButtonFeatures();
        buttonBack.setFocusable(false);
        buttonBack.setIcon(goBack.iconDefault("B3.png")); //ustawienie ikony przycisku

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;  //zmiana rozmiaru przycisku
        c.ipadx = 70;
        c.ipady = 20;

        JPanel panelGB = new JPanel();
        panelGB.setLayout(new GridBagLayout());
        panelGB.setOpaque(false);
        panelGB.add(buttonBack,c);      //dodanie przycisku do panelu z layoutem przyjmującym zmianę rozmiarów

        //----przycisk końca gry----//
        IconButton endG = new IconButton();
        endGame = new FinalButton();
        endGame.addActionListener(this);
        endGame.setButtonFeatures();
        endGame.setFocusable(false);
        endGame.setIcon(endG.iconDefault("Y3.png")); //ustawienie ikony przycisku

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.HORIZONTAL; //zmiana rozmiaru przycisku
        c1.ipadx = 70;
        c1.ipady = 20;

        JPanel panelEG = new JPanel();
        panelEG.setLayout(new GridBagLayout());
        panelEG.setOpaque(false);
        panelEG.add(endGame,c1);  //dodanie przycisku do panelu z layoutem przyjmującym zmianę rozmiarów

        //----dodanie do paneli-----//
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4));
        panel.setOpaque(false);
        panel.add(score);
        panel.add(panelGB);
        panel.add(panelEG);
        panel.add(Timer1.timeLabel);
        panelTnS.add(panel);

    }

    /**
     * Rysuje przycisk backspace
     */
    private void line5(){
        //-----------stworzenie i dodanie przycisku backspace-------
        line5 = new JPanel();
        line5.setOpaque(false);
        buttonBackspace  = new FinalButton();
        buttonBackspace.setButtonFeatures();
        buttonBackspace.addActionListener(this);
        buttonBackspace.setText("<--");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;  //zmiana rozmiaru przycisku
        c.ipadx = 60;
        c.ipady = 20;

        panel.add(buttonBackspace,c);
        line5.add(panel);
    }

    /**
     * Rysuje przycisk zatwierdzajacy wpisane slowo
     */
    private void line6(){
    //-------------stworzenie i dodanie przycisku zatwierdzjącego słowo
        buttonDone  = new FinalButton();
        buttonDone.setButtonFeatures();
        buttonDone.addActionListener(this);
        buttonDone.setText("OK");

        line6 = new JPanel();
        line6.setOpaque(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; //zmiana rozmiarów przycisku
        c.ipadx = 100;
        c.ipady = 20;

        panel.add(buttonDone,c);
        line6.add(panel);
    }

    /**
     * Dodanie do jednego panelu elementow utworzonych w line5() , line6() i timerAndScore()
     */
    private void part3(){
        //------dodanie do jednego panelu 3 wierszy----//
        timerAndScore();
        line5();
        line6();
        JPanel panel = new JPanel(); //stworzenie panelu, w którym będą przechowywane 3 wiersze elementów
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(3,0));

        panel.add(line5); //dodanie przycisku backspace
        panel.add(line6); //dodanie przycisku zatwierdzjącego wpisane słowo
        panel.add(panelTnS); //dodanie timera i punktów
        mainLabel.add(panel);
    }

    /**
     * Polaczenie 3 pomniejszych paneli w calosc. W wyniku otrzymuje się pelny interfejs
     * @throws IOException
     */
    private void userInterface() throws IOException {
        //-----połączenie wszystkich elementów w całość i ich rysowanie -------
        part1();
        part2();
        part3();
    }

    /**
     * Przechowywanie slow z pliku txt w tablicy
     * @param file plik txt wyznaczany w momemncie wybrania poziomu
     * @return tablice słow
     */
    private List<String> loadWords(String file) {
       wordsList = Collections.emptyList();  //stworzenie pustej listy "wordslist"
     try {
        wordsList = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8); //wypełnienie listy słowami z pliku txt, każde kolejne słowo ma większy indeks
     } catch (IOException e) {
         e.printStackTrace();
     }
     return wordsList;
    }

    /**
     * Tworzy tablice zdjec o takiej samej dlugości co tablica slow
     * @return tablice zdjec
     * @throws IOException dla ImageIO.read()
     */
    private ArrayList<Image> loadImage() throws IOException {

        List<String> strList = this.loadWords(this.file); //stworzenie listy ze słowami dla danego poziomu
        ArrayList<Image> images = new ArrayList<>(); //stworzenie listy przechowującej zdjęcia
         for(int i=0; i <= strList.size()-1; i++ ) { //załadowanie do tablicy zdjęć tylu zdjęć ile jest słów dla danego poziomu
             images.add(ImageIO.read(new File(String.format(this.fileDirectory, i))));
         }
         return images;
    }

    /**
     * Ustala ilosc punktow za ulozenie dobrego slowa
     * @param amount ilosc punktow za kazda prawidlowa odpowiedz
     */
    public void setAmount(int amount){
        amountConstant += amount; //przypisanie ilości punktów dla danego poziomu
    }

    /**
     * Odświeza interfejs. Ustawia dane następnego slowa i usuwa poprzedniego
     */
    private void nextWord(){
        gameloop++; //zwiększenie indeksu następnego słowa i obrazka
        buffer = ""; //zapomnienie wpisywanego tekstu
        field.setText(""); //usunięcie poprzedniego słowa z textfieldu
        mainLabel.removeAll();  //usuwa stare komponenty dla poprzedniego slowa
    }

    /**
     * Zapisuje w pliku txt wynik punktowy i czas zakonczenia gry
     * @throws IOException dla BufferedWriter
     */
    private void getTimeAndScore() throws IOException {
        Timer1.stop(); //zatrzymanie timera
        String m = Timer1.minutes_string;
        String s = Timer1.seconds_string;
        String time = m.concat(":"+s); //czas w momencie zakończenia gry w formacie String

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("wyniki.txt")); //zapisanie wyników w pliku txt
            writer.write(time + ";" + points + ";");
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton jButton :buttonsArray) { //dla przycisków liter
            if (e.getSource() == jButton) {
                String litera = jButton.getText(); //uzyskanie litery przycisku
                buffer += litera; //dodanie litery do zmiennej String
                field.setText(buffer); //wypełnienie textfieldu wpisanymi literami z przycisków
                break;
            }
        }
        if (e.getSource() == buttonBackspace) {
            buffer = buffer.substring(0, buffer.length() - 1); //usunięcie ostatniej litery ze zmiennej String buffer
            field.setText("" + buffer); //wypełnienie textfieldu słowem bez usuniętych liter
        }
        if(e.getSource() == buttonDone){ //gdy użytkownik zatwierdzi wpisane słowo
            int pointsLvL = 0;
            String odpowiedz = field.getText(); //końcowe słowo użytkownika

            if(odpowiedz.equals(word)){ //sprawdzenie czy odpowiedz uzytkownika pokrywa się ze słowem podanym w pliku txt dla danej wartości int gameloop
                pointsLvL += amountConstant; //przypisanie wartości ilości punktów, które można otrzymać za każde słowo
                count++;
                points=pointsLvL*count; //liczenie punktów
                score.setText(""+points); //wyświetlanie wyniku punktowego w label score

                if(gameloop ==wordsList.size()-1){ //jeśli zostaną wyczerpane słowa
                    try {
                        getTimeAndScore(); //spisz do pliku czas i punkty
                        new ResultsWindow().createResWindow(); //wyświetl okno z wynikami

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    window.dispose(); //zamknij bierzące okno
                }
                nextWord(); //pobranie nastepnego słowa

                if(gameloop !=wordsList.size() ) {
                    try {
                        userInterface();   //wyswietla nowe slowo
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    window.revalidate();   //te dwie funkcje updateują okno, wyswietlaja sie nowe przyciski z literami i nowe zdjecie
                    window.repaint();
                }
            } else { //jeśli wprowadzone słowo jest nieprawidłowe użytkownik otrzymuje powiadomienie
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "Odpowiedź jest niepoprawna, spróbuj ponownie!!");
                   }
        }
    if(e.getSource() == buttonBack) //powrót do okna wyboru poziomów
    {
        new LevelWindow().createLvLWindow();
        window.dispose();
    }
    if(e.getSource() == endGame) //zakończenie gry
    {
        window.dispose();
    }

}
}


