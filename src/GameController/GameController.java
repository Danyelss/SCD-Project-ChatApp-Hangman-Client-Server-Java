package GameController;

import Client.Client;
import Interface.interfaceForm;
import Server.Server;

public class GameController {

    private Server server;
    private Client client;
    private String type = "none";
    private int mistakeNumber = 0;
    private String word = "";
    private String secretWord = "";
    private boolean giveWord;
    private int yourScore = 0;
    private int opponentScore = 0;

    private interfaceForm myFrame;

    private String letters = "";

    public GameController(Server server) {
        this.type = "server";
        this.server = server;

        this.myFrame = new interfaceForm(this);
        this.giveWord = false;
        newGame();
    }

    public GameController(Client client) {
        this.type = "client";
        this.client = client;

        this.myFrame = new interfaceForm(this);
        this.giveWord = true;
        newGame();
    }

    private void decode(String string) {
        switch (string.charAt(0)) {
            case '@':
                toChat(string.substring(1));
                break;
            case '#':
                if (checkLetterInWord((string.toUpperCase()).charAt(1))) {
                    if (giveWord == false) {
                        myFrame.setInformationLabel("Give a letter!");
                    } else {
                        myFrame.setInformationLabel("Wait for the other one to guess your word.");
                    }
                    myFrame.setToWord(secretWord);

                } else {
                    displayMistake(getMistake());
                    sendData("$");
                }

                letterGiven((string.toUpperCase()).charAt(1));

                checkEnd(secretWord);

                break;
            case '$':
                displayMistake(getMistake());

                checkEnd(secretWord);

                break;
            case '%':
                secretWord = string.substring(1);

                if (giveWord == false) {
                    myFrame.setInformationLabel("Give a letter!");
                } else {
                    myFrame.setInformationLabel("Wait for the other one to guess your word.");
                }
                myFrame.setToWord(secretWord);

                checkEnd(secretWord);
                break;
            case '!':
                secretWord = string.substring(1);

                if (giveWord == false) {
                    myFrame.setInformationLabel("Give a letter!");
                } else {
                    myFrame.setInformationLabel("Wait for the other one to guess your word.");
                }
                myFrame.setToWord(secretWord);
                break;
        }
    }

    // data sending logic

    private void sendData(String string) {
        switch (type) {
            case "server":
                server.getSender().transmitData(string);
                break;
            case "client":
                client.getSender().transmitData(string);
                break;
        }
    }

    public void getData(String string) {
        decode(string);
    }

    public void fromChat(String string) {
        sendData("@" + string);
    }

    private void toChat(String string) {
        myFrame.textToChat(string);
    }

    public void sendLetter(String string) {
        sendData("#" + string.charAt(0));
    }


    // game logic

    public void letterGiven(Character character) {
        this.letters += ' ';
        this.letters += character;

        myFrame.setInformationGivenLetter(letters);
    }

    // new game
    private void newGame() {
        this.giveWord = !giveWord;
        word = "";
        secretWord = "";
        mistakeNumber = -1;
        displayMistake(getMistake());
        if (giveWord == true) {
            myFrame.setStartGame(true);
            myFrame.setInformationLabel("New game! Choose a word:");
        } else {
            myFrame.setInformationLabel("New game! Wait for a word...");
            myFrame.setStartGame(false);
        }

        System.out.println(this.type + " - " + giveWord);

    }

    public void choseWord(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        this.word = string;

        for (int i = 0; i < word.length(); i++) {
            secretWord = secretWord.substring(0, i) + '_';
        }

        sendData("!" + secretWord);
        decode("!" + secretWord);
    }

    // end game

    private void checkEnd(String string) {
        System.out.println("Called");

        if (!string.contains(String.valueOf('_'))) {

            if (giveWord == true) {
                this.opponentScore++;
            } else {
                this.yourScore++;
            }

            myFrame.setInformationLabel("Word found! Score: You: " + this.yourScore + " - Opponent: " + this.opponentScore);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myFrame.setToWord("");

            newGame();
        } else if (mistakeNumber > 5) {
            if (giveWord == true) {
                this.yourScore++;
            } else {
                this.opponentScore++;
            }

            myFrame.setInformationLabel("Word found! Score: You: " + this.yourScore + " - Opponent: " + this.opponentScore);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myFrame.setToWord("");

            newGame();
        }
    }


    private void letterExist(char letter) {

        for (int i = 0; i < word.length(); i++) {
            if (word.toUpperCase().charAt(i) == letter) {
                secretWord = secretWord.substring(0, i) + letter + secretWord.substring(i + 1);
            }
        }

        sendData("%" + secretWord);
    }

    private boolean checkLetterInWord(char letter) {
        if (word.toUpperCase().contains(String.valueOf(letter))) {
            letterExist(letter);
            return true;
        } else {
            return false;
        }
    }

    private void displayMistake(String string) {
        myFrame.setToHangText(string);
        myFrame.setInformationLabel("Wrong letter!!! :(");
    }

    public String getMistake() {
        mistakeNumber++;

        switch (mistakeNumber) {
            case 0:
                return noPersonDraw();
            case 1:
                return addHeadDraw();
            case 2:
                return addBodyDraw();
            case 3:
                return addOaneArmDraw();
            case 4:
                return addSecondArmDraw();
            case 5:
                return addFirstLegDraw();
            default:
                return fullPersonDraw();

        }
    }

    private String noPersonDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        \n" +
                "|       \n" +
                "|        \n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addHeadDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|       \n" +
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }

    public String addBodyDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|        |  \n" +
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addOaneArmDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|        | \\ \n" +
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addSecondArmDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|      / | \\ \n" +
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addFirstLegDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|      / | \\ \n" +
                "|        |\n" +
                "|         \\ \n" +
                "|\n" +
                "|\n";
    }

    public String fullPersonDraw() {
        return " - - - - -\n" +
                "|        |\n" +
                "|        O\n" +
                "|      / | \\ \n" +
                "|        |\n" +
                "|       / \\ \n" +
                "|\n" +
                "|\n";
    }

}
