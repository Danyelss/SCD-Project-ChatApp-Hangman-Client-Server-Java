package Interface;

import GameController.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class interfaceForm extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton sendMessageButton;
    private JButton sendLetterButton;
    private JEditorPane chatBox;
    private JEditorPane messageBox;
    private JEditorPane hangTextBox;
    private JEditorPane Word;
    private JEditorPane letterPane;
    private JLabel informationLabel;

    private GameController gameController;
    private boolean startGame;

    public interfaceForm(GameController gameController) {

        this.gameController = gameController;

        setContentPane(panel1);
        this.setTitle("Welcome");
        setSize(1000, 700);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ActionsListeners();

    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public void ActionsListeners() {

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                chatBox.setText(chatBox.getText() + "You: " + messageBox.getText() + "\n");

                gameController.fromChat(messageBox.getText()); // functia care trimite pe client / server

                messageBox.setText("");
            }
        });

        sendLetterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startGame == true) {
                    gameController.choseWord(Word.getText());
                    Word.setText("");
                    setStartGame(false);
                } else {
                    if (letterPane.getText().length() > 0) {

                        gameController.sendLetter(letterPane.getText()); // functia care trimite pe client / server
                    }
                }

                letterPane.setText("");

            }
        });
    }

    public void textToChat(String string) {

        chatBox.setText(chatBox.getText() + "Opponent: " + string + "\n");
    }

    public void setToHangText(String string) {
        hangTextBox.setText(string);
    }

    public void setInformationLabel(String string) {
        informationLabel.setText(string);
    }

    public void setToWord(String string) {
        Word.setText(string.substring(0, string.length()));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /*
                   // receiver - client or server - object type - can receive either one?


        Cand apesi pe button send message, sa avem o functie care sa ne returneze ce e in textul scris de utilizator
        si sa goleasca ce e in textfield

        trimite textul la client / server

        iti trebuie o functie pe server / client care sa receptioneze mesajul si sa il trimita pe portul deschis
     -------------

        O functie care adauga text in fieldul de message de la useri
        string + message from user -> dispaly on field ( la margine dai \n )

        void care doar scrie ce primeste de la client / server
    ------------------

        Faci stringuri cu pasii de spanzuratoare

        in alt fisier pui stringurile si ti le returnezi la request

        tip enumerare ( enum ) sau vector


    ------------------
        functie de scris cuvant

        string pe care il scri in textfield-ul pentru cuvant

        functie void care e apelata de server / client si retine stringul de mai sus

    --------------------

        primul joc scrie cuvantul serverul si da send letter - caz special, nu poate sa scrie mai multe litere in cazul in care jocul e inceput

        dupa ce jocul s-a terminat, e randul clientului, ai o variabila care tine minte - si se schimba dupa fiecare end game

   ---------------------

        functie send letter

        care trimite o litera la verificare

  -----------------------

        o functie care verifica daca in cuvant este litera scrisa

        returneaza true sau false

    --------------------

        o functie care

            daca exista litera: atunci afiseaza litera in fieldul de cuvant

            daca nu exista litera: avanseaza cu spanzuratoarea:
                        - numar de greseli, daca ajunge la el, eng game
                        - 0 - no mistake
                        - 1 -
                        functie(parametru nr greseala) - returneaza din array stringul

    --------------------

            eng game care reface tot ca la inceput

            si schimba jucatorii

    --------------------

        functie care porneste interfata pentru fiecare jucator

    --------------------
     */
}
