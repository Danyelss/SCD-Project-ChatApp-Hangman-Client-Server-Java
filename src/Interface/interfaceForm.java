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
    private JLabel GivenLetter;


    private GameController gameController;
    private boolean startGame;

    public interfaceForm(GameController gameController) {

        this.gameController = gameController;

        setContentPane(panel1);
        this.setTitle("Welcome");
        setSize(1000, 800);

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

    public void setInformationGivenLetter(String string) {

        GivenLetter.setText("Given letter: " + string);
    }

    public void setToWord(String string) {
        Word.setText(string.substring(0, string.length()));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}